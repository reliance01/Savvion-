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

public class ProcessTemplate implements Serializable {
   private String appName;
   private String category;
   private String description;
   private String group;
   private long id;
   private String manager;
   private String name;
   private String priority;
   private Calendar startTime;
   private String state;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ProcessTemplate.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", "ProcessTemplate"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("appName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "appName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("category");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "category"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("description");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "description"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("group");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "group"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("id");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "id"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "long"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("manager");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "manager"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
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
      elemField.setFieldName("startTime");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "startTime"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("state");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "state"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public ProcessTemplate() {
   }

   public ProcessTemplate(String appName, String category, String description, String group, long id, String manager, String name, String priority, Calendar startTime, String state) {
      this.appName = appName;
      this.category = category;
      this.description = description;
      this.group = group;
      this.id = id;
      this.manager = manager;
      this.name = name;
      this.priority = priority;
      this.startTime = startTime;
      this.state = state;
   }

   public String getAppName() {
      return this.appName;
   }

   public void setAppName(String appName) {
      this.appName = appName;
   }

   public String getCategory() {
      return this.category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getGroup() {
      return this.group;
   }

   public void setGroup(String group) {
      this.group = group;
   }

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getManager() {
      return this.manager;
   }

   public void setManager(String manager) {
      this.manager = manager;
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

   public Calendar getStartTime() {
      return this.startTime;
   }

   public void setStartTime(Calendar startTime) {
      this.startTime = startTime;
   }

   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ProcessTemplate)) {
         return false;
      } else {
         ProcessTemplate other = (ProcessTemplate)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.appName == null && other.getAppName() == null || this.appName != null && this.appName.equals(other.getAppName())) && (this.category == null && other.getCategory() == null || this.category != null && this.category.equals(other.getCategory())) && (this.description == null && other.getDescription() == null || this.description != null && this.description.equals(other.getDescription())) && (this.group == null && other.getGroup() == null || this.group != null && this.group.equals(other.getGroup())) && this.id == other.getId() && (this.manager == null && other.getManager() == null || this.manager != null && this.manager.equals(other.getManager())) && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && (this.priority == null && other.getPriority() == null || this.priority != null && this.priority.equals(other.getPriority())) && (this.startTime == null && other.getStartTime() == null || this.startTime != null && this.startTime.equals(other.getStartTime())) && (this.state == null && other.getState() == null || this.state != null && this.state.equals(other.getState()));
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
         if (this.getAppName() != null) {
            _hashCode += this.getAppName().hashCode();
         }

         if (this.getCategory() != null) {
            _hashCode += this.getCategory().hashCode();
         }

         if (this.getDescription() != null) {
            _hashCode += this.getDescription().hashCode();
         }

         if (this.getGroup() != null) {
            _hashCode += this.getGroup().hashCode();
         }

         _hashCode += (new Long(this.getId())).hashCode();
         if (this.getManager() != null) {
            _hashCode += this.getManager().hashCode();
         }

         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

         if (this.getPriority() != null) {
            _hashCode += this.getPriority().hashCode();
         }

         if (this.getStartTime() != null) {
            _hashCode += this.getStartTime().hashCode();
         }

         if (this.getState() != null) {
            _hashCode += this.getState().hashCode();
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
