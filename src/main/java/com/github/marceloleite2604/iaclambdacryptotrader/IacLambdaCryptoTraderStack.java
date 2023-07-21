package com.github.marceloleite2604.iaclambdacryptotrader;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.codebuild.*;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.amazon.awscdk.services.codepipeline.Pipeline;
import software.amazon.awscdk.services.codepipeline.StageProps;
import software.amazon.awscdk.services.codepipeline.actions.*;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.StarPrincipal;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class IacLambdaCryptoTraderStack extends Stack {

  public IacLambdaCryptoTraderStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    final var lambdasBucket = createLambdasBucket();

    final var sourceArtifact = new Artifact("lambda-crypto-trader-source");

    final var codeStarConnectionsSourceAction = CodeStarConnectionsSourceAction.Builder.create()
      .actionName("retrieve-source-from-github")
      .owner("marceloleite2604")
      .repo(Constants.GITHUB_REPO_NAME)
      .branch("main")
      .connectionArn("arn:aws:codestar-connections:sa-east-1:428099217226:connection/f808a8c2-a768-41ea-ac45-6d00bf8a0e99")
      .output(sourceArtifact)
      .runOrder(1)
      .triggerOnPush(true)
      .build();

    final var sourceStage = StageProps.builder()
      .stageName("source")
      .actions(List.of(codeStarConnectionsSourceAction))
      .build();

    final var buildEnvironment = BuildEnvironment.builder()
      .buildImage(LinuxBuildImage.STANDARD_7_0)
      .computeType(ComputeType.SMALL)
      .build();

    final var codeBuildProject = PipelineProject.Builder.create(this, "CodeBuild")
      .projectName(Constants.PROJECT_NAME)
      .description("Builds Crypto Trader Lambda using SAM.")
      .environment(buildEnvironment)
      .queuedTimeout(Duration.minutes(10))
      .timeout(Duration.minutes(10))
      .cache(Cache.bucket(lambdasBucket, BucketCacheOptions.builder()
        .prefix("crypto-trader/cache")
        .build()))
      .build();

    final var codeBuildAction = CodeBuildAction.Builder.create()
      .actionName("build-project")
      .type(CodeBuildActionType.BUILD)
      .project(codeBuildProject)
      .input(sourceArtifact)
      .runOrder(1)
      .build();

    final var buildStage = StageProps.builder()
      .stageName("build")
      .actions(List.of(codeBuildAction))
      .build();

    Pipeline.Builder.create(this, "Pipeline")
      .pipelineName("lambda-crypto-trader")
      .artifactBucket(lambdasBucket)
      .restartExecutionOnUpdate(true)
      .stages(
        List.of(sourceStage, buildStage)
      )
      .build();
  }

  private Bucket createLambdasBucket() {

    final var lambdasBucket = Bucket.Builder.create(this, "Bucket")
      .bucketName(Constants.ACCOUNT_NAME + "-lambdas")
      .versioned(true)
      .encryption(BucketEncryption.S3_MANAGED)
      .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
      .build();

    final var denyInsecureConnectionsPolicyStatement = PolicyStatement.Builder.create()
      .sid("DenyInsecureConnections")
      .effect(Effect.DENY)
      .principals(List.of(new StarPrincipal()))
      .actions(List.of("s3:*"))
      .resources(List.of(lambdasBucket.arnForObjects("*")))
      .conditions(Map.of("Bool", Map.of("aws:SecureTransport", "false")))
      .build();

    lambdasBucket.addToResourcePolicy(denyInsecureConnectionsPolicyStatement);

    return lambdasBucket;
  }
}
