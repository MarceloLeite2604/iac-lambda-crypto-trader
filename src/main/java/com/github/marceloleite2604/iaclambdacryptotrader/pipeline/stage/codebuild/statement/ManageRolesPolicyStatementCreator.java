package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageRolesPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {

    final var rolesArn = String.format("arn:aws:iam::%s:role/sam-lambda-crypto-trader-*", stack.getAccount());

    return PolicyStatement.Builder.create()
      .sid("ManageRoles")
      .effect(Effect.ALLOW)
      .actions(List.of(
        "iam:CreateRole",
        "iam:DeleteRole",
        "iam:GetRole",
        "iam:TagRole",
        "iam:PassRole",
        "iam:AttachRolePolicy",
        "iam:DetachRolePolicy"))
      .resources(List.of(rolesArn))
      .build();
  }
}
