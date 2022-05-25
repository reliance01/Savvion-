package vam.com.rgi.rcorp.wsdl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.encoding.SerializerFactory;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

public class BizLogic1SoapBindingStub extends Stub implements WorkFlowWS {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[66];

   static {
      _initOperationDesc1();
      _initOperationDesc2();
      _initOperationDesc3();
      _initOperationDesc4();
      _initOperationDesc5();
      _initOperationDesc6();
      _initOperationDesc7();
   }

   private static void _initOperationDesc1() {
      OperationDesc oper = new OperationDesc();
      oper.setName("connect");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "userId"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "password"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "connectReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[0] = oper;
      oper = new OperationDesc();
      oper.setName("disconnect");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[1] = oper;
      oper = new OperationDesc();
      oper.setName("getStatus");
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getStatusReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[2] = oper;
      oper = new OperationDesc();
      oper.setName("isSessionValid");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      oper.setReturnClass(Boolean.TYPE);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "isSessionValidReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[3] = oper;
      oper = new OperationDesc();
      oper.setName("completeWorkItem");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsi"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"), DataSlotinstance[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[4] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstanceList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      oper.setReturnClass(ProcessInstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[5] = oper;
      oper = new OperationDesc();
      oper.setName("removeProcessTemplate");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[6] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplate");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessTemplate"));
      oper.setReturnClass(ProcessTemplate.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[7] = oper;
      oper = new OperationDesc();
      oper.setName("setProcessInstancePriority");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "priority"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[8] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      oper.setReturnClass(Long.TYPE);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[9] = oper;
   }

