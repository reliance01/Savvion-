package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class WorkSteptemplate implements Serializable {
   private long duration;
   private String name;
   private String performer;
   private String priority;
   private long ptid;
   private String type;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(WorkSteptemplate.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkSteptemplate"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("duration");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "duration"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("name");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "name"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("performer");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "performer"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("priority");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "priority"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("ptid");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "ptid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("type");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "type"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public WorkSteptemplate() {
   }

   public WorkSteptemplate(long duration, String name, String performer, String priority, long ptid, String type) {
      this.duration = duration;
      this.name = name;
      this.performer = performer;
      this.priority = priority;
      this.ptid = ptid;
      this.type = type;
   }

   public long getDuration() {
      return this.duration;
   }

   public void setDuration(long duration) {
      this.duration = duration;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPerformer() {
      return this.performer;
   }

   public void setPerformer(String performer) {
      this.performer = performer;
   }

   public String getPriority() {
      return this.priority;
   }

   public void setPriority(String priority) {
      this.priority = priority;
   }

   public long getPtid() {
      return this.ptid;
   }

   public void setPtid(long ptid) {
      this.ptid = ptid;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof WorkSteptemplate)) {
         return false;
      } else {
         WorkSteptemplate other = (WorkSteptemplate)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this.duration == other.getDuration() && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && (this.performer == null && other.getPerformer() == null || this.performer != null && this.performer.equals(other.getPerformer())) && (this.priority == null && other.getPriority() == null || this.priority != null && this.priority.equals(other.getPriority())) && this.ptid == other.getPtid() && (this.type == null && other.getType() == null || this.type != null && this.type.equals(other.getType()));
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
         int _hashCode = _hashCode + (new Long(this.getDuration())).hashCode();
         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

         if (this.getPerformer() != null) {
            _hashCode += this.getPerformer().hashCode();
         }

         if (this.getPriority() != null) {
            _hashCode += this.getPriority().hashCode();
         }

         _hashCode += (new Long(this.getPtid())).hashCode();
         if (this.getType() != null) {
            _hashCode += this.getType().hashCode();
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
