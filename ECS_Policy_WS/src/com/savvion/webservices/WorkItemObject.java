package com.savvion.webservices;

import com.savvion.util.NLog;
import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class WorkItemObject implements Serializable {
   private String BASCode;
   private String LOB;
   private String TAT;
   private String UWGroup;
   private String agentCode;
   private String approvalAuthority;
   private String assignToUserId;
   private String blazeRemarks;
   private String branchCode;
   private String callEndDate;
   private String callStartDate;
   private String channel;
   private String createdBy;
   private String performer;
   private String process;
   private String processInstanceName;
   private String productCode;
   private String proposalNumber;
   private String quoteNo;
   private String remarks;
   private String status;
   private String userId;
   private String workStepName;
   private String workitemName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(WorkItemObject.class, true);

   static {
      typeDesc.setXmlType(new QName("http://rgicl.motor.savvion.com", "WorkItemObject"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("BASCode");
      elemField.setXmlName(new QName("", "BASCode"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("LOB");
      elemField.setXmlName(new QName("", "LOB"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("TAT");
      elemField.setXmlName(new QName("", "TAT"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("UWGroup");
      elemField.setXmlName(new QName("", "UWGroup"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("agentCode");
      elemField.setXmlName(new QName("", "agentCode"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("approvalAuthority");
      elemField.setXmlName(new QName("", "approvalAuthority"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("assignToUserId");
      elemField.setXmlName(new QName("", "assignToUserId"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("blazeRemarks");
      elemField.setXmlName(new QName("", "blazeRemarks"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("branchCode");
      elemField.setXmlName(new QName("", "branchCode"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("callEndDate");
      elemField.setXmlName(new QName("", "callEndDate"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("callStartDate");
      elemField.setXmlName(new QName("", "callStartDate"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("channel");
      elemField.setXmlName(new QName("", "channel"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("createdBy");
      elemField.setXmlName(new QName("", "createdBy"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("performer");
      elemField.setXmlName(new QName("", "performer"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("process");
      elemField.setXmlName(new QName("", "process"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("processInstanceName");
      elemField.setXmlName(new QName("", "processInstanceName"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("productCode");
      elemField.setXmlName(new QName("", "productCode"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("proposalNumber");
      elemField.setXmlName(new QName("", "proposalNumber"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("quoteNo");
      elemField.setXmlName(new QName("", "quoteNo"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("remarks");
      elemField.setXmlName(new QName("", "remarks"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("status");
      elemField.setXmlName(new QName("", "status"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("userId");
      elemField.setXmlName(new QName("", "userId"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("workStepName");
      elemField.setXmlName(new QName("", "workStepName"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("workitemName");
      elemField.setXmlName(new QName("", "workitemName"));
      elemField.setXmlType(new QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }

   public WorkItemObject() {
      NLog.ws.debug("Irpas WorkItemObject created for web service");
   }

   public WorkItemObject(String BASCode, String LOB, String TAT, String UWGroup, String agentCode, String approvalAuthority, String assignToUserId, String blazeRemarks, String branchCode, String callEndDate, String callStartDate, String channel, String createdBy, String performer, String process, String processInstanceName, String productCode, String proposalNumber, String quoteNo, String remarks, String status, String userId, String workStepName, String workitemName) {
      this.BASCode = BASCode;
      this.LOB = LOB;
      this.TAT = TAT;
      this.UWGroup = UWGroup;
      this.agentCode = agentCode;
      this.approvalAuthority = approvalAuthority;
      this.assignToUserId = assignToUserId;
      this.blazeRemarks = blazeRemarks;
      this.branchCode = branchCode;
      this.callEndDate = callEndDate;
      this.callStartDate = callStartDate;
      this.channel = channel;
      this.createdBy = createdBy;
      this.performer = performer;
      this.process = process;
      this.processInstanceName = processInstanceName;
      this.productCode = productCode;
      this.proposalNumber = proposalNumber;
      this.quoteNo = quoteNo;
      this.remarks = remarks;
      this.status = status;
      this.userId = userId;
      this.workStepName = workStepName;
      this.workitemName = workitemName;
   }

   public String getBASCode() {
      return this.BASCode;
   }

   public void setBASCode(String BASCode) {
      this.BASCode = BASCode;
   }

   public String getLOB() {
      return this.LOB;
   }

   public void setLOB(String LOB) {
      this.LOB = LOB;
   }

   public String getTAT() {
      return this.TAT;
   }

   public void setTAT(String TAT) {
      this.TAT = TAT;
   }

   public String getUWGroup() {
      return this.UWGroup;
   }

   public void setUWGroup(String UWGroup) {
      this.UWGroup = UWGroup;
   }

   public String getAgentCode() {
      return this.agentCode;
   }

   public void setAgentCode(String agentCode) {
      this.agentCode = agentCode;
   }

   public String getApprovalAuthority() {
      return this.approvalAuthority;
   }

   public void setApprovalAuthority(String approvalAuthority) {
      this.approvalAuthority = approvalAuthority;
   }

   public String getAssignToUserId() {
      return this.assignToUserId;
   }

   public void setAssignToUserId(String assignToUserId) {
      this.assignToUserId = assignToUserId;
   }

   public String getBlazeRemarks() {
      return this.blazeRemarks;
   }

   public void setBlazeRemarks(String blazeRemarks) {
      this.blazeRemarks = blazeRemarks;
   }

   public String getBranchCode() {
      return this.branchCode;
   }

   public void setBranchCode(String branchCode) {
      this.branchCode = branchCode;
   }

   public String getCallEndDate() {
      return this.callEndDate;
   }

   public void setCallEndDate(String callEndDate) {
      this.callEndDate = callEndDate;
   }

   public String getCallStartDate() {
      return this.callStartDate;
   }

   public void setCallStartDate(String callStartDate) {
      this.callStartDate = callStartDate;
   }

   public String getChannel() {
      return this.channel;
   }

   public void setChannel(String channel) {
      this.channel = channel;
   }

   public String getCreatedBy() {
      return this.createdBy;
   }

   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   public String getPerformer() {
      return this.performer;
   }

   public void setPerformer(String performer) {
      this.performer = performer;
   }

   public String getProcess() {
      return this.process;
   }

   public void setProcess(String process) {
      this.process = process;
   }

   public String getProcessInstanceName() {
      return this.processInstanceName;
   }

   public void setProcessInstanceName(String processInstanceName) {
      this.processInstanceName = processInstanceName;
   }

   public String getProductCode() {
      return this.productCode;
   }

   public void setProductCode(String productCode) {
      this.productCode = productCode;
   }

   public String getProposalNumber() {
      return this.proposalNumber;
   }

   public void setProposalNumber(String proposalNumber) {
      this.proposalNumber = proposalNumber;
   }

   public String getQuoteNo() {
      return this.quoteNo;
   }

   public void setQuoteNo(String quoteNo) {
      this.quoteNo = quoteNo;
   }

   public String getRemarks() {
      return this.remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getUserId() {
      return this.userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getWorkStepName() {
      return this.workStepName;
   }

   public void setWorkStepName(String workStepName) {
      this.workStepName = workStepName;
   }

   public String getWorkitemName() {
      return this.workitemName;
   }

   public void setWorkitemName(String workitemName) {
      this.workitemName = workitemName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof WorkItemObject)) {
         return false;
      } else {
         WorkItemObject other = (WorkItemObject)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = (this.BASCode == null && other.getBASCode() == null || this.BASCode != null && this.BASCode.equals(other.getBASCode())) && (this.LOB == null && other.getLOB() == null || this.LOB != null && this.LOB.equals(other.getLOB())) && (this.TAT == null && other.getTAT() == null || this.TAT != null && this.TAT.equals(other.getTAT())) && (this.UWGroup == null && other.getUWGroup() == null || this.UWGroup != null && this.UWGroup.equals(other.getUWGroup())) && (this.agentCode == null && other.getAgentCode() == null || this.agentCode != null && this.agentCode.equals(other.getAgentCode())) && (this.approvalAuthority == null && other.getApprovalAuthority() == null || this.approvalAuthority != null && this.approvalAuthority.equals(other.getApprovalAuthority())) && (this.assignToUserId == null && other.getAssignToUserId() == null || this.assignToUserId != null && this.assignToUserId.equals(other.getAssignToUserId())) && (this.blazeRemarks == null && other.getBlazeRemarks() == null || this.blazeRemarks != null && this.blazeRemarks.equals(other.getBlazeRemarks())) && (this.branchCode == null && other.getBranchCode() == null || this.branchCode != null && this.branchCode.equals(other.getBranchCode())) && (this.callEndDate == null && other.getCallEndDate() == null || this.callEndDate != null && this.callEndDate.equals(other.getCallEndDate())) && (this.callStartDate == null && other.getCallStartDate() == null || this.callStartDate != null && this.callStartDate.equals(other.getCallStartDate())) && (this.channel == null && other.getChannel() == null || this.channel != null && this.channel.equals(other.getChannel())) && (this.createdBy == null && other.getCreatedBy() == null || this.createdBy != null && this.createdBy.equals(other.getCreatedBy())) && (this.performer == null && other.getPerformer() == null || this.performer != null && this.performer.equals(other.getPerformer())) && (this.process == null && other.getProcess() == null || this.process != null && this.process.equals(other.getProcess())) && (this.processInstanceName == null && other.getProcessInstanceName() == null || this.processInstanceName != null && this.processInstanceName.equals(other.getProcessInstanceName())) && (this.productCode == null && other.getProductCode() == null || this.productCode != null && this.productCode.equals(other.getProductCode())) && (this.proposalNumber == null && other.getProposalNumber() == null || this.proposalNumber != null && this.proposalNumber.equals(other.getProposalNumber())) && (this.quoteNo == null && other.getQuoteNo() == null || this.quoteNo != null && this.quoteNo.equals(other.getQuoteNo())) && (this.remarks == null && other.getRemarks() == null || this.remarks != null && this.remarks.equals(other.getRemarks())) && (this.status == null && other.getStatus() == null || this.status != null && this.status.equals(other.getStatus())) && (this.userId == null && other.getUserId() == null || this.userId != null && this.userId.equals(other.getUserId())) && (this.workStepName == null && other.getWorkStepName() == null || this.workStepName != null && this.workStepName.equals(other.getWorkStepName())) && (this.workitemName == null && other.getWorkitemName() == null || this.workitemName != null && this.workitemName.equals(other.getWorkitemName()));
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
         if (this.getBASCode() != null) {
            _hashCode += this.getBASCode().hashCode();
         }

         if (this.getLOB() != null) {
            _hashCode += this.getLOB().hashCode();
         }

         if (this.getTAT() != null) {
            _hashCode += this.getTAT().hashCode();
         }

         if (this.getUWGroup() != null) {
            _hashCode += this.getUWGroup().hashCode();
         }

         if (this.getAgentCode() != null) {
            _hashCode += this.getAgentCode().hashCode();
         }

         if (this.getApprovalAuthority() != null) {
            _hashCode += this.getApprovalAuthority().hashCode();
         }

         if (this.getAssignToUserId() != null) {
            _hashCode += this.getAssignToUserId().hashCode();
         }

         if (this.getBlazeRemarks() != null) {
            _hashCode += this.getBlazeRemarks().hashCode();
         }

         if (this.getBranchCode() != null) {
            _hashCode += this.getBranchCode().hashCode();
         }

         if (this.getCallEndDate() != null) {
            _hashCode += this.getCallEndDate().hashCode();
         }

         if (this.getCallStartDate() != null) {
            _hashCode += this.getCallStartDate().hashCode();
         }

         if (this.getChannel() != null) {
            _hashCode += this.getChannel().hashCode();
         }

         if (this.getCreatedBy() != null) {
            _hashCode += this.getCreatedBy().hashCode();
         }

         if (this.getPerformer() != null) {
            _hashCode += this.getPerformer().hashCode();
         }

         if (this.getProcess() != null) {
            _hashCode += this.getProcess().hashCode();
         }

         if (this.getProcessInstanceName() != null) {
            _hashCode += this.getProcessInstanceName().hashCode();
         }

         if (this.getProductCode() != null) {
            _hashCode += this.getProductCode().hashCode();
         }

         if (this.getProposalNumber() != null) {
            _hashCode += this.getProposalNumber().hashCode();
         }

         if (this.getQuoteNo() != null) {
            _hashCode += this.getQuoteNo().hashCode();
         }

         if (this.getRemarks() != null) {
            _hashCode += this.getRemarks().hashCode();
         }

         if (this.getStatus() != null) {
            _hashCode += this.getStatus().hashCode();
         }

         if (this.getUserId() != null) {
            _hashCode += this.getUserId().hashCode();
         }

         if (this.getWorkStepName() != null) {
            _hashCode += this.getWorkStepName().hashCode();
         }

         if (this.getWorkitemName() != null) {
            _hashCode += this.getWorkitemName().hashCode();
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
