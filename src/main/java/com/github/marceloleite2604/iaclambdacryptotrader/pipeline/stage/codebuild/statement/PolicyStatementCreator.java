package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.PolicyStatement;

public interface PolicyStatementCreator {

  PolicyStatement create(Stack stack);
}
