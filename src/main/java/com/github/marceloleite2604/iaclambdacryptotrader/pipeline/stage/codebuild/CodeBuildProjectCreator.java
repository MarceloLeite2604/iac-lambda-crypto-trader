package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import com.github.marceloleite2604.iaclambdacryptotrader.Context;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.codebuild.*;
import software.amazon.awscdk.services.s3.Bucket;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeBuildProjectCreator {

  private final CodeBuildRoleCreator codeBuildRoleCreator;

  public PipelineProject create(Context context) {

    final var environment = createEnvironment();

    final var codeBuildRole = codeBuildRoleCreator.create(context.getStack());

    final var cache = createCache(context.getBucket());

    return PipelineProject.Builder.create(context.getStack(), "CodeBuild")
      .projectName(Constants.PROJECT_NAME)
      .description("Builds Crypto Trader Lambda using SAM.")
      .environment(environment)
      .queuedTimeout(Duration.minutes(10))
      .timeout(Duration.minutes(10))
      .role(codeBuildRole)
      .cache(cache)
      .build();
  }

  @NotNull
  private Cache createCache(Bucket bucket) {
    final var bucketCacheOptions = BucketCacheOptions.builder()
      .prefix("crypto-trader/cache")
      .build();

    return Cache.bucket(bucket, bucketCacheOptions);
  }

  @NotNull
  private BuildEnvironment createEnvironment() {
    return BuildEnvironment.builder()
      .buildImage(LinuxBuildImage.STANDARD_7_0)
      .computeType(ComputeType.SMALL)
      .build();
  }
}
