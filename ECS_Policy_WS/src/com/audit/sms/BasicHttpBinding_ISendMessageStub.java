package com.audit.sms;

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

public class BasicHttpBinding_ISendMessageStub extends Stub implements ISendMessage {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[1];

   static {
      _initOperationDesc1();
   }

   private static void _initOperationDesc1() {
      OperationDesc oper = new OperationDesc();
      oper.setName("Send");
      ParameterDesc param = new ParameterDesc(new QName("http://tempuri.org/", "smsMsg"), (byte)1, new QName("http://schemas.datacontract.org/2004/07/", "SingleMessage"), SingleMessage.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "SendResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[0] = oper;
   }

   public BasicHttpBinding_ISendMessageStub() throws AxisFault {
      this((Service)null);
   }

   public BasicHttpBinding_ISendMessageStub(URL endpointURL, Service service) throws AxisFault {
      this(service);
      super.cachedEndpoint = endpointURL;
   }

   public BasicHttpBinding_ISendMessageStub(Service service) throws AxisFault {
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
      QName qName = new QName("http://schemas.datacontract.org/2004/07/", "SingleMessage");
      this.cachedSerQNames.add(qName);
      Class cls = SingleMessage.class;
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
      } catch (Throwable var11) {
         throw new AxisFault("Failure trying to get the Call object", var11);
      }
   }

   public String send(SingleMessage smsMsg) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[0]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/ISendMessage/Send");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "Send"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{smsMsg});
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
