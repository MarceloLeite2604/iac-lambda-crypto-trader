package com.github.marceloleite2604.iaclambdacryptotrader.pipeline;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import com.github.marceloleite2604.iaclambdacryptotrader.Context;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.StageCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import software.amazon.awscdk.services.codepipeline.Pipeline;
import software.amazon.awscdk.services.codepipeline.StageProps;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PipelineCreator {

  private final List<StageCreator> stageCreators;

  public Context create(Context context) {

    final var stages = createStages(context);

    final var pipeline = Pipeline.Builder.create(context.getStack(), "Pipeline")
      .pipelineName(Constants.ProjectName.CAMEL_CASE)
      .artifactBucket(context.getBucket())
      .restartExecutionOnUpdate(true)
      .stages(stages)
      .build();

    return context.toBuilder()
      .pipeline(pipeline)
      .build();
  }

  private List<StageProps> createStages(Context context) {
    return stageCreators.stream()
      .sorted()
      .map(stageCreator -> stageCreator.create(context))
      .toList();
  }
}
