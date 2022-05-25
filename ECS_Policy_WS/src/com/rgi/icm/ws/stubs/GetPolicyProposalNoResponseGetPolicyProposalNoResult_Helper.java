package com.rgi.icm.ws.stubs;

import javax.xml.namespace.QName;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class GetPolicyProposalNoResponseGetPolicyProposalNoResult_Helper {
   private static TypeDesc typeDesc = new TypeDesc(GetPolicyProposalNoResponseGetPolicyProposalNoResult.class, true);

   static {
      typeDesc.setXmlType(new QName("http://tempuri.org/", ">>GetPolicyProposalNoResponse>GetPolicyProposalNoResult"));
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
