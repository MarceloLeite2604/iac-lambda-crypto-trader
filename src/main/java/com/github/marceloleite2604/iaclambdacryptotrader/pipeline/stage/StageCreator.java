package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage;

import com.github.marceloleite2604.iaclambdacryptotrader.Context;
import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.services.codepipeline.StageProps;

public interface StageCreator extends Comparable<StageCreator> {

  StageProps create(Context context);

  int getStageOrder();


  @Override
  default int compareTo(@NotNull StageCreator other) {
    return Integer.compare(getStageOrder(), other.getStageOrder());
  }
}
