package app.icm;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class ProposalDetailsBO extends UtilBO implements Serializable {
   private String _Actionable;
   private String _Address;
   private String _AgentCode;
   private String _AgentName;
   private ProposalDetailsBO[] _AgentRetentionDetailsDummy;
   private String _AllocatedBy;
   private String _AllocatedTo;
   private Calendar _AllocationDate;
   private boolean _BASExists;
   private String _BancaCode;
   private boolean _BarcodeExists;
   private String _BasCode;
   private String _BasName;
   private String _BranchCode;
   private Integer _BranchID;
   private String _BusinessType;
   private String _CSOCode;
   private String _CSOName;
   private String _CancelledPolicy;
   private BigDecimal _Cess;
   private String _ChessisNo;
   private int _CityID;
   private String _CityName;
   private Integer _ClassID;
   private String _Co_InsStatus;
   private String _ContactNumber;
   private boolean _CoverNoteExits;
   private Integer _DiscrepancyID_FK;
   private String _District;
   private int _DistrictID;
   private String _DocumentType;
   private BigDecimal _ERF;
   private String _EndorsementCategory;
   private String _EndorsementNo;
   private int _EndorsementReasonID;
   private String _EndorsementSubTypeName;
   private String _EndorsementType;
   private String _EndorsementTypeName;
   private String _EngineNo;
   private String _ExternalBarcode;
   private Calendar _FirstAllocationDate;
   private String _FirstName;
   private BigDecimal _GST;
   private String _HandOverTo;
   private Calendar _HandoverDate;
   private BigDecimal _HigherEduCess;
   private Calendar _InspectionDateTime;
   private int _InspectionDocNo;
   private String _InspectionDoneBy;
   private String _InspectionDoneByName;
   private String _InsuredName;
   private String _InterRefCode;
   private String _InterRefName;
   private String _IntermediaryRefCode;
   private String _IntermediaryRefName;
   private String _InternalBarcode;
   private String _InwardedBy;
   private Calendar _InwardedDateTime;
   private int _IsChequeCleared;
   private boolean _IsDeleted;
   private int _IsDiscrepancyLog;
   private boolean _IsEditable;
   private boolean _IsEverJumping;
   private boolean _IsInwarded;
   private boolean _IsMappedStatus;
   private boolean _IsNCB;
   private int _IsPDC;
   private boolean _IsPassedForPolicyGeneration;
   private boolean _IsPaymentDone;
   private int _IsPolicyGeneratedInICM;
   private String _IsPostDateCheque;
   private boolean _IsQCOk;
   private boolean _IsReissue;
   private boolean _Isjumping;
   private Calendar _IssueDate;
   private String _JobAllocatedBy;
   private String _JobAllocatedTo;
   private Calendar _JobAllocationDate;
   private Calendar _JumpingStatusChangedDate;
   private String _LastName;
   private String _LeadNo;
   private int _MakeId_FK;
   private String _MakeName;
   private BigDecimal _MarinePremium;
   private BigDecimal _MaxDiscountallowed;
   private Calendar _MaxInstrDate;
   private String _Message;
   private String _MiddleName;
   private int _ModelId_FK;
   private String _ModelVariant;
   private BigDecimal _NetOD;
   private float _NetPremium;
   private BigDecimal _Nettp;
   private String _NewAgentCode;
   private String _NewBASCode;
   private String _NewIntermediaryRefCode;
   private String _NewSMCode;
   private String _OldAgentCode;
   private String _OldBASCode;
   private String _OldIntermediaryRefCode;
   private String _PanNo;
   private String _PassportNo;
   private String _PaymentMode;
   private String _PendingPolicyBranch;
   private String _PinCode;
   private String _PlanName;
   private Integer _Planid_FK;
   private Calendar _PolicyEndDate;
   private Calendar _PolicyGenDate;
   private String _PolicyNo_FK;
   private boolean _PolicyStatus;
   private String _Prefix;
   private BigDecimal _Premium;
   private Integer _PreviousInsurerID;
   private Calendar _PreviousPolicyEndDate;
   private String _PreviousPolicyNo;
   private int _PreviousPolicyProcessedIn;
   private String _PreviousPolicyProductCode;
   private int _ProcessedAt;
   private String _ProcessedAtName;
   private int _ProcessedIn;
   private String _ProcessedInName;
   private String _Product;
   private String _ProductCode;
   private String _ProductName;
   private String _ProductType;
   private BigDecimal _ProposalAmount;
   private String _ProposalClass;
   private String _ProposalID_FK;
   private Calendar _ProposalOrEndorDate;
   private int _ProposalStatus;
   private int _ProposalStatusID;
   private String _QCDone_By;
   private Calendar _QCDone_Date;
   private String _QCRemark;
   private String _QuoteNo;
   private String _ReasonForChange;
   private Calendar _ReceivedDate;
   private String _RegionId;
   private String _RegionName;
   private String _RegistrationNo;
   private String _Remark;
   private DocumentMasterBO[] _RequiredDocument;
   private Calendar _RiskStartDate;
   private String _RuleNumber;
   private String _SMCode;
   private String _SMName;
   private boolean _SearchMode;
   private BigDecimal _ServiceLeviedTax;
   private String _ServiceStatus;
   private BigDecimal _ServiceTax;
   private String _State;
   private int _StateID;
   private String _StatusCode;
   private BigDecimal _SurchargeValue;
   private String _TableName;
   private String _TaxExempRemark;
   private BigDecimal _Terrorism;
   private BigDecimal _TotalPremium;
   private String _TransID_FK;
   private int _UnderWriterApproved;
   private Integer _Variantid_FK;
   private String _VehicalName;
   private int _VehicalSubTypeId_FK;
   private int _VehicalTypeId_FK;
   private String _VehicalTypeName;
   private boolean _VendorExists;
   private BigDecimal _War;
   private int _WhereBy;
   private String _WhereValue;
   private Integer _YearOfManufacture;
   private Integer _YearOfRegistration;
   private String _branchName;
   private String _chequeNo;
   private String _className;
   private DiscrepancyCategoryBO[] _discrCategory;
   private BigDecimal _srcc;
   private BigDecimal _stampduty;
   private String _userName;
   private int intRowID;
   private int intSubDiscrepancyID;
   private Calendar qcResolvedDate;
   private String receiveMode;
   private String sapCode;
   private BigDecimal shortPremium;
   private String strDiscrepancyName;
   private Calendar strRaisedDate;
   private String strResolvedRemark;
   private String strSubDiscrepancyName;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;
   private static TypeDesc typeDesc = new TypeDesc(ProposalDetailsBO.class, true);

   public ProposalDetailsBO() {
   }

   public ProposalDetailsBO(String coStatus, String createdBy, Calendar createdDate, String deletedBy, Calendar deletedDate, Calendar maxInstrumentDate, String modifyedBy, Calendar modifyedDate, BigDecimal totalCollection, Calendar transDate, String transactionID, String vendorType, String _Actionable, String _Address, String _AgentCode, String _AgentName, ProposalDetailsBO[] _AgentRetentionDetailsDummy, String _AllocatedBy, String _AllocatedTo, Calendar _AllocationDate, boolean _BASExists, String _BancaCode, boolean _BarcodeExists, String _BasCode, String _BasName, String _BranchCode, Integer _BranchID, String _BusinessType, String _CSOCode, String _CSOName, String _CancelledPolicy, BigDecimal _Cess, String _ChessisNo, int _CityID, String _CityName, Integer _ClassID, String _Co_InsStatus, String _ContactNumber, boolean _CoverNoteExits, Integer _DiscrepancyID_FK, String _District, int _DistrictID, String _DocumentType, BigDecimal _ERF, String _EndorsementCategory, String _EndorsementNo, int _EndorsementReasonID, String _EndorsementSubTypeName, String _EndorsementType, String _EndorsementTypeName, String _EngineNo, String _ExternalBarcode, Calendar _FirstAllocationDate, String _FirstName, BigDecimal _GST, String _HandOverTo, Calendar _HandoverDate, BigDecimal _HigherEduCess, Calendar _InspectionDateTime, int _InspectionDocNo, String _InspectionDoneBy, String _InspectionDoneByName, String _InsuredName, String _InterRefCode, String _InterRefName, String _IntermediaryRefCode, String _IntermediaryRefName, String _InternalBarcode, String _InwardedBy, Calendar _InwardedDateTime, int _IsChequeCleared, boolean _IsDeleted, int _IsDiscrepancyLog, boolean _IsEditable, boolean _IsEverJumping, boolean _IsInwarded, boolean _IsMappedStatus, boolean _IsNCB, int _IsPDC, boolean _IsPassedForPolicyGeneration, boolean _IsPaymentDone, int _IsPolicyGeneratedInICM, String _IsPostDateCheque, boolean _IsQCOk, boolean _IsReissue, boolean _Isjumping, Calendar _IssueDate, String _JobAllocatedBy, String _JobAllocatedTo, Calendar _JobAllocationDate, Calendar _JumpingStatusChangedDate, String _LastName, String _LeadNo, int _MakeId_FK, String _MakeName, BigDecimal _MarinePremium, BigDecimal _MaxDiscountallowed, Calendar _MaxInstrDate, String _Message, String _MiddleName, int _ModelId_FK, String _ModelVariant, BigDecimal _NetOD, float _NetPremium, BigDecimal _Nettp, String _NewAgentCode, String _NewBASCode, String _NewIntermediaryRefCode, String _NewSMCode, String _OldAgentCode, String _OldBASCode, String _OldIntermediaryRefCode, String _PanNo, String _PassportNo, String _PaymentMode, String _PendingPolicyBranch, String _PinCode, String _PlanName, Integer _Planid_FK, Calendar _PolicyEndDate, Calendar _PolicyGenDate, String _PolicyNo_FK, boolean _PolicyStatus, String _Prefix, BigDecimal _Premium, Integer _PreviousInsurerID, Calendar _PreviousPolicyEndDate, String _PreviousPolicyNo, int _PreviousPolicyProcessedIn, String _PreviousPolicyProductCode, int _ProcessedAt, String _ProcessedAtName, int _ProcessedIn, String _ProcessedInName, String _Product, String _ProductCode, String _ProductName, String _ProductType, BigDecimal _ProposalAmount, String _ProposalClass, String _ProposalID_FK, Calendar _ProposalOrEndorDate, int _ProposalStatus, int _ProposalStatusID, String _QCDone_By, Calendar _QCDone_Date, String _QCRemark, String _QuoteNo, String _ReasonForChange, Calendar _ReceivedDate, String _RegionId, String _RegionName, String _RegistrationNo, String _Remark, DocumentMasterBO[] _RequiredDocument, Calendar _RiskStartDate, String _RuleNumber, String _SMCode, String _SMName, boolean _SearchMode, BigDecimal _ServiceLeviedTax, String _ServiceStatus, BigDecimal _ServiceTax, String _State, int _StateID, String _StatusCode, BigDecimal _SurchargeValue, String _TableName, String _TaxExempRemark, BigDecimal _Terrorism, BigDecimal _TotalPremium, String _TransID_FK, int _UnderWriterApproved, Integer _Variantid_FK, String _VehicalName, int _VehicalSubTypeId_FK, int _VehicalTypeId_FK, String _VehicalTypeName, boolean _VendorExists, BigDecimal _War, int _WhereBy, String _WhereValue, Integer _YearOfManufacture, Integer _YearOfRegistration, String _branchName, String _chequeNo, String _className, DiscrepancyCategoryBO[] _discrCategory, BigDecimal _srcc, BigDecimal _stampduty, String _userName, int intRowID, int intSubDiscrepancyID, Calendar qcResolvedDate, String receiveMode, String sapCode, BigDecimal shortPremium, String strDiscrepancyName, Calendar strRaisedDate, String strResolvedRemark, String strSubDiscrepancyName) {
      super(coStatus, createdBy, createdDate, deletedBy, deletedDate, maxInstrumentDate, modifyedBy, modifyedDate, totalCollection, transDate, transactionID, vendorType);
      this._Actionable = _Actionable;
      this._Address = _Address;
      this._AgentCode = _AgentCode;
      this._AgentName = _AgentName;
      this._AgentRetentionDetailsDummy = _AgentRetentionDetailsDummy;
      this._AllocatedBy = _AllocatedBy;
      this._AllocatedTo = _AllocatedTo;
      this._AllocationDate = _AllocationDate;
      this._BASExists = _BASExists;
      this._BancaCode = _BancaCode;
      this._BarcodeExists = _BarcodeExists;
      this._BasCode = _BasCode;
      this._BasName = _BasName;
      this._BranchCode = _BranchCode;
      this._BranchID = _BranchID;
      this._BusinessType = _BusinessType;
      this._CSOCode = _CSOCode;
      this._CSOName = _CSOName;
      this._CancelledPolicy = _CancelledPolicy;
      this._Cess = _Cess;
      this._ChessisNo = _ChessisNo;
      this._CityID = _CityID;
      this._CityName = _CityName;
      this._ClassID = _ClassID;
      this._Co_InsStatus = _Co_InsStatus;
      this._ContactNumber = _ContactNumber;
      this._CoverNoteExits = _CoverNoteExits;
      this._DiscrepancyID_FK = _DiscrepancyID_FK;
      this._District = _District;
      this._DistrictID = _DistrictID;
      this._DocumentType = _DocumentType;
      this._ERF = _ERF;
      this._EndorsementCategory = _EndorsementCategory;
      this._EndorsementNo = _EndorsementNo;
      this._EndorsementReasonID = _EndorsementReasonID;
      this._EndorsementSubTypeName = _EndorsementSubTypeName;
      this._EndorsementType = _EndorsementType;
      this._EndorsementTypeName = _EndorsementTypeName;
      this._EngineNo = _EngineNo;
      this._ExternalBarcode = _ExternalBarcode;
      this._FirstAllocationDate = _FirstAllocationDate;
      this._FirstName = _FirstName;
      this._GST = _GST;
      this._HandOverTo = _HandOverTo;
      this._HandoverDate = _HandoverDate;
      this._HigherEduCess = _HigherEduCess;
      this._InspectionDateTime = _InspectionDateTime;
      this._InspectionDocNo = _InspectionDocNo;
      this._InspectionDoneBy = _InspectionDoneBy;
      this._InspectionDoneByName = _InspectionDoneByName;
      this._InsuredName = _InsuredName;
      this._InterRefCode = _InterRefCode;
      this._InterRefName = _InterRefName;
      this._IntermediaryRefCode = _IntermediaryRefCode;
      this._IntermediaryRefName = _IntermediaryRefName;
      this._InternalBarcode = _InternalBarcode;
      this._InwardedBy = _InwardedBy;
      this._InwardedDateTime = _InwardedDateTime;
      this._IsChequeCleared = _IsChequeCleared;
      this._IsDeleted = _IsDeleted;
      this._IsDiscrepancyLog = _IsDiscrepancyLog;
      this._IsEditable = _IsEditable;
      this._IsEverJumping = _IsEverJumping;
      this._IsInwarded = _IsInwarded;
      this._IsMappedStatus = _IsMappedStatus;
      this._IsNCB = _IsNCB;
      this._IsPDC = _IsPDC;
      this._IsPassedForPolicyGeneration = _IsPassedForPolicyGeneration;
      this._IsPaymentDone = _IsPaymentDone;
      this._IsPolicyGeneratedInICM = _IsPolicyGeneratedInICM;
      this._IsPostDateCheque = _IsPostDateCheque;
      this._IsQCOk = _IsQCOk;
      this._IsReissue = _IsReissue;
      this._Isjumping = _Isjumping;
      this._IssueDate = _IssueDate;
      this._JobAllocatedBy = _JobAllocatedBy;
      this._JobAllocatedTo = _JobAllocatedTo;
      this._JobAllocationDate = _JobAllocationDate;
      this._JumpingStatusChangedDate = _JumpingStatusChangedDate;
      this._LastName = _LastName;
      this._LeadNo = _LeadNo;
      this._MakeId_FK = _MakeId_FK;
      this._MakeName = _MakeName;
      this._MarinePremium = _MarinePremium;
      this._MaxDiscountallowed = _MaxDiscountallowed;
      this._MaxInstrDate = _MaxInstrDate;
      this._Message = _Message;
      this._MiddleName = _MiddleName;
      this._ModelId_FK = _ModelId_FK;
      this._ModelVariant = _ModelVariant;
      this._NetOD = _NetOD;
      this._NetPremium = _NetPremium;
      this._Nettp = _Nettp;
      this._NewAgentCode = _NewAgentCode;
      this._NewBASCode = _NewBASCode;
      this._NewIntermediaryRefCode = _NewIntermediaryRefCode;
      this._NewSMCode = _NewSMCode;
      this._OldAgentCode = _OldAgentCode;
      this._OldBASCode = _OldBASCode;
      this._OldIntermediaryRefCode = _OldIntermediaryRefCode;
      this._PanNo = _PanNo;
      this._PassportNo = _PassportNo;
      this._PaymentMode = _PaymentMode;
      this._PendingPolicyBranch = _PendingPolicyBranch;
      this._PinCode = _PinCode;
      this._PlanName = _PlanName;
      this._Planid_FK = _Planid_FK;
      this._PolicyEndDate = _PolicyEndDate;
      this._PolicyGenDate = _PolicyGenDate;
      this._PolicyNo_FK = _PolicyNo_FK;
      this._PolicyStatus = _PolicyStatus;
      this._Prefix = _Prefix;
      this._Premium = _Premium;
      this._PreviousInsurerID = _PreviousInsurerID;
      this._PreviousPolicyEndDate = _PreviousPolicyEndDate;
      this._PreviousPolicyNo = _PreviousPolicyNo;
      this._PreviousPolicyProcessedIn = _PreviousPolicyProcessedIn;
      this._PreviousPolicyProductCode = _PreviousPolicyProductCode;
      this._ProcessedAt = _ProcessedAt;
      this._ProcessedAtName = _ProcessedAtName;
      this._ProcessedIn = _ProcessedIn;
      this._ProcessedInName = _ProcessedInName;
      this._Product = _Product;
      this._ProductCode = _ProductCode;
      this._ProductName = _ProductName;
      this._ProductType = _ProductType;
      this._ProposalAmount = _ProposalAmount;
      this._ProposalClass = _ProposalClass;
      this._ProposalID_FK = _ProposalID_FK;
      this._ProposalOrEndorDate = _ProposalOrEndorDate;
      this._ProposalStatus = _ProposalStatus;
      this._ProposalStatusID = _ProposalStatusID;
      this._QCDone_By = _QCDone_By;
      this._QCDone_Date = _QCDone_Date;
      this._QCRemark = _QCRemark;
      this._QuoteNo = _QuoteNo;
      this._ReasonForChange = _ReasonForChange;
      this._ReceivedDate = _ReceivedDate;
      this._RegionId = _RegionId;
      this._RegionName = _RegionName;
      this._RegistrationNo = _RegistrationNo;
      this._Remark = _Remark;
      this._RequiredDocument = _RequiredDocument;
      this._RiskStartDate = _RiskStartDate;
      this._RuleNumber = _RuleNumber;
      this._SMCode = _SMCode;
      this._SMName = _SMName;
      this._SearchMode = _SearchMode;
      this._ServiceLeviedTax = _ServiceLeviedTax;
      this._ServiceStatus = _ServiceStatus;
      this._ServiceTax = _ServiceTax;
      this._State = _State;
      this._StateID = _StateID;
      this._StatusCode = _StatusCode;
      this._SurchargeValue = _SurchargeValue;
      this._TableName = _TableName;
      this._TaxExempRemark = _TaxExempRemark;
      this._Terrorism = _Terrorism;
      this._TotalPremium = _TotalPremium;
      this._TransID_FK = _TransID_FK;
      this._UnderWriterApproved = _UnderWriterApproved;
      this._Variantid_FK = _Variantid_FK;
      this._VehicalName = _VehicalName;
      this._VehicalSubTypeId_FK = _VehicalSubTypeId_FK;
      this._VehicalTypeId_FK = _VehicalTypeId_FK;
      this._VehicalTypeName = _VehicalTypeName;
      this._VendorExists = _VendorExists;
      this._War = _War;
      this._WhereBy = _WhereBy;
      this._WhereValue = _WhereValue;
      this._YearOfManufacture = _YearOfManufacture;
      this._YearOfRegistration = _YearOfRegistration;
      this._branchName = _branchName;
      this._chequeNo = _chequeNo;
      this._className = _className;
      this._discrCategory = _discrCategory;
      this._srcc = _srcc;
      this._stampduty = _stampduty;
      this._userName = _userName;
      this.intRowID = intRowID;
      this.intSubDiscrepancyID = intSubDiscrepancyID;
      this.qcResolvedDate = qcResolvedDate;
      this.receiveMode = receiveMode;
      this.sapCode = sapCode;
      this.shortPremium = shortPremium;
      this.strDiscrepancyName = strDiscrepancyName;
      this.strRaisedDate = strRaisedDate;
      this.strResolvedRemark = strResolvedRemark;
      this.strSubDiscrepancyName = strSubDiscrepancyName;
   }

   public String get_Actionable() {
      return this._Actionable;
   }

   public void set_Actionable(String _Actionable) {
      this._Actionable = _Actionable;
   }

   public String get_Address() {
      return this._Address;
   }

   public void set_Address(String _Address) {
      this._Address = _Address;
   }

   public String get_AgentCode() {
      return this._AgentCode;
   }

   public void set_AgentCode(String _AgentCode) {
      this._AgentCode = _AgentCode;
   }

   public String get_AgentName() {
      return this._AgentName;
   }

   public void set_AgentName(String _AgentName) {
      this._AgentName = _AgentName;
   }

   public ProposalDetailsBO[] get_AgentRetentionDetailsDummy() {
      return this._AgentRetentionDetailsDummy;
   }

   public void set_AgentRetentionDetailsDummy(ProposalDetailsBO[] _AgentRetentionDetailsDummy) {
      this._AgentRetentionDetailsDummy = _AgentRetentionDetailsDummy;
   }

   public String get_AllocatedBy() {
      return this._AllocatedBy;
   }

   public void set_AllocatedBy(String _AllocatedBy) {
      this._AllocatedBy = _AllocatedBy;
   }

   public String get_AllocatedTo() {
      return this._AllocatedTo;
   }

   public void set_AllocatedTo(String _AllocatedTo) {
      this._AllocatedTo = _AllocatedTo;
   }

   public Calendar get_AllocationDate() {
      return this._AllocationDate;
   }

   public void set_AllocationDate(Calendar _AllocationDate) {
      this._AllocationDate = _AllocationDate;
   }

   public boolean is_BASExists() {
      return this._BASExists;
   }

   public void set_BASExists(boolean _BASExists) {
      this._BASExists = _BASExists;
   }

   public String get_BancaCode() {
      return this._BancaCode;
   }

   public void set_BancaCode(String _BancaCode) {
      this._BancaCode = _BancaCode;
   }

   public boolean is_BarcodeExists() {
      return this._BarcodeExists;
   }

   public void set_BarcodeExists(boolean _BarcodeExists) {
      this._BarcodeExists = _BarcodeExists;
   }

   public String get_BasCode() {
      return this._BasCode;
   }

   public void set_BasCode(String _BasCode) {
      this._BasCode = _BasCode;
   }

   public String get_BasName() {
      return this._BasName;
   }

   public void set_BasName(String _BasName) {
      this._BasName = _BasName;
   }

   public String get_BranchCode() {
      return this._BranchCode;
   }

   public void set_BranchCode(String _BranchCode) {
      this._BranchCode = _BranchCode;
   }

   public Integer get_BranchID() {
      return this._BranchID;
   }

   public void set_BranchID(Integer _BranchID) {
      this._BranchID = _BranchID;
   }

   public String get_BusinessType() {
      return this._BusinessType;
   }

   public void set_BusinessType(String _BusinessType) {
      this._BusinessType = _BusinessType;
   }

   public String get_CSOCode() {
      return this._CSOCode;
   }

   public void set_CSOCode(String _CSOCode) {
      this._CSOCode = _CSOCode;
   }

   public String get_CSOName() {
      return this._CSOName;
   }

   public void set_CSOName(String _CSOName) {
      this._CSOName = _CSOName;
   }

   public String get_CancelledPolicy() {
      return this._CancelledPolicy;
   }

   public void set_CancelledPolicy(String _CancelledPolicy) {
      this._CancelledPolicy = _CancelledPolicy;
   }

   public BigDecimal get_Cess() {
      return this._Cess;
   }

   public void set_Cess(BigDecimal _Cess) {
      this._Cess = _Cess;
   }

   public String get_ChessisNo() {
      return this._ChessisNo;
   }

   public void set_ChessisNo(String _ChessisNo) {
      this._ChessisNo = _ChessisNo;
   }

   public int get_CityID() {
      return this._CityID;
   }

   public void set_CityID(int _CityID) {
      this._CityID = _CityID;
   }

   public String get_CityName() {
      return this._CityName;
   }

   public void set_CityName(String _CityName) {
      this._CityName = _CityName;
   }

   public Integer get_ClassID() {
      return this._ClassID;
   }

   public void set_ClassID(Integer _ClassID) {
      this._ClassID = _ClassID;
   }

   public String get_Co_InsStatus() {
      return this._Co_InsStatus;
   }

   public void set_Co_InsStatus(String _Co_InsStatus) {
      this._Co_InsStatus = _Co_InsStatus;
   }

   public String get_ContactNumber() {
      return this._ContactNumber;
   }

   public void set_ContactNumber(String _ContactNumber) {
      this._ContactNumber = _ContactNumber;
   }

   public boolean is_CoverNoteExits() {
      return this._CoverNoteExits;
   }

   public void set_CoverNoteExits(boolean _CoverNoteExits) {
      this._CoverNoteExits = _CoverNoteExits;
   }

   public Integer get_DiscrepancyID_FK() {
      return this._DiscrepancyID_FK;
   }

   public void set_DiscrepancyID_FK(Integer _DiscrepancyID_FK) {
      this._DiscrepancyID_FK = _DiscrepancyID_FK;
   }

   public String get_District() {
      return this._District;
   }

   public void set_District(String _District) {
      this._District = _District;
   }

   public int get_DistrictID() {
      return this._DistrictID;
   }

   public void set_DistrictID(int _DistrictID) {
      this._DistrictID = _DistrictID;
   }

   public String get_DocumentType() {
      return this._DocumentType;
   }

   public void set_DocumentType(String _DocumentType) {
      this._DocumentType = _DocumentType;
   }

   public BigDecimal get_ERF() {
      return this._ERF;
   }

   public void set_ERF(BigDecimal _ERF) {
      this._ERF = _ERF;
   }

   public String get_EndorsementCategory() {
      return this._EndorsementCategory;
   }

   public void set_EndorsementCategory(String _EndorsementCategory) {
      this._EndorsementCategory = _EndorsementCategory;
   }

   public String get_EndorsementNo() {
      return this._EndorsementNo;
   }

   public void set_EndorsementNo(String _EndorsementNo) {
      this._EndorsementNo = _EndorsementNo;
   }

   public int get_EndorsementReasonID() {
      return this._EndorsementReasonID;
   }

   public void set_EndorsementReasonID(int _EndorsementReasonID) {
      this._EndorsementReasonID = _EndorsementReasonID;
   }

   public String get_EndorsementSubTypeName() {
      return this._EndorsementSubTypeName;
   }

   public void set_EndorsementSubTypeName(String _EndorsementSubTypeName) {
      this._EndorsementSubTypeName = _EndorsementSubTypeName;
   }

   public String get_EndorsementType() {
      return this._EndorsementType;
   }

   public void set_EndorsementType(String _EndorsementType) {
      this._EndorsementType = _EndorsementType;
   }

   public String get_EndorsementTypeName() {
      return this._EndorsementTypeName;
   }

   public void set_EndorsementTypeName(String _EndorsementTypeName) {
      this._EndorsementTypeName = _EndorsementTypeName;
   }

   public String get_EngineNo() {
      return this._EngineNo;
   }

   public void set_EngineNo(String _EngineNo) {
      this._EngineNo = _EngineNo;
   }

   public String get_ExternalBarcode() {
      return this._ExternalBarcode;
   }

   public void set_ExternalBarcode(String _ExternalBarcode) {
      this._ExternalBarcode = _ExternalBarcode;
   }

   public Calendar get_FirstAllocationDate() {
      return this._FirstAllocationDate;
   }

   public void set_FirstAllocationDate(Calendar _FirstAllocationDate) {
      this._FirstAllocationDate = _FirstAllocationDate;
   }

   public String get_FirstName() {
      return this._FirstName;
   }

   public void set_FirstName(String _FirstName) {
      this._FirstName = _FirstName;
   }

   public BigDecimal get_GST() {
      return this._GST;
   }

   public void set_GST(BigDecimal _GST) {
      this._GST = _GST;
   }

   public String get_HandOverTo() {
      return this._HandOverTo;
   }

   public void set_HandOverTo(String _HandOverTo) {
      this._HandOverTo = _HandOverTo;
   }

   public Calendar get_HandoverDate() {
      return this._HandoverDate;
   }

   public void set_HandoverDate(Calendar _HandoverDate) {
      this._HandoverDate = _HandoverDate;
   }

   public BigDecimal get_HigherEduCess() {
      return this._HigherEduCess;
   }

   public void set_HigherEduCess(BigDecimal _HigherEduCess) {
      this._HigherEduCess = _HigherEduCess;
   }

   public Calendar get_InspectionDateTime() {
      return this._InspectionDateTime;
   }

   public void set_InspectionDateTime(Calendar _InspectionDateTime) {
      this._InspectionDateTime = _InspectionDateTime;
   }

   public int get_InspectionDocNo() {
      return this._InspectionDocNo;
   }

   public void set_InspectionDocNo(int _InspectionDocNo) {
      this._InspectionDocNo = _InspectionDocNo;
   }

   public String get_InspectionDoneBy() {
      return this._InspectionDoneBy;
   }

   public void set_InspectionDoneBy(String _InspectionDoneBy) {
      this._InspectionDoneBy = _InspectionDoneBy;
   }

   public String get_InspectionDoneByName() {
      return this._InspectionDoneByName;
   }

   public void set_InspectionDoneByName(String _InspectionDoneByName) {
      this._InspectionDoneByName = _InspectionDoneByName;
   }

   public String get_InsuredName() {
      return this._InsuredName;
   }

   public void set_InsuredName(String _InsuredName) {
      this._InsuredName = _InsuredName;
   }

   public String get_InterRefCode() {
      return this._InterRefCode;
   }

   public void set_InterRefCode(String _InterRefCode) {
      this._InterRefCode = _InterRefCode;
   }

   public String get_InterRefName() {
      return this._InterRefName;
   }

   public void set_InterRefName(String _InterRefName) {
      this._InterRefName = _InterRefName;
   }

   public String get_IntermediaryRefCode() {
      return this._IntermediaryRefCode;
   }

   public void set_IntermediaryRefCode(String _IntermediaryRefCode) {
      this._IntermediaryRefCode = _IntermediaryRefCode;
   }

   public String get_IntermediaryRefName() {
      return this._IntermediaryRefName;
   }

   public void set_IntermediaryRefName(String _IntermediaryRefName) {
      this._IntermediaryRefName = _IntermediaryRefName;
   }

   public String get_InternalBarcode() {
      return this._InternalBarcode;
   }

   public void set_InternalBarcode(String _InternalBarcode) {
      this._InternalBarcode = _InternalBarcode;
   }

   public String get_InwardedBy() {
      return this._InwardedBy;
   }

   public void set_InwardedBy(String _InwardedBy) {
      this._InwardedBy = _InwardedBy;
   }

   public Calendar get_InwardedDateTime() {
      return this._InwardedDateTime;
   }

   public void set_InwardedDateTime(Calendar _InwardedDateTime) {
      this._InwardedDateTime = _InwardedDateTime;
   }

   public int get_IsChequeCleared() {
      return this._IsChequeCleared;
   }

   public void set_IsChequeCleared(int _IsChequeCleared) {
      this._IsChequeCleared = _IsChequeCleared;
   }

   public boolean is_IsDeleted() {
      return this._IsDeleted;
   }

   public void set_IsDeleted(boolean _IsDeleted) {
      this._IsDeleted = _IsDeleted;
   }

   public int get_IsDiscrepancyLog() {
      return this._IsDiscrepancyLog;
   }

   public void set_IsDiscrepancyLog(int _IsDiscrepancyLog) {
      this._IsDiscrepancyLog = _IsDiscrepancyLog;
   }

   public boolean is_IsEditable() {
      return this._IsEditable;
   }

   public void set_IsEditable(boolean _IsEditable) {
      this._IsEditable = _IsEditable;
   }

   public boolean is_IsEverJumping() {
      return this._IsEverJumping;
   }

   public void set_IsEverJumping(boolean _IsEverJumping) {
      this._IsEverJumping = _IsEverJumping;
   }

   public boolean is_IsInwarded() {
      return this._IsInwarded;
   }

   public void set_IsInwarded(boolean _IsInwarded) {
      this._IsInwarded = _IsInwarded;
   }

   public boolean is_IsMappedStatus() {
      return this._IsMappedStatus;
   }

   public void set_IsMappedStatus(boolean _IsMappedStatus) {
      this._IsMappedStatus = _IsMappedStatus;
   }

   public boolean is_IsNCB() {
      return this._IsNCB;
   }

   public void set_IsNCB(boolean _IsNCB) {
      this._IsNCB = _IsNCB;
   }

   public int get_IsPDC() {
      return this._IsPDC;
   }

   public void set_IsPDC(int _IsPDC) {
      this._IsPDC = _IsPDC;
   }

   public boolean is_IsPassedForPolicyGeneration() {
      return this._IsPassedForPolicyGeneration;
   }

   public void set_IsPassedForPolicyGeneration(boolean _IsPassedForPolicyGeneration) {
      this._IsPassedForPolicyGeneration = _IsPassedForPolicyGeneration;
   }

   public boolean is_IsPaymentDone() {
      return this._IsPaymentDone;
   }

   public void set_IsPaymentDone(boolean _IsPaymentDone) {
      this._IsPaymentDone = _IsPaymentDone;
   }

   public int get_IsPolicyGeneratedInICM() {
      return this._IsPolicyGeneratedInICM;
   }

   public void set_IsPolicyGeneratedInICM(int _IsPolicyGeneratedInICM) {
      this._IsPolicyGeneratedInICM = _IsPolicyGeneratedInICM;
   }

   public String get_IsPostDateCheque() {
      return this._IsPostDateCheque;
   }

   public void set_IsPostDateCheque(String _IsPostDateCheque) {
      this._IsPostDateCheque = _IsPostDateCheque;
   }

   public boolean is_IsQCOk() {
      return this._IsQCOk;
   }

   public void set_IsQCOk(boolean _IsQCOk) {
      this._IsQCOk = _IsQCOk;
   }

   public boolean is_IsReissue() {
      return this._IsReissue;
   }

   public void set_IsReissue(boolean _IsReissue) {
      this._IsReissue = _IsReissue;
   }

   public boolean is_Isjumping() {
      return this._Isjumping;
   }

   public void set_Isjumping(boolean _Isjumping) {
      this._Isjumping = _Isjumping;
   }

   public Calendar get_IssueDate() {
      return this._IssueDate;
   }

   public void set_IssueDate(Calendar _IssueDate) {
      this._IssueDate = _IssueDate;
   }

   public String get_JobAllocatedBy() {
      return this._JobAllocatedBy;
   }

   public void set_JobAllocatedBy(String _JobAllocatedBy) {
      this._JobAllocatedBy = _JobAllocatedBy;
   }

   public String get_JobAllocatedTo() {
      return this._JobAllocatedTo;
   }

   public void set_JobAllocatedTo(String _JobAllocatedTo) {
      this._JobAllocatedTo = _JobAllocatedTo;
   }

   public Calendar get_JobAllocationDate() {
      return this._JobAllocationDate;
   }

   public void set_JobAllocationDate(Calendar _JobAllocationDate) {
      this._JobAllocationDate = _JobAllocationDate;
   }

   public Calendar get_JumpingStatusChangedDate() {
      return this._JumpingStatusChangedDate;
   }

   public void set_JumpingStatusChangedDate(Calendar _JumpingStatusChangedDate) {
      this._JumpingStatusChangedDate = _JumpingStatusChangedDate;
   }

   public String get_LastName() {
      return this._LastName;
   }

   public void set_LastName(String _LastName) {
      this._LastName = _LastName;
   }

   public String get_LeadNo() {
      return this._LeadNo;
   }

   public void set_LeadNo(String _LeadNo) {
      this._LeadNo = _LeadNo;
   }

   public int get_MakeId_FK() {
      return this._MakeId_FK;
   }

   public void set_MakeId_FK(int _MakeId_FK) {
      this._MakeId_FK = _MakeId_FK;
   }

   public String get_MakeName() {
      return this._MakeName;
   }

   public void set_MakeName(String _MakeName) {
      this._MakeName = _MakeName;
   }

   public BigDecimal get_MarinePremium() {
      return this._MarinePremium;
   }

   public void set_MarinePremium(BigDecimal _MarinePremium) {
      this._MarinePremium = _MarinePremium;
   }

   public BigDecimal get_MaxDiscountallowed() {
      return this._MaxDiscountallowed;
   }

   public void set_MaxDiscountallowed(BigDecimal _MaxDiscountallowed) {
      this._MaxDiscountallowed = _MaxDiscountallowed;
   }

   public Calendar get_MaxInstrDate() {
      return this._MaxInstrDate;
   }

   public void set_MaxInstrDate(Calendar _MaxInstrDate) {
      this._MaxInstrDate = _MaxInstrDate;
   }

   public String get_Message() {
      return this._Message;
   }

   public void set_Message(String _Message) {
      this._Message = _Message;
   }

   public String get_MiddleName() {
      return this._MiddleName;
   }

   public void set_MiddleName(String _MiddleName) {
      this._MiddleName = _MiddleName;
   }

   public int get_ModelId_FK() {
      return this._ModelId_FK;
   }

   public void set_ModelId_FK(int _ModelId_FK) {
      this._ModelId_FK = _ModelId_FK;
   }

   public String get_ModelVariant() {
      return this._ModelVariant;
   }

   public void set_ModelVariant(String _ModelVariant) {
      this._ModelVariant = _ModelVariant;
   }

   public BigDecimal get_NetOD() {
      return this._NetOD;
   }

   public void set_NetOD(BigDecimal _NetOD) {
      this._NetOD = _NetOD;
   }

   public float get_NetPremium() {
      return this._NetPremium;
   }

   public void set_NetPremium(float _NetPremium) {
      this._NetPremium = _NetPremium;
   }

   public BigDecimal get_Nettp() {
      return this._Nettp;
   }

   public void set_Nettp(BigDecimal _Nettp) {
      this._Nettp = _Nettp;
   }

   public String get_NewAgentCode() {
      return this._NewAgentCode;
   }

   public void set_NewAgentCode(String _NewAgentCode) {
      this._NewAgentCode = _NewAgentCode;
   }

   public String get_NewBASCode() {
      return this._NewBASCode;
   }

   public void set_NewBASCode(String _NewBASCode) {
      this._NewBASCode = _NewBASCode;
   }

   public String get_NewIntermediaryRefCode() {
      return this._NewIntermediaryRefCode;
   }

   public void set_NewIntermediaryRefCode(String _NewIntermediaryRefCode) {
      this._NewIntermediaryRefCode = _NewIntermediaryRefCode;
   }

   public String get_NewSMCode() {
      return this._NewSMCode;
   }

   public void set_NewSMCode(String _NewSMCode) {
      this._NewSMCode = _NewSMCode;
   }

   public String get_OldAgentCode() {
      return this._OldAgentCode;
   }

   public void set_OldAgentCode(String _OldAgentCode) {
      this._OldAgentCode = _OldAgentCode;
   }

   public String get_OldBASCode() {
      return this._OldBASCode;
   }

   public void set_OldBASCode(String _OldBASCode) {
      this._OldBASCode = _OldBASCode;
   }

   public String get_OldIntermediaryRefCode() {
      return this._OldIntermediaryRefCode;
   }

   public void set_OldIntermediaryRefCode(String _OldIntermediaryRefCode) {
      this._OldIntermediaryRefCode = _OldIntermediaryRefCode;
   }

   public String get_PanNo() {
      return this._PanNo;
   }

   public void set_PanNo(String _PanNo) {
      this._PanNo = _PanNo;
   }

   public String get_PassportNo() {
      return this._PassportNo;
   }

   public void set_PassportNo(String _PassportNo) {
      this._PassportNo = _PassportNo;
   }

   public String get_PaymentMode() {
      return this._PaymentMode;
   }

   public void set_PaymentMode(String _PaymentMode) {
      this._PaymentMode = _PaymentMode;
   }

   public String get_PendingPolicyBranch() {
      return this._PendingPolicyBranch;
   }

   public void set_PendingPolicyBranch(String _PendingPolicyBranch) {
      this._PendingPolicyBranch = _PendingPolicyBranch;
   }

   public String get_PinCode() {
      return this._PinCode;
   }

   public void set_PinCode(String _PinCode) {
      this._PinCode = _PinCode;
   }

   public String get_PlanName() {
      return this._PlanName;
   }

   public void set_PlanName(String _PlanName) {
      this._PlanName = _PlanName;
   }

   public Integer get_Planid_FK() {
      return this._Planid_FK;
   }

   public void set_Planid_FK(Integer _Planid_FK) {
      this._Planid_FK = _Planid_FK;
   }

   public Calendar get_PolicyEndDate() {
      return this._PolicyEndDate;
   }

   public void set_PolicyEndDate(Calendar _PolicyEndDate) {
      this._PolicyEndDate = _PolicyEndDate;
   }

   public Calendar get_PolicyGenDate() {
      return this._PolicyGenDate;
   }

   public void set_PolicyGenDate(Calendar _PolicyGenDate) {
      this._PolicyGenDate = _PolicyGenDate;
   }

   public String get_PolicyNo_FK() {
      return this._PolicyNo_FK;
   }

   public void set_PolicyNo_FK(String _PolicyNo_FK) {
      this._PolicyNo_FK = _PolicyNo_FK;
   }

   public boolean is_PolicyStatus() {
      return this._PolicyStatus;
   }

   public void set_PolicyStatus(boolean _PolicyStatus) {
      this._PolicyStatus = _PolicyStatus;
   }

   public String get_Prefix() {
      return this._Prefix;
   }

   public void set_Prefix(String _Prefix) {
      this._Prefix = _Prefix;
   }

   public BigDecimal get_Premium() {
      return this._Premium;
   }

   public void set_Premium(BigDecimal _Premium) {
      this._Premium = _Premium;
   }

   public Integer get_PreviousInsurerID() {
      return this._PreviousInsurerID;
   }

   public void set_PreviousInsurerID(Integer _PreviousInsurerID) {
      this._PreviousInsurerID = _PreviousInsurerID;
   }

   public Calendar get_PreviousPolicyEndDate() {
      return this._PreviousPolicyEndDate;
   }

   public void set_PreviousPolicyEndDate(Calendar _PreviousPolicyEndDate) {
      this._PreviousPolicyEndDate = _PreviousPolicyEndDate;
   }

   public String get_PreviousPolicyNo() {
      return this._PreviousPolicyNo;
   }

   public void set_PreviousPolicyNo(String _PreviousPolicyNo) {
      this._PreviousPolicyNo = _PreviousPolicyNo;
   }

   public int get_PreviousPolicyProcessedIn() {
      return this._PreviousPolicyProcessedIn;
   }

   public void set_PreviousPolicyProcessedIn(int _PreviousPolicyProcessedIn) {
      this._PreviousPolicyProcessedIn = _PreviousPolicyProcessedIn;
   }

   public String get_PreviousPolicyProductCode() {
      return this._PreviousPolicyProductCode;
   }

   public void set_PreviousPolicyProductCode(String _PreviousPolicyProductCode) {
      this._PreviousPolicyProductCode = _PreviousPolicyProductCode;
   }

   public int get_ProcessedAt() {
      return this._ProcessedAt;
   }

   public void set_ProcessedAt(int _ProcessedAt) {
      this._ProcessedAt = _ProcessedAt;
   }

   public String get_ProcessedAtName() {
      return this._ProcessedAtName;
   }

   public void set_ProcessedAtName(String _ProcessedAtName) {
      this._ProcessedAtName = _ProcessedAtName;
   }

   public int get_ProcessedIn() {
      return this._ProcessedIn;
   }

   public void set_ProcessedIn(int _ProcessedIn) {
      this._ProcessedIn = _ProcessedIn;
   }

   public String get_ProcessedInName() {
      return this._ProcessedInName;
   }

   public void set_ProcessedInName(String _ProcessedInName) {
      this._ProcessedInName = _ProcessedInName;
   }

   public String get_Product() {
      return this._Product;
   }

   public void set_Product(String _Product) {
      this._Product = _Product;
   }

   public String get_ProductCode() {
      return this._ProductCode;
   }

   public void set_ProductCode(String _ProductCode) {
      this._ProductCode = _ProductCode;
   }

   public String get_ProductName() {
      return this._ProductName;
   }

   public void set_ProductName(String _ProductName) {
      this._ProductName = _ProductName;
   }

   public String get_ProductType() {
      return this._ProductType;
   }

   public void set_ProductType(String _ProductType) {
      this._ProductType = _ProductType;
   }

   public BigDecimal get_ProposalAmount() {
      return this._ProposalAmount;
   }

   public void set_ProposalAmount(BigDecimal _ProposalAmount) {
      this._ProposalAmount = _ProposalAmount;
   }

   public String get_ProposalClass() {
      return this._ProposalClass;
   }

   public void set_ProposalClass(String _ProposalClass) {
      this._ProposalClass = _ProposalClass;
   }

   public String get_ProposalID_FK() {
      return this._ProposalID_FK;
   }

   public void set_ProposalID_FK(String _ProposalID_FK) {
      this._ProposalID_FK = _ProposalID_FK;
   }

   public Calendar get_ProposalOrEndorDate() {
      return this._ProposalOrEndorDate;
   }

   public void set_ProposalOrEndorDate(Calendar _ProposalOrEndorDate) {
      this._ProposalOrEndorDate = _ProposalOrEndorDate;
   }

   public int get_ProposalStatus() {
      return this._ProposalStatus;
   }

   public void set_ProposalStatus(int _ProposalStatus) {
      this._ProposalStatus = _ProposalStatus;
   }

   public int get_ProposalStatusID() {
      return this._ProposalStatusID;
   }

   public void set_ProposalStatusID(int _ProposalStatusID) {
      this._ProposalStatusID = _ProposalStatusID;
   }

   public String get_QCDone_By() {
      return this._QCDone_By;
   }

   public void set_QCDone_By(String _QCDone_By) {
      this._QCDone_By = _QCDone_By;
   }

   public Calendar get_QCDone_Date() {
      return this._QCDone_Date;
   }

   public void set_QCDone_Date(Calendar _QCDone_Date) {
      this._QCDone_Date = _QCDone_Date;
   }

   public String get_QCRemark() {
      return this._QCRemark;
   }

   public void set_QCRemark(String _QCRemark) {
      this._QCRemark = _QCRemark;
   }

   public String get_QuoteNo() {
      return this._QuoteNo;
   }

   public void set_QuoteNo(String _QuoteNo) {
      this._QuoteNo = _QuoteNo;
   }

   public String get_ReasonForChange() {
      return this._ReasonForChange;
   }

   public void set_ReasonForChange(String _ReasonForChange) {
      this._ReasonForChange = _ReasonForChange;
   }

   public Calendar get_ReceivedDate() {
      return this._ReceivedDate;
   }

   public void set_ReceivedDate(Calendar _ReceivedDate) {
      this._ReceivedDate = _ReceivedDate;
   }

   public String get_RegionId() {
      return this._RegionId;
   }

   public void set_RegionId(String _RegionId) {
      this._RegionId = _RegionId;
   }

   public String get_RegionName() {
      return this._RegionName;
   }

   public void set_RegionName(String _RegionName) {
      this._RegionName = _RegionName;
   }

   public String get_RegistrationNo() {
      return this._RegistrationNo;
   }

   public void set_RegistrationNo(String _RegistrationNo) {
      this._RegistrationNo = _RegistrationNo;
   }

   public String get_Remark() {
      return this._Remark;
   }

   public void set_Remark(String _Remark) {
      this._Remark = _Remark;
   }

   public DocumentMasterBO[] get_RequiredDocument() {
      return this._RequiredDocument;
   }

   public void set_RequiredDocument(DocumentMasterBO[] _RequiredDocument) {
      this._RequiredDocument = _RequiredDocument;
   }

   public Calendar get_RiskStartDate() {
      return this._RiskStartDate;
   }

   public void set_RiskStartDate(Calendar _RiskStartDate) {
      this._RiskStartDate = _RiskStartDate;
   }

   public String get_RuleNumber() {
      return this._RuleNumber;
   }

   public void set_RuleNumber(String _RuleNumber) {
      this._RuleNumber = _RuleNumber;
   }

   public String get_SMCode() {
      return this._SMCode;
   }

   public void set_SMCode(String _SMCode) {
      this._SMCode = _SMCode;
   }

   public String get_SMName() {
      return this._SMName;
   }

   public void set_SMName(String _SMName) {
      this._SMName = _SMName;
   }

   public boolean is_SearchMode() {
      return this._SearchMode;
   }

   public void set_SearchMode(boolean _SearchMode) {
      this._SearchMode = _SearchMode;
   }

   public BigDecimal get_ServiceLeviedTax() {
      return this._ServiceLeviedTax;
   }

   public void set_ServiceLeviedTax(BigDecimal _ServiceLeviedTax) {
      this._ServiceLeviedTax = _ServiceLeviedTax;
   }

   public String get_ServiceStatus() {
      return this._ServiceStatus;
   }

   public void set_ServiceStatus(String _ServiceStatus) {
      this._ServiceStatus = _ServiceStatus;
   }

   public BigDecimal get_ServiceTax() {
      return this._ServiceTax;
   }

   public void set_ServiceTax(BigDecimal _ServiceTax) {
      this._ServiceTax = _ServiceTax;
   }

   public String get_State() {
      return this._State;
   }

   public void set_State(String _State) {
      this._State = _State;
   }

   public int get_StateID() {
      return this._StateID;
   }

   public void set_StateID(int _StateID) {
      this._StateID = _StateID;
   }

   public String get_StatusCode() {
      return this._StatusCode;
   }

   public void set_StatusCode(String _StatusCode) {
      this._StatusCode = _StatusCode;
   }

   public BigDecimal get_SurchargeValue() {
      return this._SurchargeValue;
   }

   public void set_SurchargeValue(BigDecimal _SurchargeValue) {
      this._SurchargeValue = _SurchargeValue;
   }

   public String get_TableName() {
      return this._TableName;
   }

   public void set_TableName(String _TableName) {
      this._TableName = _TableName;
   }

   public String get_TaxExempRemark() {
      return this._TaxExempRemark;
   }

   public void set_TaxExempRemark(String _TaxExempRemark) {
      this._TaxExempRemark = _TaxExempRemark;
   }

   public BigDecimal get_Terrorism() {
      return this._Terrorism;
   }

   public void set_Terrorism(BigDecimal _Terrorism) {
      this._Terrorism = _Terrorism;
   }

   public BigDecimal get_TotalPremium() {
      return this._TotalPremium;
   }

   public void set_TotalPremium(BigDecimal _TotalPremium) {
      this._TotalPremium = _TotalPremium;
   }

   public String get_TransID_FK() {
      return this._TransID_FK;
   }

   public void set_TransID_FK(String _TransID_FK) {
      this._TransID_FK = _TransID_FK;
   }

   public int get_UnderWriterApproved() {
      return this._UnderWriterApproved;
   }

   public void set_UnderWriterApproved(int _UnderWriterApproved) {
      this._UnderWriterApproved = _UnderWriterApproved;
   }

   public Integer get_Variantid_FK() {
      return this._Variantid_FK;
   }

   public void set_Variantid_FK(Integer _Variantid_FK) {
      this._Variantid_FK = _Variantid_FK;
   }

   public String get_VehicalName() {
      return this._VehicalName;
   }

   public void set_VehicalName(String _VehicalName) {
      this._VehicalName = _VehicalName;
   }

   public int get_VehicalSubTypeId_FK() {
      return this._VehicalSubTypeId_FK;
   }

   public void set_VehicalSubTypeId_FK(int _VehicalSubTypeId_FK) {
      this._VehicalSubTypeId_FK = _VehicalSubTypeId_FK;
   }

   public int get_VehicalTypeId_FK() {
      return this._VehicalTypeId_FK;
   }

   public void set_VehicalTypeId_FK(int _VehicalTypeId_FK) {
      this._VehicalTypeId_FK = _VehicalTypeId_FK;
   }

   public String get_VehicalTypeName() {
      return this._VehicalTypeName;
   }

   public void set_VehicalTypeName(String _VehicalTypeName) {
      this._VehicalTypeName = _VehicalTypeName;
   }

   public boolean is_VendorExists() {
      return this._VendorExists;
   }

   public void set_VendorExists(boolean _VendorExists) {
      this._VendorExists = _VendorExists;
   }

   public BigDecimal get_War() {
      return this._War;
   }

   public void set_War(BigDecimal _War) {
      this._War = _War;
   }

   public int get_WhereBy() {
      return this._WhereBy;
   }

   public void set_WhereBy(int _WhereBy) {
      this._WhereBy = _WhereBy;
   }

   public String get_WhereValue() {
      return this._WhereValue;
   }

   public void set_WhereValue(String _WhereValue) {
      this._WhereValue = _WhereValue;
   }

   public Integer get_YearOfManufacture() {
      return this._YearOfManufacture;
   }

   public void set_YearOfManufacture(Integer _YearOfManufacture) {
      this._YearOfManufacture = _YearOfManufacture;
   }

   public Integer get_YearOfRegistration() {
      return this._YearOfRegistration;
   }

   public void set_YearOfRegistration(Integer _YearOfRegistration) {
      this._YearOfRegistration = _YearOfRegistration;
   }

   public String get_branchName() {
      return this._branchName;
   }

   public void set_branchName(String _branchName) {
      this._branchName = _branchName;
   }

   public String get_chequeNo() {
      return this._chequeNo;
   }

   public void set_chequeNo(String _chequeNo) {
      this._chequeNo = _chequeNo;
   }

   public String get_className() {
      return this._className;
   }

   public void set_className(String _className) {
      this._className = _className;
   }

   public DiscrepancyCategoryBO[] get_discrCategory() {
      return this._discrCategory;
   }

   public void set_discrCategory(DiscrepancyCategoryBO[] _discrCategory) {
      this._discrCategory = _discrCategory;
   }

   public BigDecimal get_srcc() {
      return this._srcc;
   }

   public void set_srcc(BigDecimal _srcc) {
      this._srcc = _srcc;
   }

   public BigDecimal get_stampduty() {
      return this._stampduty;
   }

   public void set_stampduty(BigDecimal _stampduty) {
      this._stampduty = _stampduty;
   }

   public String get_userName() {
      return this._userName;
   }

   public void set_userName(String _userName) {
      this._userName = _userName;
   }

   public int getIntRowID() {
      return this.intRowID;
   }

   public void setIntRowID(int intRowID) {
      this.intRowID = intRowID;
   }

   public int getIntSubDiscrepancyID() {
      return this.intSubDiscrepancyID;
   }

   public void setIntSubDiscrepancyID(int intSubDiscrepancyID) {
      this.intSubDiscrepancyID = intSubDiscrepancyID;
   }

   public Calendar getQcResolvedDate() {
      return this.qcResolvedDate;
   }

   public void setQcResolvedDate(Calendar qcResolvedDate) {
      this.qcResolvedDate = qcResolvedDate;
   }

   public String getReceiveMode() {
      return this.receiveMode;
   }

   public void setReceiveMode(String receiveMode) {
      this.receiveMode = receiveMode;
   }

   public String getSapCode() {
      return this.sapCode;
   }

   public void setSapCode(String sapCode) {
      this.sapCode = sapCode;
   }

   public BigDecimal getShortPremium() {
      return this.shortPremium;
   }

   public void setShortPremium(BigDecimal shortPremium) {
      this.shortPremium = shortPremium;
   }

   public String getStrDiscrepancyName() {
      return this.strDiscrepancyName;
   }

   public void setStrDiscrepancyName(String strDiscrepancyName) {
      this.strDiscrepancyName = strDiscrepancyName;
   }

   public Calendar getStrRaisedDate() {
      return this.strRaisedDate;
   }

   public void setStrRaisedDate(Calendar strRaisedDate) {
      this.strRaisedDate = strRaisedDate;
   }

   public String getStrResolvedRemark() {
      return this.strResolvedRemark;
   }

   public void setStrResolvedRemark(String strResolvedRemark) {
      this.strResolvedRemark = strResolvedRemark;
   }

   public String getStrSubDiscrepancyName() {
      return this.strSubDiscrepancyName;
   }

   public void setStrSubDiscrepancyName(String strSubDiscrepancyName) {
      this.strSubDiscrepancyName = strSubDiscrepancyName;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof ProposalDetailsBO)) {
         return false;
      } else {
         ProposalDetailsBO other = (ProposalDetailsBO)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = super.equals(obj) && (this._Actionable == null && other.get_Actionable() == null || this._Actionable != null && this._Actionable.equals(other.get_Actionable())) && (this._Address == null && other.get_Address() == null || this._Address != null && this._Address.equals(other.get_Address())) && (this._AgentCode == null && other.get_AgentCode() == null || this._AgentCode != null && this._AgentCode.equals(other.get_AgentCode())) && (this._AgentName == null && other.get_AgentName() == null || this._AgentName != null && this._AgentName.equals(other.get_AgentName())) && (this._AgentRetentionDetailsDummy == null && other.get_AgentRetentionDetailsDummy() == null || this._AgentRetentionDetailsDummy != null && Arrays.equals(this._AgentRetentionDetailsDummy, other.get_AgentRetentionDetailsDummy())) && (this._AllocatedBy == null && other.get_AllocatedBy() == null || this._AllocatedBy != null && this._AllocatedBy.equals(other.get_AllocatedBy())) && (this._AllocatedTo == null && other.get_AllocatedTo() == null || this._AllocatedTo != null && this._AllocatedTo.equals(other.get_AllocatedTo())) && (this._AllocationDate == null && other.get_AllocationDate() == null || this._AllocationDate != null && this._AllocationDate.equals(other.get_AllocationDate())) && this._BASExists == other.is_BASExists() && (this._BancaCode == null && other.get_BancaCode() == null || this._BancaCode != null && this._BancaCode.equals(other.get_BancaCode())) && this._BarcodeExists == other.is_BarcodeExists() && (this._BasCode == null && other.get_BasCode() == null || this._BasCode != null && this._BasCode.equals(other.get_BasCode())) && (this._BasName == null && other.get_BasName() == null || this._BasName != null && this._BasName.equals(other.get_BasName())) && (this._BranchCode == null && other.get_BranchCode() == null || this._BranchCode != null && this._BranchCode.equals(other.get_BranchCode())) && (this._BranchID == null && other.get_BranchID() == null || this._BranchID != null && this._BranchID.equals(other.get_BranchID())) && (this._BusinessType == null && other.get_BusinessType() == null || this._BusinessType != null && this._BusinessType.equals(other.get_BusinessType())) && (this._CSOCode == null && other.get_CSOCode() == null || this._CSOCode != null && this._CSOCode.equals(other.get_CSOCode())) && (this._CSOName == null && other.get_CSOName() == null || this._CSOName != null && this._CSOName.equals(other.get_CSOName())) && (this._CancelledPolicy == null && other.get_CancelledPolicy() == null || this._CancelledPolicy != null && this._CancelledPolicy.equals(other.get_CancelledPolicy())) && (this._Cess == null && other.get_Cess() == null || this._Cess != null && this._Cess.equals(other.get_Cess())) && (this._ChessisNo == null && other.get_ChessisNo() == null || this._ChessisNo != null && this._ChessisNo.equals(other.get_ChessisNo())) && this._CityID == other.get_CityID() && (this._CityName == null && other.get_CityName() == null || this._CityName != null && this._CityName.equals(other.get_CityName())) && (this._ClassID == null && other.get_ClassID() == null || this._ClassID != null && this._ClassID.equals(other.get_ClassID())) && (this._Co_InsStatus == null && other.get_Co_InsStatus() == null || this._Co_InsStatus != null && this._Co_InsStatus.equals(other.get_Co_InsStatus())) && (this._ContactNumber == null && other.get_ContactNumber() == null || this._ContactNumber != null && this._ContactNumber.equals(other.get_ContactNumber())) && this._CoverNoteExits == other.is_CoverNoteExits() && (this._DiscrepancyID_FK == null && other.get_DiscrepancyID_FK() == null || this._DiscrepancyID_FK != null && this._DiscrepancyID_FK.equals(other.get_DiscrepancyID_FK())) && (this._District == null && other.get_District() == null || this._District != null && this._District.equals(other.get_District())) && this._DistrictID == other.get_DistrictID() && (this._DocumentType == null && other.get_DocumentType() == null || this._DocumentType != null && this._DocumentType.equals(other.get_DocumentType())) && (this._ERF == null && other.get_ERF() == null || this._ERF != null && this._ERF.equals(other.get_ERF())) && (this._EndorsementCategory == null && other.get_EndorsementCategory() == null || this._EndorsementCategory != null && this._EndorsementCategory.equals(other.get_EndorsementCategory())) && (this._EndorsementNo == null && other.get_EndorsementNo() == null || this._EndorsementNo != null && this._EndorsementNo.equals(other.get_EndorsementNo())) && this._EndorsementReasonID == other.get_EndorsementReasonID() && (this._EndorsementSubTypeName == null && other.get_EndorsementSubTypeName() == null || this._EndorsementSubTypeName != null && this._EndorsementSubTypeName.equals(other.get_EndorsementSubTypeName())) && (this._EndorsementType == null && other.get_EndorsementType() == null || this._EndorsementType != null && this._EndorsementType.equals(other.get_EndorsementType())) && (this._EndorsementTypeName == null && other.get_EndorsementTypeName() == null || this._EndorsementTypeName != null && this._EndorsementTypeName.equals(other.get_EndorsementTypeName())) && (this._EngineNo == null && other.get_EngineNo() == null || this._EngineNo != null && this._EngineNo.equals(other.get_EngineNo())) && (this._ExternalBarcode == null && other.get_ExternalBarcode() == null || this._ExternalBarcode != null && this._ExternalBarcode.equals(other.get_ExternalBarcode())) && (this._FirstAllocationDate == null && other.get_FirstAllocationDate() == null || this._FirstAllocationDate != null && this._FirstAllocationDate.equals(other.get_FirstAllocationDate())) && (this._FirstName == null && other.get_FirstName() == null || this._FirstName != null && this._FirstName.equals(other.get_FirstName())) && (this._GST == null && other.get_GST() == null || this._GST != null && this._GST.equals(other.get_GST())) && (this._HandOverTo == null && other.get_HandOverTo() == null || this._HandOverTo != null && this._HandOverTo.equals(other.get_HandOverTo())) && (this._HandoverDate == null && other.get_HandoverDate() == null || this._HandoverDate != null && this._HandoverDate.equals(other.get_HandoverDate())) && (this._HigherEduCess == null && other.get_HigherEduCess() == null || this._HigherEduCess != null && this._HigherEduCess.equals(other.get_HigherEduCess())) && (this._InspectionDateTime == null && other.get_InspectionDateTime() == null || this._InspectionDateTime != null && this._InspectionDateTime.equals(other.get_InspectionDateTime())) && this._InspectionDocNo == other.get_InspectionDocNo() && (this._InspectionDoneBy == null && other.get_InspectionDoneBy() == null || this._InspectionDoneBy != null && this._InspectionDoneBy.equals(other.get_InspectionDoneBy())) && (this._InspectionDoneByName == null && other.get_InspectionDoneByName() == null || this._InspectionDoneByName != null && this._InspectionDoneByName.equals(other.get_InspectionDoneByName())) && (this._InsuredName == null && other.get_InsuredName() == null || this._InsuredName != null && this._InsuredName.equals(other.get_InsuredName())) && (this._InterRefCode == null && other.get_InterRefCode() == null || this._InterRefCode != null && this._InterRefCode.equals(other.get_InterRefCode())) && (this._InterRefName == null && other.get_InterRefName() == null || this._InterRefName != null && this._InterRefName.equals(other.get_InterRefName())) && (this._IntermediaryRefCode == null && other.get_IntermediaryRefCode() == null || this._IntermediaryRefCode != null && this._IntermediaryRefCode.equals(other.get_IntermediaryRefCode())) && (this._IntermediaryRefName == null && other.get_IntermediaryRefName() == null || this._IntermediaryRefName != null && this._IntermediaryRefName.equals(other.get_IntermediaryRefName())) && (this._InternalBarcode == null && other.get_InternalBarcode() == null || this._InternalBarcode != null && this._InternalBarcode.equals(other.get_InternalBarcode())) && (this._InwardedBy == null && other.get_InwardedBy() == null || this._InwardedBy != null && this._InwardedBy.equals(other.get_InwardedBy())) && (this._InwardedDateTime == null && other.get_InwardedDateTime() == null || this._InwardedDateTime != null && this._InwardedDateTime.equals(other.get_InwardedDateTime())) && this._IsChequeCleared == other.get_IsChequeCleared() && this._IsDeleted == other.is_IsDeleted() && this._IsDiscrepancyLog == other.get_IsDiscrepancyLog() && this._IsEditable == other.is_IsEditable() && this._IsEverJumping == other.is_IsEverJumping() && this._IsInwarded == other.is_IsInwarded() && this._IsMappedStatus == other.is_IsMappedStatus() && this._IsNCB == other.is_IsNCB() && this._IsPDC == other.get_IsPDC() && this._IsPassedForPolicyGeneration == other.is_IsPassedForPolicyGeneration() && this._IsPaymentDone == other.is_IsPaymentDone() && this._IsPolicyGeneratedInICM == other.get_IsPolicyGeneratedInICM() && (this._IsPostDateCheque == null && other.get_IsPostDateCheque() == null || this._IsPostDateCheque != null && this._IsPostDateCheque.equals(other.get_IsPostDateCheque())) && this._IsQCOk == other.is_IsQCOk() && this._IsReissue == other.is_IsReissue() && this._Isjumping == other.is_Isjumping() && (this._IssueDate == null && other.get_IssueDate() == null || this._IssueDate != null && this._IssueDate.equals(other.get_IssueDate())) && (this._JobAllocatedBy == null && other.get_JobAllocatedBy() == null || this._JobAllocatedBy != null && this._JobAllocatedBy.equals(other.get_JobAllocatedBy())) && (this._JobAllocatedTo == null && other.get_JobAllocatedTo() == null || this._JobAllocatedTo != null && this._JobAllocatedTo.equals(other.get_JobAllocatedTo())) && (this._JobAllocationDate == null && other.get_JobAllocationDate() == null || this._JobAllocationDate != null && this._JobAllocationDate.equals(other.get_JobAllocationDate())) && (this._JumpingStatusChangedDate == null && other.get_JumpingStatusChangedDate() == null || this._JumpingStatusChangedDate != null && this._JumpingStatusChangedDate.equals(other.get_JumpingStatusChangedDate())) && (this._LastName == null && other.get_LastName() == null || this._LastName != null && this._LastName.equals(other.get_LastName())) && (this._LeadNo == null && other.get_LeadNo() == null || this._LeadNo != null && this._LeadNo.equals(other.get_LeadNo())) && this._MakeId_FK == other.get_MakeId_FK() && (this._MakeName == null && other.get_MakeName() == null || this._MakeName != null && this._MakeName.equals(other.get_MakeName())) && (this._MarinePremium == null && other.get_MarinePremium() == null || this._MarinePremium != null && this._MarinePremium.equals(other.get_MarinePremium())) && (this._MaxDiscountallowed == null && other.get_MaxDiscountallowed() == null || this._MaxDiscountallowed != null && this._MaxDiscountallowed.equals(other.get_MaxDiscountallowed())) && (this._MaxInstrDate == null && other.get_MaxInstrDate() == null || this._MaxInstrDate != null && this._MaxInstrDate.equals(other.get_MaxInstrDate())) && (this._Message == null && other.get_Message() == null || this._Message != null && this._Message.equals(other.get_Message())) && (this._MiddleName == null && other.get_MiddleName() == null || this._MiddleName != null && this._MiddleName.equals(other.get_MiddleName())) && this._ModelId_FK == other.get_ModelId_FK() && (this._ModelVariant == null && other.get_ModelVariant() == null || this._ModelVariant != null && this._ModelVariant.equals(other.get_ModelVariant())) && (this._NetOD == null && other.get_NetOD() == null || this._NetOD != null && this._NetOD.equals(other.get_NetOD())) && this._NetPremium == other.get_NetPremium() && (this._Nettp == null && other.get_Nettp() == null || this._Nettp != null && this._Nettp.equals(other.get_Nettp())) && (this._NewAgentCode == null && other.get_NewAgentCode() == null || this._NewAgentCode != null && this._NewAgentCode.equals(other.get_NewAgentCode())) && (this._NewBASCode == null && other.get_NewBASCode() == null || this._NewBASCode != null && this._NewBASCode.equals(other.get_NewBASCode())) && (this._NewIntermediaryRefCode == null && other.get_NewIntermediaryRefCode() == null || this._NewIntermediaryRefCode != null && this._NewIntermediaryRefCode.equals(other.get_NewIntermediaryRefCode())) && (this._NewSMCode == null && other.get_NewSMCode() == null || this._NewSMCode != null && this._NewSMCode.equals(other.get_NewSMCode())) && (this._OldAgentCode == null && other.get_OldAgentCode() == null || this._OldAgentCode != null && this._OldAgentCode.equals(other.get_OldAgentCode())) && (this._OldBASCode == null && other.get_OldBASCode() == null || this._OldBASCode != null && this._OldBASCode.equals(other.get_OldBASCode())) && (this._OldIntermediaryRefCode == null && other.get_OldIntermediaryRefCode() == null || this._OldIntermediaryRefCode != null && this._OldIntermediaryRefCode.equals(other.get_OldIntermediaryRefCode())) && (this._PanNo == null && other.get_PanNo() == null || this._PanNo != null && this._PanNo.equals(other.get_PanNo())) && (this._PassportNo == null && other.get_PassportNo() == null || this._PassportNo != null && this._PassportNo.equals(other.get_PassportNo())) && (this._PaymentMode == null && other.get_PaymentMode() == null || this._PaymentMode != null && this._PaymentMode.equals(other.get_PaymentMode())) && (this._PendingPolicyBranch == null && other.get_PendingPolicyBranch() == null || this._PendingPolicyBranch != null && this._PendingPolicyBranch.equals(other.get_PendingPolicyBranch())) && (this._PinCode == null && other.get_PinCode() == null || this._PinCode != null && this._PinCode.equals(other.get_PinCode())) && (this._PlanName == null && other.get_PlanName() == null || this._PlanName != null && this._PlanName.equals(other.get_PlanName())) && (this._Planid_FK == null && other.get_Planid_FK() == null || this._Planid_FK != null && this._Planid_FK.equals(other.get_Planid_FK())) && (this._PolicyEndDate == null && other.get_PolicyEndDate() == null || this._PolicyEndDate != null && this._PolicyEndDate.equals(other.get_PolicyEndDate())) && (this._PolicyGenDate == null && other.get_PolicyGenDate() == null || this._PolicyGenDate != null && this._PolicyGenDate.equals(other.get_PolicyGenDate())) && (this._PolicyNo_FK == null && other.get_PolicyNo_FK() == null || this._PolicyNo_FK != null && this._PolicyNo_FK.equals(other.get_PolicyNo_FK())) && this._PolicyStatus == other.is_PolicyStatus() && (this._Prefix == null && other.get_Prefix() == null || this._Prefix != null && this._Prefix.equals(other.get_Prefix())) && (this._Premium == null && other.get_Premium() == null || this._Premium != null && this._Premium.equals(other.get_Premium())) && (this._PreviousInsurerID == null && other.get_PreviousInsurerID() == null || this._PreviousInsurerID != null && this._PreviousInsurerID.equals(other.get_PreviousInsurerID())) && (this._PreviousPolicyEndDate == null && other.get_PreviousPolicyEndDate() == null || this._PreviousPolicyEndDate != null && this._PreviousPolicyEndDate.equals(other.get_PreviousPolicyEndDate())) && (this._PreviousPolicyNo == null && other.get_PreviousPolicyNo() == null || this._PreviousPolicyNo != null && this._PreviousPolicyNo.equals(other.get_PreviousPolicyNo())) && this._PreviousPolicyProcessedIn == other.get_PreviousPolicyProcessedIn() && (this._PreviousPolicyProductCode == null && other.get_PreviousPolicyProductCode() == null || this._PreviousPolicyProductCode != null && this._PreviousPolicyProductCode.equals(other.get_PreviousPolicyProductCode())) && this._ProcessedAt == other.get_ProcessedAt() && (this._ProcessedAtName == null && other.get_ProcessedAtName() == null || this._ProcessedAtName != null && this._ProcessedAtName.equals(other.get_ProcessedAtName())) && this._ProcessedIn == other.get_ProcessedIn() && (this._ProcessedInName == null && other.get_ProcessedInName() == null || this._ProcessedInName != null && this._ProcessedInName.equals(other.get_ProcessedInName())) && (this._Product == null && other.get_Product() == null || this._Product != null && this._Product.equals(other.get_Product())) && (this._ProductCode == null && other.get_ProductCode() == null || this._ProductCode != null && this._ProductCode.equals(other.get_ProductCode())) && (this._ProductName == null && other.get_ProductName() == null || this._ProductName != null && this._ProductName.equals(other.get_ProductName())) && (this._ProductType == null && other.get_ProductType() == null || this._ProductType != null && this._ProductType.equals(other.get_ProductType())) && (this._ProposalAmount == null && other.get_ProposalAmount() == null || this._ProposalAmount != null && this._ProposalAmount.equals(other.get_ProposalAmount())) && (this._ProposalClass == null && other.get_ProposalClass() == null || this._ProposalClass != null && this._ProposalClass.equals(other.get_ProposalClass())) && (this._ProposalID_FK == null && other.get_ProposalID_FK() == null || this._ProposalID_FK != null && this._ProposalID_FK.equals(other.get_ProposalID_FK())) && (this._ProposalOrEndorDate == null && other.get_ProposalOrEndorDate() == null || this._ProposalOrEndorDate != null && this._ProposalOrEndorDate.equals(other.get_ProposalOrEndorDate())) && this._ProposalStatus == other.get_ProposalStatus() && this._ProposalStatusID == other.get_ProposalStatusID() && (this._QCDone_By == null && other.get_QCDone_By() == null || this._QCDone_By != null && this._QCDone_By.equals(other.get_QCDone_By())) && (this._QCDone_Date == null && other.get_QCDone_Date() == null || this._QCDone_Date != null && this._QCDone_Date.equals(other.get_QCDone_Date())) && (this._QCRemark == null && other.get_QCRemark() == null || this._QCRemark != null && this._QCRemark.equals(other.get_QCRemark())) && (this._QuoteNo == null && other.get_QuoteNo() == null || this._QuoteNo != null && this._QuoteNo.equals(other.get_QuoteNo())) && (this._ReasonForChange == null && other.get_ReasonForChange() == null || this._ReasonForChange != null && this._ReasonForChange.equals(other.get_ReasonForChange())) && (this._ReceivedDate == null && other.get_ReceivedDate() == null || this._ReceivedDate != null && this._ReceivedDate.equals(other.get_ReceivedDate())) && (this._RegionId == null && other.get_RegionId() == null || this._RegionId != null && this._RegionId.equals(other.get_RegionId())) && (this._RegionName == null && other.get_RegionName() == null || this._RegionName != null && this._RegionName.equals(other.get_RegionName())) && (this._RegistrationNo == null && other.get_RegistrationNo() == null || this._RegistrationNo != null && this._RegistrationNo.equals(other.get_RegistrationNo())) && (this._Remark == null && other.get_Remark() == null || this._Remark != null && this._Remark.equals(other.get_Remark())) && (this._RequiredDocument == null && other.get_RequiredDocument() == null || this._RequiredDocument != null && Arrays.equals(this._RequiredDocument, other.get_RequiredDocument())) && (this._RiskStartDate == null && other.get_RiskStartDate() == null || this._RiskStartDate != null && this._RiskStartDate.equals(other.get_RiskStartDate())) && (this._RuleNumber == null && other.get_RuleNumber() == null || this._RuleNumber != null && this._RuleNumber.equals(other.get_RuleNumber())) && (this._SMCode == null && other.get_SMCode() == null || this._SMCode != null && this._SMCode.equals(other.get_SMCode())) && (this._SMName == null && other.get_SMName() == null || this._SMName != null && this._SMName.equals(other.get_SMName())) && this._SearchMode == other.is_SearchMode() && (this._ServiceLeviedTax == null && other.get_ServiceLeviedTax() == null || this._ServiceLeviedTax != null && this._ServiceLeviedTax.equals(other.get_ServiceLeviedTax())) && (this._ServiceStatus == null && other.get_ServiceStatus() == null || this._ServiceStatus != null && this._ServiceStatus.equals(other.get_ServiceStatus())) && (this._ServiceTax == null && other.get_ServiceTax() == null || this._ServiceTax != null && this._ServiceTax.equals(other.get_ServiceTax())) && (this._State == null && other.get_State() == null || this._State != null && this._State.equals(other.get_State())) && this._StateID == other.get_StateID() && (this._StatusCode == null && other.get_StatusCode() == null || this._StatusCode != null && this._StatusCode.equals(other.get_StatusCode())) && (this._SurchargeValue == null && other.get_SurchargeValue() == null || this._SurchargeValue != null && this._SurchargeValue.equals(other.get_SurchargeValue())) && (this._TableName == null && other.get_TableName() == null || this._TableName != null && this._TableName.equals(other.get_TableName())) && (this._TaxExempRemark == null && other.get_TaxExempRemark() == null || this._TaxExempRemark != null && this._TaxExempRemark.equals(other.get_TaxExempRemark())) && (this._Terrorism == null && other.get_Terrorism() == null || this._Terrorism != null && this._Terrorism.equals(other.get_Terrorism())) && (this._TotalPremium == null && other.get_TotalPremium() == null || this._TotalPremium != null && this._TotalPremium.equals(other.get_TotalPremium())) && (this._TransID_FK == null && other.get_TransID_FK() == null || this._TransID_FK != null && this._TransID_FK.equals(other.get_TransID_FK())) && this._UnderWriterApproved == other.get_UnderWriterApproved() && (this._Variantid_FK == null && other.get_Variantid_FK() == null || this._Variantid_FK != null && this._Variantid_FK.equals(other.get_Variantid_FK())) && (this._VehicalName == null && other.get_VehicalName() == null || this._VehicalName != null && this._VehicalName.equals(other.get_VehicalName())) && this._VehicalSubTypeId_FK == other.get_VehicalSubTypeId_FK() && this._VehicalTypeId_FK == other.get_VehicalTypeId_FK() && (this._VehicalTypeName == null && other.get_VehicalTypeName() == null || this._VehicalTypeName != null && this._VehicalTypeName.equals(other.get_VehicalTypeName())) && this._VendorExists == other.is_VendorExists() && (this._War == null && other.get_War() == null || this._War != null && this._War.equals(other.get_War())) && this._WhereBy == other.get_WhereBy() && (this._WhereValue == null && other.get_WhereValue() == null || this._WhereValue != null && this._WhereValue.equals(other.get_WhereValue())) && (this._YearOfManufacture == null && other.get_YearOfManufacture() == null || this._YearOfManufacture != null && this._YearOfManufacture.equals(other.get_YearOfManufacture())) && (this._YearOfRegistration == null && other.get_YearOfRegistration() == null || this._YearOfRegistration != null && this._YearOfRegistration.equals(other.get_YearOfRegistration())) && (this._branchName == null && other.get_branchName() == null || this._branchName != null && this._branchName.equals(other.get_branchName())) && (this._chequeNo == null && other.get_chequeNo() == null || this._chequeNo != null && this._chequeNo.equals(other.get_chequeNo())) && (this._className == null && other.get_className() == null || this._className != null && this._className.equals(other.get_className())) && (this._discrCategory == null && other.get_discrCategory() == null || this._discrCategory != null && Arrays.equals(this._discrCategory, other.get_discrCategory())) && (this._srcc == null && other.get_srcc() == null || this._srcc != null && this._srcc.equals(other.get_srcc())) && (this._stampduty == null && other.get_stampduty() == null || this._stampduty != null && this._stampduty.equals(other.get_stampduty())) && (this._userName == null && other.get_userName() == null || this._userName != null && this._userName.equals(other.get_userName())) && this.intRowID == other.getIntRowID() && this.intSubDiscrepancyID == other.getIntSubDiscrepancyID() && (this.qcResolvedDate == null && other.getQcResolvedDate() == null || this.qcResolvedDate != null && this.qcResolvedDate.equals(other.getQcResolvedDate())) && (this.receiveMode == null && other.getReceiveMode() == null || this.receiveMode != null && this.receiveMode.equals(other.getReceiveMode())) && (this.sapCode == null && other.getSapCode() == null || this.sapCode != null && this.sapCode.equals(other.getSapCode())) && (this.shortPremium == null && other.getShortPremium() == null || this.shortPremium != null && this.shortPremium.equals(other.getShortPremium())) && (this.strDiscrepancyName == null && other.getStrDiscrepancyName() == null || this.strDiscrepancyName != null && this.strDiscrepancyName.equals(other.getStrDiscrepancyName())) && (this.strRaisedDate == null && other.getStrRaisedDate() == null || this.strRaisedDate != null && this.strRaisedDate.equals(other.getStrRaisedDate())) && (this.strResolvedRemark == null && other.getStrResolvedRemark() == null || this.strResolvedRemark != null && this.strResolvedRemark.equals(other.getStrResolvedRemark())) && (this.strSubDiscrepancyName == null && other.getStrSubDiscrepancyName() == null || this.strSubDiscrepancyName != null && this.strSubDiscrepancyName.equals(other.getStrSubDiscrepancyName()));
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
         int _hashCode = super.hashCode();
         if (this.get_Actionable() != null) {
            _hashCode += this.get_Actionable().hashCode();
         }

         if (this.get_Address() != null) {
            _hashCode += this.get_Address().hashCode();
         }

         if (this.get_AgentCode() != null) {
            _hashCode += this.get_AgentCode().hashCode();
         }

         if (this.get_AgentName() != null) {
            _hashCode += this.get_AgentName().hashCode();
         }

         int i;
         Object obj;
         if (this.get_AgentRetentionDetailsDummy() != null) {
            for(i = 0; i < Array.getLength(this.get_AgentRetentionDetailsDummy()); ++i) {
               obj = Array.get(this.get_AgentRetentionDetailsDummy(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         if (this.get_AllocatedBy() != null) {
            _hashCode += this.get_AllocatedBy().hashCode();
         }

         if (this.get_AllocatedTo() != null) {
            _hashCode += this.get_AllocatedTo().hashCode();
         }

         if (this.get_AllocationDate() != null) {
            _hashCode += this.get_AllocationDate().hashCode();
         }

         _hashCode += (this.is_BASExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_BancaCode() != null) {
            _hashCode += this.get_BancaCode().hashCode();
         }

         _hashCode += (this.is_BarcodeExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_BasCode() != null) {
            _hashCode += this.get_BasCode().hashCode();
         }

         if (this.get_BasName() != null) {
            _hashCode += this.get_BasName().hashCode();
         }

         if (this.get_BranchCode() != null) {
            _hashCode += this.get_BranchCode().hashCode();
         }

         if (this.get_BranchID() != null) {
            _hashCode += this.get_BranchID().hashCode();
         }

         if (this.get_BusinessType() != null) {
            _hashCode += this.get_BusinessType().hashCode();
         }

         if (this.get_CSOCode() != null) {
            _hashCode += this.get_CSOCode().hashCode();
         }

         if (this.get_CSOName() != null) {
            _hashCode += this.get_CSOName().hashCode();
         }

         if (this.get_CancelledPolicy() != null) {
            _hashCode += this.get_CancelledPolicy().hashCode();
         }

         if (this.get_Cess() != null) {
            _hashCode += this.get_Cess().hashCode();
         }

         if (this.get_ChessisNo() != null) {
            _hashCode += this.get_ChessisNo().hashCode();
         }

         _hashCode += this.get_CityID();
         if (this.get_CityName() != null) {
            _hashCode += this.get_CityName().hashCode();
         }

         if (this.get_ClassID() != null) {
            _hashCode += this.get_ClassID().hashCode();
         }

         if (this.get_Co_InsStatus() != null) {
            _hashCode += this.get_Co_InsStatus().hashCode();
         }

         if (this.get_ContactNumber() != null) {
            _hashCode += this.get_ContactNumber().hashCode();
         }

         _hashCode += (this.is_CoverNoteExits() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_DiscrepancyID_FK() != null) {
            _hashCode += this.get_DiscrepancyID_FK().hashCode();
         }

         if (this.get_District() != null) {
            _hashCode += this.get_District().hashCode();
         }

         _hashCode += this.get_DistrictID();
         if (this.get_DocumentType() != null) {
            _hashCode += this.get_DocumentType().hashCode();
         }

         if (this.get_ERF() != null) {
            _hashCode += this.get_ERF().hashCode();
         }

         if (this.get_EndorsementCategory() != null) {
            _hashCode += this.get_EndorsementCategory().hashCode();
         }

         if (this.get_EndorsementNo() != null) {
            _hashCode += this.get_EndorsementNo().hashCode();
         }

         _hashCode += this.get_EndorsementReasonID();
         if (this.get_EndorsementSubTypeName() != null) {
            _hashCode += this.get_EndorsementSubTypeName().hashCode();
         }

         if (this.get_EndorsementType() != null) {
            _hashCode += this.get_EndorsementType().hashCode();
         }

         if (this.get_EndorsementTypeName() != null) {
            _hashCode += this.get_EndorsementTypeName().hashCode();
         }

         if (this.get_EngineNo() != null) {
            _hashCode += this.get_EngineNo().hashCode();
         }

         if (this.get_ExternalBarcode() != null) {
            _hashCode += this.get_ExternalBarcode().hashCode();
         }

         if (this.get_FirstAllocationDate() != null) {
            _hashCode += this.get_FirstAllocationDate().hashCode();
         }

         if (this.get_FirstName() != null) {
            _hashCode += this.get_FirstName().hashCode();
         }

         if (this.get_GST() != null) {
            _hashCode += this.get_GST().hashCode();
         }

         if (this.get_HandOverTo() != null) {
            _hashCode += this.get_HandOverTo().hashCode();
         }

         if (this.get_HandoverDate() != null) {
            _hashCode += this.get_HandoverDate().hashCode();
         }

         if (this.get_HigherEduCess() != null) {
            _hashCode += this.get_HigherEduCess().hashCode();
         }

         if (this.get_InspectionDateTime() != null) {
            _hashCode += this.get_InspectionDateTime().hashCode();
         }

         _hashCode += this.get_InspectionDocNo();
         if (this.get_InspectionDoneBy() != null) {
            _hashCode += this.get_InspectionDoneBy().hashCode();
         }

         if (this.get_InspectionDoneByName() != null) {
            _hashCode += this.get_InspectionDoneByName().hashCode();
         }

         if (this.get_InsuredName() != null) {
            _hashCode += this.get_InsuredName().hashCode();
         }

         if (this.get_InterRefCode() != null) {
            _hashCode += this.get_InterRefCode().hashCode();
         }

         if (this.get_InterRefName() != null) {
            _hashCode += this.get_InterRefName().hashCode();
         }

         if (this.get_IntermediaryRefCode() != null) {
            _hashCode += this.get_IntermediaryRefCode().hashCode();
         }

         if (this.get_IntermediaryRefName() != null) {
            _hashCode += this.get_IntermediaryRefName().hashCode();
         }

         if (this.get_InternalBarcode() != null) {
            _hashCode += this.get_InternalBarcode().hashCode();
         }

         if (this.get_InwardedBy() != null) {
            _hashCode += this.get_InwardedBy().hashCode();
         }

         if (this.get_InwardedDateTime() != null) {
            _hashCode += this.get_InwardedDateTime().hashCode();
         }

         _hashCode += this.get_IsChequeCleared();
         _hashCode += (this.is_IsDeleted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += this.get_IsDiscrepancyLog();
         _hashCode += (this.is_IsEditable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsEverJumping() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsInwarded() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsMappedStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsNCB() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += this.get_IsPDC();
         _hashCode += (this.is_IsPassedForPolicyGeneration() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsPaymentDone() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += this.get_IsPolicyGeneratedInICM();
         if (this.get_IsPostDateCheque() != null) {
            _hashCode += this.get_IsPostDateCheque().hashCode();
         }

         _hashCode += (this.is_IsQCOk() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_IsReissue() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         _hashCode += (this.is_Isjumping() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_IssueDate() != null) {
            _hashCode += this.get_IssueDate().hashCode();
         }

         if (this.get_JobAllocatedBy() != null) {
            _hashCode += this.get_JobAllocatedBy().hashCode();
         }

         if (this.get_JobAllocatedTo() != null) {
            _hashCode += this.get_JobAllocatedTo().hashCode();
         }

         if (this.get_JobAllocationDate() != null) {
            _hashCode += this.get_JobAllocationDate().hashCode();
         }

         if (this.get_JumpingStatusChangedDate() != null) {
            _hashCode += this.get_JumpingStatusChangedDate().hashCode();
         }

         if (this.get_LastName() != null) {
            _hashCode += this.get_LastName().hashCode();
         }

         if (this.get_LeadNo() != null) {
            _hashCode += this.get_LeadNo().hashCode();
         }

         _hashCode += this.get_MakeId_FK();
         if (this.get_MakeName() != null) {
            _hashCode += this.get_MakeName().hashCode();
         }

         if (this.get_MarinePremium() != null) {
            _hashCode += this.get_MarinePremium().hashCode();
         }

         if (this.get_MaxDiscountallowed() != null) {
            _hashCode += this.get_MaxDiscountallowed().hashCode();
         }

         if (this.get_MaxInstrDate() != null) {
            _hashCode += this.get_MaxInstrDate().hashCode();
         }

         if (this.get_Message() != null) {
            _hashCode += this.get_Message().hashCode();
         }

         if (this.get_MiddleName() != null) {
            _hashCode += this.get_MiddleName().hashCode();
         }

         _hashCode += this.get_ModelId_FK();
         if (this.get_ModelVariant() != null) {
            _hashCode += this.get_ModelVariant().hashCode();
         }

         if (this.get_NetOD() != null) {
            _hashCode += this.get_NetOD().hashCode();
         }

         _hashCode += (new Float(this.get_NetPremium())).hashCode();
         if (this.get_Nettp() != null) {
            _hashCode += this.get_Nettp().hashCode();
         }

         if (this.get_NewAgentCode() != null) {
            _hashCode += this.get_NewAgentCode().hashCode();
         }

         if (this.get_NewBASCode() != null) {
            _hashCode += this.get_NewBASCode().hashCode();
         }

         if (this.get_NewIntermediaryRefCode() != null) {
            _hashCode += this.get_NewIntermediaryRefCode().hashCode();
         }

         if (this.get_NewSMCode() != null) {
            _hashCode += this.get_NewSMCode().hashCode();
         }

         if (this.get_OldAgentCode() != null) {
            _hashCode += this.get_OldAgentCode().hashCode();
         }

         if (this.get_OldBASCode() != null) {
            _hashCode += this.get_OldBASCode().hashCode();
         }

         if (this.get_OldIntermediaryRefCode() != null) {
            _hashCode += this.get_OldIntermediaryRefCode().hashCode();
         }

         if (this.get_PanNo() != null) {
            _hashCode += this.get_PanNo().hashCode();
         }

         if (this.get_PassportNo() != null) {
            _hashCode += this.get_PassportNo().hashCode();
         }

         if (this.get_PaymentMode() != null) {
            _hashCode += this.get_PaymentMode().hashCode();
         }

         if (this.get_PendingPolicyBranch() != null) {
            _hashCode += this.get_PendingPolicyBranch().hashCode();
         }

         if (this.get_PinCode() != null) {
            _hashCode += this.get_PinCode().hashCode();
         }

         if (this.get_PlanName() != null) {
            _hashCode += this.get_PlanName().hashCode();
         }

         if (this.get_Planid_FK() != null) {
            _hashCode += this.get_Planid_FK().hashCode();
         }

         if (this.get_PolicyEndDate() != null) {
            _hashCode += this.get_PolicyEndDate().hashCode();
         }

         if (this.get_PolicyGenDate() != null) {
            _hashCode += this.get_PolicyGenDate().hashCode();
         }

         if (this.get_PolicyNo_FK() != null) {
            _hashCode += this.get_PolicyNo_FK().hashCode();
         }

         _hashCode += (this.is_PolicyStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_Prefix() != null) {
            _hashCode += this.get_Prefix().hashCode();
         }

         if (this.get_Premium() != null) {
            _hashCode += this.get_Premium().hashCode();
         }

         if (this.get_PreviousInsurerID() != null) {
            _hashCode += this.get_PreviousInsurerID().hashCode();
         }

         if (this.get_PreviousPolicyEndDate() != null) {
            _hashCode += this.get_PreviousPolicyEndDate().hashCode();
         }

         if (this.get_PreviousPolicyNo() != null) {
            _hashCode += this.get_PreviousPolicyNo().hashCode();
         }

         _hashCode += this.get_PreviousPolicyProcessedIn();
         if (this.get_PreviousPolicyProductCode() != null) {
            _hashCode += this.get_PreviousPolicyProductCode().hashCode();
         }

         _hashCode += this.get_ProcessedAt();
         if (this.get_ProcessedAtName() != null) {
            _hashCode += this.get_ProcessedAtName().hashCode();
         }

         _hashCode += this.get_ProcessedIn();
         if (this.get_ProcessedInName() != null) {
            _hashCode += this.get_ProcessedInName().hashCode();
         }

         if (this.get_Product() != null) {
            _hashCode += this.get_Product().hashCode();
         }

         if (this.get_ProductCode() != null) {
            _hashCode += this.get_ProductCode().hashCode();
         }

         if (this.get_ProductName() != null) {
            _hashCode += this.get_ProductName().hashCode();
         }

         if (this.get_ProductType() != null) {
            _hashCode += this.get_ProductType().hashCode();
         }

         if (this.get_ProposalAmount() != null) {
            _hashCode += this.get_ProposalAmount().hashCode();
         }

         if (this.get_ProposalClass() != null) {
            _hashCode += this.get_ProposalClass().hashCode();
         }

         if (this.get_ProposalID_FK() != null) {
            _hashCode += this.get_ProposalID_FK().hashCode();
         }

         if (this.get_ProposalOrEndorDate() != null) {
            _hashCode += this.get_ProposalOrEndorDate().hashCode();
         }

         _hashCode += this.get_ProposalStatus();
         _hashCode += this.get_ProposalStatusID();
         if (this.get_QCDone_By() != null) {
            _hashCode += this.get_QCDone_By().hashCode();
         }

         if (this.get_QCDone_Date() != null) {
            _hashCode += this.get_QCDone_Date().hashCode();
         }

         if (this.get_QCRemark() != null) {
            _hashCode += this.get_QCRemark().hashCode();
         }

         if (this.get_QuoteNo() != null) {
            _hashCode += this.get_QuoteNo().hashCode();
         }

         if (this.get_ReasonForChange() != null) {
            _hashCode += this.get_ReasonForChange().hashCode();
         }

         if (this.get_ReceivedDate() != null) {
            _hashCode += this.get_ReceivedDate().hashCode();
         }

         if (this.get_RegionId() != null) {
            _hashCode += this.get_RegionId().hashCode();
         }

         if (this.get_RegionName() != null) {
            _hashCode += this.get_RegionName().hashCode();
         }

         if (this.get_RegistrationNo() != null) {
            _hashCode += this.get_RegistrationNo().hashCode();
         }

         if (this.get_Remark() != null) {
            _hashCode += this.get_Remark().hashCode();
         }

         if (this.get_RequiredDocument() != null) {
            for(i = 0; i < Array.getLength(this.get_RequiredDocument()); ++i) {
               obj = Array.get(this.get_RequiredDocument(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         if (this.get_RiskStartDate() != null) {
            _hashCode += this.get_RiskStartDate().hashCode();
         }

         if (this.get_RuleNumber() != null) {
            _hashCode += this.get_RuleNumber().hashCode();
         }

         if (this.get_SMCode() != null) {
            _hashCode += this.get_SMCode().hashCode();
         }

         if (this.get_SMName() != null) {
            _hashCode += this.get_SMName().hashCode();
         }

         _hashCode += (this.is_SearchMode() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_ServiceLeviedTax() != null) {
            _hashCode += this.get_ServiceLeviedTax().hashCode();
         }

         if (this.get_ServiceStatus() != null) {
            _hashCode += this.get_ServiceStatus().hashCode();
         }

         if (this.get_ServiceTax() != null) {
            _hashCode += this.get_ServiceTax().hashCode();
         }

         if (this.get_State() != null) {
            _hashCode += this.get_State().hashCode();
         }

         _hashCode += this.get_StateID();
         if (this.get_StatusCode() != null) {
            _hashCode += this.get_StatusCode().hashCode();
         }

         if (this.get_SurchargeValue() != null) {
            _hashCode += this.get_SurchargeValue().hashCode();
         }

         if (this.get_TableName() != null) {
            _hashCode += this.get_TableName().hashCode();
         }

         if (this.get_TaxExempRemark() != null) {
            _hashCode += this.get_TaxExempRemark().hashCode();
         }

         if (this.get_Terrorism() != null) {
            _hashCode += this.get_Terrorism().hashCode();
         }

         if (this.get_TotalPremium() != null) {
            _hashCode += this.get_TotalPremium().hashCode();
         }

         if (this.get_TransID_FK() != null) {
            _hashCode += this.get_TransID_FK().hashCode();
         }

         _hashCode += this.get_UnderWriterApproved();
         if (this.get_Variantid_FK() != null) {
            _hashCode += this.get_Variantid_FK().hashCode();
         }

         if (this.get_VehicalName() != null) {
            _hashCode += this.get_VehicalName().hashCode();
         }

         _hashCode += this.get_VehicalSubTypeId_FK();
         _hashCode += this.get_VehicalTypeId_FK();
         if (this.get_VehicalTypeName() != null) {
            _hashCode += this.get_VehicalTypeName().hashCode();
         }

         _hashCode += (this.is_VendorExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
         if (this.get_War() != null) {
            _hashCode += this.get_War().hashCode();
         }

         _hashCode += this.get_WhereBy();
         if (this.get_WhereValue() != null) {
            _hashCode += this.get_WhereValue().hashCode();
         }

         if (this.get_YearOfManufacture() != null) {
            _hashCode += this.get_YearOfManufacture().hashCode();
         }

         if (this.get_YearOfRegistration() != null) {
            _hashCode += this.get_YearOfRegistration().hashCode();
         }

         if (this.get_branchName() != null) {
            _hashCode += this.get_branchName().hashCode();
         }

         if (this.get_chequeNo() != null) {
            _hashCode += this.get_chequeNo().hashCode();
         }

         if (this.get_className() != null) {
            _hashCode += this.get_className().hashCode();
         }

         if (this.get_discrCategory() != null) {
            for(i = 0; i < Array.getLength(this.get_discrCategory()); ++i) {
               obj = Array.get(this.get_discrCategory(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         if (this.get_srcc() != null) {
            _hashCode += this.get_srcc().hashCode();
         }

         if (this.get_stampduty() != null) {
            _hashCode += this.get_stampduty().hashCode();
         }

         if (this.get_userName() != null) {
            _hashCode += this.get_userName().hashCode();
         }

         _hashCode += this.getIntRowID();
         _hashCode += this.getIntSubDiscrepancyID();
         if (this.getQcResolvedDate() != null) {
            _hashCode += this.getQcResolvedDate().hashCode();
         }

         if (this.getReceiveMode() != null) {
            _hashCode += this.getReceiveMode().hashCode();
         }

         if (this.getSapCode() != null) {
            _hashCode += this.getSapCode().hashCode();
         }

         if (this.getShortPremium() != null) {
            _hashCode += this.getShortPremium().hashCode();
         }

         if (this.getStrDiscrepancyName() != null) {
            _hashCode += this.getStrDiscrepancyName().hashCode();
         }

         if (this.getStrRaisedDate() != null) {
            _hashCode += this.getStrRaisedDate().hashCode();
         }

         if (this.getStrResolvedRemark() != null) {
            _hashCode += this.getStrResolvedRemark().hashCode();
         }

         if (this.getStrSubDiscrepancyName() != null) {
            _hashCode += this.getStrSubDiscrepancyName().hashCode();
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

   static {
      typeDesc.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"));
      ElementDesc elemField = new ElementDesc();
      elemField.setFieldName("_Actionable");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Actionable"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Address");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Address"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_AgentCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AgentCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_AgentName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AgentName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_AgentRetentionDetailsDummy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AgentRetentionDetailsDummy"));
      elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"));
      elemField.setNillable(true);
      elemField.setItemQName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"));
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_AllocatedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AllocatedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_AllocatedTo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AllocatedTo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_AllocationDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AllocationDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BASExists");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BASExists"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BancaCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BancaCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BarcodeExists");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BarcodeExists"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BasCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BasCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BasName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BasName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BranchCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BranchCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BranchID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BranchID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_BusinessType");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BusinessType"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CSOCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CSOCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CSOName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CSOName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CancelledPolicy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CancelledPolicy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Cess");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Cess"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ChessisNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ChessisNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CityID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CityID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CityName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CityName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ClassID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ClassID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Co_InsStatus");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Co_InsStatus"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ContactNumber");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ContactNumber"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_CoverNoteExits");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CoverNoteExits"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DiscrepancyID_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_DiscrepancyID_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_District");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_District"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DistrictID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_DistrictID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_DocumentType");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_DocumentType"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ERF");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ERF"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EndorsementCategory");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementCategory"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EndorsementNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EndorsementReasonID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementReasonID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EndorsementSubTypeName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementSubTypeName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EndorsementType");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementType"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EndorsementTypeName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementTypeName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_EngineNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EngineNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ExternalBarcode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ExternalBarcode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_FirstAllocationDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_FirstAllocationDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_FirstName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_FirstName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_GST");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_GST"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_HandOverTo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_HandOverTo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_HandoverDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_HandoverDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_HigherEduCess");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_HigherEduCess"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InspectionDateTime");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDateTime"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InspectionDocNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDocNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InspectionDoneBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDoneBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InspectionDoneByName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDoneByName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InsuredName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InsuredName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InterRefCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InterRefCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InterRefName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InterRefName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IntermediaryRefCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IntermediaryRefCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IntermediaryRefName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IntermediaryRefName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InternalBarcode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InternalBarcode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InwardedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InwardedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_InwardedDateTime");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InwardedDateTime"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsChequeCleared");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsChequeCleared"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsDeleted");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsDeleted"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsDiscrepancyLog");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsDiscrepancyLog"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsEditable");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsEditable"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsEverJumping");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsEverJumping"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsInwarded");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsInwarded"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsMappedStatus");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsMappedStatus"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsNCB");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsNCB"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsPDC");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPDC"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsPassedForPolicyGeneration");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPassedForPolicyGeneration"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsPaymentDone");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPaymentDone"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsPolicyGeneratedInICM");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPolicyGeneratedInICM"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsPostDateCheque");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPostDateCheque"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsQCOk");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsQCOk"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IsReissue");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsReissue"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Isjumping");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Isjumping"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_IssueDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IssueDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_JobAllocatedBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JobAllocatedBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_JobAllocatedTo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JobAllocatedTo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_JobAllocationDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JobAllocationDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_JumpingStatusChangedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JumpingStatusChangedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_LastName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_LastName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_LeadNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_LeadNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_MakeId_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MakeId_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_MakeName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MakeName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_MarinePremium");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MarinePremium"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_MaxDiscountallowed");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MaxDiscountallowed"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_MaxInstrDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MaxInstrDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Message");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Message"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_MiddleName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MiddleName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ModelId_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ModelId_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ModelVariant");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ModelVariant"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_NetOD");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NetOD"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_NetPremium");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NetPremium"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "float"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Nettp");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Nettp"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_NewAgentCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewAgentCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_NewBASCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewBASCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_NewIntermediaryRefCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewIntermediaryRefCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_NewSMCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewSMCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_OldAgentCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_OldAgentCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_OldBASCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_OldBASCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_OldIntermediaryRefCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_OldIntermediaryRefCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PanNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PanNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PassportNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PassportNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PaymentMode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PaymentMode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PendingPolicyBranch");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PendingPolicyBranch"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PinCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PinCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PlanName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PlanName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Planid_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Planid_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PolicyEndDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyEndDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PolicyGenDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyGenDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PolicyNo_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyNo_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PolicyStatus");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyStatus"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Prefix");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Prefix"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Premium");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Premium"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PreviousInsurerID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousInsurerID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PreviousPolicyEndDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyEndDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PreviousPolicyNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PreviousPolicyProcessedIn");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyProcessedIn"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_PreviousPolicyProductCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyProductCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProcessedAt");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedAt"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProcessedAtName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedAtName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProcessedIn");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedIn"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProcessedInName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedInName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Product");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Product"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProductCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProductCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProductName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProductName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProductType");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProductType"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalAmount");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalAmount"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalClass");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalClass"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalID_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalID_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalOrEndorDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalOrEndorDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalStatus");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalStatus"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ProposalStatusID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalStatusID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_QCDone_By");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QCDone_By"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_QCDone_Date");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QCDone_Date"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_QCRemark");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QCRemark"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_QuoteNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QuoteNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ReasonForChange");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ReasonForChange"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ReceivedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ReceivedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_RegionId");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RegionId"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_RegionName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RegionName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_RegistrationNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RegistrationNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Remark");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Remark"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_RequiredDocument");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RequiredDocument"));
      elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO"));
      elemField.setNillable(true);
      elemField.setItemQName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO"));
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_RiskStartDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RiskStartDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_RuleNumber");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RuleNumber"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_SMCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SMCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_SMName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SMName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_SearchMode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SearchMode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ServiceLeviedTax");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ServiceLeviedTax"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ServiceStatus");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ServiceStatus"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_ServiceTax");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ServiceTax"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_State");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_State"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_StateID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_StateID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_StatusCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_StatusCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_SurchargeValue");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SurchargeValue"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_TableName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TableName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_TaxExempRemark");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TaxExempRemark"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Terrorism");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Terrorism"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_TotalPremium");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TotalPremium"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_TransID_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TransID_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_UnderWriterApproved");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_UnderWriterApproved"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_Variantid_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Variantid_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_VehicalName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_VehicalSubTypeId_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalSubTypeId_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_VehicalTypeId_FK");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalTypeId_FK"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_VehicalTypeName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalTypeName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_VendorExists");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VendorExists"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_War");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_War"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_WhereBy");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_WhereBy"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_WhereValue");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_WhereValue"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_YearOfManufacture");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_YearOfManufacture"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_YearOfRegistration");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_YearOfRegistration"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_branchName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_branchName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_chequeNo");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_chequeNo"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_className");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_className"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_discrCategory");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_discrCategory"));
      elemField.setXmlType(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO"));
      elemField.setNillable(true);
      elemField.setItemQName(new QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO"));
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_srcc");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_srcc"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_stampduty");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_stampduty"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("_userName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_userName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("intRowID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "intRowID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("intSubDiscrepancyID");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "intSubDiscrepancyID"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("qcResolvedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "qcResolvedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("receiveMode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "receiveMode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("sapCode");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "sapCode"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("shortPremium");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "shortPremium"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "decimal"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("strDiscrepancyName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strDiscrepancyName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("strRaisedDate");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strRaisedDate"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
      elemField.setNillable(false);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("strResolvedRemark");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strResolvedRemark"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
      elemField = new ElementDesc();
      elemField.setFieldName("strSubDiscrepancyName");
      elemField.setXmlName(new QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strSubDiscrepancyName"));
      elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
      elemField.setNillable(true);
      typeDesc.addFieldDesc(elemField);
   }
}
