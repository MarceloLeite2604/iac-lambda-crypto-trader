package com.github.marceloleite2604.iaclambdacryptotrader;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class IacLambdaCryptoTraderApp {
  public static void main(final String[] args) {
    App app = new App();

    new IacLambdaCryptoTraderStack(app, "LambdaCryptoTraderStack", StackProps.builder()
      .env(Environment.builder()
        .account("428099217226")
        .region("sa-east-1")
        .build())
      .build());

    app.synth();
  }
}

