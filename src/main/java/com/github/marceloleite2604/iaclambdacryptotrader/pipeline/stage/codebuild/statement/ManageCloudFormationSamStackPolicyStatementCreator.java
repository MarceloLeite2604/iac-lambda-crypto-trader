package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

import java.util.List;

public class ManageCloudFormationSamStackPolicyStatementCreator implements PolicyStatementCreator {
  @Override
  public PolicyStatement create(Stack stack) {
    final var cloudFormationSamStackArn = String.format("arn:aws:cloudformation:%s:aws:transform/Serverless-2016-10-31", stack.getRegion());

    return PolicyStatement.Builder.create()
      .sid("ManageCloudFormationSamStack")
      .effect(Effect.ALLOW)
      .actions(List.of("cloudformation:CreateChangeSet"))
      .resources(List.of(cloudFormationSamStackArn))
      .build();
  }
}
