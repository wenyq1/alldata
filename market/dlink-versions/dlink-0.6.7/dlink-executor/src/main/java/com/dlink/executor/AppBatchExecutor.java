/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.dlink.executor;

import com.dlink.assertion.Asserts;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * AppBatchExecutor
 *
 * @author wenmo
 * @since 2022/2/7 22:14
 */
public class AppBatchExecutor extends Executor {

    public AppBatchExecutor(ExecutorSetting executorSetting) {
        this.executorSetting = executorSetting;
        if (Asserts.isNotNull(executorSetting.getConfig())) {
            Configuration configuration = Configuration.fromMap(executorSetting.getConfig());
            this.environment = StreamExecutionEnvironment.getExecutionEnvironment(configuration);
        } else {
            this.environment = StreamExecutionEnvironment.createLocalEnvironment();
        }
        init();
    }

    @Override
    CustomTableEnvironment createCustomTableEnvironment() {
        return CustomTableEnvironmentImpl.createBatch(environment);
    }
}
