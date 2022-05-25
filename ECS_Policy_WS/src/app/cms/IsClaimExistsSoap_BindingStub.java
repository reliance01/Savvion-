package app.cms;

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

public class IsClaimExistsSoap_BindingStub extends Stub implements IsClaimExistsSoap_PortType {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[3];

   private static void _initOperationDesc1() {
      OperationDesc oper = new OperationDesc();
      oper.setName("CheckClaimExistOnPolicy");
      ParameterDesc param = new ParameterDesc(new QName("http://tempuri.org/", "PolicyNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://tempuri.org/", ">>CheckClaimExistOnPolicyResponse>CheckClaimExistOnPolicyResult"));
      oper.setReturnClass(CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "CheckClaimExistOnPolicyResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[0] = oper;
      oper = new OperationDesc();
      oper.setName("isClaimExist");
      param = new ParameterDesc(new QName("http://tempuri.org/", "PolicyNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "CoverNoteNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "isClaimExistResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[1] = oper;
      oper = new OperationDesc();
      oper.setName("getClaimStatusForPolicy");
      param = new ParameterDesc(new QName("http://tempuri.org/", "PolicyNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://tempuri.org/", "StatusResponce"));
      oper.setReturnClass(StatusResponce.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "getClaimStatusForPolicyResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[2] = oper;
   }

   public IsClaimExistsSoap_BindingStub() throws AxisFault {
      this((Service)null);
   }

   public IsClaimExistsSoap_BindingStub(URL endpointURL, Service service) throws AxisFault {
      this(service);
      super.cachedEndpoint = endpointURL;
   }

   public IsClaimExistsSoap_BindingStub(Service service) throws AxisFault {
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
      QName qName = new QName("http://tempuri.org/", ">>CheckClaimExistOnPolicyResponse>CheckClaimExistOnPolicyResult");
      this.cachedSerQNames.add(qName);
      Class cls = CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://tempuri.org/", ">getClaimStatusForPolicy");
      this.cachedSerQNames.add(qName);
      cls = GetClaimStatusForPolicy.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://tempuri.org/", ">getClaimStatusForPolicyResponse");
      this.cachedSerQNames.add(qName);
      cls = GetClaimStatusForPolicyResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://tempuri.org/", ">isClaimExist");
      this.cachedSerQNames.add(qName);
      cls = IsClaimExist.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://tempuri.org/", ">isClaimExistResponse");
      this.cachedSerQNames.add(qName);
      cls = IsClaimExistResponse.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://tempuri.org/", "StatusResponce");
      this.cachedSerQNames.add(qName);
      cls = StatusResponce.class;
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
      } catch (Throwable var12) {
         throw new AxisFault("Failure trying to get the Call object", var12);
      }
   }

   public CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult checkClaimExistOnPolicy(String policyNo) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[0]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/CheckClaimExistOnPolicy");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "CheckClaimExistOnPolicy"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{policyNo});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult)_resp;
               } catch (Exception var5) {
                  return (CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult)JavaUtils.convert(_resp, CheckClaimExistOnPolicyResponseCheckClaimExistOnPolicyResult.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   public String isClaimExist(String policyNo, String coverNoteNo) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[1]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/isClaimExist");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "isClaimExist"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{policyNo, coverNoteNo});
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

   public StatusResponce getClaimStatusForPolicy(String policyNo) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[2]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/getClaimStatusForPolicy");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "getClaimStatusForPolicy"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{policyNo});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (StatusResponce)_resp;
               } catch (Exception var5) {
                  return (StatusResponce)JavaUtils.convert(_resp, StatusResponce.class);
               }
            }
         } catch (AxisFault var6) {
            throw var6;
         }
      }
   }

   static {
      _initOperationDesc1();
   }
}
