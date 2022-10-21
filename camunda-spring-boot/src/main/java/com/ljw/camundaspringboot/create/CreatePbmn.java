package com.ljw.camundaspringboot.create;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.GatewayDirection;
import org.camunda.bpm.model.bpmn.impl.instance.Outgoing;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/10/20 14:12
 */
@Service
public class CreatePbmn implements ApplicationRunner {

    @Resource
    RepositoryService repositoryService;


    @Resource
    private RuntimeService runtimeService;

    //public ProcessEngineRule processEngine = new ProcessEngineRule();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // create an empty model
        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();
        Definitions definitions = modelInstance.newInstance(Definitions.class);
        //definitions.setAttributeValue("bpmn","http://www.omg.org/spec/BPMN/20100524/MODEL");
        definitions.setTargetNamespace("1");
        definitions.setId("id124343543");
        definitions.setAttributeValueNs("http://camunda.org/schema/1.0/bpmn", "bpmn", "http://camunda.org/schema/1.0/bpmn", false);
        modelInstance.setDefinitions(definitions);

// create the process
        Process process = createElement(definitions, "process-with-one-task", Process.class);

// create elements
        StartEvent startEvent = createElement(process, "start", StartEvent.class);
        ParallelGateway fork = createElement(process, "fork", ParallelGateway.class);
        ServiceTask task1 = createElement(process, "task1", ServiceTask.class);
        task1.setName("Service Task");
        UserTask task2 = createElement(process, "task2", UserTask.class);
        task2.setName("User Task");
        ParallelGateway join = createElement(process, "join", ParallelGateway.class);
        EndEvent endEvent = createElement(process, "end", EndEvent.class);

// create flows
        createSequenceFlow(process, startEvent, fork);
        createSequenceFlow(process, fork, task1);
        createSequenceFlow(process, fork, task2);
        createSequenceFlow(process, task1, join);
        createSequenceFlow(process, task2, join);
        createSequenceFlow(process, join, endEvent);

// validate and write model to file
        Bpmn.validateModel(modelInstance);
        File file = File.createTempFile("bpmn-model-api-", ".bpmn");
        Bpmn.writeModelToFile(file, modelInstance);

        // convert to string
        String xmlString = Bpmn.convertToString(modelInstance);
        System.out.println(xmlString);

        final BpmnModelInstance myProcess = Bpmn.createExecutableProcess("process-payments")
                .startEvent()
                .serviceTask()
                .name("Process Payment")
                .endEvent()
                .done();
        System.out.println(Bpmn.convertToString(myProcess));

        Deployment deployment = repositoryService.createDeployment()
                .tenantId("222")
                .name("ljwceces")
                .addModelInstance("ljejjeg.bpmn", modelInstance)
                .deploy();
        System.out.println(deployment.getId());

