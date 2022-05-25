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

public class ProcessInstance implements Serializable {
   private String creator;
   private Calendar dueDate;
   private long id;
   private boolean isSubProcess;
   private boolean isSyncSubProcess;
   private String name;
   private String priority;
   private long ptid;
   private Calendar startTime;
   private String status;
   private boolean subProcess;
   private boolean syncSubProcess;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ProcessInstance.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessInstance"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("creator");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "creator"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("dueDate");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "dueDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("id");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "id"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isSubProcess");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "isSubProcess"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("isSyncSubProcess");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "isSyncSubProcess"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("name");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "name"));
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
      elemField.setFieldName("subProcess");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "subProcess"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("syncSubProcess");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "syncSubProcess"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
   }

   public ProcessInstance() {
   }

   public ProcessInstance(String creator, Calendar dueDate, long id, boolean isSubProcess, boolean isSyncSubProcess, String name, String priority, long ptid, Calendar startTime, String status, boolean subProcess, boolean syncSubProcess) {
      this.creator = creator;
      this.dueDate = dueDate;
      this.id = id;
      this.isSubProcess = isSubProcess;
      this.isSyncSubProcess = isSyncSubProcess;
      this.name = name;
      this.priority = priority;
      this.ptid = ptid;
      this.startTime = startTime;
      this.status = status;
      this.subProcess = subProcess;
      this.syncSubProcess = syncSubProcess;
   }

   public String getCreator() {
      return this.creator;
   }

   public void setCreator(String creator) {
      this.creator = creator;
   }

   public Calendar getDueDate() {
      return this.dueDate;
   }

   public void setDueDate(Calendar dueDate) {
      this.dueDate = dueDate;
   }

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public boolean isIsSubProcess() {
      return this.isSubProcess;
   }

   public void setIsSubProcess(boolean isSubProcess) {
      this.isSubProcess = isSubProcess;
   }

   public boolean isIsSyncSubProcess() {
      return this.isSyncSubProcess;
   }

   public void setIsSyncSubProcess(boolean isSyncSubProcess) {
      this.isSyncSubProcess = isSyncSubProcess;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
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

   public boolean isSubProcess() {
      return this.subProcess;
   }

   public void setSubProcess(boolean subProcess) {
      this.subProcess = subProcess;
   }

   public boolean isSyncSubProcess() {
      return this.syncSubProcess;
   }

   public void setSyncSubProcess(boolean syncSubProcess) {
      this.syncSubProcess = syncSubProcess;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ProcessInstance)) {
         return false;
      } else {
         ProcessInstance other = (ProcessInstance)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.creator == null && other.getCreator() == null || this.creator != null && this.creator.equals(other.getCreator())) && (this.dueDate == null && other.getDueDate() == null || this.dueDate != null && this.dueDate.equals(other.getDueDate())) && this.id == other.getId() && this.isSubProcess == other.isIsSubProcess() && this.isSyncSubProcess == other.isIsSyncSubProcess() && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && (this.priority == null && other.getPriority() == null || this.priority != null && this.priority.equals(other.getPriority())) && this.ptid == other.getPtid() && (this.startTime == null && other.getStartTime() == null || this.startTime != null && this.startTime.equals(other.getStartTime())) && (this.status == null && other.getStatus() == null || this.status != null && this.status.equals(other.getStatus())) && this.subProcess == other.isSubProcess() && this.syncSubProcess == other.isSyncSubProcess();
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
         if (this.getCreator() != null) {
            _hashCode += this.getCreator().hashCode();
         }

         if (this.getDueDate() != null) {
            _hashCode += this.getDueDate().hashCode();
         }

         _hashCode += (new Long(this.getId())).hashCode();
         _hashCode += (this.isIsSubProcess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.isIsSyncSubProcess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

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

         _hashCode += (this.isSubProcess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.isSyncSubProcess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
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
