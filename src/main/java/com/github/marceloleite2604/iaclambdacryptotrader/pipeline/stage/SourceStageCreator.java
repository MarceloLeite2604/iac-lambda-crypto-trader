package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import com.github.marceloleite2604.iaclambdacryptotrader.Context;
import lombok.*;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.amazon.awscdk.services.codepipeline.StageProps;
import software.amazon.awscdk.services.codepipeline.actions.CodeStarConnectionsSourceAction;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceStageCreator implements StageCreator {

  private static final int STAGE_ORDER = 1;

  public StageProps create(Context context) {

    final var codeStarConnectionsSourceAction = CodeStarConnectionsSourceAction.Builder.create()
      .actionName("retrieve-source-from-github")
      .owner("MarceloLeite2604")
      .repo(Constants.GITHUB_REPO_NAME)
      .branch("main")
      .connectionArn("arn:aws:codestar-connections:sa-east-1:428099217226:connection/f808a8c2-a768-41ea-ac45-6d00bf8a0e99")
      .output(context.getSourceArtifact())
      .runOrder(1)
      .triggerOnPush(true)
      .build();

    return StageProps.builder()
      .stageName("source")
      .actions(List.of(codeStarConnectionsSourceAction))
      .build();
  }

  @Override
  public int getStageOrder() {
    return STAGE_ORDER;
  }
}
