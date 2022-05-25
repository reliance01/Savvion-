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

public class WorkItem implements Serializable {
   private Calendar duedate;
   private long id;
   private String name;
   private String performer;
   private String piCreator;
   private String piName;
   private long piid;
   private String priority;
   private long ptid;
   private String status;
   private Calendar timeStarted;
   private String wsName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(WorkItem.class, true);

   static {
      typeDesc.setXmlType(new QName("http://workflow.webservice.savvion.com", "WorkItem"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("duedate");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "duedate"));
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
      elemField.setFieldName("piCreator");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "piCreator"));
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
      elemField.setFieldName("status");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "status"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("timeStarted");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "timeStarted"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("wsName");
      elemField.setXmlName(new QName("http://workflow.webservice.savvion.com", "wsName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public WorkItem() {
   }

   public WorkItem(Calendar duedate, long id, String name, String performer, String piCreator, String piName, long piid, String priority, long ptid, String status, Calendar timeStarted, String wsName) {
      this.duedate = duedate;
      this.id = id;
      this.name = name;
      this.performer = performer;
      this.piCreator = piCreator;
      this.piName = piName;
      this.piid = piid;
      this.priority = priority;
      this.ptid = ptid;
      this.status = status;
      this.timeStarted = timeStarted;
      this.wsName = wsName;
   }

   public Calendar getDuedate() {
      return this.duedate;
   }

   public void setDuedate(Calendar duedate) {
      this.duedate = duedate;
   }

   public long getId() {
      return this.id;
   }

   public void setId(long id) {
      this.id = id;
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

   public String getPiCreator() {
      return this.piCreator;
   }

   public void setPiCreator(String piCreator) {
      this.piCreator = piCreator;
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

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public Calendar getTimeStarted() {
      return this.timeStarted;
   }

   public void setTimeStarted(Calendar timeStarted) {
      this.timeStarted = timeStarted;
   }

   public String getWsName() {
      return this.wsName;
   }

   public void setWsName(String wsName) {
      this.wsName = wsName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof WorkItem)) {
         return false;
      } else {
         WorkItem other = (WorkItem)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.duedate == null && other.getDuedate() == null || this.duedate != null && this.duedate.equals(other.getDuedate())) && this.id == other.getId() && (this.name == null && other.getName() == null || this.name != null && this.name.equals(other.getName())) && (this.performer == null && other.getPerformer() == null || this.performer != null && this.performer.equals(other.getPerformer())) && (this.piCreator == null && other.getPiCreator() == null || this.piCreator != null && this.piCreator.equals(other.getPiCreator())) && (this.piName == null && other.getPiName() == null || this.piName != null && this.piName.equals(other.getPiName())) && this.piid == other.getPiid() && (this.priority == null && other.getPriority() == null || this.priority != null && this.priority.equals(other.getPriority())) && this.ptid == other.getPtid() && (this.status == null && other.getStatus() == null || this.status != null && this.status.equals(other.getStatus())) && (this.timeStarted == null && other.getTimeStarted() == null || this.timeStarted != null && this.timeStarted.equals(other.getTimeStarted())) && (this.wsName == null && other.getWsName() == null || this.wsName != null && this.wsName.equals(other.getWsName()));
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
         if (this.getDuedate() != null) {
            _hashCode += this.getDuedate().hashCode();
         }

         _hashCode += (new Long(this.getId())).hashCode();
         if (this.getName() != null) {
            _hashCode += this.getName().hashCode();
         }

         if (this.getPerformer() != null) {
            _hashCode += this.getPerformer().hashCode();
         }

         if (this.getPiCreator() != null) {
            _hashCode += this.getPiCreator().hashCode();
         }

         if (this.getPiName() != null) {
            _hashCode += this.getPiName().hashCode();
         }

         _hashCode += (new Long(this.getPiid())).hashCode();
         if (this.getPriority() != null) {
            _hashCode += this.getPriority().hashCode();
         }

         _hashCode += (new Long(this.getPtid())).hashCode();
         if (this.getStatus() != null) {
            _hashCode += this.getStatus().hashCode();
         }

         if (this.getTimeStarted() != null) {
            _hashCode += this.getTimeStarted().hashCode();
         }

         if (this.getWsName() != null) {
            _hashCode += this.getWsName().hashCode();
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
