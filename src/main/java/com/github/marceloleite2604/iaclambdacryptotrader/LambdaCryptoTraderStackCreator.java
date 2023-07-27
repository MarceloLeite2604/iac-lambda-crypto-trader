package com.github.marceloleite2604.iaclambdacryptotrader;

import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.PipelineCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.constructs.Construct;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LambdaCryptoTraderStackCreator {

  private final BucketCreator bucketCreator;

  private final PipelineCreator pipelineCreator;

  public void create(Construct scope,
                     String id,
                     StackProps props) {

    final var stack = new Stack(scope, id, props);

    final var sourceArtifact = new Artifact(Constants.ProjectName.CAMEL_CASE + "SourceArtifact");

    var context = Context.builder()
      .stack(stack)
      .sourceArtifact(sourceArtifact)
      .build();

    context = bucketCreator.create(context);

    context = pipelineCreator.create(context);
  }
}
