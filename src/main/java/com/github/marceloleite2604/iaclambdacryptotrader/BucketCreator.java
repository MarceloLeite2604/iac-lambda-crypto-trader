package com.github.marceloleite2604.iaclambdacryptotrader;


import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.StarPrincipal;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;

import java.util.List;
import java.util.Map;

public class BucketCreator {

  public Context create(Context context) {

    final var bucket = Bucket.Builder.create(context.getStack(), "Bucket")
      .bucketName(Constants.ACCOUNT_NAME + "-lambdas")
      .versioned(true)
      .encryption(BucketEncryption.S3_MANAGED)
      .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
      .build();

    final PolicyStatement denyInsecureConnectionsPolicyStatement = createPolicyStatement(bucket);

    bucket.addToResourcePolicy(denyInsecureConnectionsPolicyStatement);

    return context.toBuilder()
      .bucket(bucket)
      .build();
  }

  @NotNull
  private PolicyStatement createPolicyStatement(Bucket bucket) {
    return PolicyStatement.Builder.create()
      .sid("DenyInsecureConnections")
      .effect(Effect.DENY)
      .principals(List.of(new StarPrincipal()))
      .actions(List.of("s3:*"))
      .resources(List.of(bucket.arnForObjects("*")))
      .conditions(Map.of("Bool", Map.of("aws:SecureTransport", "false")))
      .build();
  }
}
