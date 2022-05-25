/**
 * ProposalDetailsBO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package app.icm;

public class ProposalDetailsBO  extends app.icm.UtilBO  implements java.io.Serializable {
    private java.lang.String _Actionable;

    private java.lang.String _Address;

    private java.lang.String _AgentCode;

    private java.lang.String _AgentName;

    private app.icm.ProposalDetailsBO[] _AgentRetentionDetailsDummy;

    private java.lang.String _AllocatedBy;

    private java.lang.String _AllocatedTo;

    private java.util.Calendar _AllocationDate;

    private boolean _BASExists;

    private java.lang.String _BGSAPBankNo;

    private java.lang.String _BGSAPCode;

    private java.lang.String _BancaCode;

    private boolean _BarcodeExists;

    private java.lang.String _BasCode;

    private java.lang.String _BasName;

    private java.lang.String _BranchCode;

    private java.lang.Integer _BranchID;

    private java.lang.String _BusinessType;

    private java.lang.String _CSOCode;

    private java.lang.String _CSOName;

    private java.lang.String _CancelledPolicy;

    private java.math.BigDecimal _Cess;

    private java.lang.String _ChessisNo;

    private int _CityID;

    private java.lang.String _CityName;

    private java.lang.Integer _ClassID;

    private java.math.BigDecimal _CoInsuranceShare;

    private java.lang.String _Co_InsStatus;

    private java.lang.String _ContactNumber;

    private boolean _CoverNoteExits;

    private java.lang.Integer _DiscrepancyID_FK;

    private java.lang.String _District;

    private int _DistrictID;

    private java.lang.String _DocumentType;

    private java.math.BigDecimal _ERF;

    private java.lang.String _EndorsementCategory;

    private java.lang.String _EndorsementNo;

    private int _EndorsementReasonID;

    private java.lang.String _EndorsementSubTypeName;

    private java.lang.String _EndorsementType;

    private java.lang.String _EndorsementTypeName;

    private java.lang.String _EngineNo;

    private java.lang.String _ExternalBarcode;

    private java.util.Calendar _FirstAllocationDate;

    private java.lang.String _FirstName;

    private java.math.BigDecimal _GST;

    private java.lang.String _HandOverTo;

    private java.util.Calendar _HandoverDate;

    private java.math.BigDecimal _HigherEduCess;

    private java.lang.String _InitiatedBy;

    private java.util.Calendar _InspectionDateTime;

    private int _InspectionDocNo;

    private boolean _InspectionDocNoExists;

    private java.lang.String _InspectionDoneBy;

    private java.lang.String _InspectionDoneByName;

    private java.lang.String _InsuredName;

    private java.lang.String _InterRefCode;

    private java.lang.String _InterRefName;

    private java.lang.String _IntermediaryRefCode;

    private java.lang.String _IntermediaryRefName;

    private java.lang.String _InternalBarcode;

    private java.lang.String _InwardedBy;

    private java.util.Calendar _InwardedDateTime;

    private int _IsChequeCleared;

    private boolean _IsCoverNoteActive;

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

    private java.lang.String _IsPostDateCheque;

    private boolean _IsQCDoneBSM;

    private boolean _IsQCOk;

    private boolean _IsReissue;

    private boolean _Isjumping;

    private java.util.Calendar _IssueDate;

    private java.lang.String _JobAllocatedBy;

    private java.lang.String _JobAllocatedTo;

    private java.util.Calendar _JobAllocationDate;

    private java.util.Calendar _JumpingStatusChangedDate;

    private int _KeyField1;

    private int _KeyField2;

    private int _KeyField3;

    private java.lang.String _LastName;

    private java.lang.String _LeadNo;

    private boolean _LeadNoExists;

    private int _MakeId_FK;

    private java.lang.String _MakeName;

    private java.math.BigDecimal _MarinePremium;

    private java.math.BigDecimal _MaxDiscountallowed;

    private java.util.Calendar _MaxInstrDate;

    private java.lang.String _Message;

    private java.lang.String _MiddleName;

    private int _ModelId_FK;

    private java.lang.String _ModelVariant;

    private java.math.BigDecimal _NetOD;

    private float _NetPremium;

    private java.math.BigDecimal _Nettp;

    private java.lang.String _NewAgentCode;

    private java.lang.String _NewBASCode;

    private java.lang.String _NewIntermediaryRefCode;

    private java.lang.String _NewSMCode;

    private java.lang.String _OldAgentCode;

    private java.lang.String _OldBASCode;

    private java.lang.String _OldIntermediaryRefCode;

    private java.lang.String _PanNo;

    private java.lang.String _PassportNo;

    private java.lang.String _PaymentMode;

    private java.lang.String _PendingPolicyBranch;

    private java.lang.String _PinCode;

    private java.lang.String _PlanName;

    private java.lang.Integer _Planid_FK;

    private java.util.Calendar _PolicyEndDate;

    private java.util.Calendar _PolicyGenDate;

    private java.lang.String _PolicyNo_FK;

    private boolean _PolicyStatus;

    private java.lang.String _Prefix;

    private java.math.BigDecimal _Premium;

    private java.lang.String _PrevEndorsementStatus;

    private java.lang.Integer _PreviousInsurerID;

    private java.util.Calendar _PreviousPolicyEndDate;

    private java.lang.String _PreviousPolicyNo;

    private int _PreviousPolicyProcessedIn;

    private java.lang.String _PreviousPolicyProductCode;

    private int _ProcessedAt;

    private java.lang.String _ProcessedAtName;

    private int _ProcessedIn;

    private java.lang.String _ProcessedInName;

    private java.lang.String _Product;

    private java.lang.String _ProductCode;

    private java.lang.String _ProductName;

    private java.lang.String _ProductType;

    private java.math.BigDecimal _ProposalAmount;

    private java.lang.String _ProposalClass;

    private java.lang.String _ProposalID_FK;

    private java.util.Calendar _ProposalOrEndorDate;

    private int _ProposalStatus;

    private int _ProposalStatusID;

    private java.lang.String _QCDone_By;

    private java.util.Calendar _QCDone_Date;

    private java.lang.String _QCRemark;

    private java.lang.String _QcStatus;

    private java.lang.String _QuoteNo;

    private boolean _QuoteNoExists;

    private java.lang.String _ReasonForChange;

    private java.util.Calendar _ReceivedDate;

    private java.lang.String _RegionId;

    private java.lang.String _RegionName;

    private java.lang.String _RegistrationNo;

    private java.lang.String _Remark;

    private app.icm.DocumentMasterBO[] _RequiredDocument;

    private java.util.Calendar _RiskStartDate;

    private java.lang.String _RuleNumber;

    private java.lang.String _SMCode;

    private java.lang.String _SMName;

    private boolean _SearchMode;

    private java.math.BigDecimal _ServiceLeviedTax;

    private java.lang.String _ServiceStatus;

    private java.math.BigDecimal _ServiceTax;

    private java.lang.String _State;

    private int _StateID;

    private java.lang.String _StatusCode;

    private java.math.BigDecimal _SurchargeValue;

    private java.lang.String _TableName;

    private java.lang.String _TaxExempRemark;

    private java.math.BigDecimal _Terrorism;

    private java.math.BigDecimal _TotalCashAmt;

    private java.math.BigDecimal _TotalPremium;

    private java.lang.String _TransID_FK;

    private int _UnderWriterApproved;

    private java.lang.String _UserRole;

    private java.lang.String _Value1;

    private java.lang.String _Value2;

    private java.lang.String _Value3;

    private java.lang.Integer _Variantid_FK;

    private java.lang.String _VehicalName;

    private int _VehicalSubTypeId_FK;

    private int _VehicalTypeId_FK;

    private java.lang.String _VehicalTypeName;

    private boolean _VendorExists;

    private java.math.BigDecimal _War;

    private int _WhereBy;

    private java.lang.String _WhereValue;

    private java.lang.Integer _YearOfManufacture;

    private java.lang.Integer _YearOfRegistration;

    private java.lang.String _branchName;

    private java.lang.String _chequeNo;

    private java.lang.String _className;

    private app.icm.DiscrepancyCategoryBO[] _discrCategory;

    private java.math.BigDecimal _srcc;

    private java.math.BigDecimal _stampduty;

    private java.lang.String _userName;

    private int intRowID;

    private int intSubDiscrepancyID;

    private java.util.Calendar qcResolvedDate;

    private java.lang.String receiveMode;

    private java.lang.String sapCode;

    private java.math.BigDecimal shortPremium;

    private java.lang.String strDiscrepancyName;

    private java.util.Calendar strRaisedDate;

    private java.lang.String strResolvedRemark;

    private java.lang.String strSubDiscrepancyName;

    public ProposalDetailsBO() {
    }

    public ProposalDetailsBO(
           java.lang.String coStatus,
           java.lang.String createdBy,
           java.util.Calendar createdDate,
           java.lang.String deletedBy,
           java.util.Calendar deletedDate,
           java.util.Calendar maxInstrumentDate,
           java.lang.String modifyedBy,
           java.util.Calendar modifyedDate,
           java.math.BigDecimal totalCollection,
           java.util.Calendar transDate,
           java.lang.String transactionID,
           java.lang.String vendorType,
           java.lang.String _Actionable,
           java.lang.String _Address,
           java.lang.String _AgentCode,
           java.lang.String _AgentName,
           app.icm.ProposalDetailsBO[] _AgentRetentionDetailsDummy,
           java.lang.String _AllocatedBy,
           java.lang.String _AllocatedTo,
           java.util.Calendar _AllocationDate,
           boolean _BASExists,
           java.lang.String _BGSAPBankNo,
           java.lang.String _BGSAPCode,
           java.lang.String _BancaCode,
           boolean _BarcodeExists,
           java.lang.String _BasCode,
           java.lang.String _BasName,
           java.lang.String _BranchCode,
           java.lang.Integer _BranchID,
           java.lang.String _BusinessType,
           java.lang.String _CSOCode,
           java.lang.String _CSOName,
           java.lang.String _CancelledPolicy,
           java.math.BigDecimal _Cess,
           java.lang.String _ChessisNo,
           int _CityID,
           java.lang.String _CityName,
           java.lang.Integer _ClassID,
           java.math.BigDecimal _CoInsuranceShare,
           java.lang.String _Co_InsStatus,
           java.lang.String _ContactNumber,
           boolean _CoverNoteExits,
           java.lang.Integer _DiscrepancyID_FK,
           java.lang.String _District,
           int _DistrictID,
           java.lang.String _DocumentType,
           java.math.BigDecimal _ERF,
           java.lang.String _EndorsementCategory,
           java.lang.String _EndorsementNo,
           int _EndorsementReasonID,
           java.lang.String _EndorsementSubTypeName,
           java.lang.String _EndorsementType,
           java.lang.String _EndorsementTypeName,
           java.lang.String _EngineNo,
           java.lang.String _ExternalBarcode,
           java.util.Calendar _FirstAllocationDate,
           java.lang.String _FirstName,
           java.math.BigDecimal _GST,
           java.lang.String _HandOverTo,
           java.util.Calendar _HandoverDate,
           java.math.BigDecimal _HigherEduCess,
           java.lang.String _InitiatedBy,
           java.util.Calendar _InspectionDateTime,
           int _InspectionDocNo,
           boolean _InspectionDocNoExists,
           java.lang.String _InspectionDoneBy,
           java.lang.String _InspectionDoneByName,
           java.lang.String _InsuredName,
           java.lang.String _InterRefCode,
           java.lang.String _InterRefName,
           java.lang.String _IntermediaryRefCode,
           java.lang.String _IntermediaryRefName,
           java.lang.String _InternalBarcode,
           java.lang.String _InwardedBy,
           java.util.Calendar _InwardedDateTime,
           int _IsChequeCleared,
           boolean _IsCoverNoteActive,
           boolean _IsDeleted,
           int _IsDiscrepancyLog,
           boolean _IsEditable,
           boolean _IsEverJumping,
           boolean _IsInwarded,
           boolean _IsMappedStatus,
           boolean _IsNCB,
           int _IsPDC,
           boolean _IsPassedForPolicyGeneration,
           boolean _IsPaymentDone,
           int _IsPolicyGeneratedInICM,
           java.lang.String _IsPostDateCheque,
           boolean _IsQCDoneBSM,
           boolean _IsQCOk,
           boolean _IsReissue,
           boolean _Isjumping,
           java.util.Calendar _IssueDate,
           java.lang.String _JobAllocatedBy,
           java.lang.String _JobAllocatedTo,
           java.util.Calendar _JobAllocationDate,
           java.util.Calendar _JumpingStatusChangedDate,
           int _KeyField1,
           int _KeyField2,
           int _KeyField3,
           java.lang.String _LastName,
           java.lang.String _LeadNo,
           boolean _LeadNoExists,
           int _MakeId_FK,
           java.lang.String _MakeName,
           java.math.BigDecimal _MarinePremium,
           java.math.BigDecimal _MaxDiscountallowed,
           java.util.Calendar _MaxInstrDate,
           java.lang.String _Message,
           java.lang.String _MiddleName,
           int _ModelId_FK,
           java.lang.String _ModelVariant,
           java.math.BigDecimal _NetOD,
           float _NetPremium,
           java.math.BigDecimal _Nettp,
           java.lang.String _NewAgentCode,
           java.lang.String _NewBASCode,
           java.lang.String _NewIntermediaryRefCode,
           java.lang.String _NewSMCode,
           java.lang.String _OldAgentCode,
           java.lang.String _OldBASCode,
           java.lang.String _OldIntermediaryRefCode,
           java.lang.String _PanNo,
           java.lang.String _PassportNo,
           java.lang.String _PaymentMode,
           java.lang.String _PendingPolicyBranch,
           java.lang.String _PinCode,
           java.lang.String _PlanName,
           java.lang.Integer _Planid_FK,
           java.util.Calendar _PolicyEndDate,
           java.util.Calendar _PolicyGenDate,
           java.lang.String _PolicyNo_FK,
           boolean _PolicyStatus,
           java.lang.String _Prefix,
           java.math.BigDecimal _Premium,
           java.lang.String _PrevEndorsementStatus,
           java.lang.Integer _PreviousInsurerID,
           java.util.Calendar _PreviousPolicyEndDate,
           java.lang.String _PreviousPolicyNo,
           int _PreviousPolicyProcessedIn,
           java.lang.String _PreviousPolicyProductCode,
           int _ProcessedAt,
           java.lang.String _ProcessedAtName,
           int _ProcessedIn,
           java.lang.String _ProcessedInName,
           java.lang.String _Product,
           java.lang.String _ProductCode,
           java.lang.String _ProductName,
           java.lang.String _ProductType,
           java.math.BigDecimal _ProposalAmount,
           java.lang.String _ProposalClass,
           java.lang.String _ProposalID_FK,
           java.util.Calendar _ProposalOrEndorDate,
           int _ProposalStatus,
           int _ProposalStatusID,
           java.lang.String _QCDone_By,
           java.util.Calendar _QCDone_Date,
           java.lang.String _QCRemark,
           java.lang.String _QcStatus,
           java.lang.String _QuoteNo,
           boolean _QuoteNoExists,
           java.lang.String _ReasonForChange,
           java.util.Calendar _ReceivedDate,
           java.lang.String _RegionId,
           java.lang.String _RegionName,
           java.lang.String _RegistrationNo,
           java.lang.String _Remark,
           app.icm.DocumentMasterBO[] _RequiredDocument,
           java.util.Calendar _RiskStartDate,
           java.lang.String _RuleNumber,
           java.lang.String _SMCode,
           java.lang.String _SMName,
           boolean _SearchMode,
           java.math.BigDecimal _ServiceLeviedTax,
           java.lang.String _ServiceStatus,
           java.math.BigDecimal _ServiceTax,
           java.lang.String _State,
           int _StateID,
           java.lang.String _StatusCode,
           java.math.BigDecimal _SurchargeValue,
           java.lang.String _TableName,
           java.lang.String _TaxExempRemark,
           java.math.BigDecimal _Terrorism,
           java.math.BigDecimal _TotalCashAmt,
           java.math.BigDecimal _TotalPremium,
           java.lang.String _TransID_FK,
           int _UnderWriterApproved,
           java.lang.String _UserRole,
           java.lang.String _Value1,
           java.lang.String _Value2,
           java.lang.String _Value3,
           java.lang.Integer _Variantid_FK,
           java.lang.String _VehicalName,
           int _VehicalSubTypeId_FK,
           int _VehicalTypeId_FK,
           java.lang.String _VehicalTypeName,
           boolean _VendorExists,
           java.math.BigDecimal _War,
           int _WhereBy,
           java.lang.String _WhereValue,
           java.lang.Integer _YearOfManufacture,
           java.lang.Integer _YearOfRegistration,
           java.lang.String _branchName,
           java.lang.String _chequeNo,
           java.lang.String _className,
           app.icm.DiscrepancyCategoryBO[] _discrCategory,
           java.math.BigDecimal _srcc,
           java.math.BigDecimal _stampduty,
           java.lang.String _userName,
           int intRowID,
           int intSubDiscrepancyID,
           java.util.Calendar qcResolvedDate,
           java.lang.String receiveMode,
           java.lang.String sapCode,
           java.math.BigDecimal shortPremium,
           java.lang.String strDiscrepancyName,
           java.util.Calendar strRaisedDate,
           java.lang.String strResolvedRemark,
           java.lang.String strSubDiscrepancyName) {
        super(
            coStatus,
            createdBy,
            createdDate,
            deletedBy,
            deletedDate,
            maxInstrumentDate,
            modifyedBy,
            modifyedDate,
            totalCollection,
            transDate,
            transactionID,
            vendorType);
        this._Actionable = _Actionable;
        this._Address = _Address;
        this._AgentCode = _AgentCode;
        this._AgentName = _AgentName;
        this._AgentRetentionDetailsDummy = _AgentRetentionDetailsDummy;
        this._AllocatedBy = _AllocatedBy;
        this._AllocatedTo = _AllocatedTo;
        this._AllocationDate = _AllocationDate;
        this._BASExists = _BASExists;
        this._BGSAPBankNo = _BGSAPBankNo;
        this._BGSAPCode = _BGSAPCode;
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
        this._CoInsuranceShare = _CoInsuranceShare;
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
        this._InitiatedBy = _InitiatedBy;
        this._InspectionDateTime = _InspectionDateTime;
        this._InspectionDocNo = _InspectionDocNo;
        this._InspectionDocNoExists = _InspectionDocNoExists;
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
        this._IsCoverNoteActive = _IsCoverNoteActive;
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
        this._IsQCDoneBSM = _IsQCDoneBSM;
        this._IsQCOk = _IsQCOk;
        this._IsReissue = _IsReissue;
        this._Isjumping = _Isjumping;
        this._IssueDate = _IssueDate;
        this._JobAllocatedBy = _JobAllocatedBy;
        this._JobAllocatedTo = _JobAllocatedTo;
        this._JobAllocationDate = _JobAllocationDate;
        this._JumpingStatusChangedDate = _JumpingStatusChangedDate;
        this._KeyField1 = _KeyField1;
        this._KeyField2 = _KeyField2;
        this._KeyField3 = _KeyField3;
        this._LastName = _LastName;
        this._LeadNo = _LeadNo;
        this._LeadNoExists = _LeadNoExists;
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
        this._PrevEndorsementStatus = _PrevEndorsementStatus;
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
        this._QcStatus = _QcStatus;
        this._QuoteNo = _QuoteNo;
        this._QuoteNoExists = _QuoteNoExists;
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
        this._TotalCashAmt = _TotalCashAmt;
        this._TotalPremium = _TotalPremium;
        this._TransID_FK = _TransID_FK;
        this._UnderWriterApproved = _UnderWriterApproved;
        this._UserRole = _UserRole;
        this._Value1 = _Value1;
        this._Value2 = _Value2;
        this._Value3 = _Value3;
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


    /**
     * Gets the _Actionable value for this ProposalDetailsBO.
     * 
     * @return _Actionable
     */
    public java.lang.String get_Actionable() {
        return _Actionable;
    }


    /**
     * Sets the _Actionable value for this ProposalDetailsBO.
     * 
     * @param _Actionable
     */
    public void set_Actionable(java.lang.String _Actionable) {
        this._Actionable = _Actionable;
    }


    /**
     * Gets the _Address value for this ProposalDetailsBO.
     * 
     * @return _Address
     */
    public java.lang.String get_Address() {
        return _Address;
    }


    /**
     * Sets the _Address value for this ProposalDetailsBO.
     * 
     * @param _Address
     */
    public void set_Address(java.lang.String _Address) {
        this._Address = _Address;
    }


    /**
     * Gets the _AgentCode value for this ProposalDetailsBO.
     * 
     * @return _AgentCode
     */
    public java.lang.String get_AgentCode() {
        return _AgentCode;
    }


    /**
     * Sets the _AgentCode value for this ProposalDetailsBO.
     * 
     * @param _AgentCode
     */
    public void set_AgentCode(java.lang.String _AgentCode) {
        this._AgentCode = _AgentCode;
    }


    /**
     * Gets the _AgentName value for this ProposalDetailsBO.
     * 
     * @return _AgentName
     */
    public java.lang.String get_AgentName() {
        return _AgentName;
    }


    /**
     * Sets the _AgentName value for this ProposalDetailsBO.
     * 
     * @param _AgentName
     */
    public void set_AgentName(java.lang.String _AgentName) {
        this._AgentName = _AgentName;
    }


    /**
     * Gets the _AgentRetentionDetailsDummy value for this ProposalDetailsBO.
     * 
     * @return _AgentRetentionDetailsDummy
     */
    public app.icm.ProposalDetailsBO[] get_AgentRetentionDetailsDummy() {
        return _AgentRetentionDetailsDummy;
    }


    /**
     * Sets the _AgentRetentionDetailsDummy value for this ProposalDetailsBO.
     * 
     * @param _AgentRetentionDetailsDummy
     */
    public void set_AgentRetentionDetailsDummy(app.icm.ProposalDetailsBO[] _AgentRetentionDetailsDummy) {
        this._AgentRetentionDetailsDummy = _AgentRetentionDetailsDummy;
    }


    /**
     * Gets the _AllocatedBy value for this ProposalDetailsBO.
     * 
     * @return _AllocatedBy
     */
    public java.lang.String get_AllocatedBy() {
        return _AllocatedBy;
    }


    /**
     * Sets the _AllocatedBy value for this ProposalDetailsBO.
     * 
     * @param _AllocatedBy
     */
    public void set_AllocatedBy(java.lang.String _AllocatedBy) {
        this._AllocatedBy = _AllocatedBy;
    }


    /**
     * Gets the _AllocatedTo value for this ProposalDetailsBO.
     * 
     * @return _AllocatedTo
     */
    public java.lang.String get_AllocatedTo() {
        return _AllocatedTo;
    }


    /**
     * Sets the _AllocatedTo value for this ProposalDetailsBO.
     * 
     * @param _AllocatedTo
     */
    public void set_AllocatedTo(java.lang.String _AllocatedTo) {
        this._AllocatedTo = _AllocatedTo;
    }


    /**
     * Gets the _AllocationDate value for this ProposalDetailsBO.
     * 
     * @return _AllocationDate
     */
    public java.util.Calendar get_AllocationDate() {
        return _AllocationDate;
    }


    /**
     * Sets the _AllocationDate value for this ProposalDetailsBO.
     * 
     * @param _AllocationDate
     */
    public void set_AllocationDate(java.util.Calendar _AllocationDate) {
        this._AllocationDate = _AllocationDate;
    }


    /**
     * Gets the _BASExists value for this ProposalDetailsBO.
     * 
     * @return _BASExists
     */
    public boolean is_BASExists() {
        return _BASExists;
    }


    /**
     * Sets the _BASExists value for this ProposalDetailsBO.
     * 
     * @param _BASExists
     */
    public void set_BASExists(boolean _BASExists) {
        this._BASExists = _BASExists;
    }


    /**
     * Gets the _BGSAPBankNo value for this ProposalDetailsBO.
     * 
     * @return _BGSAPBankNo
     */
    public java.lang.String get_BGSAPBankNo() {
        return _BGSAPBankNo;
    }


    /**
     * Sets the _BGSAPBankNo value for this ProposalDetailsBO.
     * 
     * @param _BGSAPBankNo
     */
    public void set_BGSAPBankNo(java.lang.String _BGSAPBankNo) {
        this._BGSAPBankNo = _BGSAPBankNo;
    }


    /**
     * Gets the _BGSAPCode value for this ProposalDetailsBO.
     * 
     * @return _BGSAPCode
     */
    public java.lang.String get_BGSAPCode() {
        return _BGSAPCode;
    }


    /**
     * Sets the _BGSAPCode value for this ProposalDetailsBO.
     * 
     * @param _BGSAPCode
     */
    public void set_BGSAPCode(java.lang.String _BGSAPCode) {
        this._BGSAPCode = _BGSAPCode;
    }


    /**
     * Gets the _BancaCode value for this ProposalDetailsBO.
     * 
     * @return _BancaCode
     */
    public java.lang.String get_BancaCode() {
        return _BancaCode;
    }


    /**
     * Sets the _BancaCode value for this ProposalDetailsBO.
     * 
     * @param _BancaCode
     */
    public void set_BancaCode(java.lang.String _BancaCode) {
        this._BancaCode = _BancaCode;
    }


    /**
     * Gets the _BarcodeExists value for this ProposalDetailsBO.
     * 
     * @return _BarcodeExists
     */
    public boolean is_BarcodeExists() {
        return _BarcodeExists;
    }


    /**
     * Sets the _BarcodeExists value for this ProposalDetailsBO.
     * 
     * @param _BarcodeExists
     */
    public void set_BarcodeExists(boolean _BarcodeExists) {
        this._BarcodeExists = _BarcodeExists;
    }


    /**
     * Gets the _BasCode value for this ProposalDetailsBO.
     * 
     * @return _BasCode
     */
    public java.lang.String get_BasCode() {
        return _BasCode;
    }


    /**
     * Sets the _BasCode value for this ProposalDetailsBO.
     * 
     * @param _BasCode
     */
    public void set_BasCode(java.lang.String _BasCode) {
        this._BasCode = _BasCode;
    }


    /**
     * Gets the _BasName value for this ProposalDetailsBO.
     * 
     * @return _BasName
     */
    public java.lang.String get_BasName() {
        return _BasName;
    }


    /**
     * Sets the _BasName value for this ProposalDetailsBO.
     * 
     * @param _BasName
     */
    public void set_BasName(java.lang.String _BasName) {
        this._BasName = _BasName;
    }


    /**
     * Gets the _BranchCode value for this ProposalDetailsBO.
     * 
     * @return _BranchCode
     */
    public java.lang.String get_BranchCode() {
        return _BranchCode;
    }


    /**
     * Sets the _BranchCode value for this ProposalDetailsBO.
     * 
     * @param _BranchCode
     */
    public void set_BranchCode(java.lang.String _BranchCode) {
        this._BranchCode = _BranchCode;
    }


    /**
     * Gets the _BranchID value for this ProposalDetailsBO.
     * 
     * @return _BranchID
     */
    public java.lang.Integer get_BranchID() {
        return _BranchID;
    }


    /**
     * Sets the _BranchID value for this ProposalDetailsBO.
     * 
     * @param _BranchID
     */
    public void set_BranchID(java.lang.Integer _BranchID) {
        this._BranchID = _BranchID;
    }


    /**
     * Gets the _BusinessType value for this ProposalDetailsBO.
     * 
     * @return _BusinessType
     */
    public java.lang.String get_BusinessType() {
        return _BusinessType;
    }


    /**
     * Sets the _BusinessType value for this ProposalDetailsBO.
     * 
     * @param _BusinessType
     */
    public void set_BusinessType(java.lang.String _BusinessType) {
        this._BusinessType = _BusinessType;
    }


    /**
     * Gets the _CSOCode value for this ProposalDetailsBO.
     * 
     * @return _CSOCode
     */
    public java.lang.String get_CSOCode() {
        return _CSOCode;
    }


    /**
     * Sets the _CSOCode value for this ProposalDetailsBO.
     * 
     * @param _CSOCode
     */
    public void set_CSOCode(java.lang.String _CSOCode) {
        this._CSOCode = _CSOCode;
    }


    /**
     * Gets the _CSOName value for this ProposalDetailsBO.
     * 
     * @return _CSOName
     */
    public java.lang.String get_CSOName() {
        return _CSOName;
    }


    /**
     * Sets the _CSOName value for this ProposalDetailsBO.
     * 
     * @param _CSOName
     */
    public void set_CSOName(java.lang.String _CSOName) {
        this._CSOName = _CSOName;
    }


    /**
     * Gets the _CancelledPolicy value for this ProposalDetailsBO.
     * 
     * @return _CancelledPolicy
     */
    public java.lang.String get_CancelledPolicy() {
        return _CancelledPolicy;
    }


    /**
     * Sets the _CancelledPolicy value for this ProposalDetailsBO.
     * 
     * @param _CancelledPolicy
     */
    public void set_CancelledPolicy(java.lang.String _CancelledPolicy) {
        this._CancelledPolicy = _CancelledPolicy;
    }


    /**
     * Gets the _Cess value for this ProposalDetailsBO.
     * 
     * @return _Cess
     */
    public java.math.BigDecimal get_Cess() {
        return _Cess;
    }


    /**
     * Sets the _Cess value for this ProposalDetailsBO.
     * 
     * @param _Cess
     */
    public void set_Cess(java.math.BigDecimal _Cess) {
        this._Cess = _Cess;
    }


    /**
     * Gets the _ChessisNo value for this ProposalDetailsBO.
     * 
     * @return _ChessisNo
     */
    public java.lang.String get_ChessisNo() {
        return _ChessisNo;
    }


    /**
     * Sets the _ChessisNo value for this ProposalDetailsBO.
     * 
     * @param _ChessisNo
     */
    public void set_ChessisNo(java.lang.String _ChessisNo) {
        this._ChessisNo = _ChessisNo;
    }


    /**
     * Gets the _CityID value for this ProposalDetailsBO.
     * 
     * @return _CityID
     */
    public int get_CityID() {
        return _CityID;
    }


    /**
     * Sets the _CityID value for this ProposalDetailsBO.
     * 
     * @param _CityID
     */
    public void set_CityID(int _CityID) {
        this._CityID = _CityID;
    }


    /**
     * Gets the _CityName value for this ProposalDetailsBO.
     * 
     * @return _CityName
     */
    public java.lang.String get_CityName() {
        return _CityName;
    }


    /**
     * Sets the _CityName value for this ProposalDetailsBO.
     * 
     * @param _CityName
     */
    public void set_CityName(java.lang.String _CityName) {
        this._CityName = _CityName;
    }


    /**
     * Gets the _ClassID value for this ProposalDetailsBO.
     * 
     * @return _ClassID
     */
    public java.lang.Integer get_ClassID() {
        return _ClassID;
    }


    /**
     * Sets the _ClassID value for this ProposalDetailsBO.
     * 
     * @param _ClassID
     */
    public void set_ClassID(java.lang.Integer _ClassID) {
        this._ClassID = _ClassID;
    }


    /**
     * Gets the _CoInsuranceShare value for this ProposalDetailsBO.
     * 
     * @return _CoInsuranceShare
     */
    public java.math.BigDecimal get_CoInsuranceShare() {
        return _CoInsuranceShare;
    }


    /**
     * Sets the _CoInsuranceShare value for this ProposalDetailsBO.
     * 
     * @param _CoInsuranceShare
     */
    public void set_CoInsuranceShare(java.math.BigDecimal _CoInsuranceShare) {
        this._CoInsuranceShare = _CoInsuranceShare;
    }


    /**
     * Gets the _Co_InsStatus value for this ProposalDetailsBO.
     * 
     * @return _Co_InsStatus
     */
    public java.lang.String get_Co_InsStatus() {
        return _Co_InsStatus;
    }


    /**
     * Sets the _Co_InsStatus value for this ProposalDetailsBO.
     * 
     * @param _Co_InsStatus
     */
    public void set_Co_InsStatus(java.lang.String _Co_InsStatus) {
        this._Co_InsStatus = _Co_InsStatus;
    }


    /**
     * Gets the _ContactNumber value for this ProposalDetailsBO.
     * 
     * @return _ContactNumber
     */
    public java.lang.String get_ContactNumber() {
        return _ContactNumber;
    }


    /**
     * Sets the _ContactNumber value for this ProposalDetailsBO.
     * 
     * @param _ContactNumber
     */
    public void set_ContactNumber(java.lang.String _ContactNumber) {
        this._ContactNumber = _ContactNumber;
    }


    /**
     * Gets the _CoverNoteExits value for this ProposalDetailsBO.
     * 
     * @return _CoverNoteExits
     */
    public boolean is_CoverNoteExits() {
        return _CoverNoteExits;
    }


    /**
     * Sets the _CoverNoteExits value for this ProposalDetailsBO.
     * 
     * @param _CoverNoteExits
     */
    public void set_CoverNoteExits(boolean _CoverNoteExits) {
        this._CoverNoteExits = _CoverNoteExits;
    }


    /**
     * Gets the _DiscrepancyID_FK value for this ProposalDetailsBO.
     * 
     * @return _DiscrepancyID_FK
     */
    public java.lang.Integer get_DiscrepancyID_FK() {
        return _DiscrepancyID_FK;
    }


    /**
     * Sets the _DiscrepancyID_FK value for this ProposalDetailsBO.
     * 
     * @param _DiscrepancyID_FK
     */
    public void set_DiscrepancyID_FK(java.lang.Integer _DiscrepancyID_FK) {
        this._DiscrepancyID_FK = _DiscrepancyID_FK;
    }


    /**
     * Gets the _District value for this ProposalDetailsBO.
     * 
     * @return _District
     */
    public java.lang.String get_District() {
        return _District;
    }


    /**
     * Sets the _District value for this ProposalDetailsBO.
     * 
     * @param _District
     */
    public void set_District(java.lang.String _District) {
        this._District = _District;
    }


    /**
     * Gets the _DistrictID value for this ProposalDetailsBO.
     * 
     * @return _DistrictID
     */
    public int get_DistrictID() {
        return _DistrictID;
    }


    /**
     * Sets the _DistrictID value for this ProposalDetailsBO.
     * 
     * @param _DistrictID
     */
    public void set_DistrictID(int _DistrictID) {
        this._DistrictID = _DistrictID;
    }


    /**
     * Gets the _DocumentType value for this ProposalDetailsBO.
     * 
     * @return _DocumentType
     */
    public java.lang.String get_DocumentType() {
        return _DocumentType;
    }


    /**
     * Sets the _DocumentType value for this ProposalDetailsBO.
     * 
     * @param _DocumentType
     */
    public void set_DocumentType(java.lang.String _DocumentType) {
        this._DocumentType = _DocumentType;
    }


    /**
     * Gets the _ERF value for this ProposalDetailsBO.
     * 
     * @return _ERF
     */
    public java.math.BigDecimal get_ERF() {
        return _ERF;
    }


    /**
     * Sets the _ERF value for this ProposalDetailsBO.
     * 
     * @param _ERF
     */
    public void set_ERF(java.math.BigDecimal _ERF) {
        this._ERF = _ERF;
    }


    /**
     * Gets the _EndorsementCategory value for this ProposalDetailsBO.
     * 
     * @return _EndorsementCategory
     */
    public java.lang.String get_EndorsementCategory() {
        return _EndorsementCategory;
    }


    /**
     * Sets the _EndorsementCategory value for this ProposalDetailsBO.
     * 
     * @param _EndorsementCategory
     */
    public void set_EndorsementCategory(java.lang.String _EndorsementCategory) {
        this._EndorsementCategory = _EndorsementCategory;
    }


    /**
     * Gets the _EndorsementNo value for this ProposalDetailsBO.
     * 
     * @return _EndorsementNo
     */
    public java.lang.String get_EndorsementNo() {
        return _EndorsementNo;
    }


    /**
     * Sets the _EndorsementNo value for this ProposalDetailsBO.
     * 
     * @param _EndorsementNo
     */
    public void set_EndorsementNo(java.lang.String _EndorsementNo) {
        this._EndorsementNo = _EndorsementNo;
    }


    /**
     * Gets the _EndorsementReasonID value for this ProposalDetailsBO.
     * 
     * @return _EndorsementReasonID
     */
    public int get_EndorsementReasonID() {
        return _EndorsementReasonID;
    }


    /**
     * Sets the _EndorsementReasonID value for this ProposalDetailsBO.
     * 
     * @param _EndorsementReasonID
     */
    public void set_EndorsementReasonID(int _EndorsementReasonID) {
        this._EndorsementReasonID = _EndorsementReasonID;
    }


    /**
     * Gets the _EndorsementSubTypeName value for this ProposalDetailsBO.
     * 
     * @return _EndorsementSubTypeName
     */
    public java.lang.String get_EndorsementSubTypeName() {
        return _EndorsementSubTypeName;
    }


    /**
     * Sets the _EndorsementSubTypeName value for this ProposalDetailsBO.
     * 
     * @param _EndorsementSubTypeName
     */
    public void set_EndorsementSubTypeName(java.lang.String _EndorsementSubTypeName) {
        this._EndorsementSubTypeName = _EndorsementSubTypeName;
    }


    /**
     * Gets the _EndorsementType value for this ProposalDetailsBO.
     * 
     * @return _EndorsementType
     */
    public java.lang.String get_EndorsementType() {
        return _EndorsementType;
    }


    /**
     * Sets the _EndorsementType value for this ProposalDetailsBO.
     * 
     * @param _EndorsementType
     */
    public void set_EndorsementType(java.lang.String _EndorsementType) {
        this._EndorsementType = _EndorsementType;
    }


    /**
     * Gets the _EndorsementTypeName value for this ProposalDetailsBO.
     * 
     * @return _EndorsementTypeName
     */
    public java.lang.String get_EndorsementTypeName() {
        return _EndorsementTypeName;
    }


    /**
     * Sets the _EndorsementTypeName value for this ProposalDetailsBO.
     * 
     * @param _EndorsementTypeName
     */
    public void set_EndorsementTypeName(java.lang.String _EndorsementTypeName) {
        this._EndorsementTypeName = _EndorsementTypeName;
    }


    /**
     * Gets the _EngineNo value for this ProposalDetailsBO.
     * 
     * @return _EngineNo
     */
    public java.lang.String get_EngineNo() {
        return _EngineNo;
    }


    /**
     * Sets the _EngineNo value for this ProposalDetailsBO.
     * 
     * @param _EngineNo
     */
    public void set_EngineNo(java.lang.String _EngineNo) {
        this._EngineNo = _EngineNo;
    }


    /**
     * Gets the _ExternalBarcode value for this ProposalDetailsBO.
     * 
     * @return _ExternalBarcode
     */
    public java.lang.String get_ExternalBarcode() {
        return _ExternalBarcode;
    }


    /**
     * Sets the _ExternalBarcode value for this ProposalDetailsBO.
     * 
     * @param _ExternalBarcode
     */
    public void set_ExternalBarcode(java.lang.String _ExternalBarcode) {
        this._ExternalBarcode = _ExternalBarcode;
    }


    /**
     * Gets the _FirstAllocationDate value for this ProposalDetailsBO.
     * 
     * @return _FirstAllocationDate
     */
    public java.util.Calendar get_FirstAllocationDate() {
        return _FirstAllocationDate;
    }


    /**
     * Sets the _FirstAllocationDate value for this ProposalDetailsBO.
     * 
     * @param _FirstAllocationDate
     */
    public void set_FirstAllocationDate(java.util.Calendar _FirstAllocationDate) {
        this._FirstAllocationDate = _FirstAllocationDate;
    }


    /**
     * Gets the _FirstName value for this ProposalDetailsBO.
     * 
     * @return _FirstName
     */
    public java.lang.String get_FirstName() {
        return _FirstName;
    }


    /**
     * Sets the _FirstName value for this ProposalDetailsBO.
     * 
     * @param _FirstName
     */
    public void set_FirstName(java.lang.String _FirstName) {
        this._FirstName = _FirstName;
    }


    /**
     * Gets the _GST value for this ProposalDetailsBO.
     * 
     * @return _GST
     */
    public java.math.BigDecimal get_GST() {
        return _GST;
    }


    /**
     * Sets the _GST value for this ProposalDetailsBO.
     * 
     * @param _GST
     */
    public void set_GST(java.math.BigDecimal _GST) {
        this._GST = _GST;
    }


    /**
     * Gets the _HandOverTo value for this ProposalDetailsBO.
     * 
     * @return _HandOverTo
     */
    public java.lang.String get_HandOverTo() {
        return _HandOverTo;
    }


    /**
     * Sets the _HandOverTo value for this ProposalDetailsBO.
     * 
     * @param _HandOverTo
     */
    public void set_HandOverTo(java.lang.String _HandOverTo) {
        this._HandOverTo = _HandOverTo;
    }


    /**
     * Gets the _HandoverDate value for this ProposalDetailsBO.
     * 
     * @return _HandoverDate
     */
    public java.util.Calendar get_HandoverDate() {
        return _HandoverDate;
    }


    /**
     * Sets the _HandoverDate value for this ProposalDetailsBO.
     * 
     * @param _HandoverDate
     */
    public void set_HandoverDate(java.util.Calendar _HandoverDate) {
        this._HandoverDate = _HandoverDate;
    }


    /**
     * Gets the _HigherEduCess value for this ProposalDetailsBO.
     * 
     * @return _HigherEduCess
     */
    public java.math.BigDecimal get_HigherEduCess() {
        return _HigherEduCess;
    }


    /**
     * Sets the _HigherEduCess value for this ProposalDetailsBO.
     * 
     * @param _HigherEduCess
     */
    public void set_HigherEduCess(java.math.BigDecimal _HigherEduCess) {
        this._HigherEduCess = _HigherEduCess;
    }


    /**
     * Gets the _InitiatedBy value for this ProposalDetailsBO.
     * 
     * @return _InitiatedBy
     */
    public java.lang.String get_InitiatedBy() {
        return _InitiatedBy;
    }


    /**
     * Sets the _InitiatedBy value for this ProposalDetailsBO.
     * 
     * @param _InitiatedBy
     */
    public void set_InitiatedBy(java.lang.String _InitiatedBy) {
        this._InitiatedBy = _InitiatedBy;
    }


    /**
     * Gets the _InspectionDateTime value for this ProposalDetailsBO.
     * 
     * @return _InspectionDateTime
     */
    public java.util.Calendar get_InspectionDateTime() {
        return _InspectionDateTime;
    }


    /**
     * Sets the _InspectionDateTime value for this ProposalDetailsBO.
     * 
     * @param _InspectionDateTime
     */
    public void set_InspectionDateTime(java.util.Calendar _InspectionDateTime) {
        this._InspectionDateTime = _InspectionDateTime;
    }


    /**
     * Gets the _InspectionDocNo value for this ProposalDetailsBO.
     * 
     * @return _InspectionDocNo
     */
    public int get_InspectionDocNo() {
        return _InspectionDocNo;
    }


    /**
     * Sets the _InspectionDocNo value for this ProposalDetailsBO.
     * 
     * @param _InspectionDocNo
     */
    public void set_InspectionDocNo(int _InspectionDocNo) {
        this._InspectionDocNo = _InspectionDocNo;
    }


    /**
     * Gets the _InspectionDocNoExists value for this ProposalDetailsBO.
     * 
     * @return _InspectionDocNoExists
     */
    public boolean is_InspectionDocNoExists() {
        return _InspectionDocNoExists;
    }


    /**
     * Sets the _InspectionDocNoExists value for this ProposalDetailsBO.
     * 
     * @param _InspectionDocNoExists
     */
    public void set_InspectionDocNoExists(boolean _InspectionDocNoExists) {
        this._InspectionDocNoExists = _InspectionDocNoExists;
    }


    /**
     * Gets the _InspectionDoneBy value for this ProposalDetailsBO.
     * 
     * @return _InspectionDoneBy
     */
    public java.lang.String get_InspectionDoneBy() {
        return _InspectionDoneBy;
    }


    /**
     * Sets the _InspectionDoneBy value for this ProposalDetailsBO.
     * 
     * @param _InspectionDoneBy
     */
    public void set_InspectionDoneBy(java.lang.String _InspectionDoneBy) {
        this._InspectionDoneBy = _InspectionDoneBy;
    }


    /**
     * Gets the _InspectionDoneByName value for this ProposalDetailsBO.
     * 
     * @return _InspectionDoneByName
     */
    public java.lang.String get_InspectionDoneByName() {
        return _InspectionDoneByName;
    }


    /**
     * Sets the _InspectionDoneByName value for this ProposalDetailsBO.
     * 
     * @param _InspectionDoneByName
     */
    public void set_InspectionDoneByName(java.lang.String _InspectionDoneByName) {
        this._InspectionDoneByName = _InspectionDoneByName;
    }


    /**
     * Gets the _InsuredName value for this ProposalDetailsBO.
     * 
     * @return _InsuredName
     */
    public java.lang.String get_InsuredName() {
        return _InsuredName;
    }


    /**
     * Sets the _InsuredName value for this ProposalDetailsBO.
     * 
     * @param _InsuredName
     */
    public void set_InsuredName(java.lang.String _InsuredName) {
        this._InsuredName = _InsuredName;
    }


    /**
     * Gets the _InterRefCode value for this ProposalDetailsBO.
     * 
     * @return _InterRefCode
     */
    public java.lang.String get_InterRefCode() {
        return _InterRefCode;
    }


    /**
     * Sets the _InterRefCode value for this ProposalDetailsBO.
     * 
     * @param _InterRefCode
     */
    public void set_InterRefCode(java.lang.String _InterRefCode) {
        this._InterRefCode = _InterRefCode;
    }


    /**
     * Gets the _InterRefName value for this ProposalDetailsBO.
     * 
     * @return _InterRefName
     */
    public java.lang.String get_InterRefName() {
        return _InterRefName;
    }


    /**
     * Sets the _InterRefName value for this ProposalDetailsBO.
     * 
     * @param _InterRefName
     */
    public void set_InterRefName(java.lang.String _InterRefName) {
        this._InterRefName = _InterRefName;
    }


    /**
     * Gets the _IntermediaryRefCode value for this ProposalDetailsBO.
     * 
     * @return _IntermediaryRefCode
     */
    public java.lang.String get_IntermediaryRefCode() {
        return _IntermediaryRefCode;
    }


    /**
     * Sets the _IntermediaryRefCode value for this ProposalDetailsBO.
     * 
     * @param _IntermediaryRefCode
     */
    public void set_IntermediaryRefCode(java.lang.String _IntermediaryRefCode) {
        this._IntermediaryRefCode = _IntermediaryRefCode;
    }


    /**
     * Gets the _IntermediaryRefName value for this ProposalDetailsBO.
     * 
     * @return _IntermediaryRefName
     */
    public java.lang.String get_IntermediaryRefName() {
        return _IntermediaryRefName;
    }


    /**
     * Sets the _IntermediaryRefName value for this ProposalDetailsBO.
     * 
     * @param _IntermediaryRefName
     */
    public void set_IntermediaryRefName(java.lang.String _IntermediaryRefName) {
        this._IntermediaryRefName = _IntermediaryRefName;
    }


    /**
     * Gets the _InternalBarcode value for this ProposalDetailsBO.
     * 
     * @return _InternalBarcode
     */
    public java.lang.String get_InternalBarcode() {
        return _InternalBarcode;
    }


    /**
     * Sets the _InternalBarcode value for this ProposalDetailsBO.
     * 
     * @param _InternalBarcode
     */
    public void set_InternalBarcode(java.lang.String _InternalBarcode) {
        this._InternalBarcode = _InternalBarcode;
    }


    /**
     * Gets the _InwardedBy value for this ProposalDetailsBO.
     * 
     * @return _InwardedBy
     */
    public java.lang.String get_InwardedBy() {
        return _InwardedBy;
    }


    /**
     * Sets the _InwardedBy value for this ProposalDetailsBO.
     * 
     * @param _InwardedBy
     */
    public void set_InwardedBy(java.lang.String _InwardedBy) {
        this._InwardedBy = _InwardedBy;
    }


    /**
     * Gets the _InwardedDateTime value for this ProposalDetailsBO.
     * 
     * @return _InwardedDateTime
     */
    public java.util.Calendar get_InwardedDateTime() {
        return _InwardedDateTime;
    }


    /**
     * Sets the _InwardedDateTime value for this ProposalDetailsBO.
     * 
     * @param _InwardedDateTime
     */
    public void set_InwardedDateTime(java.util.Calendar _InwardedDateTime) {
        this._InwardedDateTime = _InwardedDateTime;
    }


    /**
     * Gets the _IsChequeCleared value for this ProposalDetailsBO.
     * 
     * @return _IsChequeCleared
     */
    public int get_IsChequeCleared() {
        return _IsChequeCleared;
    }


    /**
     * Sets the _IsChequeCleared value for this ProposalDetailsBO.
     * 
     * @param _IsChequeCleared
     */
    public void set_IsChequeCleared(int _IsChequeCleared) {
        this._IsChequeCleared = _IsChequeCleared;
    }


    /**
     * Gets the _IsCoverNoteActive value for this ProposalDetailsBO.
     * 
     * @return _IsCoverNoteActive
     */
    public boolean is_IsCoverNoteActive() {
        return _IsCoverNoteActive;
    }


    /**
     * Sets the _IsCoverNoteActive value for this ProposalDetailsBO.
     * 
     * @param _IsCoverNoteActive
     */
    public void set_IsCoverNoteActive(boolean _IsCoverNoteActive) {
        this._IsCoverNoteActive = _IsCoverNoteActive;
    }


    /**
     * Gets the _IsDeleted value for this ProposalDetailsBO.
     * 
     * @return _IsDeleted
     */
    public boolean is_IsDeleted() {
        return _IsDeleted;
    }


    /**
     * Sets the _IsDeleted value for this ProposalDetailsBO.
     * 
     * @param _IsDeleted
     */
    public void set_IsDeleted(boolean _IsDeleted) {
        this._IsDeleted = _IsDeleted;
    }


    /**
     * Gets the _IsDiscrepancyLog value for this ProposalDetailsBO.
     * 
     * @return _IsDiscrepancyLog
     */
    public int get_IsDiscrepancyLog() {
        return _IsDiscrepancyLog;
    }


    /**
     * Sets the _IsDiscrepancyLog value for this ProposalDetailsBO.
     * 
     * @param _IsDiscrepancyLog
     */
    public void set_IsDiscrepancyLog(int _IsDiscrepancyLog) {
        this._IsDiscrepancyLog = _IsDiscrepancyLog;
    }


    /**
     * Gets the _IsEditable value for this ProposalDetailsBO.
     * 
     * @return _IsEditable
     */
    public boolean is_IsEditable() {
        return _IsEditable;
    }


    /**
     * Sets the _IsEditable value for this ProposalDetailsBO.
     * 
     * @param _IsEditable
     */
    public void set_IsEditable(boolean _IsEditable) {
        this._IsEditable = _IsEditable;
    }


    /**
     * Gets the _IsEverJumping value for this ProposalDetailsBO.
     * 
     * @return _IsEverJumping
     */
    public boolean is_IsEverJumping() {
        return _IsEverJumping;
    }


    /**
     * Sets the _IsEverJumping value for this ProposalDetailsBO.
     * 
     * @param _IsEverJumping
     */
    public void set_IsEverJumping(boolean _IsEverJumping) {
        this._IsEverJumping = _IsEverJumping;
    }


    /**
     * Gets the _IsInwarded value for this ProposalDetailsBO.
     * 
     * @return _IsInwarded
     */
    public boolean is_IsInwarded() {
        return _IsInwarded;
    }


    /**
     * Sets the _IsInwarded value for this ProposalDetailsBO.
     * 
     * @param _IsInwarded
     */
    public void set_IsInwarded(boolean _IsInwarded) {
        this._IsInwarded = _IsInwarded;
    }


    /**
     * Gets the _IsMappedStatus value for this ProposalDetailsBO.
     * 
     * @return _IsMappedStatus
     */
    public boolean is_IsMappedStatus() {
        return _IsMappedStatus;
    }


    /**
     * Sets the _IsMappedStatus value for this ProposalDetailsBO.
     * 
     * @param _IsMappedStatus
     */
    public void set_IsMappedStatus(boolean _IsMappedStatus) {
        this._IsMappedStatus = _IsMappedStatus;
    }


    /**
     * Gets the _IsNCB value for this ProposalDetailsBO.
     * 
     * @return _IsNCB
     */
    public boolean is_IsNCB() {
        return _IsNCB;
    }


    /**
     * Sets the _IsNCB value for this ProposalDetailsBO.
     * 
     * @param _IsNCB
     */
    public void set_IsNCB(boolean _IsNCB) {
        this._IsNCB = _IsNCB;
    }


    /**
     * Gets the _IsPDC value for this ProposalDetailsBO.
     * 
     * @return _IsPDC
     */
    public int get_IsPDC() {
        return _IsPDC;
    }


    /**
     * Sets the _IsPDC value for this ProposalDetailsBO.
     * 
     * @param _IsPDC
     */
    public void set_IsPDC(int _IsPDC) {
        this._IsPDC = _IsPDC;
    }


    /**
     * Gets the _IsPassedForPolicyGeneration value for this ProposalDetailsBO.
     * 
     * @return _IsPassedForPolicyGeneration
     */
    public boolean is_IsPassedForPolicyGeneration() {
        return _IsPassedForPolicyGeneration;
    }


    /**
     * Sets the _IsPassedForPolicyGeneration value for this ProposalDetailsBO.
     * 
     * @param _IsPassedForPolicyGeneration
     */
    public void set_IsPassedForPolicyGeneration(boolean _IsPassedForPolicyGeneration) {
        this._IsPassedForPolicyGeneration = _IsPassedForPolicyGeneration;
    }


    /**
     * Gets the _IsPaymentDone value for this ProposalDetailsBO.
     * 
     * @return _IsPaymentDone
     */
    public boolean is_IsPaymentDone() {
        return _IsPaymentDone;
    }


    /**
     * Sets the _IsPaymentDone value for this ProposalDetailsBO.
     * 
     * @param _IsPaymentDone
     */
    public void set_IsPaymentDone(boolean _IsPaymentDone) {
        this._IsPaymentDone = _IsPaymentDone;
    }


    /**
     * Gets the _IsPolicyGeneratedInICM value for this ProposalDetailsBO.
     * 
     * @return _IsPolicyGeneratedInICM
     */
    public int get_IsPolicyGeneratedInICM() {
        return _IsPolicyGeneratedInICM;
    }


    /**
     * Sets the _IsPolicyGeneratedInICM value for this ProposalDetailsBO.
     * 
     * @param _IsPolicyGeneratedInICM
     */
    public void set_IsPolicyGeneratedInICM(int _IsPolicyGeneratedInICM) {
        this._IsPolicyGeneratedInICM = _IsPolicyGeneratedInICM;
    }


    /**
     * Gets the _IsPostDateCheque value for this ProposalDetailsBO.
     * 
     * @return _IsPostDateCheque
     */
    public java.lang.String get_IsPostDateCheque() {
        return _IsPostDateCheque;
    }


    /**
     * Sets the _IsPostDateCheque value for this ProposalDetailsBO.
     * 
     * @param _IsPostDateCheque
     */
    public void set_IsPostDateCheque(java.lang.String _IsPostDateCheque) {
        this._IsPostDateCheque = _IsPostDateCheque;
    }


    /**
     * Gets the _IsQCDoneBSM value for this ProposalDetailsBO.
     * 
     * @return _IsQCDoneBSM
     */
    public boolean is_IsQCDoneBSM() {
        return _IsQCDoneBSM;
    }


    /**
     * Sets the _IsQCDoneBSM value for this ProposalDetailsBO.
     * 
     * @param _IsQCDoneBSM
     */
    public void set_IsQCDoneBSM(boolean _IsQCDoneBSM) {
        this._IsQCDoneBSM = _IsQCDoneBSM;
    }


    /**
     * Gets the _IsQCOk value for this ProposalDetailsBO.
     * 
     * @return _IsQCOk
     */
    public boolean is_IsQCOk() {
        return _IsQCOk;
    }


    /**
     * Sets the _IsQCOk value for this ProposalDetailsBO.
     * 
     * @param _IsQCOk
     */
    public void set_IsQCOk(boolean _IsQCOk) {
        this._IsQCOk = _IsQCOk;
    }


    /**
     * Gets the _IsReissue value for this ProposalDetailsBO.
     * 
     * @return _IsReissue
     */
    public boolean is_IsReissue() {
        return _IsReissue;
    }


    /**
     * Sets the _IsReissue value for this ProposalDetailsBO.
     * 
     * @param _IsReissue
     */
    public void set_IsReissue(boolean _IsReissue) {
        this._IsReissue = _IsReissue;
    }


    /**
     * Gets the _Isjumping value for this ProposalDetailsBO.
     * 
     * @return _Isjumping
     */
    public boolean is_Isjumping() {
        return _Isjumping;
    }


    /**
     * Sets the _Isjumping value for this ProposalDetailsBO.
     * 
     * @param _Isjumping
     */
    public void set_Isjumping(boolean _Isjumping) {
        this._Isjumping = _Isjumping;
    }


    /**
     * Gets the _IssueDate value for this ProposalDetailsBO.
     * 
     * @return _IssueDate
     */
    public java.util.Calendar get_IssueDate() {
        return _IssueDate;
    }


    /**
     * Sets the _IssueDate value for this ProposalDetailsBO.
     * 
     * @param _IssueDate
     */
    public void set_IssueDate(java.util.Calendar _IssueDate) {
        this._IssueDate = _IssueDate;
    }


    /**
     * Gets the _JobAllocatedBy value for this ProposalDetailsBO.
     * 
     * @return _JobAllocatedBy
     */
    public java.lang.String get_JobAllocatedBy() {
        return _JobAllocatedBy;
    }


    /**
     * Sets the _JobAllocatedBy value for this ProposalDetailsBO.
     * 
     * @param _JobAllocatedBy
     */
    public void set_JobAllocatedBy(java.lang.String _JobAllocatedBy) {
        this._JobAllocatedBy = _JobAllocatedBy;
    }


    /**
     * Gets the _JobAllocatedTo value for this ProposalDetailsBO.
     * 
     * @return _JobAllocatedTo
     */
    public java.lang.String get_JobAllocatedTo() {
        return _JobAllocatedTo;
    }


    /**
     * Sets the _JobAllocatedTo value for this ProposalDetailsBO.
     * 
     * @param _JobAllocatedTo
     */
    public void set_JobAllocatedTo(java.lang.String _JobAllocatedTo) {
        this._JobAllocatedTo = _JobAllocatedTo;
    }


    /**
     * Gets the _JobAllocationDate value for this ProposalDetailsBO.
     * 
     * @return _JobAllocationDate
     */
    public java.util.Calendar get_JobAllocationDate() {
        return _JobAllocationDate;
    }


    /**
     * Sets the _JobAllocationDate value for this ProposalDetailsBO.
     * 
     * @param _JobAllocationDate
     */
    public void set_JobAllocationDate(java.util.Calendar _JobAllocationDate) {
        this._JobAllocationDate = _JobAllocationDate;
    }


    /**
     * Gets the _JumpingStatusChangedDate value for this ProposalDetailsBO.
     * 
     * @return _JumpingStatusChangedDate
     */
    public java.util.Calendar get_JumpingStatusChangedDate() {
        return _JumpingStatusChangedDate;
    }


    /**
     * Sets the _JumpingStatusChangedDate value for this ProposalDetailsBO.
     * 
     * @param _JumpingStatusChangedDate
     */
    public void set_JumpingStatusChangedDate(java.util.Calendar _JumpingStatusChangedDate) {
        this._JumpingStatusChangedDate = _JumpingStatusChangedDate;
    }


    /**
     * Gets the _KeyField1 value for this ProposalDetailsBO.
     * 
     * @return _KeyField1
     */
    public int get_KeyField1() {
        return _KeyField1;
    }


    /**
     * Sets the _KeyField1 value for this ProposalDetailsBO.
     * 
     * @param _KeyField1
     */
    public void set_KeyField1(int _KeyField1) {
        this._KeyField1 = _KeyField1;
    }


    /**
     * Gets the _KeyField2 value for this ProposalDetailsBO.
     * 
     * @return _KeyField2
     */
    public int get_KeyField2() {
        return _KeyField2;
    }


    /**
     * Sets the _KeyField2 value for this ProposalDetailsBO.
     * 
     * @param _KeyField2
     */
    public void set_KeyField2(int _KeyField2) {
        this._KeyField2 = _KeyField2;
    }


    /**
     * Gets the _KeyField3 value for this ProposalDetailsBO.
     * 
     * @return _KeyField3
     */
    public int get_KeyField3() {
        return _KeyField3;
    }


    /**
     * Sets the _KeyField3 value for this ProposalDetailsBO.
     * 
     * @param _KeyField3
     */
    public void set_KeyField3(int _KeyField3) {
        this._KeyField3 = _KeyField3;
    }


    /**
     * Gets the _LastName value for this ProposalDetailsBO.
     * 
     * @return _LastName
     */
    public java.lang.String get_LastName() {
        return _LastName;
    }


    /**
     * Sets the _LastName value for this ProposalDetailsBO.
     * 
     * @param _LastName
     */
    public void set_LastName(java.lang.String _LastName) {
        this._LastName = _LastName;
    }


    /**
     * Gets the _LeadNo value for this ProposalDetailsBO.
     * 
     * @return _LeadNo
     */
    public java.lang.String get_LeadNo() {
        return _LeadNo;
    }


    /**
     * Sets the _LeadNo value for this ProposalDetailsBO.
     * 
     * @param _LeadNo
     */
    public void set_LeadNo(java.lang.String _LeadNo) {
        this._LeadNo = _LeadNo;
    }


    /**
     * Gets the _LeadNoExists value for this ProposalDetailsBO.
     * 
     * @return _LeadNoExists
     */
    public boolean is_LeadNoExists() {
        return _LeadNoExists;
    }


    /**
     * Sets the _LeadNoExists value for this ProposalDetailsBO.
     * 
     * @param _LeadNoExists
     */
    public void set_LeadNoExists(boolean _LeadNoExists) {
        this._LeadNoExists = _LeadNoExists;
    }


    /**
     * Gets the _MakeId_FK value for this ProposalDetailsBO.
     * 
     * @return _MakeId_FK
     */
    public int get_MakeId_FK() {
        return _MakeId_FK;
    }


    /**
     * Sets the _MakeId_FK value for this ProposalDetailsBO.
     * 
     * @param _MakeId_FK
     */
    public void set_MakeId_FK(int _MakeId_FK) {
        this._MakeId_FK = _MakeId_FK;
    }


    /**
     * Gets the _MakeName value for this ProposalDetailsBO.
     * 
     * @return _MakeName
     */
    public java.lang.String get_MakeName() {
        return _MakeName;
    }


    /**
     * Sets the _MakeName value for this ProposalDetailsBO.
     * 
     * @param _MakeName
     */
    public void set_MakeName(java.lang.String _MakeName) {
        this._MakeName = _MakeName;
    }


    /**
     * Gets the _MarinePremium value for this ProposalDetailsBO.
     * 
     * @return _MarinePremium
     */
    public java.math.BigDecimal get_MarinePremium() {
        return _MarinePremium;
    }


    /**
     * Sets the _MarinePremium value for this ProposalDetailsBO.
     * 
     * @param _MarinePremium
     */
    public void set_MarinePremium(java.math.BigDecimal _MarinePremium) {
        this._MarinePremium = _MarinePremium;
    }


    /**
     * Gets the _MaxDiscountallowed value for this ProposalDetailsBO.
     * 
     * @return _MaxDiscountallowed
     */
    public java.math.BigDecimal get_MaxDiscountallowed() {
        return _MaxDiscountallowed;
    }


    /**
     * Sets the _MaxDiscountallowed value for this ProposalDetailsBO.
     * 
     * @param _MaxDiscountallowed
     */
    public void set_MaxDiscountallowed(java.math.BigDecimal _MaxDiscountallowed) {
        this._MaxDiscountallowed = _MaxDiscountallowed;
    }


    /**
     * Gets the _MaxInstrDate value for this ProposalDetailsBO.
     * 
     * @return _MaxInstrDate
     */
    public java.util.Calendar get_MaxInstrDate() {
        return _MaxInstrDate;
    }


    /**
     * Sets the _MaxInstrDate value for this ProposalDetailsBO.
     * 
     * @param _MaxInstrDate
     */
    public void set_MaxInstrDate(java.util.Calendar _MaxInstrDate) {
        this._MaxInstrDate = _MaxInstrDate;
    }


    /**
     * Gets the _Message value for this ProposalDetailsBO.
     * 
     * @return _Message
     */
    public java.lang.String get_Message() {
        return _Message;
    }


    /**
     * Sets the _Message value for this ProposalDetailsBO.
     * 
     * @param _Message
     */
    public void set_Message(java.lang.String _Message) {
        this._Message = _Message;
    }


    /**
     * Gets the _MiddleName value for this ProposalDetailsBO.
     * 
     * @return _MiddleName
     */
    public java.lang.String get_MiddleName() {
        return _MiddleName;
    }


    /**
     * Sets the _MiddleName value for this ProposalDetailsBO.
     * 
     * @param _MiddleName
     */
    public void set_MiddleName(java.lang.String _MiddleName) {
        this._MiddleName = _MiddleName;
    }


    /**
     * Gets the _ModelId_FK value for this ProposalDetailsBO.
     * 
     * @return _ModelId_FK
     */
    public int get_ModelId_FK() {
        return _ModelId_FK;
    }


    /**
     * Sets the _ModelId_FK value for this ProposalDetailsBO.
     * 
     * @param _ModelId_FK
     */
    public void set_ModelId_FK(int _ModelId_FK) {
        this._ModelId_FK = _ModelId_FK;
    }


    /**
     * Gets the _ModelVariant value for this ProposalDetailsBO.
     * 
     * @return _ModelVariant
     */
    public java.lang.String get_ModelVariant() {
        return _ModelVariant;
    }


    /**
     * Sets the _ModelVariant value for this ProposalDetailsBO.
     * 
     * @param _ModelVariant
     */
    public void set_ModelVariant(java.lang.String _ModelVariant) {
        this._ModelVariant = _ModelVariant;
    }


    /**
     * Gets the _NetOD value for this ProposalDetailsBO.
     * 
     * @return _NetOD
     */
    public java.math.BigDecimal get_NetOD() {
        return _NetOD;
    }


    /**
     * Sets the _NetOD value for this ProposalDetailsBO.
     * 
     * @param _NetOD
     */
    public void set_NetOD(java.math.BigDecimal _NetOD) {
        this._NetOD = _NetOD;
    }


    /**
     * Gets the _NetPremium value for this ProposalDetailsBO.
     * 
     * @return _NetPremium
     */
    public float get_NetPremium() {
        return _NetPremium;
    }


    /**
     * Sets the _NetPremium value for this ProposalDetailsBO.
     * 
     * @param _NetPremium
     */
    public void set_NetPremium(float _NetPremium) {
        this._NetPremium = _NetPremium;
    }


    /**
     * Gets the _Nettp value for this ProposalDetailsBO.
     * 
     * @return _Nettp
     */
    public java.math.BigDecimal get_Nettp() {
        return _Nettp;
    }


    /**
     * Sets the _Nettp value for this ProposalDetailsBO.
     * 
     * @param _Nettp
     */
    public void set_Nettp(java.math.BigDecimal _Nettp) {
        this._Nettp = _Nettp;
    }


    /**
     * Gets the _NewAgentCode value for this ProposalDetailsBO.
     * 
     * @return _NewAgentCode
     */
    public java.lang.String get_NewAgentCode() {
        return _NewAgentCode;
    }


    /**
     * Sets the _NewAgentCode value for this ProposalDetailsBO.
     * 
     * @param _NewAgentCode
     */
    public void set_NewAgentCode(java.lang.String _NewAgentCode) {
        this._NewAgentCode = _NewAgentCode;
    }


    /**
     * Gets the _NewBASCode value for this ProposalDetailsBO.
     * 
     * @return _NewBASCode
     */
    public java.lang.String get_NewBASCode() {
        return _NewBASCode;
    }


    /**
     * Sets the _NewBASCode value for this ProposalDetailsBO.
     * 
     * @param _NewBASCode
     */
    public void set_NewBASCode(java.lang.String _NewBASCode) {
        this._NewBASCode = _NewBASCode;
    }


    /**
     * Gets the _NewIntermediaryRefCode value for this ProposalDetailsBO.
     * 
     * @return _NewIntermediaryRefCode
     */
    public java.lang.String get_NewIntermediaryRefCode() {
        return _NewIntermediaryRefCode;
    }


    /**
     * Sets the _NewIntermediaryRefCode value for this ProposalDetailsBO.
     * 
     * @param _NewIntermediaryRefCode
     */
    public void set_NewIntermediaryRefCode(java.lang.String _NewIntermediaryRefCode) {
        this._NewIntermediaryRefCode = _NewIntermediaryRefCode;
    }


    /**
     * Gets the _NewSMCode value for this ProposalDetailsBO.
     * 
     * @return _NewSMCode
     */
    public java.lang.String get_NewSMCode() {
        return _NewSMCode;
    }


    /**
     * Sets the _NewSMCode value for this ProposalDetailsBO.
     * 
     * @param _NewSMCode
     */
    public void set_NewSMCode(java.lang.String _NewSMCode) {
        this._NewSMCode = _NewSMCode;
    }


    /**
     * Gets the _OldAgentCode value for this ProposalDetailsBO.
     * 
     * @return _OldAgentCode
     */
    public java.lang.String get_OldAgentCode() {
        return _OldAgentCode;
    }


    /**
     * Sets the _OldAgentCode value for this ProposalDetailsBO.
     * 
     * @param _OldAgentCode
     */
    public void set_OldAgentCode(java.lang.String _OldAgentCode) {
        this._OldAgentCode = _OldAgentCode;
    }


    /**
     * Gets the _OldBASCode value for this ProposalDetailsBO.
     * 
     * @return _OldBASCode
     */
    public java.lang.String get_OldBASCode() {
        return _OldBASCode;
    }


    /**
     * Sets the _OldBASCode value for this ProposalDetailsBO.
     * 
     * @param _OldBASCode
     */
    public void set_OldBASCode(java.lang.String _OldBASCode) {
        this._OldBASCode = _OldBASCode;
    }


    /**
     * Gets the _OldIntermediaryRefCode value for this ProposalDetailsBO.
     * 
     * @return _OldIntermediaryRefCode
     */
    public java.lang.String get_OldIntermediaryRefCode() {
        return _OldIntermediaryRefCode;
    }


    /**
     * Sets the _OldIntermediaryRefCode value for this ProposalDetailsBO.
     * 
     * @param _OldIntermediaryRefCode
     */
    public void set_OldIntermediaryRefCode(java.lang.String _OldIntermediaryRefCode) {
        this._OldIntermediaryRefCode = _OldIntermediaryRefCode;
    }


    /**
     * Gets the _PanNo value for this ProposalDetailsBO.
     * 
     * @return _PanNo
     */
    public java.lang.String get_PanNo() {
        return _PanNo;
    }


    /**
     * Sets the _PanNo value for this ProposalDetailsBO.
     * 
     * @param _PanNo
     */
    public void set_PanNo(java.lang.String _PanNo) {
        this._PanNo = _PanNo;
    }


    /**
     * Gets the _PassportNo value for this ProposalDetailsBO.
     * 
     * @return _PassportNo
     */
    public java.lang.String get_PassportNo() {
        return _PassportNo;
    }


    /**
     * Sets the _PassportNo value for this ProposalDetailsBO.
     * 
     * @param _PassportNo
     */
    public void set_PassportNo(java.lang.String _PassportNo) {
        this._PassportNo = _PassportNo;
    }


    /**
     * Gets the _PaymentMode value for this ProposalDetailsBO.
     * 
     * @return _PaymentMode
     */
    public java.lang.String get_PaymentMode() {
        return _PaymentMode;
    }


    /**
     * Sets the _PaymentMode value for this ProposalDetailsBO.
     * 
     * @param _PaymentMode
     */
    public void set_PaymentMode(java.lang.String _PaymentMode) {
        this._PaymentMode = _PaymentMode;
    }


    /**
     * Gets the _PendingPolicyBranch value for this ProposalDetailsBO.
     * 
     * @return _PendingPolicyBranch
     */
    public java.lang.String get_PendingPolicyBranch() {
        return _PendingPolicyBranch;
    }


    /**
     * Sets the _PendingPolicyBranch value for this ProposalDetailsBO.
     * 
     * @param _PendingPolicyBranch
     */
    public void set_PendingPolicyBranch(java.lang.String _PendingPolicyBranch) {
        this._PendingPolicyBranch = _PendingPolicyBranch;
    }


    /**
     * Gets the _PinCode value for this ProposalDetailsBO.
     * 
     * @return _PinCode
     */
    public java.lang.String get_PinCode() {
        return _PinCode;
    }


    /**
     * Sets the _PinCode value for this ProposalDetailsBO.
     * 
     * @param _PinCode
     */
    public void set_PinCode(java.lang.String _PinCode) {
        this._PinCode = _PinCode;
    }


    /**
     * Gets the _PlanName value for this ProposalDetailsBO.
     * 
     * @return _PlanName
     */
    public java.lang.String get_PlanName() {
        return _PlanName;
    }


    /**
     * Sets the _PlanName value for this ProposalDetailsBO.
     * 
     * @param _PlanName
     */
    public void set_PlanName(java.lang.String _PlanName) {
        this._PlanName = _PlanName;
    }


    /**
     * Gets the _Planid_FK value for this ProposalDetailsBO.
     * 
     * @return _Planid_FK
     */
    public java.lang.Integer get_Planid_FK() {
        return _Planid_FK;
    }


    /**
     * Sets the _Planid_FK value for this ProposalDetailsBO.
     * 
     * @param _Planid_FK
     */
    public void set_Planid_FK(java.lang.Integer _Planid_FK) {
        this._Planid_FK = _Planid_FK;
    }


    /**
     * Gets the _PolicyEndDate value for this ProposalDetailsBO.
     * 
     * @return _PolicyEndDate
     */
    public java.util.Calendar get_PolicyEndDate() {
        return _PolicyEndDate;
    }


    /**
     * Sets the _PolicyEndDate value for this ProposalDetailsBO.
     * 
     * @param _PolicyEndDate
     */
    public void set_PolicyEndDate(java.util.Calendar _PolicyEndDate) {
        this._PolicyEndDate = _PolicyEndDate;
    }


    /**
     * Gets the _PolicyGenDate value for this ProposalDetailsBO.
     * 
     * @return _PolicyGenDate
     */
    public java.util.Calendar get_PolicyGenDate() {
        return _PolicyGenDate;
    }


    /**
     * Sets the _PolicyGenDate value for this ProposalDetailsBO.
     * 
     * @param _PolicyGenDate
     */
    public void set_PolicyGenDate(java.util.Calendar _PolicyGenDate) {
        this._PolicyGenDate = _PolicyGenDate;
    }


    /**
     * Gets the _PolicyNo_FK value for this ProposalDetailsBO.
     * 
     * @return _PolicyNo_FK
     */
    public java.lang.String get_PolicyNo_FK() {
        return _PolicyNo_FK;
    }


    /**
     * Sets the _PolicyNo_FK value for this ProposalDetailsBO.
     * 
     * @param _PolicyNo_FK
     */
    public void set_PolicyNo_FK(java.lang.String _PolicyNo_FK) {
        this._PolicyNo_FK = _PolicyNo_FK;
    }


    /**
     * Gets the _PolicyStatus value for this ProposalDetailsBO.
     * 
     * @return _PolicyStatus
     */
    public boolean is_PolicyStatus() {
        return _PolicyStatus;
    }


    /**
     * Sets the _PolicyStatus value for this ProposalDetailsBO.
     * 
     * @param _PolicyStatus
     */
    public void set_PolicyStatus(boolean _PolicyStatus) {
        this._PolicyStatus = _PolicyStatus;
    }


    /**
     * Gets the _Prefix value for this ProposalDetailsBO.
     * 
     * @return _Prefix
     */
    public java.lang.String get_Prefix() {
        return _Prefix;
    }


    /**
     * Sets the _Prefix value for this ProposalDetailsBO.
     * 
     * @param _Prefix
     */
    public void set_Prefix(java.lang.String _Prefix) {
        this._Prefix = _Prefix;
    }


    /**
     * Gets the _Premium value for this ProposalDetailsBO.
     * 
     * @return _Premium
     */
    public java.math.BigDecimal get_Premium() {
        return _Premium;
    }


    /**
     * Sets the _Premium value for this ProposalDetailsBO.
     * 
     * @param _Premium
     */
    public void set_Premium(java.math.BigDecimal _Premium) {
        this._Premium = _Premium;
    }


    /**
     * Gets the _PrevEndorsementStatus value for this ProposalDetailsBO.
     * 
     * @return _PrevEndorsementStatus
     */
    public java.lang.String get_PrevEndorsementStatus() {
        return _PrevEndorsementStatus;
    }


    /**
     * Sets the _PrevEndorsementStatus value for this ProposalDetailsBO.
     * 
     * @param _PrevEndorsementStatus
     */
    public void set_PrevEndorsementStatus(java.lang.String _PrevEndorsementStatus) {
        this._PrevEndorsementStatus = _PrevEndorsementStatus;
    }


    /**
     * Gets the _PreviousInsurerID value for this ProposalDetailsBO.
     * 
     * @return _PreviousInsurerID
     */
    public java.lang.Integer get_PreviousInsurerID() {
        return _PreviousInsurerID;
    }


    /**
     * Sets the _PreviousInsurerID value for this ProposalDetailsBO.
     * 
     * @param _PreviousInsurerID
     */
    public void set_PreviousInsurerID(java.lang.Integer _PreviousInsurerID) {
        this._PreviousInsurerID = _PreviousInsurerID;
    }


    /**
     * Gets the _PreviousPolicyEndDate value for this ProposalDetailsBO.
     * 
     * @return _PreviousPolicyEndDate
     */
    public java.util.Calendar get_PreviousPolicyEndDate() {
        return _PreviousPolicyEndDate;
    }


    /**
     * Sets the _PreviousPolicyEndDate value for this ProposalDetailsBO.
     * 
     * @param _PreviousPolicyEndDate
     */
    public void set_PreviousPolicyEndDate(java.util.Calendar _PreviousPolicyEndDate) {
        this._PreviousPolicyEndDate = _PreviousPolicyEndDate;
    }


    /**
     * Gets the _PreviousPolicyNo value for this ProposalDetailsBO.
     * 
     * @return _PreviousPolicyNo
     */
    public java.lang.String get_PreviousPolicyNo() {
        return _PreviousPolicyNo;
    }


    /**
     * Sets the _PreviousPolicyNo value for this ProposalDetailsBO.
     * 
     * @param _PreviousPolicyNo
     */
    public void set_PreviousPolicyNo(java.lang.String _PreviousPolicyNo) {
        this._PreviousPolicyNo = _PreviousPolicyNo;
    }


    /**
     * Gets the _PreviousPolicyProcessedIn value for this ProposalDetailsBO.
     * 
     * @return _PreviousPolicyProcessedIn
     */
    public int get_PreviousPolicyProcessedIn() {
        return _PreviousPolicyProcessedIn;
    }


    /**
     * Sets the _PreviousPolicyProcessedIn value for this ProposalDetailsBO.
     * 
     * @param _PreviousPolicyProcessedIn
     */
    public void set_PreviousPolicyProcessedIn(int _PreviousPolicyProcessedIn) {
        this._PreviousPolicyProcessedIn = _PreviousPolicyProcessedIn;
    }


    /**
     * Gets the _PreviousPolicyProductCode value for this ProposalDetailsBO.
     * 
     * @return _PreviousPolicyProductCode
     */
    public java.lang.String get_PreviousPolicyProductCode() {
        return _PreviousPolicyProductCode;
    }


    /**
     * Sets the _PreviousPolicyProductCode value for this ProposalDetailsBO.
     * 
     * @param _PreviousPolicyProductCode
     */
    public void set_PreviousPolicyProductCode(java.lang.String _PreviousPolicyProductCode) {
        this._PreviousPolicyProductCode = _PreviousPolicyProductCode;
    }


    /**
     * Gets the _ProcessedAt value for this ProposalDetailsBO.
     * 
     * @return _ProcessedAt
     */
    public int get_ProcessedAt() {
        return _ProcessedAt;
    }


    /**
     * Sets the _ProcessedAt value for this ProposalDetailsBO.
     * 
     * @param _ProcessedAt
     */
    public void set_ProcessedAt(int _ProcessedAt) {
        this._ProcessedAt = _ProcessedAt;
    }


    /**
     * Gets the _ProcessedAtName value for this ProposalDetailsBO.
     * 
     * @return _ProcessedAtName
     */
    public java.lang.String get_ProcessedAtName() {
        return _ProcessedAtName;
    }


    /**
     * Sets the _ProcessedAtName value for this ProposalDetailsBO.
     * 
     * @param _ProcessedAtName
     */
    public void set_ProcessedAtName(java.lang.String _ProcessedAtName) {
        this._ProcessedAtName = _ProcessedAtName;
    }


    /**
     * Gets the _ProcessedIn value for this ProposalDetailsBO.
     * 
     * @return _ProcessedIn
     */
    public int get_ProcessedIn() {
        return _ProcessedIn;
    }


    /**
     * Sets the _ProcessedIn value for this ProposalDetailsBO.
     * 
     * @param _ProcessedIn
     */
    public void set_ProcessedIn(int _ProcessedIn) {
        this._ProcessedIn = _ProcessedIn;
    }


    /**
     * Gets the _ProcessedInName value for this ProposalDetailsBO.
     * 
     * @return _ProcessedInName
     */
    public java.lang.String get_ProcessedInName() {
        return _ProcessedInName;
    }


    /**
     * Sets the _ProcessedInName value for this ProposalDetailsBO.
     * 
     * @param _ProcessedInName
     */
    public void set_ProcessedInName(java.lang.String _ProcessedInName) {
        this._ProcessedInName = _ProcessedInName;
    }


    /**
     * Gets the _Product value for this ProposalDetailsBO.
     * 
     * @return _Product
     */
    public java.lang.String get_Product() {
        return _Product;
    }


    /**
     * Sets the _Product value for this ProposalDetailsBO.
     * 
     * @param _Product
     */
    public void set_Product(java.lang.String _Product) {
        this._Product = _Product;
    }


    /**
     * Gets the _ProductCode value for this ProposalDetailsBO.
     * 
     * @return _ProductCode
     */
    public java.lang.String get_ProductCode() {
        return _ProductCode;
    }


    /**
     * Sets the _ProductCode value for this ProposalDetailsBO.
     * 
     * @param _ProductCode
     */
    public void set_ProductCode(java.lang.String _ProductCode) {
        this._ProductCode = _ProductCode;
    }


    /**
     * Gets the _ProductName value for this ProposalDetailsBO.
     * 
     * @return _ProductName
     */
    public java.lang.String get_ProductName() {
        return _ProductName;
    }


    /**
     * Sets the _ProductName value for this ProposalDetailsBO.
     * 
     * @param _ProductName
     */
    public void set_ProductName(java.lang.String _ProductName) {
        this._ProductName = _ProductName;
    }


    /**
     * Gets the _ProductType value for this ProposalDetailsBO.
     * 
     * @return _ProductType
     */
    public java.lang.String get_ProductType() {
        return _ProductType;
    }


    /**
     * Sets the _ProductType value for this ProposalDetailsBO.
     * 
     * @param _ProductType
     */
    public void set_ProductType(java.lang.String _ProductType) {
        this._ProductType = _ProductType;
    }


    /**
     * Gets the _ProposalAmount value for this ProposalDetailsBO.
     * 
     * @return _ProposalAmount
     */
    public java.math.BigDecimal get_ProposalAmount() {
        return _ProposalAmount;
    }


    /**
     * Sets the _ProposalAmount value for this ProposalDetailsBO.
     * 
     * @param _ProposalAmount
     */
    public void set_ProposalAmount(java.math.BigDecimal _ProposalAmount) {
        this._ProposalAmount = _ProposalAmount;
    }


    /**
     * Gets the _ProposalClass value for this ProposalDetailsBO.
     * 
     * @return _ProposalClass
     */
    public java.lang.String get_ProposalClass() {
        return _ProposalClass;
    }


    /**
     * Sets the _ProposalClass value for this ProposalDetailsBO.
     * 
     * @param _ProposalClass
     */
    public void set_ProposalClass(java.lang.String _ProposalClass) {
        this._ProposalClass = _ProposalClass;
    }


    /**
     * Gets the _ProposalID_FK value for this ProposalDetailsBO.
     * 
     * @return _ProposalID_FK
     */
    public java.lang.String get_ProposalID_FK() {
        return _ProposalID_FK;
    }


    /**
     * Sets the _ProposalID_FK value for this ProposalDetailsBO.
     * 
     * @param _ProposalID_FK
     */
    public void set_ProposalID_FK(java.lang.String _ProposalID_FK) {
        this._ProposalID_FK = _ProposalID_FK;
    }


    /**
     * Gets the _ProposalOrEndorDate value for this ProposalDetailsBO.
     * 
     * @return _ProposalOrEndorDate
     */
    public java.util.Calendar get_ProposalOrEndorDate() {
        return _ProposalOrEndorDate;
    }


    /**
     * Sets the _ProposalOrEndorDate value for this ProposalDetailsBO.
     * 
     * @param _ProposalOrEndorDate
     */
    public void set_ProposalOrEndorDate(java.util.Calendar _ProposalOrEndorDate) {
        this._ProposalOrEndorDate = _ProposalOrEndorDate;
    }


    /**
     * Gets the _ProposalStatus value for this ProposalDetailsBO.
     * 
     * @return _ProposalStatus
     */
    public int get_ProposalStatus() {
        return _ProposalStatus;
    }


    /**
     * Sets the _ProposalStatus value for this ProposalDetailsBO.
     * 
     * @param _ProposalStatus
     */
    public void set_ProposalStatus(int _ProposalStatus) {
        this._ProposalStatus = _ProposalStatus;
    }


    /**
     * Gets the _ProposalStatusID value for this ProposalDetailsBO.
     * 
     * @return _ProposalStatusID
     */
    public int get_ProposalStatusID() {
        return _ProposalStatusID;
    }


    /**
     * Sets the _ProposalStatusID value for this ProposalDetailsBO.
     * 
     * @param _ProposalStatusID
     */
    public void set_ProposalStatusID(int _ProposalStatusID) {
        this._ProposalStatusID = _ProposalStatusID;
    }


    /**
     * Gets the _QCDone_By value for this ProposalDetailsBO.
     * 
     * @return _QCDone_By
     */
    public java.lang.String get_QCDone_By() {
        return _QCDone_By;
    }


    /**
     * Sets the _QCDone_By value for this ProposalDetailsBO.
     * 
     * @param _QCDone_By
     */
    public void set_QCDone_By(java.lang.String _QCDone_By) {
        this._QCDone_By = _QCDone_By;
    }


    /**
     * Gets the _QCDone_Date value for this ProposalDetailsBO.
     * 
     * @return _QCDone_Date
     */
    public java.util.Calendar get_QCDone_Date() {
        return _QCDone_Date;
    }


    /**
     * Sets the _QCDone_Date value for this ProposalDetailsBO.
     * 
     * @param _QCDone_Date
     */
    public void set_QCDone_Date(java.util.Calendar _QCDone_Date) {
        this._QCDone_Date = _QCDone_Date;
    }


    /**
     * Gets the _QCRemark value for this ProposalDetailsBO.
     * 
     * @return _QCRemark
     */
    public java.lang.String get_QCRemark() {
        return _QCRemark;
    }


    /**
     * Sets the _QCRemark value for this ProposalDetailsBO.
     * 
     * @param _QCRemark
     */
    public void set_QCRemark(java.lang.String _QCRemark) {
        this._QCRemark = _QCRemark;
    }


    /**
     * Gets the _QcStatus value for this ProposalDetailsBO.
     * 
     * @return _QcStatus
     */
    public java.lang.String get_QcStatus() {
        return _QcStatus;
    }


    /**
     * Sets the _QcStatus value for this ProposalDetailsBO.
     * 
     * @param _QcStatus
     */
    public void set_QcStatus(java.lang.String _QcStatus) {
        this._QcStatus = _QcStatus;
    }


    /**
     * Gets the _QuoteNo value for this ProposalDetailsBO.
     * 
     * @return _QuoteNo
     */
    public java.lang.String get_QuoteNo() {
        return _QuoteNo;
    }


    /**
     * Sets the _QuoteNo value for this ProposalDetailsBO.
     * 
     * @param _QuoteNo
     */
    public void set_QuoteNo(java.lang.String _QuoteNo) {
        this._QuoteNo = _QuoteNo;
    }


    /**
     * Gets the _QuoteNoExists value for this ProposalDetailsBO.
     * 
     * @return _QuoteNoExists
     */
    public boolean is_QuoteNoExists() {
        return _QuoteNoExists;
    }


    /**
     * Sets the _QuoteNoExists value for this ProposalDetailsBO.
     * 
     * @param _QuoteNoExists
     */
    public void set_QuoteNoExists(boolean _QuoteNoExists) {
        this._QuoteNoExists = _QuoteNoExists;
    }


    /**
     * Gets the _ReasonForChange value for this ProposalDetailsBO.
     * 
     * @return _ReasonForChange
     */
    public java.lang.String get_ReasonForChange() {
        return _ReasonForChange;
    }


    /**
     * Sets the _ReasonForChange value for this ProposalDetailsBO.
     * 
     * @param _ReasonForChange
     */
    public void set_ReasonForChange(java.lang.String _ReasonForChange) {
        this._ReasonForChange = _ReasonForChange;
    }


    /**
     * Gets the _ReceivedDate value for this ProposalDetailsBO.
     * 
     * @return _ReceivedDate
     */
    public java.util.Calendar get_ReceivedDate() {
        return _ReceivedDate;
    }


    /**
     * Sets the _ReceivedDate value for this ProposalDetailsBO.
     * 
     * @param _ReceivedDate
     */
    public void set_ReceivedDate(java.util.Calendar _ReceivedDate) {
        this._ReceivedDate = _ReceivedDate;
    }


    /**
     * Gets the _RegionId value for this ProposalDetailsBO.
     * 
     * @return _RegionId
     */
    public java.lang.String get_RegionId() {
        return _RegionId;
    }


    /**
     * Sets the _RegionId value for this ProposalDetailsBO.
     * 
     * @param _RegionId
     */
    public void set_RegionId(java.lang.String _RegionId) {
        this._RegionId = _RegionId;
    }


    /**
     * Gets the _RegionName value for this ProposalDetailsBO.
     * 
     * @return _RegionName
     */
    public java.lang.String get_RegionName() {
        return _RegionName;
    }


    /**
     * Sets the _RegionName value for this ProposalDetailsBO.
     * 
     * @param _RegionName
     */
    public void set_RegionName(java.lang.String _RegionName) {
        this._RegionName = _RegionName;
    }


    /**
     * Gets the _RegistrationNo value for this ProposalDetailsBO.
     * 
     * @return _RegistrationNo
     */
    public java.lang.String get_RegistrationNo() {
        return _RegistrationNo;
    }


    /**
     * Sets the _RegistrationNo value for this ProposalDetailsBO.
     * 
     * @param _RegistrationNo
     */
    public void set_RegistrationNo(java.lang.String _RegistrationNo) {
        this._RegistrationNo = _RegistrationNo;
    }


    /**
     * Gets the _Remark value for this ProposalDetailsBO.
     * 
     * @return _Remark
     */
    public java.lang.String get_Remark() {
        return _Remark;
    }


    /**
     * Sets the _Remark value for this ProposalDetailsBO.
     * 
     * @param _Remark
     */
    public void set_Remark(java.lang.String _Remark) {
        this._Remark = _Remark;
    }


    /**
     * Gets the _RequiredDocument value for this ProposalDetailsBO.
     * 
     * @return _RequiredDocument
     */
    public app.icm.DocumentMasterBO[] get_RequiredDocument() {
        return _RequiredDocument;
    }


    /**
     * Sets the _RequiredDocument value for this ProposalDetailsBO.
     * 
     * @param _RequiredDocument
     */
    public void set_RequiredDocument(app.icm.DocumentMasterBO[] _RequiredDocument) {
        this._RequiredDocument = _RequiredDocument;
    }


    /**
     * Gets the _RiskStartDate value for this ProposalDetailsBO.
     * 
     * @return _RiskStartDate
     */
    public java.util.Calendar get_RiskStartDate() {
        return _RiskStartDate;
    }


    /**
     * Sets the _RiskStartDate value for this ProposalDetailsBO.
     * 
     * @param _RiskStartDate
     */
    public void set_RiskStartDate(java.util.Calendar _RiskStartDate) {
        this._RiskStartDate = _RiskStartDate;
    }


    /**
     * Gets the _RuleNumber value for this ProposalDetailsBO.
     * 
     * @return _RuleNumber
     */
    public java.lang.String get_RuleNumber() {
        return _RuleNumber;
    }


    /**
     * Sets the _RuleNumber value for this ProposalDetailsBO.
     * 
     * @param _RuleNumber
     */
    public void set_RuleNumber(java.lang.String _RuleNumber) {
        this._RuleNumber = _RuleNumber;
    }


    /**
     * Gets the _SMCode value for this ProposalDetailsBO.
     * 
     * @return _SMCode
     */
    public java.lang.String get_SMCode() {
        return _SMCode;
    }


    /**
     * Sets the _SMCode value for this ProposalDetailsBO.
     * 
     * @param _SMCode
     */
    public void set_SMCode(java.lang.String _SMCode) {
        this._SMCode = _SMCode;
    }


    /**
     * Gets the _SMName value for this ProposalDetailsBO.
     * 
     * @return _SMName
     */
    public java.lang.String get_SMName() {
        return _SMName;
    }


    /**
     * Sets the _SMName value for this ProposalDetailsBO.
     * 
     * @param _SMName
     */
    public void set_SMName(java.lang.String _SMName) {
        this._SMName = _SMName;
    }


    /**
     * Gets the _SearchMode value for this ProposalDetailsBO.
     * 
     * @return _SearchMode
     */
    public boolean is_SearchMode() {
        return _SearchMode;
    }


    /**
     * Sets the _SearchMode value for this ProposalDetailsBO.
     * 
     * @param _SearchMode
     */
    public void set_SearchMode(boolean _SearchMode) {
        this._SearchMode = _SearchMode;
    }


    /**
     * Gets the _ServiceLeviedTax value for this ProposalDetailsBO.
     * 
     * @return _ServiceLeviedTax
     */
    public java.math.BigDecimal get_ServiceLeviedTax() {
        return _ServiceLeviedTax;
    }


    /**
     * Sets the _ServiceLeviedTax value for this ProposalDetailsBO.
     * 
     * @param _ServiceLeviedTax
     */
    public void set_ServiceLeviedTax(java.math.BigDecimal _ServiceLeviedTax) {
        this._ServiceLeviedTax = _ServiceLeviedTax;
    }


    /**
     * Gets the _ServiceStatus value for this ProposalDetailsBO.
     * 
     * @return _ServiceStatus
     */
    public java.lang.String get_ServiceStatus() {
        return _ServiceStatus;
    }


    /**
     * Sets the _ServiceStatus value for this ProposalDetailsBO.
     * 
     * @param _ServiceStatus
     */
    public void set_ServiceStatus(java.lang.String _ServiceStatus) {
        this._ServiceStatus = _ServiceStatus;
    }


    /**
     * Gets the _ServiceTax value for this ProposalDetailsBO.
     * 
     * @return _ServiceTax
     */
    public java.math.BigDecimal get_ServiceTax() {
        return _ServiceTax;
    }


    /**
     * Sets the _ServiceTax value for this ProposalDetailsBO.
     * 
     * @param _ServiceTax
     */
    public void set_ServiceTax(java.math.BigDecimal _ServiceTax) {
        this._ServiceTax = _ServiceTax;
    }


    /**
     * Gets the _State value for this ProposalDetailsBO.
     * 
     * @return _State
     */
    public java.lang.String get_State() {
        return _State;
    }


    /**
     * Sets the _State value for this ProposalDetailsBO.
     * 
     * @param _State
     */
    public void set_State(java.lang.String _State) {
        this._State = _State;
    }


    /**
     * Gets the _StateID value for this ProposalDetailsBO.
     * 
     * @return _StateID
     */
    public int get_StateID() {
        return _StateID;
    }


    /**
     * Sets the _StateID value for this ProposalDetailsBO.
     * 
     * @param _StateID
     */
    public void set_StateID(int _StateID) {
        this._StateID = _StateID;
    }


    /**
     * Gets the _StatusCode value for this ProposalDetailsBO.
     * 
     * @return _StatusCode
     */
    public java.lang.String get_StatusCode() {
        return _StatusCode;
    }


    /**
     * Sets the _StatusCode value for this ProposalDetailsBO.
     * 
     * @param _StatusCode
     */
    public void set_StatusCode(java.lang.String _StatusCode) {
        this._StatusCode = _StatusCode;
    }


    /**
     * Gets the _SurchargeValue value for this ProposalDetailsBO.
     * 
     * @return _SurchargeValue
     */
    public java.math.BigDecimal get_SurchargeValue() {
        return _SurchargeValue;
    }


    /**
     * Sets the _SurchargeValue value for this ProposalDetailsBO.
     * 
     * @param _SurchargeValue
     */
    public void set_SurchargeValue(java.math.BigDecimal _SurchargeValue) {
        this._SurchargeValue = _SurchargeValue;
    }


    /**
     * Gets the _TableName value for this ProposalDetailsBO.
     * 
     * @return _TableName
     */
    public java.lang.String get_TableName() {
        return _TableName;
    }


    /**
     * Sets the _TableName value for this ProposalDetailsBO.
     * 
     * @param _TableName
     */
    public void set_TableName(java.lang.String _TableName) {
        this._TableName = _TableName;
    }


    /**
     * Gets the _TaxExempRemark value for this ProposalDetailsBO.
     * 
     * @return _TaxExempRemark
     */
    public java.lang.String get_TaxExempRemark() {
        return _TaxExempRemark;
    }


    /**
     * Sets the _TaxExempRemark value for this ProposalDetailsBO.
     * 
     * @param _TaxExempRemark
     */
    public void set_TaxExempRemark(java.lang.String _TaxExempRemark) {
        this._TaxExempRemark = _TaxExempRemark;
    }


    /**
     * Gets the _Terrorism value for this ProposalDetailsBO.
     * 
     * @return _Terrorism
     */
    public java.math.BigDecimal get_Terrorism() {
        return _Terrorism;
    }


    /**
     * Sets the _Terrorism value for this ProposalDetailsBO.
     * 
     * @param _Terrorism
     */
    public void set_Terrorism(java.math.BigDecimal _Terrorism) {
        this._Terrorism = _Terrorism;
    }


    /**
     * Gets the _TotalCashAmt value for this ProposalDetailsBO.
     * 
     * @return _TotalCashAmt
     */
    public java.math.BigDecimal get_TotalCashAmt() {
        return _TotalCashAmt;
    }


    /**
     * Sets the _TotalCashAmt value for this ProposalDetailsBO.
     * 
     * @param _TotalCashAmt
     */
    public void set_TotalCashAmt(java.math.BigDecimal _TotalCashAmt) {
        this._TotalCashAmt = _TotalCashAmt;
    }


    /**
     * Gets the _TotalPremium value for this ProposalDetailsBO.
     * 
     * @return _TotalPremium
     */
    public java.math.BigDecimal get_TotalPremium() {
        return _TotalPremium;
    }


    /**
     * Sets the _TotalPremium value for this ProposalDetailsBO.
     * 
     * @param _TotalPremium
     */
    public void set_TotalPremium(java.math.BigDecimal _TotalPremium) {
        this._TotalPremium = _TotalPremium;
    }


    /**
     * Gets the _TransID_FK value for this ProposalDetailsBO.
     * 
     * @return _TransID_FK
     */
    public java.lang.String get_TransID_FK() {
        return _TransID_FK;
    }


    /**
     * Sets the _TransID_FK value for this ProposalDetailsBO.
     * 
     * @param _TransID_FK
     */
    public void set_TransID_FK(java.lang.String _TransID_FK) {
        this._TransID_FK = _TransID_FK;
    }


    /**
     * Gets the _UnderWriterApproved value for this ProposalDetailsBO.
     * 
     * @return _UnderWriterApproved
     */
    public int get_UnderWriterApproved() {
        return _UnderWriterApproved;
    }


    /**
     * Sets the _UnderWriterApproved value for this ProposalDetailsBO.
     * 
     * @param _UnderWriterApproved
     */
    public void set_UnderWriterApproved(int _UnderWriterApproved) {
        this._UnderWriterApproved = _UnderWriterApproved;
    }


    /**
     * Gets the _UserRole value for this ProposalDetailsBO.
     * 
     * @return _UserRole
     */
    public java.lang.String get_UserRole() {
        return _UserRole;
    }


    /**
     * Sets the _UserRole value for this ProposalDetailsBO.
     * 
     * @param _UserRole
     */
    public void set_UserRole(java.lang.String _UserRole) {
        this._UserRole = _UserRole;
    }


    /**
     * Gets the _Value1 value for this ProposalDetailsBO.
     * 
     * @return _Value1
     */
    public java.lang.String get_Value1() {
        return _Value1;
    }


    /**
     * Sets the _Value1 value for this ProposalDetailsBO.
     * 
     * @param _Value1
     */
    public void set_Value1(java.lang.String _Value1) {
        this._Value1 = _Value1;
    }


    /**
     * Gets the _Value2 value for this ProposalDetailsBO.
     * 
     * @return _Value2
     */
    public java.lang.String get_Value2() {
        return _Value2;
    }


    /**
     * Sets the _Value2 value for this ProposalDetailsBO.
     * 
     * @param _Value2
     */
    public void set_Value2(java.lang.String _Value2) {
        this._Value2 = _Value2;
    }


    /**
     * Gets the _Value3 value for this ProposalDetailsBO.
     * 
     * @return _Value3
     */
    public java.lang.String get_Value3() {
        return _Value3;
    }


    /**
     * Sets the _Value3 value for this ProposalDetailsBO.
     * 
     * @param _Value3
     */
    public void set_Value3(java.lang.String _Value3) {
        this._Value3 = _Value3;
    }


    /**
     * Gets the _Variantid_FK value for this ProposalDetailsBO.
     * 
     * @return _Variantid_FK
     */
    public java.lang.Integer get_Variantid_FK() {
        return _Variantid_FK;
    }


    /**
     * Sets the _Variantid_FK value for this ProposalDetailsBO.
     * 
     * @param _Variantid_FK
     */
    public void set_Variantid_FK(java.lang.Integer _Variantid_FK) {
        this._Variantid_FK = _Variantid_FK;
    }


    /**
     * Gets the _VehicalName value for this ProposalDetailsBO.
     * 
     * @return _VehicalName
     */
    public java.lang.String get_VehicalName() {
        return _VehicalName;
    }


    /**
     * Sets the _VehicalName value for this ProposalDetailsBO.
     * 
     * @param _VehicalName
     */
    public void set_VehicalName(java.lang.String _VehicalName) {
        this._VehicalName = _VehicalName;
    }


    /**
     * Gets the _VehicalSubTypeId_FK value for this ProposalDetailsBO.
     * 
     * @return _VehicalSubTypeId_FK
     */
    public int get_VehicalSubTypeId_FK() {
        return _VehicalSubTypeId_FK;
    }


    /**
     * Sets the _VehicalSubTypeId_FK value for this ProposalDetailsBO.
     * 
     * @param _VehicalSubTypeId_FK
     */
    public void set_VehicalSubTypeId_FK(int _VehicalSubTypeId_FK) {
        this._VehicalSubTypeId_FK = _VehicalSubTypeId_FK;
    }


    /**
     * Gets the _VehicalTypeId_FK value for this ProposalDetailsBO.
     * 
     * @return _VehicalTypeId_FK
     */
    public int get_VehicalTypeId_FK() {
        return _VehicalTypeId_FK;
    }


    /**
     * Sets the _VehicalTypeId_FK value for this ProposalDetailsBO.
     * 
     * @param _VehicalTypeId_FK
     */
    public void set_VehicalTypeId_FK(int _VehicalTypeId_FK) {
        this._VehicalTypeId_FK = _VehicalTypeId_FK;
    }


    /**
     * Gets the _VehicalTypeName value for this ProposalDetailsBO.
     * 
     * @return _VehicalTypeName
     */
    public java.lang.String get_VehicalTypeName() {
        return _VehicalTypeName;
    }


    /**
     * Sets the _VehicalTypeName value for this ProposalDetailsBO.
     * 
     * @param _VehicalTypeName
     */
    public void set_VehicalTypeName(java.lang.String _VehicalTypeName) {
        this._VehicalTypeName = _VehicalTypeName;
    }


    /**
     * Gets the _VendorExists value for this ProposalDetailsBO.
     * 
     * @return _VendorExists
     */
    public boolean is_VendorExists() {
        return _VendorExists;
    }


    /**
     * Sets the _VendorExists value for this ProposalDetailsBO.
     * 
     * @param _VendorExists
     */
    public void set_VendorExists(boolean _VendorExists) {
        this._VendorExists = _VendorExists;
    }


    /**
     * Gets the _War value for this ProposalDetailsBO.
     * 
     * @return _War
     */
    public java.math.BigDecimal get_War() {
        return _War;
    }


    /**
     * Sets the _War value for this ProposalDetailsBO.
     * 
     * @param _War
     */
    public void set_War(java.math.BigDecimal _War) {
        this._War = _War;
    }


    /**
     * Gets the _WhereBy value for this ProposalDetailsBO.
     * 
     * @return _WhereBy
     */
    public int get_WhereBy() {
        return _WhereBy;
    }


    /**
     * Sets the _WhereBy value for this ProposalDetailsBO.
     * 
     * @param _WhereBy
     */
    public void set_WhereBy(int _WhereBy) {
        this._WhereBy = _WhereBy;
    }


    /**
     * Gets the _WhereValue value for this ProposalDetailsBO.
     * 
     * @return _WhereValue
     */
    public java.lang.String get_WhereValue() {
        return _WhereValue;
    }


    /**
     * Sets the _WhereValue value for this ProposalDetailsBO.
     * 
     * @param _WhereValue
     */
    public void set_WhereValue(java.lang.String _WhereValue) {
        this._WhereValue = _WhereValue;
    }


    /**
     * Gets the _YearOfManufacture value for this ProposalDetailsBO.
     * 
     * @return _YearOfManufacture
     */
    public java.lang.Integer get_YearOfManufacture() {
        return _YearOfManufacture;
    }


    /**
     * Sets the _YearOfManufacture value for this ProposalDetailsBO.
     * 
     * @param _YearOfManufacture
     */
    public void set_YearOfManufacture(java.lang.Integer _YearOfManufacture) {
        this._YearOfManufacture = _YearOfManufacture;
    }


    /**
     * Gets the _YearOfRegistration value for this ProposalDetailsBO.
     * 
     * @return _YearOfRegistration
     */
    public java.lang.Integer get_YearOfRegistration() {
        return _YearOfRegistration;
    }


    /**
     * Sets the _YearOfRegistration value for this ProposalDetailsBO.
     * 
     * @param _YearOfRegistration
     */
    public void set_YearOfRegistration(java.lang.Integer _YearOfRegistration) {
        this._YearOfRegistration = _YearOfRegistration;
    }


    /**
     * Gets the _branchName value for this ProposalDetailsBO.
     * 
     * @return _branchName
     */
    public java.lang.String get_branchName() {
        return _branchName;
    }


    /**
     * Sets the _branchName value for this ProposalDetailsBO.
     * 
     * @param _branchName
     */
    public void set_branchName(java.lang.String _branchName) {
        this._branchName = _branchName;
    }


    /**
     * Gets the _chequeNo value for this ProposalDetailsBO.
     * 
     * @return _chequeNo
     */
    public java.lang.String get_chequeNo() {
        return _chequeNo;
    }


    /**
     * Sets the _chequeNo value for this ProposalDetailsBO.
     * 
     * @param _chequeNo
     */
    public void set_chequeNo(java.lang.String _chequeNo) {
        this._chequeNo = _chequeNo;
    }


    /**
     * Gets the _className value for this ProposalDetailsBO.
     * 
     * @return _className
     */
    public java.lang.String get_className() {
        return _className;
    }


    /**
     * Sets the _className value for this ProposalDetailsBO.
     * 
     * @param _className
     */
    public void set_className(java.lang.String _className) {
        this._className = _className;
    }


    /**
     * Gets the _discrCategory value for this ProposalDetailsBO.
     * 
     * @return _discrCategory
     */
    public app.icm.DiscrepancyCategoryBO[] get_discrCategory() {
        return _discrCategory;
    }


    /**
     * Sets the _discrCategory value for this ProposalDetailsBO.
     * 
     * @param _discrCategory
     */
    public void set_discrCategory(app.icm.DiscrepancyCategoryBO[] _discrCategory) {
        this._discrCategory = _discrCategory;
    }


    /**
     * Gets the _srcc value for this ProposalDetailsBO.
     * 
     * @return _srcc
     */
    public java.math.BigDecimal get_srcc() {
        return _srcc;
    }


    /**
     * Sets the _srcc value for this ProposalDetailsBO.
     * 
     * @param _srcc
     */
    public void set_srcc(java.math.BigDecimal _srcc) {
        this._srcc = _srcc;
    }


    /**
     * Gets the _stampduty value for this ProposalDetailsBO.
     * 
     * @return _stampduty
     */
    public java.math.BigDecimal get_stampduty() {
        return _stampduty;
    }


    /**
     * Sets the _stampduty value for this ProposalDetailsBO.
     * 
     * @param _stampduty
     */
    public void set_stampduty(java.math.BigDecimal _stampduty) {
        this._stampduty = _stampduty;
    }


    /**
     * Gets the _userName value for this ProposalDetailsBO.
     * 
     * @return _userName
     */
    public java.lang.String get_userName() {
        return _userName;
    }


    /**
     * Sets the _userName value for this ProposalDetailsBO.
     * 
     * @param _userName
     */
    public void set_userName(java.lang.String _userName) {
        this._userName = _userName;
    }


    /**
     * Gets the intRowID value for this ProposalDetailsBO.
     * 
     * @return intRowID
     */
    public int getIntRowID() {
        return intRowID;
    }


    /**
     * Sets the intRowID value for this ProposalDetailsBO.
     * 
     * @param intRowID
     */
    public void setIntRowID(int intRowID) {
        this.intRowID = intRowID;
    }


    /**
     * Gets the intSubDiscrepancyID value for this ProposalDetailsBO.
     * 
     * @return intSubDiscrepancyID
     */
    public int getIntSubDiscrepancyID() {
        return intSubDiscrepancyID;
    }


    /**
     * Sets the intSubDiscrepancyID value for this ProposalDetailsBO.
     * 
     * @param intSubDiscrepancyID
     */
    public void setIntSubDiscrepancyID(int intSubDiscrepancyID) {
        this.intSubDiscrepancyID = intSubDiscrepancyID;
    }


    /**
     * Gets the qcResolvedDate value for this ProposalDetailsBO.
     * 
     * @return qcResolvedDate
     */
    public java.util.Calendar getQcResolvedDate() {
        return qcResolvedDate;
    }


    /**
     * Sets the qcResolvedDate value for this ProposalDetailsBO.
     * 
     * @param qcResolvedDate
     */
    public void setQcResolvedDate(java.util.Calendar qcResolvedDate) {
        this.qcResolvedDate = qcResolvedDate;
    }


    /**
     * Gets the receiveMode value for this ProposalDetailsBO.
     * 
     * @return receiveMode
     */
    public java.lang.String getReceiveMode() {
        return receiveMode;
    }


    /**
     * Sets the receiveMode value for this ProposalDetailsBO.
     * 
     * @param receiveMode
     */
    public void setReceiveMode(java.lang.String receiveMode) {
        this.receiveMode = receiveMode;
    }


    /**
     * Gets the sapCode value for this ProposalDetailsBO.
     * 
     * @return sapCode
     */
    public java.lang.String getSapCode() {
        return sapCode;
    }


    /**
     * Sets the sapCode value for this ProposalDetailsBO.
     * 
     * @param sapCode
     */
    public void setSapCode(java.lang.String sapCode) {
        this.sapCode = sapCode;
    }


    /**
     * Gets the shortPremium value for this ProposalDetailsBO.
     * 
     * @return shortPremium
     */
    public java.math.BigDecimal getShortPremium() {
        return shortPremium;
    }


    /**
     * Sets the shortPremium value for this ProposalDetailsBO.
     * 
     * @param shortPremium
     */
    public void setShortPremium(java.math.BigDecimal shortPremium) {
        this.shortPremium = shortPremium;
    }


    /**
     * Gets the strDiscrepancyName value for this ProposalDetailsBO.
     * 
     * @return strDiscrepancyName
     */
    public java.lang.String getStrDiscrepancyName() {
        return strDiscrepancyName;
    }


    /**
     * Sets the strDiscrepancyName value for this ProposalDetailsBO.
     * 
     * @param strDiscrepancyName
     */
    public void setStrDiscrepancyName(java.lang.String strDiscrepancyName) {
        this.strDiscrepancyName = strDiscrepancyName;
    }


    /**
     * Gets the strRaisedDate value for this ProposalDetailsBO.
     * 
     * @return strRaisedDate
     */
    public java.util.Calendar getStrRaisedDate() {
        return strRaisedDate;
    }


    /**
     * Sets the strRaisedDate value for this ProposalDetailsBO.
     * 
     * @param strRaisedDate
     */
    public void setStrRaisedDate(java.util.Calendar strRaisedDate) {
        this.strRaisedDate = strRaisedDate;
    }


    /**
     * Gets the strResolvedRemark value for this ProposalDetailsBO.
     * 
     * @return strResolvedRemark
     */
    public java.lang.String getStrResolvedRemark() {
        return strResolvedRemark;
    }


    /**
     * Sets the strResolvedRemark value for this ProposalDetailsBO.
     * 
     * @param strResolvedRemark
     */
    public void setStrResolvedRemark(java.lang.String strResolvedRemark) {
        this.strResolvedRemark = strResolvedRemark;
    }


    /**
     * Gets the strSubDiscrepancyName value for this ProposalDetailsBO.
     * 
     * @return strSubDiscrepancyName
     */
    public java.lang.String getStrSubDiscrepancyName() {
        return strSubDiscrepancyName;
    }


    /**
     * Sets the strSubDiscrepancyName value for this ProposalDetailsBO.
     * 
     * @param strSubDiscrepancyName
     */
    public void setStrSubDiscrepancyName(java.lang.String strSubDiscrepancyName) {
        this.strSubDiscrepancyName = strSubDiscrepancyName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProposalDetailsBO)) return false;
        ProposalDetailsBO other = (ProposalDetailsBO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this._Actionable==null && other.get_Actionable()==null) || 
             (this._Actionable!=null &&
              this._Actionable.equals(other.get_Actionable()))) &&
            ((this._Address==null && other.get_Address()==null) || 
             (this._Address!=null &&
              this._Address.equals(other.get_Address()))) &&
            ((this._AgentCode==null && other.get_AgentCode()==null) || 
             (this._AgentCode!=null &&
              this._AgentCode.equals(other.get_AgentCode()))) &&
            ((this._AgentName==null && other.get_AgentName()==null) || 
             (this._AgentName!=null &&
              this._AgentName.equals(other.get_AgentName()))) &&
            ((this._AgentRetentionDetailsDummy==null && other.get_AgentRetentionDetailsDummy()==null) || 
             (this._AgentRetentionDetailsDummy!=null &&
              java.util.Arrays.equals(this._AgentRetentionDetailsDummy, other.get_AgentRetentionDetailsDummy()))) &&
            ((this._AllocatedBy==null && other.get_AllocatedBy()==null) || 
             (this._AllocatedBy!=null &&
              this._AllocatedBy.equals(other.get_AllocatedBy()))) &&
            ((this._AllocatedTo==null && other.get_AllocatedTo()==null) || 
             (this._AllocatedTo!=null &&
              this._AllocatedTo.equals(other.get_AllocatedTo()))) &&
            ((this._AllocationDate==null && other.get_AllocationDate()==null) || 
             (this._AllocationDate!=null &&
              this._AllocationDate.equals(other.get_AllocationDate()))) &&
            this._BASExists == other.is_BASExists() &&
            ((this._BGSAPBankNo==null && other.get_BGSAPBankNo()==null) || 
             (this._BGSAPBankNo!=null &&
              this._BGSAPBankNo.equals(other.get_BGSAPBankNo()))) &&
            ((this._BGSAPCode==null && other.get_BGSAPCode()==null) || 
             (this._BGSAPCode!=null &&
              this._BGSAPCode.equals(other.get_BGSAPCode()))) &&
            ((this._BancaCode==null && other.get_BancaCode()==null) || 
             (this._BancaCode!=null &&
              this._BancaCode.equals(other.get_BancaCode()))) &&
            this._BarcodeExists == other.is_BarcodeExists() &&
            ((this._BasCode==null && other.get_BasCode()==null) || 
             (this._BasCode!=null &&
              this._BasCode.equals(other.get_BasCode()))) &&
            ((this._BasName==null && other.get_BasName()==null) || 
             (this._BasName!=null &&
              this._BasName.equals(other.get_BasName()))) &&
            ((this._BranchCode==null && other.get_BranchCode()==null) || 
             (this._BranchCode!=null &&
              this._BranchCode.equals(other.get_BranchCode()))) &&
            ((this._BranchID==null && other.get_BranchID()==null) || 
             (this._BranchID!=null &&
              this._BranchID.equals(other.get_BranchID()))) &&
            ((this._BusinessType==null && other.get_BusinessType()==null) || 
             (this._BusinessType!=null &&
              this._BusinessType.equals(other.get_BusinessType()))) &&
            ((this._CSOCode==null && other.get_CSOCode()==null) || 
             (this._CSOCode!=null &&
              this._CSOCode.equals(other.get_CSOCode()))) &&
            ((this._CSOName==null && other.get_CSOName()==null) || 
             (this._CSOName!=null &&
              this._CSOName.equals(other.get_CSOName()))) &&
            ((this._CancelledPolicy==null && other.get_CancelledPolicy()==null) || 
             (this._CancelledPolicy!=null &&
              this._CancelledPolicy.equals(other.get_CancelledPolicy()))) &&
            ((this._Cess==null && other.get_Cess()==null) || 
             (this._Cess!=null &&
              this._Cess.equals(other.get_Cess()))) &&
            ((this._ChessisNo==null && other.get_ChessisNo()==null) || 
             (this._ChessisNo!=null &&
              this._ChessisNo.equals(other.get_ChessisNo()))) &&
            this._CityID == other.get_CityID() &&
            ((this._CityName==null && other.get_CityName()==null) || 
             (this._CityName!=null &&
              this._CityName.equals(other.get_CityName()))) &&
            ((this._ClassID==null && other.get_ClassID()==null) || 
             (this._ClassID!=null &&
              this._ClassID.equals(other.get_ClassID()))) &&
            ((this._CoInsuranceShare==null && other.get_CoInsuranceShare()==null) || 
             (this._CoInsuranceShare!=null &&
              this._CoInsuranceShare.equals(other.get_CoInsuranceShare()))) &&
            ((this._Co_InsStatus==null && other.get_Co_InsStatus()==null) || 
             (this._Co_InsStatus!=null &&
              this._Co_InsStatus.equals(other.get_Co_InsStatus()))) &&
            ((this._ContactNumber==null && other.get_ContactNumber()==null) || 
             (this._ContactNumber!=null &&
              this._ContactNumber.equals(other.get_ContactNumber()))) &&
            this._CoverNoteExits == other.is_CoverNoteExits() &&
            ((this._DiscrepancyID_FK==null && other.get_DiscrepancyID_FK()==null) || 
             (this._DiscrepancyID_FK!=null &&
              this._DiscrepancyID_FK.equals(other.get_DiscrepancyID_FK()))) &&
            ((this._District==null && other.get_District()==null) || 
             (this._District!=null &&
              this._District.equals(other.get_District()))) &&
            this._DistrictID == other.get_DistrictID() &&
            ((this._DocumentType==null && other.get_DocumentType()==null) || 
             (this._DocumentType!=null &&
              this._DocumentType.equals(other.get_DocumentType()))) &&
            ((this._ERF==null && other.get_ERF()==null) || 
             (this._ERF!=null &&
              this._ERF.equals(other.get_ERF()))) &&
            ((this._EndorsementCategory==null && other.get_EndorsementCategory()==null) || 
             (this._EndorsementCategory!=null &&
              this._EndorsementCategory.equals(other.get_EndorsementCategory()))) &&
            ((this._EndorsementNo==null && other.get_EndorsementNo()==null) || 
             (this._EndorsementNo!=null &&
              this._EndorsementNo.equals(other.get_EndorsementNo()))) &&
            this._EndorsementReasonID == other.get_EndorsementReasonID() &&
            ((this._EndorsementSubTypeName==null && other.get_EndorsementSubTypeName()==null) || 
             (this._EndorsementSubTypeName!=null &&
              this._EndorsementSubTypeName.equals(other.get_EndorsementSubTypeName()))) &&
            ((this._EndorsementType==null && other.get_EndorsementType()==null) || 
             (this._EndorsementType!=null &&
              this._EndorsementType.equals(other.get_EndorsementType()))) &&
            ((this._EndorsementTypeName==null && other.get_EndorsementTypeName()==null) || 
             (this._EndorsementTypeName!=null &&
              this._EndorsementTypeName.equals(other.get_EndorsementTypeName()))) &&
            ((this._EngineNo==null && other.get_EngineNo()==null) || 
             (this._EngineNo!=null &&
              this._EngineNo.equals(other.get_EngineNo()))) &&
            ((this._ExternalBarcode==null && other.get_ExternalBarcode()==null) || 
             (this._ExternalBarcode!=null &&
              this._ExternalBarcode.equals(other.get_ExternalBarcode()))) &&
            ((this._FirstAllocationDate==null && other.get_FirstAllocationDate()==null) || 
             (this._FirstAllocationDate!=null &&
              this._FirstAllocationDate.equals(other.get_FirstAllocationDate()))) &&
            ((this._FirstName==null && other.get_FirstName()==null) || 
             (this._FirstName!=null &&
              this._FirstName.equals(other.get_FirstName()))) &&
            ((this._GST==null && other.get_GST()==null) || 
             (this._GST!=null &&
              this._GST.equals(other.get_GST()))) &&
            ((this._HandOverTo==null && other.get_HandOverTo()==null) || 
             (this._HandOverTo!=null &&
              this._HandOverTo.equals(other.get_HandOverTo()))) &&
            ((this._HandoverDate==null && other.get_HandoverDate()==null) || 
             (this._HandoverDate!=null &&
              this._HandoverDate.equals(other.get_HandoverDate()))) &&
            ((this._HigherEduCess==null && other.get_HigherEduCess()==null) || 
             (this._HigherEduCess!=null &&
              this._HigherEduCess.equals(other.get_HigherEduCess()))) &&
            ((this._InitiatedBy==null && other.get_InitiatedBy()==null) || 
             (this._InitiatedBy!=null &&
              this._InitiatedBy.equals(other.get_InitiatedBy()))) &&
            ((this._InspectionDateTime==null && other.get_InspectionDateTime()==null) || 
             (this._InspectionDateTime!=null &&
              this._InspectionDateTime.equals(other.get_InspectionDateTime()))) &&
            this._InspectionDocNo == other.get_InspectionDocNo() &&
            this._InspectionDocNoExists == other.is_InspectionDocNoExists() &&
            ((this._InspectionDoneBy==null && other.get_InspectionDoneBy()==null) || 
             (this._InspectionDoneBy!=null &&
              this._InspectionDoneBy.equals(other.get_InspectionDoneBy()))) &&
            ((this._InspectionDoneByName==null && other.get_InspectionDoneByName()==null) || 
             (this._InspectionDoneByName!=null &&
              this._InspectionDoneByName.equals(other.get_InspectionDoneByName()))) &&
            ((this._InsuredName==null && other.get_InsuredName()==null) || 
             (this._InsuredName!=null &&
              this._InsuredName.equals(other.get_InsuredName()))) &&
            ((this._InterRefCode==null && other.get_InterRefCode()==null) || 
             (this._InterRefCode!=null &&
              this._InterRefCode.equals(other.get_InterRefCode()))) &&
            ((this._InterRefName==null && other.get_InterRefName()==null) || 
             (this._InterRefName!=null &&
              this._InterRefName.equals(other.get_InterRefName()))) &&
            ((this._IntermediaryRefCode==null && other.get_IntermediaryRefCode()==null) || 
             (this._IntermediaryRefCode!=null &&
              this._IntermediaryRefCode.equals(other.get_IntermediaryRefCode()))) &&
            ((this._IntermediaryRefName==null && other.get_IntermediaryRefName()==null) || 
             (this._IntermediaryRefName!=null &&
              this._IntermediaryRefName.equals(other.get_IntermediaryRefName()))) &&
            ((this._InternalBarcode==null && other.get_InternalBarcode()==null) || 
             (this._InternalBarcode!=null &&
              this._InternalBarcode.equals(other.get_InternalBarcode()))) &&
            ((this._InwardedBy==null && other.get_InwardedBy()==null) || 
             (this._InwardedBy!=null &&
              this._InwardedBy.equals(other.get_InwardedBy()))) &&
            ((this._InwardedDateTime==null && other.get_InwardedDateTime()==null) || 
             (this._InwardedDateTime!=null &&
              this._InwardedDateTime.equals(other.get_InwardedDateTime()))) &&
            this._IsChequeCleared == other.get_IsChequeCleared() &&
            this._IsCoverNoteActive == other.is_IsCoverNoteActive() &&
            this._IsDeleted == other.is_IsDeleted() &&
            this._IsDiscrepancyLog == other.get_IsDiscrepancyLog() &&
            this._IsEditable == other.is_IsEditable() &&
            this._IsEverJumping == other.is_IsEverJumping() &&
            this._IsInwarded == other.is_IsInwarded() &&
            this._IsMappedStatus == other.is_IsMappedStatus() &&
            this._IsNCB == other.is_IsNCB() &&
            this._IsPDC == other.get_IsPDC() &&
            this._IsPassedForPolicyGeneration == other.is_IsPassedForPolicyGeneration() &&
            this._IsPaymentDone == other.is_IsPaymentDone() &&
            this._IsPolicyGeneratedInICM == other.get_IsPolicyGeneratedInICM() &&
            ((this._IsPostDateCheque==null && other.get_IsPostDateCheque()==null) || 
             (this._IsPostDateCheque!=null &&
              this._IsPostDateCheque.equals(other.get_IsPostDateCheque()))) &&
            this._IsQCDoneBSM == other.is_IsQCDoneBSM() &&
            this._IsQCOk == other.is_IsQCOk() &&
            this._IsReissue == other.is_IsReissue() &&
            this._Isjumping == other.is_Isjumping() &&
            ((this._IssueDate==null && other.get_IssueDate()==null) || 
             (this._IssueDate!=null &&
              this._IssueDate.equals(other.get_IssueDate()))) &&
            ((this._JobAllocatedBy==null && other.get_JobAllocatedBy()==null) || 
             (this._JobAllocatedBy!=null &&
              this._JobAllocatedBy.equals(other.get_JobAllocatedBy()))) &&
            ((this._JobAllocatedTo==null && other.get_JobAllocatedTo()==null) || 
             (this._JobAllocatedTo!=null &&
              this._JobAllocatedTo.equals(other.get_JobAllocatedTo()))) &&
            ((this._JobAllocationDate==null && other.get_JobAllocationDate()==null) || 
             (this._JobAllocationDate!=null &&
              this._JobAllocationDate.equals(other.get_JobAllocationDate()))) &&
            ((this._JumpingStatusChangedDate==null && other.get_JumpingStatusChangedDate()==null) || 
             (this._JumpingStatusChangedDate!=null &&
              this._JumpingStatusChangedDate.equals(other.get_JumpingStatusChangedDate()))) &&
            this._KeyField1 == other.get_KeyField1() &&
            this._KeyField2 == other.get_KeyField2() &&
            this._KeyField3 == other.get_KeyField3() &&
            ((this._LastName==null && other.get_LastName()==null) || 
             (this._LastName!=null &&
              this._LastName.equals(other.get_LastName()))) &&
            ((this._LeadNo==null && other.get_LeadNo()==null) || 
             (this._LeadNo!=null &&
              this._LeadNo.equals(other.get_LeadNo()))) &&
            this._LeadNoExists == other.is_LeadNoExists() &&
            this._MakeId_FK == other.get_MakeId_FK() &&
            ((this._MakeName==null && other.get_MakeName()==null) || 
             (this._MakeName!=null &&
              this._MakeName.equals(other.get_MakeName()))) &&
            ((this._MarinePremium==null && other.get_MarinePremium()==null) || 
             (this._MarinePremium!=null &&
              this._MarinePremium.equals(other.get_MarinePremium()))) &&
            ((this._MaxDiscountallowed==null && other.get_MaxDiscountallowed()==null) || 
             (this._MaxDiscountallowed!=null &&
              this._MaxDiscountallowed.equals(other.get_MaxDiscountallowed()))) &&
            ((this._MaxInstrDate==null && other.get_MaxInstrDate()==null) || 
             (this._MaxInstrDate!=null &&
              this._MaxInstrDate.equals(other.get_MaxInstrDate()))) &&
            ((this._Message==null && other.get_Message()==null) || 
             (this._Message!=null &&
              this._Message.equals(other.get_Message()))) &&
            ((this._MiddleName==null && other.get_MiddleName()==null) || 
             (this._MiddleName!=null &&
              this._MiddleName.equals(other.get_MiddleName()))) &&
            this._ModelId_FK == other.get_ModelId_FK() &&
            ((this._ModelVariant==null && other.get_ModelVariant()==null) || 
             (this._ModelVariant!=null &&
              this._ModelVariant.equals(other.get_ModelVariant()))) &&
            ((this._NetOD==null && other.get_NetOD()==null) || 
             (this._NetOD!=null &&
              this._NetOD.equals(other.get_NetOD()))) &&
            this._NetPremium == other.get_NetPremium() &&
            ((this._Nettp==null && other.get_Nettp()==null) || 
             (this._Nettp!=null &&
              this._Nettp.equals(other.get_Nettp()))) &&
            ((this._NewAgentCode==null && other.get_NewAgentCode()==null) || 
             (this._NewAgentCode!=null &&
              this._NewAgentCode.equals(other.get_NewAgentCode()))) &&
            ((this._NewBASCode==null && other.get_NewBASCode()==null) || 
             (this._NewBASCode!=null &&
              this._NewBASCode.equals(other.get_NewBASCode()))) &&
            ((this._NewIntermediaryRefCode==null && other.get_NewIntermediaryRefCode()==null) || 
             (this._NewIntermediaryRefCode!=null &&
              this._NewIntermediaryRefCode.equals(other.get_NewIntermediaryRefCode()))) &&
            ((this._NewSMCode==null && other.get_NewSMCode()==null) || 
             (this._NewSMCode!=null &&
              this._NewSMCode.equals(other.get_NewSMCode()))) &&
            ((this._OldAgentCode==null && other.get_OldAgentCode()==null) || 
             (this._OldAgentCode!=null &&
              this._OldAgentCode.equals(other.get_OldAgentCode()))) &&
            ((this._OldBASCode==null && other.get_OldBASCode()==null) || 
             (this._OldBASCode!=null &&
              this._OldBASCode.equals(other.get_OldBASCode()))) &&
            ((this._OldIntermediaryRefCode==null && other.get_OldIntermediaryRefCode()==null) || 
             (this._OldIntermediaryRefCode!=null &&
              this._OldIntermediaryRefCode.equals(other.get_OldIntermediaryRefCode()))) &&
            ((this._PanNo==null && other.get_PanNo()==null) || 
             (this._PanNo!=null &&
              this._PanNo.equals(other.get_PanNo()))) &&
            ((this._PassportNo==null && other.get_PassportNo()==null) || 
             (this._PassportNo!=null &&
              this._PassportNo.equals(other.get_PassportNo()))) &&
            ((this._PaymentMode==null && other.get_PaymentMode()==null) || 
             (this._PaymentMode!=null &&
              this._PaymentMode.equals(other.get_PaymentMode()))) &&
            ((this._PendingPolicyBranch==null && other.get_PendingPolicyBranch()==null) || 
             (this._PendingPolicyBranch!=null &&
              this._PendingPolicyBranch.equals(other.get_PendingPolicyBranch()))) &&
            ((this._PinCode==null && other.get_PinCode()==null) || 
             (this._PinCode!=null &&
              this._PinCode.equals(other.get_PinCode()))) &&
            ((this._PlanName==null && other.get_PlanName()==null) || 
             (this._PlanName!=null &&
              this._PlanName.equals(other.get_PlanName()))) &&
            ((this._Planid_FK==null && other.get_Planid_FK()==null) || 
             (this._Planid_FK!=null &&
              this._Planid_FK.equals(other.get_Planid_FK()))) &&
            ((this._PolicyEndDate==null && other.get_PolicyEndDate()==null) || 
             (this._PolicyEndDate!=null &&
              this._PolicyEndDate.equals(other.get_PolicyEndDate()))) &&
            ((this._PolicyGenDate==null && other.get_PolicyGenDate()==null) || 
             (this._PolicyGenDate!=null &&
              this._PolicyGenDate.equals(other.get_PolicyGenDate()))) &&
            ((this._PolicyNo_FK==null && other.get_PolicyNo_FK()==null) || 
             (this._PolicyNo_FK!=null &&
              this._PolicyNo_FK.equals(other.get_PolicyNo_FK()))) &&
            this._PolicyStatus == other.is_PolicyStatus() &&
            ((this._Prefix==null && other.get_Prefix()==null) || 
             (this._Prefix!=null &&
              this._Prefix.equals(other.get_Prefix()))) &&
            ((this._Premium==null && other.get_Premium()==null) || 
             (this._Premium!=null &&
              this._Premium.equals(other.get_Premium()))) &&
            ((this._PrevEndorsementStatus==null && other.get_PrevEndorsementStatus()==null) || 
             (this._PrevEndorsementStatus!=null &&
              this._PrevEndorsementStatus.equals(other.get_PrevEndorsementStatus()))) &&
            ((this._PreviousInsurerID==null && other.get_PreviousInsurerID()==null) || 
             (this._PreviousInsurerID!=null &&
              this._PreviousInsurerID.equals(other.get_PreviousInsurerID()))) &&
            ((this._PreviousPolicyEndDate==null && other.get_PreviousPolicyEndDate()==null) || 
             (this._PreviousPolicyEndDate!=null &&
              this._PreviousPolicyEndDate.equals(other.get_PreviousPolicyEndDate()))) &&
            ((this._PreviousPolicyNo==null && other.get_PreviousPolicyNo()==null) || 
             (this._PreviousPolicyNo!=null &&
              this._PreviousPolicyNo.equals(other.get_PreviousPolicyNo()))) &&
            this._PreviousPolicyProcessedIn == other.get_PreviousPolicyProcessedIn() &&
            ((this._PreviousPolicyProductCode==null && other.get_PreviousPolicyProductCode()==null) || 
             (this._PreviousPolicyProductCode!=null &&
              this._PreviousPolicyProductCode.equals(other.get_PreviousPolicyProductCode()))) &&
            this._ProcessedAt == other.get_ProcessedAt() &&
            ((this._ProcessedAtName==null && other.get_ProcessedAtName()==null) || 
             (this._ProcessedAtName!=null &&
              this._ProcessedAtName.equals(other.get_ProcessedAtName()))) &&
            this._ProcessedIn == other.get_ProcessedIn() &&
            ((this._ProcessedInName==null && other.get_ProcessedInName()==null) || 
             (this._ProcessedInName!=null &&
              this._ProcessedInName.equals(other.get_ProcessedInName()))) &&
            ((this._Product==null && other.get_Product()==null) || 
             (this._Product!=null &&
              this._Product.equals(other.get_Product()))) &&
            ((this._ProductCode==null && other.get_ProductCode()==null) || 
             (this._ProductCode!=null &&
              this._ProductCode.equals(other.get_ProductCode()))) &&
            ((this._ProductName==null && other.get_ProductName()==null) || 
             (this._ProductName!=null &&
              this._ProductName.equals(other.get_ProductName()))) &&
            ((this._ProductType==null && other.get_ProductType()==null) || 
             (this._ProductType!=null &&
              this._ProductType.equals(other.get_ProductType()))) &&
            ((this._ProposalAmount==null && other.get_ProposalAmount()==null) || 
             (this._ProposalAmount!=null &&
              this._ProposalAmount.equals(other.get_ProposalAmount()))) &&
            ((this._ProposalClass==null && other.get_ProposalClass()==null) || 
             (this._ProposalClass!=null &&
              this._ProposalClass.equals(other.get_ProposalClass()))) &&
            ((this._ProposalID_FK==null && other.get_ProposalID_FK()==null) || 
             (this._ProposalID_FK!=null &&
              this._ProposalID_FK.equals(other.get_ProposalID_FK()))) &&
            ((this._ProposalOrEndorDate==null && other.get_ProposalOrEndorDate()==null) || 
             (this._ProposalOrEndorDate!=null &&
              this._ProposalOrEndorDate.equals(other.get_ProposalOrEndorDate()))) &&
            this._ProposalStatus == other.get_ProposalStatus() &&
            this._ProposalStatusID == other.get_ProposalStatusID() &&
            ((this._QCDone_By==null && other.get_QCDone_By()==null) || 
             (this._QCDone_By!=null &&
              this._QCDone_By.equals(other.get_QCDone_By()))) &&
            ((this._QCDone_Date==null && other.get_QCDone_Date()==null) || 
             (this._QCDone_Date!=null &&
              this._QCDone_Date.equals(other.get_QCDone_Date()))) &&
            ((this._QCRemark==null && other.get_QCRemark()==null) || 
             (this._QCRemark!=null &&
              this._QCRemark.equals(other.get_QCRemark()))) &&
            ((this._QcStatus==null && other.get_QcStatus()==null) || 
             (this._QcStatus!=null &&
              this._QcStatus.equals(other.get_QcStatus()))) &&
            ((this._QuoteNo==null && other.get_QuoteNo()==null) || 
             (this._QuoteNo!=null &&
              this._QuoteNo.equals(other.get_QuoteNo()))) &&
            this._QuoteNoExists == other.is_QuoteNoExists() &&
            ((this._ReasonForChange==null && other.get_ReasonForChange()==null) || 
             (this._ReasonForChange!=null &&
              this._ReasonForChange.equals(other.get_ReasonForChange()))) &&
            ((this._ReceivedDate==null && other.get_ReceivedDate()==null) || 
             (this._ReceivedDate!=null &&
              this._ReceivedDate.equals(other.get_ReceivedDate()))) &&
            ((this._RegionId==null && other.get_RegionId()==null) || 
             (this._RegionId!=null &&
              this._RegionId.equals(other.get_RegionId()))) &&
            ((this._RegionName==null && other.get_RegionName()==null) || 
             (this._RegionName!=null &&
              this._RegionName.equals(other.get_RegionName()))) &&
            ((this._RegistrationNo==null && other.get_RegistrationNo()==null) || 
             (this._RegistrationNo!=null &&
              this._RegistrationNo.equals(other.get_RegistrationNo()))) &&
            ((this._Remark==null && other.get_Remark()==null) || 
             (this._Remark!=null &&
              this._Remark.equals(other.get_Remark()))) &&
            ((this._RequiredDocument==null && other.get_RequiredDocument()==null) || 
             (this._RequiredDocument!=null &&
              java.util.Arrays.equals(this._RequiredDocument, other.get_RequiredDocument()))) &&
            ((this._RiskStartDate==null && other.get_RiskStartDate()==null) || 
             (this._RiskStartDate!=null &&
              this._RiskStartDate.equals(other.get_RiskStartDate()))) &&
            ((this._RuleNumber==null && other.get_RuleNumber()==null) || 
             (this._RuleNumber!=null &&
              this._RuleNumber.equals(other.get_RuleNumber()))) &&
            ((this._SMCode==null && other.get_SMCode()==null) || 
             (this._SMCode!=null &&
              this._SMCode.equals(other.get_SMCode()))) &&
            ((this._SMName==null && other.get_SMName()==null) || 
             (this._SMName!=null &&
              this._SMName.equals(other.get_SMName()))) &&
            this._SearchMode == other.is_SearchMode() &&
            ((this._ServiceLeviedTax==null && other.get_ServiceLeviedTax()==null) || 
             (this._ServiceLeviedTax!=null &&
              this._ServiceLeviedTax.equals(other.get_ServiceLeviedTax()))) &&
            ((this._ServiceStatus==null && other.get_ServiceStatus()==null) || 
             (this._ServiceStatus!=null &&
              this._ServiceStatus.equals(other.get_ServiceStatus()))) &&
            ((this._ServiceTax==null && other.get_ServiceTax()==null) || 
             (this._ServiceTax!=null &&
              this._ServiceTax.equals(other.get_ServiceTax()))) &&
            ((this._State==null && other.get_State()==null) || 
             (this._State!=null &&
              this._State.equals(other.get_State()))) &&
            this._StateID == other.get_StateID() &&
            ((this._StatusCode==null && other.get_StatusCode()==null) || 
             (this._StatusCode!=null &&
              this._StatusCode.equals(other.get_StatusCode()))) &&
            ((this._SurchargeValue==null && other.get_SurchargeValue()==null) || 
             (this._SurchargeValue!=null &&
              this._SurchargeValue.equals(other.get_SurchargeValue()))) &&
            ((this._TableName==null && other.get_TableName()==null) || 
             (this._TableName!=null &&
              this._TableName.equals(other.get_TableName()))) &&
            ((this._TaxExempRemark==null && other.get_TaxExempRemark()==null) || 
             (this._TaxExempRemark!=null &&
              this._TaxExempRemark.equals(other.get_TaxExempRemark()))) &&
            ((this._Terrorism==null && other.get_Terrorism()==null) || 
             (this._Terrorism!=null &&
              this._Terrorism.equals(other.get_Terrorism()))) &&
            ((this._TotalCashAmt==null && other.get_TotalCashAmt()==null) || 
             (this._TotalCashAmt!=null &&
              this._TotalCashAmt.equals(other.get_TotalCashAmt()))) &&
            ((this._TotalPremium==null && other.get_TotalPremium()==null) || 
             (this._TotalPremium!=null &&
              this._TotalPremium.equals(other.get_TotalPremium()))) &&
            ((this._TransID_FK==null && other.get_TransID_FK()==null) || 
             (this._TransID_FK!=null &&
              this._TransID_FK.equals(other.get_TransID_FK()))) &&
            this._UnderWriterApproved == other.get_UnderWriterApproved() &&
            ((this._UserRole==null && other.get_UserRole()==null) || 
             (this._UserRole!=null &&
              this._UserRole.equals(other.get_UserRole()))) &&
            ((this._Value1==null && other.get_Value1()==null) || 
             (this._Value1!=null &&
              this._Value1.equals(other.get_Value1()))) &&
            ((this._Value2==null && other.get_Value2()==null) || 
             (this._Value2!=null &&
              this._Value2.equals(other.get_Value2()))) &&
            ((this._Value3==null && other.get_Value3()==null) || 
             (this._Value3!=null &&
              this._Value3.equals(other.get_Value3()))) &&
            ((this._Variantid_FK==null && other.get_Variantid_FK()==null) || 
             (this._Variantid_FK!=null &&
              this._Variantid_FK.equals(other.get_Variantid_FK()))) &&
            ((this._VehicalName==null && other.get_VehicalName()==null) || 
             (this._VehicalName!=null &&
              this._VehicalName.equals(other.get_VehicalName()))) &&
            this._VehicalSubTypeId_FK == other.get_VehicalSubTypeId_FK() &&
            this._VehicalTypeId_FK == other.get_VehicalTypeId_FK() &&
            ((this._VehicalTypeName==null && other.get_VehicalTypeName()==null) || 
             (this._VehicalTypeName!=null &&
              this._VehicalTypeName.equals(other.get_VehicalTypeName()))) &&
            this._VendorExists == other.is_VendorExists() &&
            ((this._War==null && other.get_War()==null) || 
             (this._War!=null &&
              this._War.equals(other.get_War()))) &&
            this._WhereBy == other.get_WhereBy() &&
            ((this._WhereValue==null && other.get_WhereValue()==null) || 
             (this._WhereValue!=null &&
              this._WhereValue.equals(other.get_WhereValue()))) &&
            ((this._YearOfManufacture==null && other.get_YearOfManufacture()==null) || 
             (this._YearOfManufacture!=null &&
              this._YearOfManufacture.equals(other.get_YearOfManufacture()))) &&
            ((this._YearOfRegistration==null && other.get_YearOfRegistration()==null) || 
             (this._YearOfRegistration!=null &&
              this._YearOfRegistration.equals(other.get_YearOfRegistration()))) &&
            ((this._branchName==null && other.get_branchName()==null) || 
             (this._branchName!=null &&
              this._branchName.equals(other.get_branchName()))) &&
            ((this._chequeNo==null && other.get_chequeNo()==null) || 
             (this._chequeNo!=null &&
              this._chequeNo.equals(other.get_chequeNo()))) &&
            ((this._className==null && other.get_className()==null) || 
             (this._className!=null &&
              this._className.equals(other.get_className()))) &&
            ((this._discrCategory==null && other.get_discrCategory()==null) || 
             (this._discrCategory!=null &&
              java.util.Arrays.equals(this._discrCategory, other.get_discrCategory()))) &&
            ((this._srcc==null && other.get_srcc()==null) || 
             (this._srcc!=null &&
              this._srcc.equals(other.get_srcc()))) &&
            ((this._stampduty==null && other.get_stampduty()==null) || 
             (this._stampduty!=null &&
              this._stampduty.equals(other.get_stampduty()))) &&
            ((this._userName==null && other.get_userName()==null) || 
             (this._userName!=null &&
              this._userName.equals(other.get_userName()))) &&
            this.intRowID == other.getIntRowID() &&
            this.intSubDiscrepancyID == other.getIntSubDiscrepancyID() &&
            ((this.qcResolvedDate==null && other.getQcResolvedDate()==null) || 
             (this.qcResolvedDate!=null &&
              this.qcResolvedDate.equals(other.getQcResolvedDate()))) &&
            ((this.receiveMode==null && other.getReceiveMode()==null) || 
             (this.receiveMode!=null &&
              this.receiveMode.equals(other.getReceiveMode()))) &&
            ((this.sapCode==null && other.getSapCode()==null) || 
             (this.sapCode!=null &&
              this.sapCode.equals(other.getSapCode()))) &&
            ((this.shortPremium==null && other.getShortPremium()==null) || 
             (this.shortPremium!=null &&
              this.shortPremium.equals(other.getShortPremium()))) &&
            ((this.strDiscrepancyName==null && other.getStrDiscrepancyName()==null) || 
             (this.strDiscrepancyName!=null &&
              this.strDiscrepancyName.equals(other.getStrDiscrepancyName()))) &&
            ((this.strRaisedDate==null && other.getStrRaisedDate()==null) || 
             (this.strRaisedDate!=null &&
              this.strRaisedDate.equals(other.getStrRaisedDate()))) &&
            ((this.strResolvedRemark==null && other.getStrResolvedRemark()==null) || 
             (this.strResolvedRemark!=null &&
              this.strResolvedRemark.equals(other.getStrResolvedRemark()))) &&
            ((this.strSubDiscrepancyName==null && other.getStrSubDiscrepancyName()==null) || 
             (this.strSubDiscrepancyName!=null &&
              this.strSubDiscrepancyName.equals(other.getStrSubDiscrepancyName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (get_Actionable() != null) {
            _hashCode += get_Actionable().hashCode();
        }
        if (get_Address() != null) {
            _hashCode += get_Address().hashCode();
        }
        if (get_AgentCode() != null) {
            _hashCode += get_AgentCode().hashCode();
        }
        if (get_AgentName() != null) {
            _hashCode += get_AgentName().hashCode();
        }
        if (get_AgentRetentionDetailsDummy() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_AgentRetentionDetailsDummy());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_AgentRetentionDetailsDummy(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (get_AllocatedBy() != null) {
            _hashCode += get_AllocatedBy().hashCode();
        }
        if (get_AllocatedTo() != null) {
            _hashCode += get_AllocatedTo().hashCode();
        }
        if (get_AllocationDate() != null) {
            _hashCode += get_AllocationDate().hashCode();
        }
        _hashCode += (is_BASExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_BGSAPBankNo() != null) {
            _hashCode += get_BGSAPBankNo().hashCode();
        }
        if (get_BGSAPCode() != null) {
            _hashCode += get_BGSAPCode().hashCode();
        }
        if (get_BancaCode() != null) {
            _hashCode += get_BancaCode().hashCode();
        }
        _hashCode += (is_BarcodeExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_BasCode() != null) {
            _hashCode += get_BasCode().hashCode();
        }
        if (get_BasName() != null) {
            _hashCode += get_BasName().hashCode();
        }
        if (get_BranchCode() != null) {
            _hashCode += get_BranchCode().hashCode();
        }
        if (get_BranchID() != null) {
            _hashCode += get_BranchID().hashCode();
        }
        if (get_BusinessType() != null) {
            _hashCode += get_BusinessType().hashCode();
        }
        if (get_CSOCode() != null) {
            _hashCode += get_CSOCode().hashCode();
        }
        if (get_CSOName() != null) {
            _hashCode += get_CSOName().hashCode();
        }
        if (get_CancelledPolicy() != null) {
            _hashCode += get_CancelledPolicy().hashCode();
        }
        if (get_Cess() != null) {
            _hashCode += get_Cess().hashCode();
        }
        if (get_ChessisNo() != null) {
            _hashCode += get_ChessisNo().hashCode();
        }
        _hashCode += get_CityID();
        if (get_CityName() != null) {
            _hashCode += get_CityName().hashCode();
        }
        if (get_ClassID() != null) {
            _hashCode += get_ClassID().hashCode();
        }
        if (get_CoInsuranceShare() != null) {
            _hashCode += get_CoInsuranceShare().hashCode();
        }
        if (get_Co_InsStatus() != null) {
            _hashCode += get_Co_InsStatus().hashCode();
        }
        if (get_ContactNumber() != null) {
            _hashCode += get_ContactNumber().hashCode();
        }
        _hashCode += (is_CoverNoteExits() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_DiscrepancyID_FK() != null) {
            _hashCode += get_DiscrepancyID_FK().hashCode();
        }
        if (get_District() != null) {
            _hashCode += get_District().hashCode();
        }
        _hashCode += get_DistrictID();
        if (get_DocumentType() != null) {
            _hashCode += get_DocumentType().hashCode();
        }
        if (get_ERF() != null) {
            _hashCode += get_ERF().hashCode();
        }
        if (get_EndorsementCategory() != null) {
            _hashCode += get_EndorsementCategory().hashCode();
        }
        if (get_EndorsementNo() != null) {
            _hashCode += get_EndorsementNo().hashCode();
        }
        _hashCode += get_EndorsementReasonID();
        if (get_EndorsementSubTypeName() != null) {
            _hashCode += get_EndorsementSubTypeName().hashCode();
        }
        if (get_EndorsementType() != null) {
            _hashCode += get_EndorsementType().hashCode();
        }
        if (get_EndorsementTypeName() != null) {
            _hashCode += get_EndorsementTypeName().hashCode();
        }
        if (get_EngineNo() != null) {
            _hashCode += get_EngineNo().hashCode();
        }
        if (get_ExternalBarcode() != null) {
            _hashCode += get_ExternalBarcode().hashCode();
        }
        if (get_FirstAllocationDate() != null) {
            _hashCode += get_FirstAllocationDate().hashCode();
        }
        if (get_FirstName() != null) {
            _hashCode += get_FirstName().hashCode();
        }
        if (get_GST() != null) {
            _hashCode += get_GST().hashCode();
        }
        if (get_HandOverTo() != null) {
            _hashCode += get_HandOverTo().hashCode();
        }
        if (get_HandoverDate() != null) {
            _hashCode += get_HandoverDate().hashCode();
        }
        if (get_HigherEduCess() != null) {
            _hashCode += get_HigherEduCess().hashCode();
        }
        if (get_InitiatedBy() != null) {
            _hashCode += get_InitiatedBy().hashCode();
        }
        if (get_InspectionDateTime() != null) {
            _hashCode += get_InspectionDateTime().hashCode();
        }
        _hashCode += get_InspectionDocNo();
        _hashCode += (is_InspectionDocNoExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_InspectionDoneBy() != null) {
            _hashCode += get_InspectionDoneBy().hashCode();
        }
        if (get_InspectionDoneByName() != null) {
            _hashCode += get_InspectionDoneByName().hashCode();
        }
        if (get_InsuredName() != null) {
            _hashCode += get_InsuredName().hashCode();
        }
        if (get_InterRefCode() != null) {
            _hashCode += get_InterRefCode().hashCode();
        }
        if (get_InterRefName() != null) {
            _hashCode += get_InterRefName().hashCode();
        }
        if (get_IntermediaryRefCode() != null) {
            _hashCode += get_IntermediaryRefCode().hashCode();
        }
        if (get_IntermediaryRefName() != null) {
            _hashCode += get_IntermediaryRefName().hashCode();
        }
        if (get_InternalBarcode() != null) {
            _hashCode += get_InternalBarcode().hashCode();
        }
        if (get_InwardedBy() != null) {
            _hashCode += get_InwardedBy().hashCode();
        }
        if (get_InwardedDateTime() != null) {
            _hashCode += get_InwardedDateTime().hashCode();
        }
        _hashCode += get_IsChequeCleared();
        _hashCode += (is_IsCoverNoteActive() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsDeleted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += get_IsDiscrepancyLog();
        _hashCode += (is_IsEditable() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsEverJumping() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsInwarded() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsMappedStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsNCB() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += get_IsPDC();
        _hashCode += (is_IsPassedForPolicyGeneration() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsPaymentDone() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += get_IsPolicyGeneratedInICM();
        if (get_IsPostDateCheque() != null) {
            _hashCode += get_IsPostDateCheque().hashCode();
        }
        _hashCode += (is_IsQCDoneBSM() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsQCOk() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_IsReissue() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (is_Isjumping() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_IssueDate() != null) {
            _hashCode += get_IssueDate().hashCode();
        }
        if (get_JobAllocatedBy() != null) {
            _hashCode += get_JobAllocatedBy().hashCode();
        }
        if (get_JobAllocatedTo() != null) {
            _hashCode += get_JobAllocatedTo().hashCode();
        }
        if (get_JobAllocationDate() != null) {
            _hashCode += get_JobAllocationDate().hashCode();
        }
        if (get_JumpingStatusChangedDate() != null) {
            _hashCode += get_JumpingStatusChangedDate().hashCode();
        }
        _hashCode += get_KeyField1();
        _hashCode += get_KeyField2();
        _hashCode += get_KeyField3();
        if (get_LastName() != null) {
            _hashCode += get_LastName().hashCode();
        }
        if (get_LeadNo() != null) {
            _hashCode += get_LeadNo().hashCode();
        }
        _hashCode += (is_LeadNoExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += get_MakeId_FK();
        if (get_MakeName() != null) {
            _hashCode += get_MakeName().hashCode();
        }
        if (get_MarinePremium() != null) {
            _hashCode += get_MarinePremium().hashCode();
        }
        if (get_MaxDiscountallowed() != null) {
            _hashCode += get_MaxDiscountallowed().hashCode();
        }
        if (get_MaxInstrDate() != null) {
            _hashCode += get_MaxInstrDate().hashCode();
        }
        if (get_Message() != null) {
            _hashCode += get_Message().hashCode();
        }
        if (get_MiddleName() != null) {
            _hashCode += get_MiddleName().hashCode();
        }
        _hashCode += get_ModelId_FK();
        if (get_ModelVariant() != null) {
            _hashCode += get_ModelVariant().hashCode();
        }
        if (get_NetOD() != null) {
            _hashCode += get_NetOD().hashCode();
        }
        _hashCode += new Float(get_NetPremium()).hashCode();
        if (get_Nettp() != null) {
            _hashCode += get_Nettp().hashCode();
        }
        if (get_NewAgentCode() != null) {
            _hashCode += get_NewAgentCode().hashCode();
        }
        if (get_NewBASCode() != null) {
            _hashCode += get_NewBASCode().hashCode();
        }
        if (get_NewIntermediaryRefCode() != null) {
            _hashCode += get_NewIntermediaryRefCode().hashCode();
        }
        if (get_NewSMCode() != null) {
            _hashCode += get_NewSMCode().hashCode();
        }
        if (get_OldAgentCode() != null) {
            _hashCode += get_OldAgentCode().hashCode();
        }
        if (get_OldBASCode() != null) {
            _hashCode += get_OldBASCode().hashCode();
        }
        if (get_OldIntermediaryRefCode() != null) {
            _hashCode += get_OldIntermediaryRefCode().hashCode();
        }
        if (get_PanNo() != null) {
            _hashCode += get_PanNo().hashCode();
        }
        if (get_PassportNo() != null) {
            _hashCode += get_PassportNo().hashCode();
        }
        if (get_PaymentMode() != null) {
            _hashCode += get_PaymentMode().hashCode();
        }
        if (get_PendingPolicyBranch() != null) {
            _hashCode += get_PendingPolicyBranch().hashCode();
        }
        if (get_PinCode() != null) {
            _hashCode += get_PinCode().hashCode();
        }
        if (get_PlanName() != null) {
            _hashCode += get_PlanName().hashCode();
        }
        if (get_Planid_FK() != null) {
            _hashCode += get_Planid_FK().hashCode();
        }
        if (get_PolicyEndDate() != null) {
            _hashCode += get_PolicyEndDate().hashCode();
        }
        if (get_PolicyGenDate() != null) {
            _hashCode += get_PolicyGenDate().hashCode();
        }
        if (get_PolicyNo_FK() != null) {
            _hashCode += get_PolicyNo_FK().hashCode();
        }
        _hashCode += (is_PolicyStatus() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_Prefix() != null) {
            _hashCode += get_Prefix().hashCode();
        }
        if (get_Premium() != null) {
            _hashCode += get_Premium().hashCode();
        }
        if (get_PrevEndorsementStatus() != null) {
            _hashCode += get_PrevEndorsementStatus().hashCode();
        }
        if (get_PreviousInsurerID() != null) {
            _hashCode += get_PreviousInsurerID().hashCode();
        }
        if (get_PreviousPolicyEndDate() != null) {
            _hashCode += get_PreviousPolicyEndDate().hashCode();
        }
        if (get_PreviousPolicyNo() != null) {
            _hashCode += get_PreviousPolicyNo().hashCode();
        }
        _hashCode += get_PreviousPolicyProcessedIn();
        if (get_PreviousPolicyProductCode() != null) {
            _hashCode += get_PreviousPolicyProductCode().hashCode();
        }
        _hashCode += get_ProcessedAt();
        if (get_ProcessedAtName() != null) {
            _hashCode += get_ProcessedAtName().hashCode();
        }
        _hashCode += get_ProcessedIn();
        if (get_ProcessedInName() != null) {
            _hashCode += get_ProcessedInName().hashCode();
        }
        if (get_Product() != null) {
            _hashCode += get_Product().hashCode();
        }
        if (get_ProductCode() != null) {
            _hashCode += get_ProductCode().hashCode();
        }
        if (get_ProductName() != null) {
            _hashCode += get_ProductName().hashCode();
        }
        if (get_ProductType() != null) {
            _hashCode += get_ProductType().hashCode();
        }
        if (get_ProposalAmount() != null) {
            _hashCode += get_ProposalAmount().hashCode();
        }
        if (get_ProposalClass() != null) {
            _hashCode += get_ProposalClass().hashCode();
        }
        if (get_ProposalID_FK() != null) {
            _hashCode += get_ProposalID_FK().hashCode();
        }
        if (get_ProposalOrEndorDate() != null) {
            _hashCode += get_ProposalOrEndorDate().hashCode();
        }
        _hashCode += get_ProposalStatus();
        _hashCode += get_ProposalStatusID();
        if (get_QCDone_By() != null) {
            _hashCode += get_QCDone_By().hashCode();
        }
        if (get_QCDone_Date() != null) {
            _hashCode += get_QCDone_Date().hashCode();
        }
        if (get_QCRemark() != null) {
            _hashCode += get_QCRemark().hashCode();
        }
        if (get_QcStatus() != null) {
            _hashCode += get_QcStatus().hashCode();
        }
        if (get_QuoteNo() != null) {
            _hashCode += get_QuoteNo().hashCode();
        }
        _hashCode += (is_QuoteNoExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_ReasonForChange() != null) {
            _hashCode += get_ReasonForChange().hashCode();
        }
        if (get_ReceivedDate() != null) {
            _hashCode += get_ReceivedDate().hashCode();
        }
        if (get_RegionId() != null) {
            _hashCode += get_RegionId().hashCode();
        }
        if (get_RegionName() != null) {
            _hashCode += get_RegionName().hashCode();
        }
        if (get_RegistrationNo() != null) {
            _hashCode += get_RegistrationNo().hashCode();
        }
        if (get_Remark() != null) {
            _hashCode += get_Remark().hashCode();
        }
        if (get_RequiredDocument() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_RequiredDocument());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_RequiredDocument(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (get_RiskStartDate() != null) {
            _hashCode += get_RiskStartDate().hashCode();
        }
        if (get_RuleNumber() != null) {
            _hashCode += get_RuleNumber().hashCode();
        }
        if (get_SMCode() != null) {
            _hashCode += get_SMCode().hashCode();
        }
        if (get_SMName() != null) {
            _hashCode += get_SMName().hashCode();
        }
        _hashCode += (is_SearchMode() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_ServiceLeviedTax() != null) {
            _hashCode += get_ServiceLeviedTax().hashCode();
        }
        if (get_ServiceStatus() != null) {
            _hashCode += get_ServiceStatus().hashCode();
        }
        if (get_ServiceTax() != null) {
            _hashCode += get_ServiceTax().hashCode();
        }
        if (get_State() != null) {
            _hashCode += get_State().hashCode();
        }
        _hashCode += get_StateID();
        if (get_StatusCode() != null) {
            _hashCode += get_StatusCode().hashCode();
        }
        if (get_SurchargeValue() != null) {
            _hashCode += get_SurchargeValue().hashCode();
        }
        if (get_TableName() != null) {
            _hashCode += get_TableName().hashCode();
        }
        if (get_TaxExempRemark() != null) {
            _hashCode += get_TaxExempRemark().hashCode();
        }
        if (get_Terrorism() != null) {
            _hashCode += get_Terrorism().hashCode();
        }
        if (get_TotalCashAmt() != null) {
            _hashCode += get_TotalCashAmt().hashCode();
        }
        if (get_TotalPremium() != null) {
            _hashCode += get_TotalPremium().hashCode();
        }
        if (get_TransID_FK() != null) {
            _hashCode += get_TransID_FK().hashCode();
        }
        _hashCode += get_UnderWriterApproved();
        if (get_UserRole() != null) {
            _hashCode += get_UserRole().hashCode();
        }
        if (get_Value1() != null) {
            _hashCode += get_Value1().hashCode();
        }
        if (get_Value2() != null) {
            _hashCode += get_Value2().hashCode();
        }
        if (get_Value3() != null) {
            _hashCode += get_Value3().hashCode();
        }
        if (get_Variantid_FK() != null) {
            _hashCode += get_Variantid_FK().hashCode();
        }
        if (get_VehicalName() != null) {
            _hashCode += get_VehicalName().hashCode();
        }
        _hashCode += get_VehicalSubTypeId_FK();
        _hashCode += get_VehicalTypeId_FK();
        if (get_VehicalTypeName() != null) {
            _hashCode += get_VehicalTypeName().hashCode();
        }
        _hashCode += (is_VendorExists() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (get_War() != null) {
            _hashCode += get_War().hashCode();
        }
        _hashCode += get_WhereBy();
        if (get_WhereValue() != null) {
            _hashCode += get_WhereValue().hashCode();
        }
        if (get_YearOfManufacture() != null) {
            _hashCode += get_YearOfManufacture().hashCode();
        }
        if (get_YearOfRegistration() != null) {
            _hashCode += get_YearOfRegistration().hashCode();
        }
        if (get_branchName() != null) {
            _hashCode += get_branchName().hashCode();
        }
        if (get_chequeNo() != null) {
            _hashCode += get_chequeNo().hashCode();
        }
        if (get_className() != null) {
            _hashCode += get_className().hashCode();
        }
        if (get_discrCategory() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(get_discrCategory());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(get_discrCategory(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (get_srcc() != null) {
            _hashCode += get_srcc().hashCode();
        }
        if (get_stampduty() != null) {
            _hashCode += get_stampduty().hashCode();
        }
        if (get_userName() != null) {
            _hashCode += get_userName().hashCode();
        }
        _hashCode += getIntRowID();
        _hashCode += getIntSubDiscrepancyID();
        if (getQcResolvedDate() != null) {
            _hashCode += getQcResolvedDate().hashCode();
        }
        if (getReceiveMode() != null) {
            _hashCode += getReceiveMode().hashCode();
        }
        if (getSapCode() != null) {
            _hashCode += getSapCode().hashCode();
        }
        if (getShortPremium() != null) {
            _hashCode += getShortPremium().hashCode();
        }
        if (getStrDiscrepancyName() != null) {
            _hashCode += getStrDiscrepancyName().hashCode();
        }
        if (getStrRaisedDate() != null) {
            _hashCode += getStrRaisedDate().hashCode();
        }
        if (getStrResolvedRemark() != null) {
            _hashCode += getStrResolvedRemark().hashCode();
        }
        if (getStrSubDiscrepancyName() != null) {
            _hashCode += getStrSubDiscrepancyName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProposalDetailsBO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Actionable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Actionable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_AgentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AgentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_AgentName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AgentName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_AgentRetentionDetailsDummy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AgentRetentionDetailsDummy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "ProposalDetailsBO"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_AllocatedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AllocatedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_AllocatedTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AllocatedTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_AllocationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_AllocationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BASExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BASExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BGSAPBankNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BGSAPBankNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BGSAPCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BGSAPCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BancaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BancaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BarcodeExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BarcodeExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BasCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BasCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BasName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BasName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BranchCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BranchCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BranchID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BranchID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_BusinessType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_BusinessType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CSOCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CSOCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CSOName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CSOName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CancelledPolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CancelledPolicy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Cess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Cess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ChessisNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ChessisNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CityID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ClassID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ClassID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CoInsuranceShare");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CoInsuranceShare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Co_InsStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Co_InsStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ContactNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ContactNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_CoverNoteExits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_CoverNoteExits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_DiscrepancyID_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_DiscrepancyID_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_District");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_District"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_DistrictID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_DistrictID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_DocumentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_DocumentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ERF");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ERF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EndorsementCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EndorsementNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EndorsementReasonID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementReasonID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EndorsementSubTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementSubTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EndorsementType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EndorsementTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EndorsementTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_EngineNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_EngineNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ExternalBarcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ExternalBarcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_FirstAllocationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_FirstAllocationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_FirstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_GST");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_GST"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_HandOverTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_HandOverTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_HandoverDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_HandoverDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_HigherEduCess");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_HigherEduCess"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InitiatedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InitiatedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InspectionDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InspectionDocNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDocNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InspectionDocNoExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDocNoExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InspectionDoneBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDoneBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InspectionDoneByName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InspectionDoneByName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InsuredName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InsuredName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InterRefCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InterRefCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InterRefName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InterRefName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IntermediaryRefCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IntermediaryRefCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IntermediaryRefName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IntermediaryRefName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InternalBarcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InternalBarcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InwardedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InwardedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_InwardedDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_InwardedDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsChequeCleared");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsChequeCleared"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsCoverNoteActive");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsCoverNoteActive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsDeleted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsDeleted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsDiscrepancyLog");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsDiscrepancyLog"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsEditable");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsEditable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsEverJumping");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsEverJumping"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsInwarded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsInwarded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsMappedStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsMappedStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsNCB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsNCB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsPDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsPassedForPolicyGeneration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPassedForPolicyGeneration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsPaymentDone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPaymentDone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsPolicyGeneratedInICM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPolicyGeneratedInICM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsPostDateCheque");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsPostDateCheque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsQCDoneBSM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsQCDoneBSM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsQCOk");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsQCOk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IsReissue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IsReissue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Isjumping");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Isjumping"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_IssueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_IssueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_JobAllocatedBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JobAllocatedBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_JobAllocatedTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JobAllocatedTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_JobAllocationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JobAllocationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_JumpingStatusChangedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_JumpingStatusChangedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_KeyField1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_KeyField1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_KeyField2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_KeyField2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_KeyField3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_KeyField3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_LastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_LeadNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_LeadNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_LeadNoExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_LeadNoExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_MakeId_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MakeId_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_MakeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MakeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_MarinePremium");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MarinePremium"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_MaxDiscountallowed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MaxDiscountallowed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_MaxInstrDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MaxInstrDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_MiddleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_MiddleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ModelId_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ModelId_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ModelVariant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ModelVariant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NetOD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NetOD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NetPremium");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NetPremium"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Nettp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Nettp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NewAgentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewAgentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NewBASCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewBASCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NewIntermediaryRefCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewIntermediaryRefCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_NewSMCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_NewSMCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_OldAgentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_OldAgentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_OldBASCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_OldBASCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_OldIntermediaryRefCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_OldIntermediaryRefCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PanNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PanNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PassportNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PassportNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PaymentMode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PaymentMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PendingPolicyBranch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PendingPolicyBranch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PinCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PinCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PlanName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PlanName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Planid_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Planid_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PolicyEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PolicyGenDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyGenDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PolicyNo_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyNo_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PolicyStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PolicyStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Prefix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Prefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Premium");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Premium"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PrevEndorsementStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PrevEndorsementStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PreviousInsurerID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousInsurerID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PreviousPolicyEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PreviousPolicyNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PreviousPolicyProcessedIn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyProcessedIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_PreviousPolicyProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_PreviousPolicyProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProcessedAt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedAt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProcessedAtName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedAtName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProcessedIn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProcessedInName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProcessedInName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Product");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Product"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProductName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProductName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProductType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalID_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalID_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalOrEndorDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalOrEndorDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ProposalStatusID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ProposalStatusID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_QCDone_By");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QCDone_By"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_QCDone_Date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QCDone_Date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_QCRemark");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QCRemark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_QcStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QcStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_QuoteNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QuoteNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_QuoteNoExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_QuoteNoExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ReasonForChange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ReasonForChange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ReceivedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ReceivedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_RegionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RegionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_RegionName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RegionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_RegistrationNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RegistrationNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Remark");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Remark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_RequiredDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RequiredDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.MasterManager", "DocumentMasterBO"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_RiskStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RiskStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_RuleNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_RuleNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_SMCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SMCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_SMName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SMName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_SearchMode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SearchMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ServiceLeviedTax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ServiceLeviedTax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ServiceStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ServiceStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_ServiceTax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_ServiceTax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_State");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_State"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_StateID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_StateID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_StatusCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_StatusCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_SurchargeValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_SurchargeValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_TableName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TableName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_TaxExempRemark");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TaxExempRemark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Terrorism");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Terrorism"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_TotalCashAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TotalCashAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_TotalPremium");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TotalPremium"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_TransID_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_TransID_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_UnderWriterApproved");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_UnderWriterApproved"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_UserRole");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_UserRole"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Value1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Value1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Value2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Value2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Value3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Value3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_Variantid_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_Variantid_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_VehicalName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_VehicalSubTypeId_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalSubTypeId_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_VehicalTypeId_FK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalTypeId_FK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_VehicalTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VehicalTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_VendorExists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_VendorExists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_War");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_War"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_WhereBy");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_WhereBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_WhereValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_WhereValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_YearOfManufacture");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_YearOfManufacture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_YearOfRegistration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_YearOfRegistration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_branchName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_branchName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_chequeNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_chequeNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_className");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_className"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_discrCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_discrCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO", "DiscrepancyCategoryBO"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_srcc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_srcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_stampduty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_stampduty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_userName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "_userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intRowID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "intRowID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intSubDiscrepancyID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "intSubDiscrepancyID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qcResolvedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "qcResolvedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("receiveMode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "receiveMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sapCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "sapCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortPremium");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "shortPremium"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strDiscrepancyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strDiscrepancyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strRaisedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strRaisedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strResolvedRemark");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strResolvedRemark"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strSubDiscrepancyName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/ICMBO.ProposalManager", "strSubDiscrepancyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
