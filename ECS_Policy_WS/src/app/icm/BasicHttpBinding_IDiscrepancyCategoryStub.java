package app.icm;

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

public class BasicHttpBinding_IDiscrepancyCategoryStub extends Stub implements IDiscrepancyCategory {
   private Vector cachedSerClasses;
   private Vector cachedSerQNames;
   private Vector cachedSerFactories;
   private Vector cachedDeserFactories;
   static OperationDesc[] _operations = new OperationDesc[5];

   private static void _initOperationDesc1() {
      OperationDesc oper = new OperationDesc();
      oper.setName("AddDiscrepancyDetailsService");
      ParameterDesc param = new ParameterDesc(new QName("http://tempuri.org/", "strProposalNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "_discrepancyBOList"), (byte)1, new QName("http://schemas.datacontract.org/2004/07/ICMBO", "ArrayOfDiscrepancyCategoryBO"), DiscrepancyCategoryBO[].class, false, false);
      param.setItemQName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO"));
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      oper.setReturnClass(Boolean.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "AddDiscrepancyDetailsServiceResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[0] = oper;
      oper = new OperationDesc();
      oper.setName("ResolveDiscrepancy");
      param = new ParameterDesc(new QName("http://tempuri.org/", "objProposalDetailsBO"), (byte)1, new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"), ProposalDetailsBO.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "ResolveDiscrepancyResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[1] = oper;
      oper = new OperationDesc();
      oper.setName("ResolveDiscrepancySavvion");
      param = new ParameterDesc(new QName("http://tempuri.org/", "strProposalNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "strResolveRemark"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "strusername"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "strQcResolvedDate"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "SubDiscrepancyID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
      param.setOmittable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "ResolveDiscrepancySavvionResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[2] = oper;
      oper = new OperationDesc();
      oper.setName("CheckDiscrepancyExists");
      param = new ParameterDesc(new QName("http://tempuri.org/", "strProposalNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "CheckDiscrepancyExistsResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[3] = oper;
      oper = new OperationDesc();
      oper.setName("RaisedRemarkService");
      param = new ParameterDesc(new QName("http://tempuri.org/", "strProposalNo"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      param = new ParameterDesc(new QName("http://tempuri.org/", "strRaisedRemark"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
      param.setOmittable(true);
      param.setNillable(true);
      oper.addParameter(param);
      oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      oper.setReturnClass(String.class);
      oper.setReturnQName(new QName("http://tempuri.org/", "RaisedRemarkServiceResult"));
      oper.setStyle(Style.WRAPPED);
      oper.setUse(Use.LITERAL);
      _operations[4] = oper;
   }

   public BasicHttpBinding_IDiscrepancyCategoryStub() throws AxisFault {
      this((Service)null);
   }

   public BasicHttpBinding_IDiscrepancyCategoryStub(URL endpointURL, Service service) throws AxisFault {
      this(service);
      super.cachedEndpoint = endpointURL;
   }

   public BasicHttpBinding_IDiscrepancyCategoryStub(Service service) throws AxisFault {
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
      QName qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "ArrayOfDocumentMasterBO");
      this.cachedSerQNames.add(qName);
      Class cls = DocumentMasterBO[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO");
      QName qName2 = new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO");
      this.cachedSerQNames.add(qName);
      cls = DocumentMasterBO.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ArrayOfProposalDetailsBO");
      this.cachedSerQNames.add(qName);
      cls = ProposalDetailsBO[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO");
      qName2 = new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO");
      this.cachedSerQNames.add(qName);
      cls = ProposalDetailsBO.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO", "ArrayOfDiscrepancyCategoryBO");
      this.cachedSerQNames.add(qName);
      cls = DiscrepancyCategoryBO[].class;
      this.cachedSerClasses.add(cls);
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO");
      qName2 = new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO");
      this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
      this.cachedDeserFactories.add(new ArrayDeserializerFactory());
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO");
      this.cachedSerQNames.add(qName);
      cls = DiscrepancyCategoryBO.class;
      this.cachedSerClasses.add(cls);
      this.cachedSerFactories.add(beansf);
      this.cachedDeserFactories.add(beandf);
      qName = new QName("http://schemas.datacontract.org/2004/07/ICMBO", "UtilBO");
      this.cachedSerQNames.add(qName);
      cls = UtilBO.class;
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

   public Boolean addDiscrepancyDetailsService(String strProposalNo, DiscrepancyCategoryBO[] _discrepancyBOList) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[0]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/IDiscrepancyCategory/AddDiscrepancyDetailsService");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "AddDiscrepancyDetailsService"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{strProposalNo, _discrepancyBOList});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (Boolean)_resp;
               } catch (Exception var6) {
                  return (Boolean)JavaUtils.convert(_resp, Boolean.class);
               }
            }
         } catch (AxisFault var7) {
            throw var7;
         }
      }
   }

   public String resolveDiscrepancy(ProposalDetailsBO objProposalDetailsBO) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[1]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/IDiscrepancyCategory/ResolveDiscrepancy");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "ResolveDiscrepancy"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{objProposalDetailsBO});
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

   public String resolveDiscrepancySavvion(String strProposalNo, String strResolveRemark, String strusername, String strQcResolvedDate, Integer subDiscrepancyID) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[2]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/IDiscrepancyCategory/ResolveDiscrepancySavvion");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "ResolveDiscrepancySavvion"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{strProposalNo, strResolveRemark, strusername, strQcResolvedDate, subDiscrepancyID});
            if (_resp instanceof RemoteException) {
               throw (RemoteException)_resp;
            } else {
               this.extractAttachments(_call);

               try {
                  return (String)_resp;
               } catch (Exception var9) {
                  return (String)JavaUtils.convert(_resp, String.class);
               }
            }
         } catch (AxisFault var10) {
            throw var10;
         }
      }
   }

   public String checkDiscrepancyExists(String strProposalNo) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[3]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/IDiscrepancyCategory/CheckDiscrepancyExists");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "CheckDiscrepancyExists"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{strProposalNo});
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

   public String raisedRemarkService(String strProposalNo, String strRaisedRemark) throws RemoteException {
      if (super.cachedEndpoint == null) {
         throw new NoEndPointException();
      } else {
         Call _call = this.createCall();
         _call.setOperation(_operations[4]);
         _call.setUseSOAPAction(true);
         _call.setSOAPActionURI("http://tempuri.org/IDiscrepancyCategory/RaisedRemarkService");
         _call.setEncodingStyle((String)null);
         _call.setProperty("sendXsiTypes", Boolean.FALSE);
         _call.setProperty("sendMultiRefs", Boolean.FALSE);
         _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
         _call.setOperationName(new QName("http://tempuri.org/", "RaisedRemarkService"));
         this.setRequestHeaders(_call);
         this.setAttachments(_call);

         try {
            Object _resp = _call.invoke(new Object[]{strProposalNo, strRaisedRemark});
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

   static {
      _initOperationDesc1();
   }
}
