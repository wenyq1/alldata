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


import {Typography, Tabs, Badge, Empty} from "antd";
import CodeShow from "@/components/Common/CodeShow";
import {useEffect, useState} from "react";
import {getTaskAPIAddress} from "@/pages/API/service";


const {Title, Paragraph, Text, Link} = Typography;
const {TabPane} = Tabs;

const TaskAPI = (props: any) => {

  const {task} = props;
  const [address, setAddress] = useState<string>('127.0.0.1:8888');

  useEffect(() => {
    getAddress();
  }, []);

  const getAddress = () => {
    const res = getTaskAPIAddress();
    res.then((result)=>{
      if(result.datas){
        setAddress(result.datas);
      }
    })
  }

  return (
    <Tabs defaultActiveKey="tableInfo" size="small">
      <TabPane
        tab={
          <span>
          异步提交
        </span>
        }
        key="submitTask"
      >
        <CodeShow code={`curl http://${address}/openapi/submitTask?id=${(task ? task.id : '1')}`} language='shell'
                  height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          停止作业
        </span>
        }
        key="cancelJob"
      >
        <CodeShow code={`curl --location --request POST 'http://${address}/openapi/savepointTask' \\
--header 'Content-Type: application/json' \\
--data-raw '{
        "taskId":${(task ? task.id : '1')},
        "type":"canceljob"
}'`} language='shell' height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          重启作业
        </span>
        }
        key="restartTask"
      >
        <CodeShow code={`curl http://${address}/openapi/restartTask?id=${(task ? task.id : '1')}`} language='shell'
                  height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          作业实例
        </span>
        }
        key="taskInstance"
      >
        <CodeShow code={`curl http://${address}/openapi/getJobInstanceByTaskId?id=${(task ? task.id : '1')}`} language='shell'
                  height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          SavePoint 触发
        </span>
        }
        key="triggerSavePoint"
      >
        <CodeShow code={`curl --location --request POST 'http://${address}/openapi/savepointTask' \\
--header 'Content-Type: application/json' \\
--data-raw '{
        "taskId":${(task ? task.id : '1')},
        "type":"trigger"
}'`} language='shell' height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          SavePoint 停止
        </span>
        }
        key="cancelSavePoint"
      >
        <CodeShow code={`curl --location --request POST 'http://${address}/openapi/savepointTask' \\
--header 'Content-Type: application/json' \\
--data-raw '{
        "taskId":${(task ? task.id : '1')},
        "type":"cancel"
}'`} language='shell' height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          上线作业
        </span>
        }
        key="onLineTask"
      >
        <CodeShow code={`curl http://${address}/openapi/onLineTask?id=${(task ? task.id : '1')}`} language='shell'
                  height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          下线作业
        </span>
        }
        key="offLineTask"
      >
        <CodeShow code={`curl http://${address}/openapi/offLineTask?id=${(task ? task.id : '1')}`} language='shell'
                  height='500px' theme="vs-dark"/>
      </TabPane>
      <TabPane
        tab={
          <span>
          重新上线作业
        </span>
        }
        key="reOnLineTask"
      >
        <CodeShow code={`curl http://${address}/openapi/reOnLineTask?id=${(task ? task.id : '1')}`} language='shell'
                  height='500px' theme="vs-dark"/>
      </TabPane>
    </Tabs>
  );
};

export default TaskAPI;