   private static void _initOperationDesc2() {
      OperationDesc oper = new OperationDesc();
      oper.setName("getProcessTemplateVersions");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "appName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateVersionsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[10] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateXML");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateXMLReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[11] = oper;
      oper = new OperationDesc();
      oper.setName("getUserAuthorizedProcessTemplateList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessTemplate"));
      oper.setReturnClass(ProcessTemplate[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getUserAuthorizedProcessTemplateListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[12] = oper;
      oper = new OperationDesc();
      oper.setName("createProcessInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piNamePrefix"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "priority"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dst"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"), DataSlotTemplate[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      oper.setReturnClass(ProcessInstance.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "createProcessInstanceReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[13] = oper;
      oper = new OperationDesc();
      oper.setName("resumeProcessInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[14] = oper;
      oper = new OperationDesc();
      oper.setName("suspendProcessInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[15] = oper;
      oper = new OperationDesc();
      oper.setName("isProcessTemplateExist");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      oper.setReturnClass(Boolean.TYPE);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "isProcessTemplateExistReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[16] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      oper.setReturnClass(ProcessInstance.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[17] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepTemplate");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate"));
      oper.setReturnClass(WorkSteptemplate.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[18] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkStepInstance"));
      oper.setReturnClass(WorkStepInstance.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[19] = oper;
   }

   private static void _initOperationDesc3() {
      OperationDesc oper = new OperationDesc();
      oper.setName("getWorkItem");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkItemReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[20] = oper;
      oper = new OperationDesc();
      oper.setName("getAvailableWorkItemList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getAvailableWorkItemListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[21] = oper;
      oper = new OperationDesc();
      oper.setName("getAssignedWorkItemList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getAssignedWorkItemListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[22] = oper;
      oper = new OperationDesc();
      oper.setName("getProxyAssignedWorkItemList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProxyAssignedWorkItemListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[23] = oper;
      oper = new OperationDesc();
      oper.setName("getProxyAvailableWorkItemList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProxyAvailableWorkItemListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[24] = oper;
      oper = new OperationDesc();
      oper.setName("getSuspendedWorkItemList");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getSuspendedWorkItemListReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[25] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateAppName");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateAppNameReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[26] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateNameFromProcessInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateNameFromProcessInstanceReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[27] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateNameFromProcessID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateNameFromProcessIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[28] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateWorkSteps");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate"));
      oper.setReturnClass(WorkSteptemplate[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateWorkStepsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[29] = oper;
   }

   private static void _initOperationDesc4() {
      OperationDesc oper = new OperationDesc();
      oper.setName("getUserAuthorizedProcessTemplateNames");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getUserAuthorizedProcessTemplateNamesReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[30] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateFromID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessTemplate"));
      oper.setReturnClass(ProcessTemplate.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateFromIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[31] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateDataSlots");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"));
      oper.setReturnClass(DataSlotTemplate[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateDataSlotsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[32] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessTemplateDataSlot");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"));
      oper.setReturnClass(DataSlotTemplate[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateDataSlotReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[33] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstanceDataSlots");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[34] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstanceDataSlot");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[35] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstanceDataSlotFromPIID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotFromPIIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[36] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepInstanceDataSlots");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceDataSlotsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[37] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepInstanceDataSlotsFromPIID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceDataSlotsFromPIIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[38] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkItemDataSlots");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkItemDataSlotsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[39] = oper;
   }

   private static void _initOperationDesc5() {
      OperationDesc oper = new OperationDesc();
      oper.setName("getWorkItemDataSlotsFromWIID");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"));
      oper.setReturnClass(DataSlotinstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkItemDataSlotsFromWIIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[40] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepTemplateDataSlots");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"));
      oper.setReturnClass(DataSlotTemplate[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateDataSlotsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[41] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepTemplateDataSlotsFromPTID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"));
      oper.setReturnClass(DataSlotTemplate[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateDataSlotsFromPTIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[42] = oper;
      oper = new OperationDesc();
      oper.setName("setProcessTemplateDataSlotValue");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dst"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"), DataSlotTemplate.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[43] = oper;
      oper = new OperationDesc();
      oper.setName("setProcessTemplateDataSlotsValue");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dst"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate"), DataSlotTemplate[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[44] = oper;
      oper = new OperationDesc();
      oper.setName("setProcessInstanceDataSlotValue");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsi"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"), DataSlotinstance.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[45] = oper;
      oper = new OperationDesc();
      oper.setName("setProcessInstanceDataSlotsValue");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsi"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"), DataSlotinstance[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[46] = oper;
      oper = new OperationDesc();
      oper.setName("setWorkItemDataSlotsValue");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsi"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"), DataSlotinstance[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[47] = oper;
      oper = new OperationDesc();
      oper.setName("setWorkItemDataSlotValue");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsi"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"), DataSlotinstance.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[48] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstanceFromID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      oper.setReturnClass(ProcessInstance.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceFromIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[49] = oper;
   }

   private static void _initOperationDesc6() {
      OperationDesc oper = new OperationDesc();
      oper.setName("setProcessInstanceDueDate");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dueDate"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "dateTime"), Calendar.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[50] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkItemFromID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      oper.setReturnClass(WorkItem.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkItemFromIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[51] = oper;
      oper = new OperationDesc();
      oper.setName("suspendWorkItem");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[52] = oper;
      oper = new OperationDesc();
      oper.setName("resumeWorkItem");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[53] = oper;
      oper = new OperationDesc();
      oper.setName("assignWorkItem");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "performer"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[54] = oper;
      oper = new OperationDesc();
      oper.setName("assignWorkItemFromWIID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "performer"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[55] = oper;
      oper = new OperationDesc();
      oper.setName("reAssignWorkItem");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "performer"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[56] = oper;
      oper = new OperationDesc();
      oper.setName("reAssignWorkItemFromWIID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "performer"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[57] = oper;
      oper = new OperationDesc();
      oper.setName("makeAvailableWorkItem");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "performers"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[58] = oper;
      oper = new OperationDesc();
      oper.setName("makeAvailableWorkItemFromWIID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "performers"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[59] = oper;
   }

   private static void _initOperationDesc7() {
      OperationDesc oper = new OperationDesc();
      oper.setName("completeWorkItemFromWIID");
      ParameterDesc param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wiid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "dsi"), (byte)1, new QName("http://workflow.webservice.savvion.com", "DataSlotinstance"), DataSlotinstance[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[60] = oper;
      oper = new OperationDesc();
      oper.setName("getWorkStepInstanceFromPIID");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piid"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "long"), Long.TYPE, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkStepInstance"));
      oper.setReturnClass(WorkStepInstance.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceFromPIIDReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[61] = oper;
      oper = new OperationDesc();
      oper.setName("getProcessInstanceWorkSteps");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkStepInstance"));
      oper.setReturnClass(WorkStepInstance[].class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceWorkStepsReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[62] = oper;
      oper = new OperationDesc();
      oper.setName("suspendWorkStepInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[63] = oper;
      oper = new OperationDesc();
      oper.setName("resumeWorkStepInstance");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "piName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "wsName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[64] = oper;
      oper = new OperationDesc();
      oper.setName("getStartWorkStepTemplate");
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "session"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://workflow.webservice.savvion.com", "ptname"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate"));
      oper.setReturnClass(WorkSteptemplate.class);
      oper.setReturnQName(new QName("http://workflow.webservice.savvion.com", "getStartWorkStepTemplateReturn"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[65] = oper;
   }

   public BizLogic1SoapBindingStub() throws AxisFault {
      this((Service)null);
   }

   public BizLogic1SoapBindingStub(URL endpointURL, Service service) throws AxisFault {
      this(service);
      super.cachedEndpoint = endpointURL;
   }

   public BizLogic1SoapBindingStub(Service service) throws AxisFault {
      this.cachedSerClasses = new Vector();
      this.cachedSerQNames = new Vector();
      this.cachedSerFactories = new Vector();
      this.cachedDeserFactories = new Vector();
      if (service == null) {
         super.service = new org.apache.axis.client.Service();
      } else {
         super.service = service;
      }

      ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
      Class beansf = BeanSerializerFactory.class;
      Class beandf = BeanDeserializerFactory.class;
      Class enumsf = EnumSerializerFactory.class;
      Class enumdf = EnumDeserializerFactory.class;
      Class arraysf = ArraySerializerFactory.class;
      Class arraydf = ArrayDeserializerFactory.class;
      Class simplesf = SimpleSerializerFactory.class;
      Class simpledf = SimpleDeserializerFactory.class;
      Class simplelistsf = SimpleListSerializerFactory.class;
      Class simplelistdf = SimpleListDeserializerFactory.class;
      this.addBindings0();
      this.addBindings1();
   }

   private void addBindings0() {
      Class beansf = BeanSerializerFactory.class;
      Class beandf = BeanDeserializerFactory.class;
      Class enumsf = EnumSerializerFactory.class;
      Class enumdf = EnumDeserializerFactory.class;
      Class arraysf = ArraySerializerFactory.class;
      Class arraydf = ArrayDeserializerFactory.class;
      Class simplesf = SimpleSerializerFactory.class;
      Class simpledf = SimpleDeserializerFactory.class;
      Class simplelistsf = SimpleListSerializerFactory.class;
      Class simplelistdf = SimpleListDeserializerFactory.class;
      QName qName = new QName("http://workflow.webservice.savvion.com", ">assignWorkItem");
      this.cachedSerQNames.add(qName);
      Class cls = AssignWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">assignWorkItemFromWIID");
      this.cachedSerQNames.add(qName);
      cls = AssignWorkItemFromWIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">assignWorkItemFromWIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = AssignWorkItemFromWIIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">assignWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = AssignWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">completeWorkItem");
      this.cachedSerQNames.add(qName);
      cls = CompleteWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">completeWorkItemFromWIID");
      this.cachedSerQNames.add(qName);
      cls = CompleteWorkItemFromWIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">completeWorkItemFromWIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = CompleteWorkItemFromWIIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">completeWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = CompleteWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">connect");
      this.cachedSerQNames.add(qName);
      cls = Connect.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">connectResponse");
      this.cachedSerQNames.add(qName);
      cls = ConnectResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">createProcessInstance");
      this.cachedSerQNames.add(qName);
      cls = CreateProcessInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">createProcessInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = CreateProcessInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">disconnect");
      this.cachedSerQNames.add(qName);
      cls = Disconnect.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">disconnectResponse");
      this.cachedSerQNames.add(qName);
      cls = DisconnectResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getAssignedWorkItemList");
      this.cachedSerQNames.add(qName);
      cls = GetAssignedWorkItemList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getAssignedWorkItemListResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkItem[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkItem");
      QName qName2 = new QName("http://workflow.webservice.savvion.com", "getAssignedWorkItemListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getAvailableWorkItemList");
      this.cachedSerQNames.add(qName);
      cls = GetAvailableWorkItemList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getAvailableWorkItemListResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkItem[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkItem");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getAvailableWorkItemListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstance");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlot");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceDataSlot.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlotFromPIID");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceDataSlotFromPIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlotFromPIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotFromPIIDReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlotResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlots");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceDataSlots.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceDataSlotsResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceFromID");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceFromID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceFromIDResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceFromIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceList");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceListResponse");
      this.cachedSerQNames.add(qName);
      cls = ProcessInstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "ProcessInstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessInstanceListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceWorkSteps");
      this.cachedSerQNames.add(qName);
      cls = GetProcessInstanceWorkSteps.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessInstanceWorkStepsResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkStepInstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkStepInstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessInstanceWorkStepsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplate");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateAppName");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateAppName.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateAppNameResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateAppNameResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateDataSlot");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateDataSlot.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateDataSlotResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotTemplate[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessTemplateDataSlotReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateDataSlots");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateDataSlots.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateDataSlotsResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotTemplate[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessTemplateDataSlotsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateFromID");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateFromID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateFromIDResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateFromIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateID");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateIDResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateNameFromProcessID");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateNameFromProcessID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateNameFromProcessIDResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateNameFromProcessIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateNameFromProcessInstance");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateNameFromProcessInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateNameFromProcessInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateNameFromProcessInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateVersions");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateVersions.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateVersionsResponse");
      this.cachedSerQNames.add(qName);
      cls = String[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://www.w3.org/2001/XMLSchema", "string");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessTemplateVersionsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateWorkSteps");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateWorkSteps.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateWorkStepsResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkSteptemplate[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProcessTemplateWorkStepsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateXML");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateXML.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProcessTemplateXMLResponse");
      this.cachedSerQNames.add(qName);
      cls = GetProcessTemplateXMLResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProxyAssignedWorkItemList");
      this.cachedSerQNames.add(qName);
      cls = GetProxyAssignedWorkItemList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProxyAssignedWorkItemListResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkItem[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkItem");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProxyAssignedWorkItemListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getProxyAvailableWorkItemList");
      this.cachedSerQNames.add(qName);
      cls = GetProxyAvailableWorkItemList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getProxyAvailableWorkItemListResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkItem[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkItem");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getProxyAvailableWorkItemListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getStartWorkStepTemplate");
      this.cachedSerQNames.add(qName);
      cls = GetStartWorkStepTemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getStartWorkStepTemplateResponse");
      this.cachedSerQNames.add(qName);
      cls = GetStartWorkStepTemplateResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getStatus");
      this.cachedSerQNames.add(qName);
      cls = GetStatus.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getStatusResponse");
      this.cachedSerQNames.add(qName);
      cls = GetStatusResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getSuspendedWorkItemList");
      this.cachedSerQNames.add(qName);
      cls = GetSuspendedWorkItemList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getSuspendedWorkItemListResponse");
      this.cachedSerQNames.add(qName);
      cls = WorkItem[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkItem");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getSuspendedWorkItemListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getUserAuthorizedProcessTemplateList");
      this.cachedSerQNames.add(qName);
      cls = GetUserAuthorizedProcessTemplateList.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getUserAuthorizedProcessTemplateListResponse");
      this.cachedSerQNames.add(qName);
      cls = ProcessTemplate[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "ProcessTemplate");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getUserAuthorizedProcessTemplateListReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getUserAuthorizedProcessTemplateNames");
      this.cachedSerQNames.add(qName);
      cls = GetUserAuthorizedProcessTemplateNames.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getUserAuthorizedProcessTemplateNamesResponse");
      this.cachedSerQNames.add(qName);
      cls = String[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://www.w3.org/2001/XMLSchema", "string");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getUserAuthorizedProcessTemplateNamesReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItem");
      this.cachedSerQNames.add(qName);
      cls = GetWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemDataSlots");
      this.cachedSerQNames.add(qName);
      cls = GetWorkItemDataSlots.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemDataSlotsFromWIID");
      this.cachedSerQNames.add(qName);
      cls = GetWorkItemDataSlotsFromWIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemDataSlotsFromWIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getWorkItemDataSlotsFromWIIDReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemDataSlotsResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getWorkItemDataSlotsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemFromID");
      this.cachedSerQNames.add(qName);
      cls = GetWorkItemFromID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemFromIDResponse");
      this.cachedSerQNames.add(qName);
      cls = GetWorkItemFromIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = GetWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstance");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceDataSlots");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepInstanceDataSlots.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceDataSlotsFromPIID");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepInstanceDataSlotsFromPIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceDataSlotsFromPIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceDataSlotsFromPIIDReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceDataSlotsResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceDataSlotsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceFromPIID");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepInstanceFromPIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceFromPIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepInstanceFromPIIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplate");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepTemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplateDataSlots");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepTemplateDataSlots.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplateDataSlotsFromPTID");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepTemplateDataSlotsFromPTID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplateDataSlotsFromPTIDResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotTemplate[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateDataSlotsFromPTIDReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplateDataSlotsResponse");
      this.cachedSerQNames.add(qName);
      cls = DataSlotTemplate[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate");
      qName2 = new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateDataSlotsReturn");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://workflow.webservice.savvion.com", ">getWorkStepTemplateResponse");
      this.cachedSerQNames.add(qName);
      cls = GetWorkStepTemplateResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">isProcessTemplateExist");
      this.cachedSerQNames.add(qName);
      cls = IsProcessTemplateExist.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">isProcessTemplateExistResponse");
      this.cachedSerQNames.add(qName);
      cls = IsProcessTemplateExistResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">isSessionValid");
      this.cachedSerQNames.add(qName);
      cls = IsSessionValid.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">isSessionValidResponse");
      this.cachedSerQNames.add(qName);
      cls = IsSessionValidResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">makeAvailableWorkItem");
      this.cachedSerQNames.add(qName);
      cls = MakeAvailableWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">makeAvailableWorkItemFromWIID");
      this.cachedSerQNames.add(qName);
      cls = MakeAvailableWorkItemFromWIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">makeAvailableWorkItemFromWIIDResponse");
      this.cachedSerQNames.add(qName);
      cls = MakeAvailableWorkItemFromWIIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">makeAvailableWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = MakeAvailableWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">reAssignWorkItem");
      this.cachedSerQNames.add(qName);
      cls = ReAssignWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">reAssignWorkItemFromWIID");
      this.cachedSerQNames.add(qName);
      cls = ReAssignWorkItemFromWIID.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
   }

   private void addBindings1() {
      Class beansf = BeanSerializerFactory.class;
      Class beandf = BeanDeserializerFactory.class;
      Class enumsf = EnumSerializerFactory.class;
      Class enumdf = EnumDeserializerFactory.class;
      Class arraysf = ArraySerializerFactory.class;
      Class arraydf = ArrayDeserializerFactory.class;
      Class simplesf = SimpleSerializerFactory.class;
      Class simpledf = SimpleDeserializerFactory.class;
      Class simplelistsf = SimpleListSerializerFactory.class;
      Class simplelistdf = SimpleListDeserializerFactory.class;
      QName qName = new QName("http://workflow.webservice.savvion.com", ">reAssignWorkItemFromWIIDResponse");
      this.cachedSerQNames.add(qName);
      Class cls = ReAssignWorkItemFromWIIDResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">reAssignWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = ReAssignWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">removeProcessTemplate");
      this.cachedSerQNames.add(qName);
      cls = RemoveProcessTemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">removeProcessTemplateResponse");
      this.cachedSerQNames.add(qName);
      cls = RemoveProcessTemplateResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">resumeProcessInstance");
      this.cachedSerQNames.add(qName);
      cls = ResumeProcessInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">resumeProcessInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = ResumeProcessInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">resumeWorkItem");
      this.cachedSerQNames.add(qName);
      cls = ResumeWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">resumeWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = ResumeWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">resumeWorkStepInstance");
      this.cachedSerQNames.add(qName);
      cls = ResumeWorkStepInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">resumeWorkStepInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = ResumeWorkStepInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDataSlotsValue");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstanceDataSlotsValue.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDataSlotsValueResponse");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstanceDataSlotsValueResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDataSlotValue");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstanceDataSlotValue.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDataSlotValueResponse");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstanceDataSlotValueResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDueDate");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstanceDueDate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstanceDueDateResponse");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstanceDueDateResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstancePriority");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstancePriority.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessInstancePriorityResponse");
      this.cachedSerQNames.add(qName);
      cls = SetProcessInstancePriorityResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessTemplateDataSlotsValue");
      this.cachedSerQNames.add(qName);
      cls = SetProcessTemplateDataSlotsValue.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessTemplateDataSlotsValueResponse");
      this.cachedSerQNames.add(qName);
      cls = SetProcessTemplateDataSlotsValueResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessTemplateDataSlotValue");
      this.cachedSerQNames.add(qName);
      cls = SetProcessTemplateDataSlotValue.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setProcessTemplateDataSlotValueResponse");
      this.cachedSerQNames.add(qName);
      cls = SetProcessTemplateDataSlotValueResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setWorkItemDataSlotsValue");
      this.cachedSerQNames.add(qName);
      cls = SetWorkItemDataSlotsValue.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setWorkItemDataSlotsValueResponse");
      this.cachedSerQNames.add(qName);
      cls = SetWorkItemDataSlotsValueResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setWorkItemDataSlotValue");
      this.cachedSerQNames.add(qName);
      cls = SetWorkItemDataSlotValue.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">setWorkItemDataSlotValueResponse");
      this.cachedSerQNames.add(qName);
      cls = SetWorkItemDataSlotValueResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">suspendProcessInstance");
      this.cachedSerQNames.add(qName);
      cls = SuspendProcessInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">suspendProcessInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = SuspendProcessInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">suspendWorkItem");
      this.cachedSerQNames.add(qName);
      cls = SuspendWorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">suspendWorkItemResponse");
      this.cachedSerQNames.add(qName);
      cls = SuspendWorkItemResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">suspendWorkStepInstance");
      this.cachedSerQNames.add(qName);
      cls = SuspendWorkStepInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", ">suspendWorkStepInstanceResponse");
      this.cachedSerQNames.add(qName);
      cls = SuspendWorkStepInstanceResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotinstance");
      this.cachedSerQNames.add(qName);
      cls = DataSlotinstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "DataSlotTemplate");
      this.cachedSerQNames.add(qName);
      cls = DataSlotTemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "ProcessInstance");
      this.cachedSerQNames.add(qName);
      cls = ProcessInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "ProcessTemplate");
      this.cachedSerQNames.add(qName);
      cls = ProcessTemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkItem");
      this.cachedSerQNames.add(qName);
      cls = WorkItem.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkStepInstance");
      this.cachedSerQNames.add(qName);
      cls = WorkStepInstance.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate");
      this.cachedSerQNames.add(qName);
      cls = WorkSteptemplate.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
   }

   protected Call createCall() throws RemoteException {
      try {
         Call _call = super._createCall();
         if (super.maintainSessionSet) {
            _call.setMaintainSession(super.maintainSession);
         }

         if (super.cachedUsername != null) {
            _call.setUsername(super.cachedUsername);
         }

         if (super.cachedPassword != null) {
            _call.setPassword(super.cachedPassword);
         }

         if (super.cachedEndpoint != null) {
            _call.setTargetEndpointAddress(super.cachedEndpoint);
         }

         if (super.cachedTimeout != null) {
            _call.setTimeout(super.cachedTimeout);
         }

         if (super.cachedPortName != null) {
            _call.setPortName(super.cachedPortName);
         }

         Enumeration keys = super.cachedProperties.keys();

         while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            _call.setProperty(key, super.cachedProperties.get(key));
         }

         synchronized(this) {
            if (this.firstCall()) {
               _call.setEncodingStyle((String)null);

               for(int i = 0; i < this.cachedSerFactories.size(); ++i) {
                  Class cls = (Class)((Class)this.cachedSerClasses.get(i));
                  QName qName = (QName)this.cachedSerQNames.get(i);
                  Object x = this.cachedSerFactories.get(i);
                  if (x instanceof Class) {
                     Class sf = (Class)((Class)this.cachedSerFactories.get(i));
                     Class df = (Class)((Class)this.cachedDeserFactories.get(i));
                     _call.registerTypeMapping(cls, qName, sf, df, false);
                  } else if (x instanceof SerializerFactory) {
                     org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
                     DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
                     _call.registerTypeMapping(cls, qName, sf, df, false);
                  }
               }
            }
         }

         return _call;
      } catch (Throwable var11) {
         throw new AxisFault("Failure trying to get the Call object", var11);
      }
   }

   public String connect(String userId, String password) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[0]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "connect"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{userId, password});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var6) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public void disconnect(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[1]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "disconnect"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var4) {
            throw var4;
         }
      }
   }

   public String getStatus() throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[2]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getStatus"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[0]);
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var4) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public boolean isSessionValid(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[3]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "isSessionValid"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (Boolean)_resp;
               } catch (Exception var5) {
                  return (Boolean)JavaUtils.convert(_resp, Boolean.TYPE);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void completeWorkItem(String session, String wiName, DataSlotinstance[] dsi) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[4]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "completeWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName, dsi});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public ProcessInstance[] getProcessInstanceList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[5]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessInstance[])_resp;
               } catch (Exception var5) {
                  return (ProcessInstance[])JavaUtils.convert(_resp, ProcessInstance[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void removeProcessTemplate(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[6]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "removeProcessTemplate"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public ProcessTemplate getProcessTemplate(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[7]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplate"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessTemplate)_resp;
               } catch (Exception var6) {
                  return (ProcessTemplate)JavaUtils.convert(_resp, ProcessTemplate.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public void setProcessInstancePriority(String session, String piName, String priority) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[8]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setProcessInstancePriority"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, priority});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public long getProcessTemplateID(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[9]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (Long)_resp;
               } catch (Exception var6) {
                  return (Long)JavaUtils.convert(_resp, Long.TYPE);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public String[] getProcessTemplateVersions(String session, String appName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[10]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateVersions"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, appName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String[])_resp;
               } catch (Exception var6) {
                  return (String[])JavaUtils.convert(_resp, String[].class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public String getProcessTemplateXML(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[11]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateXML"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var6) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public ProcessTemplate[] getUserAuthorizedProcessTemplateList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[12]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getUserAuthorizedProcessTemplateList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessTemplate[])_resp;
               } catch (Exception var5) {
                  return (ProcessTemplate[])JavaUtils.convert(_resp, ProcessTemplate[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public ProcessInstance createProcessInstance(String session, String ptName, String piNamePrefix, String priority, DataSlotTemplate[] dst) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[13]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "createProcessInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName, piNamePrefix, priority, dst});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessInstance)_resp;
               } catch (Exception var9) {
                  return (ProcessInstance)JavaUtils.convert(_resp, ProcessInstance.class);
               }
            }
         } catch (AxisFault var10) {
            throw var10;
         }
      }
   }

   public void resumeProcessInstance(String session, String piName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[14]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "resumeProcessInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public void suspendProcessInstance(String session, String piName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[15]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "suspendProcessInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public boolean isProcessTemplateExist(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[16]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "isProcessTemplateExist"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (Boolean)_resp;
               } catch (Exception var6) {
                  return (Boolean)JavaUtils.convert(_resp, Boolean.TYPE);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public ProcessInstance getProcessInstance(String session, String piName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[17]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessInstance)_resp;
               } catch (Exception var6) {
                  return (ProcessInstance)JavaUtils.convert(_resp, ProcessInstance.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public WorkSteptemplate getWorkStepTemplate(String session, String ptName, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[18]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplate"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName, wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkSteptemplate)_resp;
               } catch (Exception var7) {
                  return (WorkSteptemplate)JavaUtils.convert(_resp, WorkSteptemplate.class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public WorkStepInstance getWorkStepInstance(String session, String piName, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[19]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkStepInstance)_resp;
               } catch (Exception var7) {
                  return (WorkStepInstance)JavaUtils.convert(_resp, WorkStepInstance.class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public WorkItem getWorkItem(String session, String wiName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[20]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem)_resp;
               } catch (Exception var6) {
                  return (WorkItem)JavaUtils.convert(_resp, WorkItem.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public WorkItem[] getAvailableWorkItemList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[21]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getAvailableWorkItemList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem[])_resp;
               } catch (Exception var5) {
                  return (WorkItem[])JavaUtils.convert(_resp, WorkItem[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public WorkItem[] getAssignedWorkItemList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[22]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getAssignedWorkItemList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem[])_resp;
               } catch (Exception var5) {
                  return (WorkItem[])JavaUtils.convert(_resp, WorkItem[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public WorkItem[] getProxyAssignedWorkItemList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[23]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProxyAssignedWorkItemList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem[])_resp;
               } catch (Exception var5) {
                  return (WorkItem[])JavaUtils.convert(_resp, WorkItem[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public WorkItem[] getProxyAvailableWorkItemList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[24]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProxyAvailableWorkItemList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem[])_resp;
               } catch (Exception var5) {
                  return (WorkItem[])JavaUtils.convert(_resp, WorkItem[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public WorkItem[] getSuspendedWorkItemList(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[25]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getSuspendedWorkItemList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem[])_resp;
               } catch (Exception var5) {
                  return (WorkItem[])JavaUtils.convert(_resp, WorkItem[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public String getProcessTemplateAppName(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[26]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateAppName"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var6) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public String getProcessTemplateNameFromProcessInstance(String session, String piName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[27]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateNameFromProcessInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var6) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public String getProcessTemplateNameFromProcessID(String session, long ptID) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[28]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateNameFromProcessID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(ptID)});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var7) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public WorkSteptemplate[] getProcessTemplateWorkSteps(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[29]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateWorkSteps"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkSteptemplate[])_resp;
               } catch (Exception var6) {
                  return (WorkSteptemplate[])JavaUtils.convert(_resp, WorkSteptemplate[].class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public String[] getUserAuthorizedProcessTemplateNames(String session) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[30]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getUserAuthorizedProcessTemplateNames"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String[])_resp;
               } catch (Exception var5) {
                  return (String[])JavaUtils.convert(_resp, String[].class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public ProcessTemplate getProcessTemplateFromID(String session, long ptid) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[31]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateFromID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(ptid)});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessTemplate)_resp;
               } catch (Exception var7) {
                  return (ProcessTemplate)JavaUtils.convert(_resp, ProcessTemplate.class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public DataSlotTemplate[] getProcessTemplateDataSlots(String session, String ptName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[32]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateDataSlots"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotTemplate[])_resp;
               } catch (Exception var6) {
                  return (DataSlotTemplate[])JavaUtils.convert(_resp, DataSlotTemplate[].class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public DataSlotTemplate[] getProcessTemplateDataSlot(String session, String ptName, String[] dsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[33]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessTemplateDataSlot"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName, dsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotTemplate[])_resp;
               } catch (Exception var7) {
                  return (DataSlotTemplate[])JavaUtils.convert(_resp, DataSlotTemplate[].class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public DataSlotinstance[] getProcessInstanceDataSlots(String session, String piName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[34]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlots"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var6) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public DataSlotinstance[] getProcessInstanceDataSlot(String session, String piName, String[] dsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[35]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlot"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, dsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var7) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public DataSlotinstance[] getProcessInstanceDataSlotFromPIID(String session, long piid, String[] dsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[36]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceDataSlotFromPIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(piid), dsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var8) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var9) {
            throw var9;
         }
      }
   }

   public DataSlotinstance[] getWorkStepInstanceDataSlots(String session, String piName, String wsiName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[37]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceDataSlots"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, wsiName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var7) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public DataSlotinstance[] getWorkStepInstanceDataSlotsFromPIID(String session, long piid, String wsiName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[38]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceDataSlotsFromPIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(piid), wsiName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var8) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var9) {
            throw var9;
         }
      }
   }

   public DataSlotinstance[] getWorkItemDataSlots(String session, String wiName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[39]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkItemDataSlots"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var6) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public DataSlotinstance[] getWorkItemDataSlotsFromWIID(String session, long wiid) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[40]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkItemDataSlotsFromWIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(wiid)});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotinstance[])_resp;
               } catch (Exception var7) {
                  return (DataSlotinstance[])JavaUtils.convert(_resp, DataSlotinstance[].class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public DataSlotTemplate[] getWorkStepTemplateDataSlots(String session, String ptName, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[41]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateDataSlots"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName, wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotTemplate[])_resp;
               } catch (Exception var7) {
                  return (DataSlotTemplate[])JavaUtils.convert(_resp, DataSlotTemplate[].class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public DataSlotTemplate[] getWorkStepTemplateDataSlotsFromPTID(String session, long ptid, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[42]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepTemplateDataSlotsFromPTID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(ptid), wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (DataSlotTemplate[])_resp;
               } catch (Exception var8) {
                  return (DataSlotTemplate[])JavaUtils.convert(_resp, DataSlotTemplate[].class);
               }
            }
         } catch (AxisFault var9) {
            throw var9;
         }
      }
   }

   public void setProcessTemplateDataSlotValue(String session, String ptName, DataSlotTemplate dst) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[43]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setProcessTemplateDataSlotValue"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName, dst});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void setProcessTemplateDataSlotsValue(String session, String ptName, DataSlotTemplate[] dst) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[44]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setProcessTemplateDataSlotsValue"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptName, dst});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void setProcessInstanceDataSlotValue(String session, String piName, DataSlotinstance dsi) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[45]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setProcessInstanceDataSlotValue"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, dsi});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void setProcessInstanceDataSlotsValue(String session, String piName, DataSlotinstance[] dsi) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[46]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setProcessInstanceDataSlotsValue"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, dsi});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void setWorkItemDataSlotsValue(String session, String wiName, DataSlotinstance[] dsi) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[47]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setWorkItemDataSlotsValue"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName, dsi});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void setWorkItemDataSlotValue(String session, String wiName, DataSlotinstance dsi) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[48]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setWorkItemDataSlotValue"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName, dsi});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public ProcessInstance getProcessInstanceFromID(String session, long piid) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[49]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceFromID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(piid)});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ProcessInstance)_resp;
               } catch (Exception var7) {
                  return (ProcessInstance)JavaUtils.convert(_resp, ProcessInstance.class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public void setProcessInstanceDueDate(String session, String piName, Calendar dueDate) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[50]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "setProcessInstanceDueDate"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, dueDate});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public WorkItem getWorkItemFromID(String session, long wiid) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[51]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkItemFromID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(wiid)});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkItem)_resp;
               } catch (Exception var7) {
                  return (WorkItem)JavaUtils.convert(_resp, WorkItem.class);
               }
            }
         } catch (AxisFault var8) {
            throw var8;
         }
      }
   }

   public void suspendWorkItem(String session, String wiName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[52]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "suspendWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public void resumeWorkItem(String session, String wiName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[53]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "resumeWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public void assignWorkItem(String session, String wiName, String performer) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[54]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "assignWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName, performer});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void assignWorkItemFromWIID(String session, long wiid, String performer) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[55]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "assignWorkItemFromWIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(wiid), performer});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public void reAssignWorkItem(String session, String wiName, String performer) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[56]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "reAssignWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName, performer});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void reAssignWorkItemFromWIID(String session, long wiid, String performer) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[57]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "reAssignWorkItemFromWIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(wiid), performer});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public void makeAvailableWorkItem(String session, String wiName, String[] performers) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[58]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "makeAvailableWorkItem"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, wiName, performers});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void makeAvailableWorkItemFromWIID(String session, long wiid, String[] performers) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[59]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "makeAvailableWorkItemFromWIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(wiid), performers});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public void completeWorkItemFromWIID(String session, long wiid, DataSlotinstance[] dsi) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[60]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "completeWorkItemFromWIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(wiid), dsi});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public WorkStepInstance getWorkStepInstanceFromPIID(String session, long piid, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[61]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getWorkStepInstanceFromPIID"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, new Long(piid), wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkStepInstance)_resp;
               } catch (Exception var8) {
                  return (WorkStepInstance)JavaUtils.convert(_resp, WorkStepInstance.class);
               }
            }
         } catch (AxisFault var9) {
            throw var9;
         }
      }
   }

   public WorkStepInstance[] getProcessInstanceWorkSteps(String session, String piName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[62]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getProcessInstanceWorkSteps"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkStepInstance[])_resp;
               } catch (Exception var6) {
                  return (WorkStepInstance[])JavaUtils.convert(_resp, WorkStepInstance[].class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public void suspendWorkStepInstance(String session, String piName, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[63]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "suspendWorkStepInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void resumeWorkStepInstance(String session, String piName, String wsName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[64]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "resumeWorkStepInstance"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, piName, wsName});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public WorkSteptemplate getStartWorkStepTemplate(String session, String ptname) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[65]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://workflow.webservice.savvion.com", "getStartWorkStepTemplate"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{session, ptname});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (WorkSteptemplate)_resp;
               } catch (Exception var6) {
                  return (WorkSteptemplate)JavaUtils.convert(_resp, WorkSteptemplate.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }
}
