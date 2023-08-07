package com.github.marceloleite2604.iaclambdacryptotrader;

import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.PipelineCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.constructs.Construct;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LambdaCryptoTraderStackCreator {

  private final BucketCreator bucketCreator;

  private final PipelineCreator pipelineCreator;

  public Context create(Construct scope,
                        String id,
                        Environment environment) {

    final var description = String.format("Pipeline stack for %s project.", Constants.ProjectName.SPACED);

    final var stack = Stack.Builder.create(scope, id)
      .description(description)
      .env(environment)
      .build();

    final var sourceArtifact = new Artifact(Constants.ProjectName.CAMEL_CASE + "SourceArtifact");

    var context = Context.builder()
      .stack(stack)
      .sourceArtifact(sourceArtifact)
      .build();

    context = bucketCreator.create(context);

    return pipelineCreator.create(context);
  }
}
