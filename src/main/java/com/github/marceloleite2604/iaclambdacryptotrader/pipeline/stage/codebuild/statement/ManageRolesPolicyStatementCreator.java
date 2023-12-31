package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageRolesPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {

    final var rolesArn = String.format("arn:aws:iam::%s:role/%s-*", stack.getAccount(), Constants.Sam.STACK_NAME);

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
