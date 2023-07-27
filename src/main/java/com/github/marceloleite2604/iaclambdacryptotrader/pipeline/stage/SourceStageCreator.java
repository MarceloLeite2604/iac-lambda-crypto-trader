package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import com.github.marceloleite2604.iaclambdacryptotrader.Context;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import software.amazon.awscdk.services.codepipeline.StageProps;
import software.amazon.awscdk.services.codepipeline.actions.CodeStarConnectionsSourceAction;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceStageCreator implements StageCreator {

  private static final int STAGE_ORDER = 1;

  public StageProps create(Context context) {

    final var codeStarConnectionsSourceAction = CodeStarConnectionsSourceAction.Builder.create()
      .actionName("RetrieveSourceFromGitHub")
      .owner(Constants.GitHub.OWNER)
      .repo(Constants.GitHub.REPOSITORY)
      .branch(Constants.GitHub.BRANCH)
      .connectionArn(Constants.GitHub.CONNECTION_ARN)
      .output(context.getSourceArtifact())
      .runOrder(1)
      .triggerOnPush(true)
      .build();

    return StageProps.builder()
      .stageName("Source")
      .actions(List.of(codeStarConnectionsSourceAction))
      .build();
  }

  @Override
  public int getStageOrder() {
    return STAGE_ORDER;
  }
}
