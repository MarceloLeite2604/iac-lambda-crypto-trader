package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ReadSsmParametersPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var ssmParametersResourceArn = String.format("arn:aws:ssm:%s:%s:parameter/lambda-crypto-trader/*", stack.getRegion(), stack.getAccount());

    return PolicyStatement.Builder.create()
      .sid("ReadSsmParameters")
      .effect(Effect.ALLOW)
      .actions(List.of("ssm:GetParameters"))
      .resources(List.of(ssmParametersResourceArn))
      .build();
  }
}
