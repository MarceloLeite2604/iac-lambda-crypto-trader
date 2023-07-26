package com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild;

import com.github.marceloleite2604.iaclambdacryptotrader.Constants;
import com.github.marceloleite2604.iaclambdacryptotrader.pipeline.stage.codebuild.statement.PolicyStatementCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeBuildRoleCreator {

  private final Collection<PolicyStatementCreator> policyStatementCreators;

  public Role create(Stack stack) {

    final var managedPolicy = createManagedPolicy(stack);

    final var servicePrincipal = new ServicePrincipal("codebuild.amazonaws.com");
    
    return Role.Builder.create(stack, "CodeBuildRole")
      .roleName(Constants.PROJECT_NAME + "-code-build-role")
      .description("Crypto Trader Code Builder role")
      .managedPolicies(List.of(managedPolicy))
      .assumedBy(servicePrincipal)
      .build();
  }

  @NotNull
  private ManagedPolicy createManagedPolicy(Stack stack) {

    final var statements = policyStatementCreators.stream()
      .map(creator -> creator.create(stack))
      .toList();

    return ManagedPolicy.Builder.create(stack, "CodeBuildSamLambdaPolicy")
      .managedPolicyName("CodeBuildSamCryptoTrader")
      .statements(statements)
      .build();
  }
}
