package com.microsoft.schemas.BizTalk._2003.Any;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.AnyContentType;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.MixedContentType;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.axis.message.MessageElement;

public class Root implements Serializable, AnyContentType, MixedContentType {
   private MessageElement[] _any;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(Root.class, true);

   static {
      typeDesc.setXmlType(new QName("http://schemas.microsoft.com/BizTalk/2003/Any", ">Root"));
   }

   public Root() {
   }

   public Root(MessageElement[] _any) {
      this._any = _any;
   }

   public MessageElement[] get_any() {
      return this._any;
   }

   public void set_any(MessageElement[] _any) {
      this._any = _any;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof Root)) {
         return false;
      } else {
         Root other = (Root)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this._any == null && other.get_any() == null || this._any != null && Arrays.equals(this._any, other.get_any());
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
         if (this.get_any() != null) {
            for(int i = 0; i < Array.getLength(this.get_any()); ++i) {
               Object obj = Array.get(this.get_any(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
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
