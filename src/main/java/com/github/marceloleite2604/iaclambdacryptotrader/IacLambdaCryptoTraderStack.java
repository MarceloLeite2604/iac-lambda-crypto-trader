package com.github.marceloleite2604.iaclambdacryptotrader;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.StarPrincipal;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class IacLambdaCryptoTraderStack extends Stack {

  public IacLambdaCryptoTraderStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    final var lambdaBucketName = Constants.ACCOUNT_NAME + "-lambdas";

    final var lambdasBucket = Bucket.Builder.create(this, lambdaBucketName)
      .versioned(true)
      .encryption(BucketEncryption.S3_MANAGED)
      .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
      .build();

    final var denyInsecureConnectionsPolicyStatement = PolicyStatement.Builder.create()
      .sid("DenyInsecureConnections")
      .effect(Effect.DENY)
      .principals(List.of(new StarPrincipal()))
      .actions(List.of("s3:*"))
      .resources(List.of(lambdasBucket.arnForObjects("*")))
      .conditions(Map.of("Bool", Map.of("aws:SecureTransport", "false")))
      .build();

    lambdasBucket.addToResourcePolicy(denyInsecureConnectionsPolicyStatement);
  }
}
