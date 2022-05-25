package com.savvion.mom;

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

public class Service1Soap12Stub extends Stub implements Service1Soap_PortType {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[9];

   static {
      _initOperationDesc1();
   }

   private static void _initOperationDesc1() {
      OperationDesc oper = new OperationDesc();
      oper.setName("HelloWorld");
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "HelloWorldResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[0] = oper;
      oper = new OperationDesc();
      oper.setName("GetRegions");
      oper.setReturnType(new QName("http://tempuri.org/", "ArrayOfString"));
      oper.setReturnClass(String[].class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetRegionsResult"));
      ParameterDesc param = oper.getReturnParamDesc();
      param.setItemQName(new QName("http://tempuri.org/", "string"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[1] = oper;
      oper = new OperationDesc();
      oper.setName("GetRegionBranchs");
      param = new ParameterDesc(new QName("http://tempuri.org/", "regionName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "branchType"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://tempuri.org/", "ArrayOfString"));
      oper.setReturnClass(String[].class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetRegionBranchsResult"));
      param = oper.getReturnParamDesc();
      param.setItemQName(new QName("http://tempuri.org/", "string"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[2] = oper;
      oper = new OperationDesc();
      oper.setName("GetRegionFromBranch");
      param = new ParameterDesc(new QName("http://tempuri.org/", "branchName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetRegionFromBranchResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[3] = oper;
      oper = new OperationDesc();
      oper.setName("GetBMDetails");
      param = new ParameterDesc(new QName("http://tempuri.org/", "branchName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://tempuri.org/", "ArrayOfString"));
      oper.setReturnClass(String[].class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetBMDetailsResult"));
      param = oper.getReturnParamDesc();
      param.setItemQName(new QName("http://tempuri.org/", "string"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[4] = oper;
      oper = new OperationDesc();
      oper.setName("GetBSMDetails");
      param = new ParameterDesc(new QName("http://tempuri.org/", "branchName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://tempuri.org/", "ArrayOfString"));
      oper.setReturnClass(String[].class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetBSMDetailsResult"));
      param = oper.getReturnParamDesc();
      param.setItemQName(new QName("http://tempuri.org/", "string"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[5] = oper;
      oper = new OperationDesc();
      oper.setName("GetZHDetails");
      param = new ParameterDesc(new QName("http://tempuri.org/", "regionName"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetZHDetailsResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[6] = oper;
      oper = new OperationDesc();
      oper.setName("GetEmployeeEmailId");
      param = new ParameterDesc(new QName("http://tempuri.org/", "employeeCode"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetEmployeeEmailIdResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[7] = oper;
      oper = new OperationDesc();
      oper.setName("GetEmployeeContactNo");
      param = new ParameterDesc(new QName("http://tempuri.org/", "employeeCode"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "GetEmployeeContactNoResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[8] = oper;
   }

   public Service1Soap12Stub() throws AxisFault {
      this((Service)null);
   }

   public Service1Soap12Stub(URL endpointURL, Service service) throws AxisFault {
      this(service);
      super.cachedEndpoint = endpointURL;
   }

   public Service1Soap12Stub(Service service) throws AxisFault {
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
      QName qName = new QName("http://tempuri.org/", "ArrayOfString");
      this.cachedSerQNames.add(qName);
      Class cls = String[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://www.w3.org/2001/XMLSchema", "string");
      QName qName2 = new QName("http://tempuri.org/", "string");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
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

   public String helloWorld() throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[0]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/HelloWorld");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "HelloWorld"));
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

   public String[] getRegions() throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[1]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetRegions");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetRegions"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[0]);
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String[])_resp;
               } catch (Exception var4) {
                  return (String[])JavaUtils.convert(_resp, String[].class);
               }
            }
         } catch (AxisFault var5) {
            throw var5;
         }
      }
   }

   public String[] getRegionBranchs(String regionName, String branchType) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[2]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetRegionBranchs");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetRegionBranchs"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{regionName, branchType});
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

   public String getRegionFromBranch(String branchName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[3]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetRegionFromBranch");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetRegionFromBranch"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{branchName});
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

   public String[] getBMDetails(String branchName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[4]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetBMDetails");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetBMDetails"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{branchName});
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

   public String[] getBSMDetails(String branchName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[5]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetBSMDetails");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetBSMDetails"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{branchName});
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

   public String getZHDetails(String regionName) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[6]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetZHDetails");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetZHDetails"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{regionName});
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

   public String getEmployeeEmailId(String employeeCode) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[7]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetEmployeeEmailId");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetEmployeeEmailId"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{employeeCode});
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

   public String getEmployeeContactNo(String employeeCode) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[8]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/GetEmployeeContactNo");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "GetEmployeeContactNo"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{employeeCode});
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
}
