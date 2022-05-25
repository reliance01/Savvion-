package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ConnectResponse implements Serializable {
   private String connectReturn;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ConnectResponse.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", ">connectResponse"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("connectReturn");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "connectReturn"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public ConnectResponse() {
   }

   public ConnectResponse(String connectReturn) {
      this.connectReturn = connectReturn;
   }

   public String getConnectReturn() {
      return this.connectReturn;
   }

   public void setConnectReturn(String connectReturn) {
      this.connectReturn = connectReturn;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ConnectResponse)) {
         return false;
      } else {
         ConnectResponse other = (ConnectResponse)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.connectReturn == null && other.getConnectReturn() == null || this.connectReturn != null && this.connectReturn.equals(other.getConnectReturn());
            this.__equalsCalc = null;
            return _equals;
         }
      }
   }

   public synchronized int hashCode() {
      if (this.__hashCodeCalc) {
         return 0;
      } else {
         this.__hashCodeCalc = true;
         int _hashCode = 1;
         if (this.getConnectReturn() != null) {
            _hashCode += this.getConnectReturn().hashCode();
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }

   public static TypeDesc getTypeDesc() {
      return typeDesc;
   }

   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
      return new BeanSerializer(_javaType, _xmlType, typeDesc);
   }

   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
      return new BeanDeserializer(_javaType, _xmlType, typeDesc);
   }
}
