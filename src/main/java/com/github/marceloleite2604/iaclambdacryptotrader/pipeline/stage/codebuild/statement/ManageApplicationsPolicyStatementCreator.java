package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageApplicationsPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var applicationsArn = String.format("arn:aws:codedeploy:%s:%s:application:%s-*",
      stack.getRegion(),
      stack.getAccount(),
      Constants.Sam.STACK_NAME);

    return PolicyStatement.Builder.create()
      .sid("ManageApplications")
      .effect(Effect.ALLOW)
      .actions(List.of(
        "codedeploy:CreateApplication",
        "codedeploy:DeleteApplication"))
      .resources(List.of(applicationsArn))
      .build();
  }
}
