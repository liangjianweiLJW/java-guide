package com.ljw.camundaspringboot.create;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.GatewayDirection;
import org.camunda.bpm.model.bpmn.builder.*;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.camunda.CamundaFormDataImpl;
import org.camunda.bpm.model.bpmn.impl.instance.camunda.CamundaValidationImpl;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaConstraint;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormField;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaValidation;
import org.camunda.bpm.model.xml.impl.ModelInstanceImpl;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.impl.type.ModelElementTypeImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/10/20 14:12
 */
@Service
public class CreatePbmn implements ApplicationRunner {

    @Resource
    RepositoryService repositoryService;

    //public ProcessEngineRule processEngine = new ProcessEngineRule();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // create an empty model
        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();
        Definitions definitions = modelInstance.newInstance(Definitions.class);
        //definitions.setAttributeValue("bpmn","http://www.omg.org/spec/BPMN/20100524/MODEL");
        definitions.setTargetNamespace("1");
        definitions.setId("id124343543");
        definitions.setAttributeValueNs("http://camunda.org/schema/1.0/bpmn","bpmn","http://camunda.org/schema/1.0/bpmn",false);
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
                .addModelInstance("ljejjeg.bpmn",modelInstance)
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
        BpmnModelInstance modelInstance = Bpmn.createEmptyModel();
        CamundaFormData formData = modelInstance.newInstance(CamundaFormData.class);
        CamundaFormField formField= modelInstance.newInstance(CamundaFormField.class);
        formField.setCamundaId("111");
        formField.setCamundaLabel("2222");
        formField.setCamundaType("string");
        CamundaValidation formField= modelInstance.newInstance(CamundaValidation.class);
        formField.setCamundaValidation(formField);
        formData.addChildElement(formField);
        //	<bpmn:process id="Process_6d099cd" name="低价审批222" isExecutable="true">
        ProcessBuilder processBuilder = Bpmn.createExecutableProcess(processId).name("低价审批").executable();
        StartEventBuilder startEventBuilder = processBuilder.startEvent().name("开始节点").id("StartEvent_000001");

        UserTaskBuilder name = startEventBuilder.userTask().id("Action_904859").name("执行人");
        name.documentation("{\"taskType\":1,\"userType\":\"4\"}");
        name.addExtensionElement(formData)
                .camundaFormField().camundaId("discountReason").camundaType("String").camundaLabel("低价原因")
                .camundaTaskListenerDelegateExpression("create", "${startUserTaskListener}")
                .done();






        String xmlString = Bpmn.convertToString(done);
        System.out.println(xmlString);


    }

    public void testCreateInvoiceProcess() throws Exception {
        //AbstractFlowNodeBuilder abstractFlowNodeBuilder
        BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("invoice")
                .name("BPMN API Invoice Process")
                .startEvent()
                .name("Invoice received")
                .userTask()
                .name("Assign Approver")
                .camundaAssignee("demo")
                .userTask()
                .id("approveInvoice")
                .name("Approve Invoice")
                .exclusiveGateway()
                .name("Invoice approved?")
                .gatewayDirection(GatewayDirection.Diverging)
                .condition("yes", "${approved}")
                .userTask()
                .name("Prepare Bank Transfer")
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
                .name("Review Invoice")
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
                .addModelInstance("apimy.bpmn",modelInstance)
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
