package com.github.marceloleite2604.iaclambdacryptotrader;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final String ACCOUNT_NAME = "marceloleite2604-aws-v1";

  @UtilityClass
  public static final class ProjectName {
    public static final String CAMEL_CASE = "CryptoTrader";
    public static final String SPACED = "Crypto Trader";
    public static final String KEBAB = "crypto-trader";
  }

  @UtilityClass
  public static final class GitHub {
    public static final String OWNER = "MarceloLeite2604";
    public static final String BRANCH = "main";
    public static final String REPOSITORY = "lambda-crypto-trader";
    public static final String CONNECTION_ARN = "arn:aws:codestar-connections:sa-east-1:428099217226:connection/f808a8c2-a768-41ea-ac45-6d00bf8a0e99";
  }

  @UtilityClass
  public static final class Sam {
    public static final String STACK_NAME = "SamCryptoTrader";
  }
}
