package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageEventsPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var functionsArn = String.format("arn:aws:events:%s:%s:rule/%s-*",
      stack.getRegion(),
      stack.getAccount(),
      Constants.Sam.STACK_NAME);

    return PolicyStatement.Builder.create()
      .sid("ManageEvents")
      .effect(Effect.ALLOW)
      .actions(List.of(
        "events:PutRule",
        "events:DescribeRule",
        "events:DeleteRule",
        "events:RemoveTargets",
        "events:PutTargets"))
      .resources(List.of(functionsArn))
      .build();
  }
}
