/*
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may
 * not use this file except in compliance with the License. A copy of the
 * License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.blox.frontend.integration;

import com.amazonaws.blox.dataservicemodel.v1.client.DataService;
import com.amazonaws.blox.dataservicemodel.v1.model.EnvironmentId;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationTestConfig.class)
@WebAppConfiguration
@TestExecutionListeners(
  inheritListeners = false,
  listeners = {DependencyInjectionTestExecutionListener.class}
)
public abstract class IntegrationTestBase {

  protected static final String ACCOUNT_ID = "123456789012";
  protected static final String CLUSTER_NAME = "myCluster";
  protected static final String ENVIRONMENT_NAME = "myEnv";

  protected EnvironmentId environmentId;

  @Autowired protected SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

  @Autowired protected MockLambdaContext lambdaContext;

  @Autowired protected DataService dataService;

  @Autowired protected ObjectMapper objectMapper;

  @Before
  public void setUpBase() {
    Mockito.reset(dataService);
    environmentId =
        EnvironmentId.builder()
            .accountId(ACCOUNT_ID)
            .cluster(CLUSTER_NAME)
            .environmentName(ENVIRONMENT_NAME)
            .build();
  }
}
