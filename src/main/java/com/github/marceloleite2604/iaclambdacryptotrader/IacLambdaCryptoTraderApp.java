package com.github.marceloleite2604.iaclambdacryptotrader;

import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.PipelineCreator;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.SourceStageCreator;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.CodeBuildProjectCreator;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.CodeBuildRoleCreator;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.BuildStageCreator;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement.*;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.List;

public class IacLambdaCryptoTraderApp {
  public static void main(final String[] args) {

    final var readSsmParametersPolicyStatementCreator = new ReadSsmParametersPolicyStatementCreator();

    final var manageCloudFormationSamStackPolicyStatementCreator = new ManageCloudFormationSamStackPolicyStatementCreator();

    final var manageRolesPolicyStatementCreator = new ManageRolesPolicyStatementCreator();

    final var manageApplicationsPolicyStatementCreator = new ManageApplicationsPolicyStatementCreator();

    final var manageDeploymentGroupsPolicyStatementCreator = new ManageDeploymentGroupsPolicyStatementCreator();

    final var manageCloudFormationStackPolicyStatementCreator = new ManageCloudFormationStackPolicyStatementCreator();

    final var manageLambdaFunctionsPolicyStatementCreator = new ManageLambdaFunctionsPolicyStatementCreator();

    final var manageEventsPolicyStatementCreator = new ManageEventsPolicyStatementCreator();

    final var policyStatementCreators = List.of(
      readSsmParametersPolicyStatementCreator,
      manageCloudFormationSamStackPolicyStatementCreator,
      manageRolesPolicyStatementCreator,
      manageApplicationsPolicyStatementCreator,
      manageDeploymentGroupsPolicyStatementCreator,
      manageCloudFormationStackPolicyStatementCreator,
      manageLambdaFunctionsPolicyStatementCreator,
      manageEventsPolicyStatementCreator);

    final var codeBuildRoleCreator = CodeBuildRoleCreator.builder()
      .policyStatementCreators(policyStatementCreators)
      .build();

    final var codeBuildProjectCreator = CodeBuildProjectCreator.builder()
      .codeBuildRoleCreator(codeBuildRoleCreator)
      .build();

    final var codeBuildStageCreator = BuildStageCreator.builder()
      .codeBuildProjectCreator(codeBuildProjectCreator)
      .build();

    final var sourceStageCreator = SourceStageCreator.builder()
      .build();

    final var stageCreators = List.of(
      sourceStageCreator,
      codeBuildStageCreator);

    final var pipelineCreator = PipelineCreator.builder()
      .stageCreators(stageCreators)
      .build();

    final var bucketCreator = new BucketCreator();

    final var lambdaCryptoTraderStackCreator = LambdaCryptoTraderStackCreator.builder()
      .bucketCreator(bucketCreator)
      .pipelineCreator(pipelineCreator)
      .build();

    final var app = new App();

    final var environment = Environment.builder()
      .account("428099217226")
      .region("sa-east-1")
      .build();

    lambdaCryptoTraderStackCreator.create(app, Constants.ProjectName.CAMEL_CASE + "PipelineStack", environment);

    app.synth();
  }
}

