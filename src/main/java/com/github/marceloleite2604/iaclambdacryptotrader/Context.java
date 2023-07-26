package com.github.marceloleite2604.iaclambdacryptotrader;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.codepipeline.Artifact;
import software.amazon.awscdk.services.s3.Bucket;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Context {

  private final Stack stack;

  private final Bucket bucket;

  private final Artifact sourceArtifact;
}
