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
     *  
     *
     * @param param
     * @return
     */
    @PostMapping("/cccc")
    public String get(@RequestBody Map<String, Object> param) {
        ArrayList<Map<String, String>> sequenceFlow = new ArrayList<>();
        ArrayList<Map<String, String>> userTask = new ArrayList<>();
        Map<String, Object> groupEndNodeMap = new HashMap();
        Map<String, String> sonFatherIdMap = new HashMap();
        Map<String, Object> instantMap = new HashMap<>();
        Map<String, String> startEvent = new HashMap<>();
        startEvent.put("id", "startEvent");
        startEvent.put("name", "开始节点");
        startEvent.put("type", "0");
        startEvent.put("outgoing", "FlowEvent_000001");
        this.convert(param, startEvent, null, sequenceFlow, userTask, groupEndNodeMap, sonFatherIdMap, instantMap);
        System.out.println(sequenceFlow);
        System.out.println(userTask);
        return null;
    }

    private void convert(Map<String, Object> param, Map<String, String> sourceNode, String parentGroupId, ArrayList<Map<String, String>> sequenceFlow, ArrayList<Map<String, String>> userTask, Map<String, Object> groupEndNodeMap, Map<String, String> sonFatherIdMap, Map<String, Object> instantMap) {
        //下一个节点
        Map<String, String> currentNode = new HashMap<>();
        currentNode.put("id", String.valueOf(param.get("id")));
        currentNode.put("name", String.valueOf(param.get("nodeName")));
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
                if (parentGroupId != null) {
                    sonFatherIdMap.put(currentGroupId, parentGroupId);
                }
                //连线,
                this.connect(sourceNode, currentNode, sequenceFlow);
                for (Map<String, Object> stringObjectMap : conditionList) {
                    //存入当前分支的下个接口 递归
                    convert(stringObjectMap, currentNode, currentGroupId, sequenceFlow, userTask, groupEndNodeMap, sonFatherIdMap, instantMap);
                }

            } else if (currentNode.get("type").equals("99")) {
                this.connect(sourceNode, currentNode, sequenceFlow);
            } else {
                //连线,
                connect(sourceNode, currentNode, sequenceFlow);
                if (childNode == null) {
                    if (parentGroupId != null) {
                        //一个组结束，出口 实例化groupNode
                        Map<String, Object> groupEndNode = (Map<String, Object>) groupEndNodeMap.get(parentGroupId);
                        if (groupEndNode == null) {
                            String pGroupId = sonFatherIdMap.get(parentGroupId);
                            Map<String, Object> pGroupEndNode = (Map<String, Object>) groupEndNodeMap.get(pGroupId);
                            if (pGroupEndNode == null) {
                                System.out.println("k---------------------");
                            } else {
                                convert(pGroupEndNode, currentNode, pGroupId, sequenceFlow, userTask, groupEndNodeMap, sonFatherIdMap, instantMap);
                            }
                        } else {
                            if (currentNode.get("id").equals(groupEndNode.get("id"))) {
                                return;
                            }
                            convert(groupEndNode, currentNode, parentGroupId, sequenceFlow, userTask, groupEndNodeMap, sonFatherIdMap, instantMap);
                        }
                    } else {
                        return;
                    }
                } else {
                    //递归
                    convert(childNode, currentNode, parentGroupId, sequenceFlow, userTask, groupEndNodeMap, sonFatherIdMap, instantMap);

                }
            }
        } else {
            //已经实例化过了
            connect(sourceNode, currentNode, sequenceFlow);
        }

    }

    private Integer idd = 1;

    private void connect(Map<String, String> sourceNode, Map<String, String> currentNode, ArrayList<Map<String, String>> sequenceFlow) {
        Map<String, String> sequence = new HashMap<>();
        String id = idd.toString();
        ++idd;
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

