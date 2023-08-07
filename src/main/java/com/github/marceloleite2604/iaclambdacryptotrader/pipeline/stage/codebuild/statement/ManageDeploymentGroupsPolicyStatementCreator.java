package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageDeploymentGroupsPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var deploymentGroupsArn = String.format("arn:aws:codedeploy:%s:%s:deploymentgroup:%s-*",
      stack.getRegion(),
      stack.getAccount(),
      Constants.Sam.STACK_NAME);

    return PolicyStatement.Builder.create()
      .sid("ManageDeploymentGroups")
      .effect(Effect.ALLOW)
      .actions(List.of(
        "codedeploy:CreateDeploymentGroup",
        "codedeploy:UpdateDeploymentGroup",
        "codedeploy:DeleteDeploymentGroup",
        "codedeploy:GetDeploymentGroup"
      ))
      .resources(List.of(deploymentGroupsArn))
      .build();
  }
}
