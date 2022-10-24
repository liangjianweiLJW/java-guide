package com.ljw.springbootaop.controller;

import com.ljw.springbootaop.aspect.annotation.AopOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2021/12/7 20:37
 */
@RestController
@Slf4j
public class AopOrderController {

    @GetMapping("/aopOrder")
    @AopOrder(module = "测试aop顺序")
    public String aopOrder() throws InterruptedException {
        log.info("我的业务方法");
        Thread.sleep(1000);
        return "执行成功";
    }


    @GetMapping("/aopOrderError")
    @AopOrder(module = "测试aop顺序")
    public String aopOrderError() {
        log.info("我的业务方法");
        int i = 1 / 0;
        return "执行失败";
    }


    /**
     * "bpmn:sequenceFlow": [
     * {
     * "id": "FlowEvent_000001",
     * "targetRef": "Action_904859",
     * "sourceRef": "StartEvent_000001"
     * },
     * {
     * "id": "FlowEvent_9999999",
     * "targetRef": "EndEvent_9999999",
     * "sourceRef": "Copy_584252"
     * },
     * {
     * "id": "FlowEvent_195366",
     * "targetRef": "Approval_016232",
     * "sourceRef": "Action_904859"
     * },
     * {
     * "id": "FlowEvent_360376",
     * "targetRef": "Copy_584252",
     * "sourceRef": "Approval_016232"
     * }
     * ],
     * "bpmn:userTask": [
     * {
     * "id": "Action_904859",
     * "name": "执行人",
     * "bpmn:incoming": "FlowEvent_000001",
     * "bpmn:outgoing": "FlowEvent_793873",
     * "taskType": 1,
     * "fields": {
     * "type": 0,
     * "users": "",
     * "roles": ""
     * }
     * },
     * {
     * "id": "Approval_016232",
     * "name": "审批人",
     * "bpmn:incoming": "FlowEvent_793873",
     * "bpmn:outgoing": "FlowEvent_195366",
     * "taskType": 2,
     * "fields": {
     * "type": 2,
     * "users": "392,390",
     * "roles": "554,555"
     * }
     * },
     * {
     * "id": "Copy_584252",
     * "name": "抄送人",
     * "bpmn:incoming": "FlowEvent_195366",
     * "bpmn:outgoing": "FlowEvent_9999999",
     * "taskType": 3,
     * "fields": {
     * "type": 1,
     * "users": "390,392,396,398,432",
     * "roles": ""
     * }
     * }
     * ],
     *
     * @param param
     * @return
     */
    @PostMapping("/cccc")
    public String get(@RequestBody Map<String, Object> param) {
        ArrayList<Map<String, String>> sequenceFlow = new ArrayList<>();
        ArrayList<Map<String, String>> userTask = new ArrayList<>();
        Map<String, Object> groupEndNodeMap = new HashMap();
        Map<String, Object> instantMap = new HashMap<>();
        Map<String, String> startEvent = new HashMap<>();
        startEvent.put("id", "startEvent");
        startEvent.put("name", "开始节点");
        startEvent.put("type", "0");
        startEvent.put("outgoing", "FlowEvent_000001");
        this.convert(param, startEvent, null, sequenceFlow, userTask, groupEndNodeMap, instantMap);
        return null;
    }

    private void convert(Map<String, Object> param, Map<String, String> sourceNode, String groupId, ArrayList<Map<String, String>> sequenceFlow, ArrayList<Map<String, String>> userTask, Map<String, Object> groupEndNodeMap, Map<String, Object> instantMap) {
        //下一个节点
        Map<String, String> currentNode = new HashMap<>();
        currentNode.put("id", String.valueOf(param.get("id")));
        currentNode.put("name", String.valueOf(param.get("name")));
        currentNode.put("type", String.valueOf(param.get("type")));
        currentNode.put("incoming", "");
        currentNode.put("outgoing", "");
        Map<String, Object> childNode = (Map<String, Object>) param.get("childNode");
        List<Map<String, Object>> conditionList = (List<Map<String, Object>>) param.get("conditionList");
        String currentNodeId = currentNode.get("id");
        if (instantMap.get(currentNodeId) == null) {
            //还没有实例化过
            //构建node
            instantMap.put(currentNodeId, currentNode);
            //添加节点到 bpmn:userTask
            userTask.add(currentNode);
            if (currentNode.get("type").equals("5")) {
                //把分组的总出口先保存
                String currentGroupId = currentNode.get("id");
                groupEndNodeMap.put(currentGroupId, childNode);
                //连线,
                this.connect(sourceNode, currentNode, sequenceFlow);
                for (Map<String, Object> stringObjectMap : conditionList) {
                    //存入当前分支的下个接口 递归
                    convert(stringObjectMap, currentNode, currentGroupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
                }

            } else {
                //连线,
                connect(sourceNode, currentNode, sequenceFlow);
                if (childNode == null) {
                    if (groupId != null) {
                        //一个组结束，出口 实例化groupNode
                        Map<String, Object> groupEndNode = (Map<String, Object>) groupEndNodeMap.get(groupId);
                        convert(groupEndNode, currentNode, groupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
                    } else {
                        return;
                    }
                }
                //递归
                convert(childNode, currentNode, groupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
            }
        } else {
            //已经实例化过了
            connect(sourceNode, currentNode, sequenceFlow);
            //下下个节点
            if (currentNode.get("childNode") == null) {
                if (groupId != null) {
                    //一个组结束，出口 实例化groupNode
                    Map<String, Object> groupEndNode = (Map<String, Object>) groupEndNodeMap.get(groupId);
                    convert(groupEndNode, currentNode, groupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
                } else {
                    return;
                }
            }
            //遇到已经实例化的节点
            if (currentNode.get("type").equals("5")) {
                //把分组的总出口先保存
                String currentGroupId = currentNode.get("id");
                groupEndNodeMap.put(currentGroupId, childNode);
                //连线,
                this.connect(sourceNode, currentNode, sequenceFlow);
                for (Map<String, Object> stringObjectMap : conditionList) {
                    //存入当前分支的下个接口 递归
                    convert(stringObjectMap, currentNode, groupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
                }

            } else {
                //连线,
                connect(sourceNode, currentNode, sequenceFlow);
                if (childNode == null) {
                    if (groupId != null) {
                        //一个组结束，出口 实例化groupNode
                        Map<String, Object> groupEndNode = (Map<String, Object>) groupEndNodeMap.get(groupId);
                        convert(groupEndNode, currentNode, groupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
                    } else {
                        return;
                    }
                }
                //递归
                convert(childNode, currentNode, groupId, sequenceFlow, userTask, groupEndNodeMap, instantMap);
            }
        }

    }

    private void connect(Map<String, String> sourceNode, Map<String, String> currentNode, ArrayList<Map<String, String>> sequenceFlow) {
        Map<String, String> sequence = new HashMap<>();
        String id = String.valueOf(System.currentTimeMillis());
        sequence.put("id", id);
        sequence.put("targetRef", currentNode.get("id"));
        sequence.put("sourceRef", sourceNode.get("id"));
        String outgoing = sourceNode.get("outgoing");
        if (outgoing != null && outgoing != "") {
            outgoing = outgoing + "," + id;
            sourceNode.put("outgoing", outgoing);
        } else {
            sourceNode.put("outgoing", id);
        }
        String incoming = currentNode.get("incoming");
        if (incoming != null && incoming != "") {
            incoming = incoming + "," + id;
            currentNode.put("incoming", incoming);
        } else {
            currentNode.put("incoming", id);
        }
        sequenceFlow.add(sequence);
    }

}

