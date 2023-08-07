package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageCloudFormationStackPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var cloudFormationStackArn = String.format("arn:aws:cloudformation:%s:%s:stack/%s/*",
      stack.getRegion(),
      stack.getAccount(),
      Constants.Sam.STACK_NAME);

    return PolicyStatement.Builder.create()
      .sid("ManageCloudFormationStackStatement")
      .effect(Effect.ALLOW)
      .actions(List.of(
        "cloudformation:DescribeStacks",
        "cloudformation:CreateChangeSet",
        "cloudformation:DescribeChangeSet",
        "cloudformation:ExecuteChangeSet",
        "cloudformation:DescribeStackEvents",
        "cloudformation:GetTemplateSummary"))
      .resources(List.of(cloudFormationStackArn))
      .build();
  }
}
