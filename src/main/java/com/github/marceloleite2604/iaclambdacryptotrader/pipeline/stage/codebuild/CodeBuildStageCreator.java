package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild;

import com.github.marceloleite2604.iaclambdacryptotrader.Context;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.StageCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import software.amazon.awscdk.services.codepipeline.StageProps;
import software.amazon.awscdk.services.codepipeline.actions.CodeBuildAction;
import software.amazon.awscdk.services.codepipeline.actions.CodeBuildActionType;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeBuildStageCreator implements StageCreator {

  private static final int STAGE_ORDER = 2;

  private final CodeBuildProjectCreator codeBuildProjectCreator;

  public StageProps create(Context context) {

    final var project = codeBuildProjectCreator.create(context);

    final var codeBuildAction = CodeBuildAction.Builder.create()
      .actionName("build-project")
      .type(CodeBuildActionType.BUILD)
      .project(project)
      .input(context.getSourceArtifact())
      .runOrder(1)
      .build();

    return StageProps.builder()
      .stageName("build")
      .actions(List.of(codeBuildAction))
      .build();
  }

  @Override
  public int getStageOrder() {
    return STAGE_ORDER;
  }
}