        testCreateInvoiceProcess2("ig3333");
    }


    public SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to) {
        String identifier = from.getId() + "-" + to.getId();
        SequenceFlow sequenceFlow = createElement(process, identifier, SequenceFlow.class);
        process.addChildElement(sequenceFlow);
        sequenceFlow.setSource(from);
        from.getOutgoing().add(sequenceFlow);
        sequenceFlow.setTarget(to);
        to.getIncoming().add(sequenceFlow);
        return sequenceFlow;
    }

    protected <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement, String id, Class<T> elementClass) {
        T element = parentElement.getModelInstance().newInstance(elementClass);
        element.setAttributeValue("id", id, true);
        parentElement.addChildElement(element);
        return element;
    }

    public void testCreateInvoiceProcess2(String processId) throws Exception {
        //创建进程
        //	<bpmn:process id="Process_6d099cd" name="低价审批222" isExecutable="true">
        //ProcessBuilder processBuilder = Bpmn.createExecutableProcess(processId).name("低价审批").executable();


        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();
        Definitions definitions = modelInstance.newInstance(Definitions.class);
        definitions.setTargetNamespace("1");
        definitions.setId("id124343543");

        Process process = definitions.getModelInstance().newInstance(Process.class);
        //process.setAttributeValue("id", "myProcess_", true);
        //process.setAttributeValue("name", "低价审批", true);
        process.setId("myProcess_");
        process.setName("低价审批");
        process.isExecutable();
        process.setAttributeValue("isExecutable", "true", true);

        StartEvent startEvent = process.getModelInstance().newInstance(StartEvent.class);
        startEvent.setId("Event_03z5fdx");
        startEvent.setName("开始节点");
        process.addChildElement(startEvent);
        //Outgoing outgoing = process.getModelInstance().newInstance(Outgoing.class);
        //outgoing.setTextContent("Flow_1unnocy");
        //startEvent.addChildElement(outgoing);

        definitions.addChildElement(process);
        modelInstance.setDefinitions(definitions);
        UserTask userTask = process.getModelInstance().newInstance(UserTask.class);
        process.addChildElement(userTask);
        Documentation documentation = userTask.getModelInstance().newInstance(Documentation.class);
        documentation.setTextContent("{\"taskType\":1,\"userType\":\"4\"}");
        userTask.addChildElement(documentation);
        CamundaFormData camundaFormData = process.getModelInstance().newInstance(CamundaFormData.class);
        ExtensionElements extensionElements = process.getModelInstance().newInstance(ExtensionElements.class);
        extensionElements.addChildElement(camundaFormData);

        CamundaFormField camundaFormField = extensionElements.getModelInstance().newInstance(CamundaFormField.class);
        camundaFormField.setCamundaId("discountReason");
        camundaFormField.setCamundaLabel("低价原因");
        camundaFormField.setCamundaType("string");
        CamundaValidation camundaValidation = camundaFormField.getModelInstance().newInstance(CamundaValidation.class);
        CamundaConstraint camundaConstraint = camundaValidation.getModelInstance().newInstance(CamundaConstraint.class);
        camundaConstraint.setCamundaName("required");
        camundaValidation.addChildElement(camundaConstraint);
        camundaFormField.addChildElement(camundaValidation);
        camundaFormData.addChildElement(camundaFormField);


        CamundaTaskListener camundaTaskListener = extensionElements.getModelInstance().newInstance(CamundaTaskListener.class);
        camundaTaskListener.setCamundaDelegateExpression("${startUserTaskListener}");
        camundaTaskListener.setCamundaEvent("create");
        CamundaField formData = camundaTaskListener.getModelInstance().newInstance(CamundaField.class);
        formData.setCamundaName("type");
        CamundaString camundaString = formData.getModelInstance().newInstance(CamundaString.class);
        camundaString.setTextContent("<![CDATA[2]]>");
        formData.addChildElement(camundaString);
        camundaTaskListener.addChildElement(formData);
        extensionElements.addChildElement(camundaTaskListener);

        userTask.addChildElement(extensionElements);
        userTask.setName("执行人");

        //SequenceFlow sequenceFlow = process.getModelInstance().newInstance(SequenceFlow.class);
        createSequenceFlow(process,startEvent,userTask);

        // convert to string
        String xmlString = Bpmn.convertToString(modelInstance);
        System.out.println(xmlString);

        Deployment deployment = repositoryService.createDeployment()
                .tenantId("333")
                .name("ljwceces333")
                .addModelInstance("ljejjeg333.bpmn", modelInstance)
                .deploy();
        System.out.println(deployment.getId());
        runtimeService.startProcessInstanceByKey("myProcess_");
        //StartEventBuilder startEventBuilder = processBuilder.startEvent().name("开始节点").id("StartEvent_000001");
        //UserTaskBuilder userTaskBuilder = startEventBuilder.userTask().id("Action_904859").name("执行人");
        //UserTaskBuilder documentation = userTaskBuilder.documentation("{\"taskType\":1,\"userType\":\"4\"}");
        //CamundaUserTaskFormFieldBuilder camundaUserTaskFormFieldBuilder = userTaskBuilder.camundaFormField().camundaId("discountReason").camundaType("String").camundaLabel("低价原因");
        //userTaskBuilder.camundaTaskListenerDelegateExpression("create", "${startUserTaskListener}")
        //        .addExtensionElement(formData);

        //String xmlString = Bpmn.convertToString(done);
        //System.out.println(xmlString);


    }

    public void testCreateInvoiceProcess() throws Exception {
        //AbstractFlowNodeBuilder abstractFlowNodeBuilder
        BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("invoice")
                .name("我的流程定义名称")
                //
                .startEvent()
                .name("开始节点")

                .userTask()
                .name("发起")
                .camundaAssignee("demo")

                .userTask()
                .id("approveInvoice")
                .name("审批")

                .exclusiveGateway()
                .name("排他网关名")
                .gatewayDirection(GatewayDirection.Diverging)
                .condition("yes", "${approved}")

                .userTask()
                .name("yes 接收")
                .camundaFormKey("embedded:app:forms/prepare-bank-transfer.html")
                .camundaCandidateGroups("accounting")

                .serviceTask()
                .name("Archive Invoice")
                .camundaClass("org.camunda.bpm.example.invoice.service.ArchiveInvoiceService")
                .endEvent()
                .name("Invoice processed")

                .moveToLastGateway()
                .condition("no", "${!approved}")
                .userTask()
                .name("no 接收")
                .camundaAssignee("demo")

                .exclusiveGateway()
                .name("Review successful?")

                .gatewayDirection(GatewayDirection.Diverging)
                .condition("no", "${!clarified}")
                .endEvent()
                .name("Invoice not processed")

                .moveToLastGateway()
                .condition("yes", "${clarified}")
                .connectTo("approveInvoice")
                .done();

        String xmlString = Bpmn.convertToString(modelInstance);
        System.out.println(xmlString);
        Deployment deployment = repositoryService.createDeployment()
                .tenantId("222")
                .name("我的api流程")
                .addModelInstance("apimy.bpmn", modelInstance)
                .deploy();
        System.out.println(deployment.getId());
/*
        // deploy process model
        processEngine.getRepositoryService().createDeployment().addModelInstance("invoice.bpmn", modelInstance).deploy();

        // start process model
        processEngine.getRuntimeService().startProcessInstanceByKey("invoice");

        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        // check and complete task "Assign Approver"
        System.out.println(taskQuery.count());
        processEngine.getTaskService().complete(taskQuery.singleResult().getId());

        // check and complete task "Approve Invoice"
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", true);

        System.out.println(taskQuery.count());
        processEngine.getTaskService().complete(taskQuery.singleResult().getId(), variables);

        // check and complete task "Prepare Bank Transfer"
        System.out.println(taskQuery.count());
        processEngine.getTaskService().complete(taskQuery.singleResult().getId());

        // check if Delegate was executed

        // check if process instance is ended 0
        System.out.println(processEngine.getRuntimeService().createProcessInstanceQuery().count());*/

        // show the BPMN 2.0 process model XML on the console log
//      Bpmn.writeModelToStream(System.out, modelInstance);
    }
}
