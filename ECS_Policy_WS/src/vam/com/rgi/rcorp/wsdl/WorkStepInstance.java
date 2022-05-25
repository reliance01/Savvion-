package vam.com.rgi.rcorp.wsdl;

import java.io.Serializable;
import java.util.Calendar;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class WorkStepInstance implements Serializable {
   private Calendar dueDate;
   private int loopCounter;
   private String name;
   private String performer;
   private String piName;
   private long piid;
   private String priority;
   private long ptid;
   private Calendar startTime;
   private String status;
   private String type;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(WorkStepInstance.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkStepInstance"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("dueDate");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "dueDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("loopCounter");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "loopCounter"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
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
      elemField.setFieldName("piName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "piName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("piid");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "piid"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
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
      elemField.setFieldName("startTime");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "startTime"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("status");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "status"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("type");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "type"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public WorkStepInstance() {
   }

   public WorkStepInstance(Calendar dueDate, int loopCounter, String name, String performer, String piName, long piid, String priority, long ptid, Calendar startTime, String status, String type) {
      this.dueDate = dueDate;
      this.loopCounter = loopCounter;
      this.name = name;
      this.performer = performer;
      this.piName = piName;
      this.piid = piid;
      this.priority = priority;
      this.ptid = ptid;
      this.startTime = startTime;
      this.status = status;
      this.type = type;
   }

   public Calendar getDueDate() {
      return this.dueDate;
   }

   public void setDueDate(Calendar dueDate) {
      this.dueDate = dueDate;
   }

   public int getLoopCounter() {
      return this.loopCounter;
   }

   public void setLoopCounter(int loopCounter) {
      this.loopCounter = loopCounter;
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

   public String getPiName() {
      return this.piName;
   }

   public void setPiName(String piName) {
      this.piName = piName;
   }

   public long getPiid() {
      return this.piid;
   }

   public void setPiid(long piid) {
      this.piid = piid;
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

   public Calendar getStartTime() {
      return this.startTime;
   }

   public void setStartTime(Calendar startTime) {
      this.startTime = startTime;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof WorkStepInstance)) {
         return false;
      } else {
         WorkStepInstance other = (WorkStepInstance)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.dueDate == null && other.getDueDate() == null || this.dueDate != null && this.dueDate.equals(other.getDueDate())) && this.loopCounter == other.getLoopCounter() && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && (this.performer == null && other.getPerformer() == null || this.performer != null && this.performer.equals(other.getPerformer())) && (this.piName == null && other.getPiName() == null || this.piName != null && this.piName.equals(other.getPiName())) && this.piid == other.getPiid() && (this.priority == null && other.getPriority() == null || this.priority != null && this.priority.equals(other.getPriority())) && this.ptid == other.getPtid() && (this.startTime == null && other.getStartTime() == null || this.startTime != null && this.startTime.equals(other.getStartTime())) && (this.status == null && other.getStatus() == null || this.status != null && this.status.equals(other.getStatus())) && (this.type == null && other.getType() == null || this.type != null && this.type.equals(other.getType()));
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
         if (this.getDueDate() != null) {
            _hashCode += this.getDueDate().hashCode();
         }

         _hashCode += this.getLoopCounter();
         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

         if (this.getPerformer() != null) {
            _hashCode += this.getPerformer().hashCode();
         }

         if (this.getPiName() != null) {
            _hashCode += this.getPiName().hashCode();
         }

         _hashCode += (new Long(this.getPiid())).hashCode();
         if (this.getPriority() != null) {
            _hashCode += this.getPriority().hashCode();
         }

         _hashCode += (new Long(this.getPtid())).hashCode();
         if (this.getStartTime() != null) {
            _hashCode += this.getStartTime().hashCode();
         }

         if (this.getStatus() != null) {
            _hashCode += this.getStatus().hashCode();
         }

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
