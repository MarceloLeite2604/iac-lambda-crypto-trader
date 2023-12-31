package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageLambdaFunctionsPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var functionsArn = String.format("arn:aws:lambda:%s:%s:function:%s-*",
      stack.getRegion(),
      stack.getAccount(),
      Constants.Sam.STACK_NAME);

    return PolicyStatement.Builder.create()
      .sid("ManageFunctions")
      .effect(Effect.ALLOW)
      .actions(List.of(
        "lambda:CreateFunction",
        "lambda:DeleteFunction",
        "lambda:TagResource",
        "lambda:GetFunction",
        "lambda:ListVersionsByFunction",
        "lambda:PublishVersion",
        "lambda:CreateAlias",
        "lambda:DeleteAlias",
        "lambda:AddPermission",
        "lambda:RemovePermission"))
      .resources(List.of(functionsArn))
      .build();
  }
}
