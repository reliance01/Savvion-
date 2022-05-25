package com.savvion.webservices;

import java.net.URL;
import java.rmi.RemoteException;
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

public class RGICL_MotorUW_AppSoapBindingStub extends Stub implements com.savvion.webservices.WS_RGICL_MotorUW_App {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[10];

   static {
      _initOperationDesc1();
   }

   private static void _initOperationDesc1() {
      OperationDesc oper = new OperationDesc();
      oper.setName("getUnderWriterDetails");
      ParameterDesc param = new ParameterDesc(new QName("", "BranchCode"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("", "getUnderWriterDetailsReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[0] = oper;
      oper = new OperationDesc();
      oper.setName("initiateMotorIRPASFlow");
      param = new ParameterDesc(new QName("", "reqObj"), (byte)1, new QName("http://rgicl.motor.savvion.com", "WorkItemObject"), WorkItemObject.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://rgicl.motor.savvion.com", "ResponseObject"));
      oper.setReturnClass(ResponseObject.class);
      oper.setReturnQName(new QName("", "initiateMotorIRPASFlowReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[1] = oper;
      oper = new OperationDesc();
      oper.setName("getIRPASMotorTaskList");
      param = new ParameterDesc(new QName("", "reqObj"), (byte)1, new QName("http://rgicl.motor.savvion.com", "WorkItemObject"), WorkItemObject.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://rgicl.motor.savvion.com", "ResponseObject"));
      oper.setReturnClass(ResponseObject.class);
      oper.setReturnQName(new QName("", "getIRPASMotorTaskListReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[2] = oper;
      oper = new OperationDesc();
      oper.setName("checkProposalExists");
      param = new ParameterDesc(new QName("", "proposalNumber"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      oper.setReturnClass(Boolean.TYPE);
      oper.setReturnQName(new QName("", "checkProposalExistsReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[3] = oper;
      oper = new OperationDesc();
      oper.setName("deleteProposalQuoteFromSavvion");
      param = new ParameterDesc(new QName("", "proposalNumber"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("", "deleteProposalQuoteFromSavvionReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[4] = oper;
      oper = new OperationDesc();
      oper.setName("getActionDetails");
      param = new ParameterDesc(new QName("", "proposalNumber"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("", "getActionDetailsReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[5] = oper;
      oper = new OperationDesc();
      oper.setName("getRuntimeActionDetails");
      param = new ParameterDesc(new QName("", "proposalNumber"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("", "newAuthCode"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("", "getRuntimeActionDetailsReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[6] = oper;
      oper = new OperationDesc();
      oper.setName("UpdateMotorIRPASSetUpDataSlots");
      param = new ParameterDesc(new QName("", "reqObj"), (byte)1, new QName("http://rgicl.motor.savvion.com", "WorkItemObject"), WorkItemObject.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://rgicl.motor.savvion.com", "ResponseObject"));
      oper.setReturnClass(ResponseObject.class);
      oper.setReturnQName(new QName("", "UpdateMotorIRPASSetUpDataSlotsReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[7] = oper;
      oper = new OperationDesc();
      oper.setName("getHistory");
      param = new ParameterDesc(new QName("", "proposalNo"), (byte)1, new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), String.class, false, false);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("", "getHistoryReturn"));
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[8] = oper;
      oper = new OperationDesc();
      oper.setName("main");
      param = new ParameterDesc(new QName("", "args"), (byte)1, new QName("http://rgicl.motor.savvion.com", "ArrayOf_soapenc_string"), String[].class, false, false);
      oper.addParameter(param);
      oper.setReturnType(XMLType.AXIS_VOID);
      oper.setStyle(Style.RPC);
      oper.setUse(Use.ENCODED);
      _operations[9] = oper;
   }

   public RGICL_MotorUW_AppSoapBindingStub() throws AxisFault {
      this((Service)null);
   }

   public RGICL_MotorUW_AppSoapBindingStub(URL endpointURL, Service service) throws AxisFault {
      this(service);
      super.cachedEndpoint = endpointURL;
   }

   public RGICL_MotorUW_AppSoapBindingStub(Service service) throws AxisFault {
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
      QName qName = new QName("http://rgicl.motor.savvion.com", "ArrayOf_soapenc_string");
      this.cachedSerQNames.add(qName);
      Class cls = String[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
      QName qName2 = null;
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, (QName)qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://rgicl.motor.savvion.com", "ArrayOfWorkItemObject");
      this.cachedSerQNames.add(qName);
      cls = WorkItemObject[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://rgicl.motor.savvion.com", "WorkItemObject");
      qName2 = null;
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, (QName)qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://rgicl.motor.savvion.com", "ResponseObject");
      this.cachedSerQNames.add(qName);
      cls = ResponseObject.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://rgicl.motor.savvion.com", "WorkItemObject");
      this.cachedSerQNames.add(qName);
      cls = WorkItemObject.class;
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
               _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
               _call.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

               for(int i = 0; i < this.cachedSerFactories.size(); ++i) {
                  Class cls = (Class)this.cachedSerClasses.get(i);
                  QName qName = (QName)this.cachedSerQNames.get(i);
                  Object x = this.cachedSerFactories.get(i);
                  if (x instanceof Class) {
                     Class sf = (Class)this.cachedSerFactories.get(i);
                     Class df = (Class)this.cachedDeserFactories.get(i);
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

   public String getUnderWriterDetails(String branchCode) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[0]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "getUnderWriterDetails"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{branchCode});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var5) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public ResponseObject initiateMotorIRPASFlow(WorkItemObject reqObj) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[1]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "initiateMotorIRPASFlow"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{reqObj});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ResponseObject)_resp;
               } catch (Exception var5) {
                  return (ResponseObject)JavaUtils.convert(_resp, ResponseObject.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public ResponseObject getIRPASMotorTaskList(WorkItemObject reqObj) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[2]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "getIRPASMotorTaskList"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{reqObj});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ResponseObject)_resp;
               } catch (Exception var5) {
                  return (ResponseObject)JavaUtils.convert(_resp, ResponseObject.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public boolean checkProposalExists(String proposalNumber) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[3]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "checkProposalExists"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{proposalNumber});
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

   public String deleteProposalQuoteFromSavvion(String proposalNumber) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[4]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "deleteProposalQuoteFromSavvion"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{proposalNumber});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var5) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public String getActionDetails(String proposalNumber) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[5]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "getActionDetails"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{proposalNumber});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var5) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public String getRuntimeActionDetails(String proposalNumber, String newAuthCode) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[6]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "getRuntimeActionDetails"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{proposalNumber, newAuthCode});
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

   public ResponseObject updateMotorIRPASSetUpDataSlots(WorkItemObject reqObj) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[7]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "UpdateMotorIRPASSetUpDataSlots"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{reqObj});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (ResponseObject)_resp;
               } catch (Exception var5) {
                  return (ResponseObject)JavaUtils.convert(_resp, ResponseObject.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public String getHistory(String proposalNo) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[8]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "getHistory"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{proposalNo});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var5) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public void main(String[] args) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[9]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("");
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://rgicl.motor.savvion.com", "main"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{args});
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
}
