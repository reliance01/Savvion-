package com.motor.agent;

import java.io.Serializable;

public class MWAgentBean implements Serializable {
   private static final long serialVersionUID = 612129863L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private String bas_code = "";
   private boolean bas_codeModified = false;
   private String agent_code = "";
   private boolean agent_codeModified = false;
   private String product_cat = "";
   private boolean product_catModified = false;
   private String make = "";
   private boolean makeModified = false;
   private String region_code = "";
   private boolean region_codeModified = false;
   private String branch_code = "";
   private boolean branch_codeModified = false;
   private String policy_count = "";
   private boolean policy_countModified = false;
   private String gwp = "";
   private boolean gwpModified = false;
   private String loss_ratio = "";
   private boolean loss_ratioModified = false;
   private String avg_od_discount = "";
   private boolean avg_od_discountModified = false;

   public MWAgentBean() {
   }

   public MWAgentBean(String _bas_code, String _agent_code, String _product_cat, String _make, String _region_code, String _branch_code, String _policy_count, String _gwp, String _loss_ratio, String _avg_od_discount) {
      this.bas_code = _bas_code;
      this.agent_code = _agent_code;
      this.product_cat = _product_cat;
      this.make = _make;
      this.region_code = _region_code;
      this.branch_code = _branch_code;
      this.policy_count = _policy_count;
      this.gwp = _gwp;
      this.loss_ratio = _loss_ratio;
      this.avg_od_discount = _avg_od_discount;
   }

   public String getBas_code() {
      return this.bas_code;
   }

   public boolean checkIsBas_codeModified() {
      return this.bas_codeModified;
   }

   public void setBas_code(String _bas_code) {
      if (this.bas_code != null || _bas_code != null) {
         if (this.bas_code != null && this.bas_code.equals(_bas_code)) {
            if (_bas_code == null || !_bas_code.equals(this.bas_code)) {
               this.bas_code = _bas_code;
               this.bas_codeModified = true;
               this._modified = true;
            }
         } else {
            this.bas_code = _bas_code;
            this.bas_codeModified = true;
            this._modified = true;
         }

      }
   }

   public String getAgent_code() {
      return this.agent_code;
   }

   public boolean checkIsAgent_codeModified() {
      return this.agent_codeModified;
   }

   public void setAgent_code(String _agent_code) {
      if (this.agent_code != null || _agent_code != null) {
         if (this.agent_code != null && this.agent_code.equals(_agent_code)) {
            if (_agent_code == null || !_agent_code.equals(this.agent_code)) {
               this.agent_code = _agent_code;
               this.agent_codeModified = true;
               this._modified = true;
            }
         } else {
            this.agent_code = _agent_code;
            this.agent_codeModified = true;
            this._modified = true;
         }

      }
   }

   public String getProduct_cat() {
      return this.product_cat;
   }

   public boolean checkIsProduct_catModified() {
      return this.product_catModified;
   }

   public void setProduct_cat(String _product_cat) {
      if (this.product_cat != null || _product_cat != null) {
         if (this.product_cat != null && this.product_cat.equals(_product_cat)) {
            if (_product_cat == null || !_product_cat.equals(this.product_cat)) {
               this.product_cat = _product_cat;
               this.product_catModified = true;
               this._modified = true;
            }
         } else {
            this.product_cat = _product_cat;
            this.product_catModified = true;
            this._modified = true;
         }

      }
   }

   public String getMake() {
      return this.make;
   }

   public boolean checkIsMakeModified() {
      return this.makeModified;
   }

   public void setMake(String _make) {
      if (this.make != null || _make != null) {
         if (this.make != null && this.make.equals(_make)) {
            if (_make == null || !_make.equals(this.make)) {
               this.make = _make;
               this.makeModified = true;
               this._modified = true;
            }
         } else {
            this.make = _make;
            this.makeModified = true;
            this._modified = true;
         }

      }
   }

   public String getRegion_code() {
      return this.region_code;
   }

   public boolean checkIsRegion_codeModified() {
      return this.region_codeModified;
   }

   public void setRegion_code(String _region_code) {
      if (this.region_code != null || _region_code != null) {
         if (this.region_code != null && this.region_code.equals(_region_code)) {
            if (_region_code == null || !_region_code.equals(this.region_code)) {
               this.region_code = _region_code;
               this.region_codeModified = true;
               this._modified = true;
            }
         } else {
            this.region_code = _region_code;
            this.region_codeModified = true;
            this._modified = true;
         }

      }
   }

   public String getBranch_code() {
      return this.branch_code;
   }

   public boolean checkIsBranch_codeModified() {
      return this.branch_codeModified;
   }

   public void setBranch_code(String _branch_code) {
      if (this.branch_code != null || _branch_code != null) {
         if (this.branch_code != null && this.branch_code.equals(_branch_code)) {
            if (_branch_code == null || !_branch_code.equals(this.branch_code)) {
               this.branch_code = _branch_code;
               this.branch_codeModified = true;
               this._modified = true;
            }
         } else {
            this.branch_code = _branch_code;
            this.branch_codeModified = true;
            this._modified = true;
         }

      }
   }

   public String getPolicy_count() {
      return this.policy_count;
   }

   public boolean checkIsPolicy_countModified() {
      return this.policy_countModified;
   }

   public void setPolicy_count(String _policy_count) {
      if (this.policy_count != null || _policy_count != null) {
         if (this.policy_count != null && this.policy_count.equals(_policy_count)) {
            if (_policy_count == null || !_policy_count.equals(this.policy_count)) {
               this.policy_count = _policy_count;
               this.policy_countModified = true;
               this._modified = true;
            }
         } else {
            this.policy_count = _policy_count;
            this.policy_countModified = true;
            this._modified = true;
         }

      }
   }

   public String getGwp() {
      return this.gwp;
   }

   public boolean checkIsGwpModified() {
      return this.gwpModified;
   }

   public void setGwp(String _gwp) {
      if (this.gwp != null || _gwp != null) {
         if (this.gwp != null && this.gwp.equals(_gwp)) {
            if (_gwp == null || !_gwp.equals(this.gwp)) {
               this.gwp = _gwp;
               this.gwpModified = true;
               this._modified = true;
            }
         } else {
            this.gwp = _gwp;
            this.gwpModified = true;
            this._modified = true;
         }

      }
   }

   public String getLoss_ratio() {
      return this.loss_ratio;
   }

   public boolean checkIsLoss_ratioModified() {
      return this.loss_ratioModified;
   }

   public void setLoss_ratio(String _loss_ratio) {
      if (this.loss_ratio != null || _loss_ratio != null) {
         if (this.loss_ratio != null && this.loss_ratio.equals(_loss_ratio)) {
            if (_loss_ratio == null || !_loss_ratio.equals(this.loss_ratio)) {
               this.loss_ratio = _loss_ratio;
               this.loss_ratioModified = true;
               this._modified = true;
            }
         } else {
            this.loss_ratio = _loss_ratio;
            this.loss_ratioModified = true;
            this._modified = true;
         }

      }
   }

   public String getAvg_od_discount() {
      return this.avg_od_discount;
   }

   public boolean checkIsAvg_od_discountModified() {
      return this.avg_od_discountModified;
   }

   public void setAvg_od_discount(String _avg_od_discount) {
      if (this.avg_od_discount != null || _avg_od_discount != null) {
         if (this.avg_od_discount != null && this.avg_od_discount.equals(_avg_od_discount)) {
            if (_avg_od_discount == null || !_avg_od_discount.equals(this.avg_od_discount)) {
               this.avg_od_discount = _avg_od_discount;
               this.avg_od_discountModified = true;
               this._modified = true;
            }
         } else {
            this.avg_od_discount = _avg_od_discount;
            this.avg_od_discountModified = true;
            this._modified = true;
         }

      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof MWAgentBean)) {
         return false;
      } else {
         MWAgentBean other = (MWAgentBean)obj;
         if (other == null) {
            return false;
         } else {
            return this == other ? true : true;
         }
      }
   }

   public int hashCode() {
      int _hashCode = 0;
      if (this.bas_code != null) {
         _hashCode += 29 * _hashCode + this.bas_code.hashCode();
      }

      if (this.agent_code != null) {
         _hashCode += 29 * _hashCode + this.agent_code.hashCode();
      }

      if (this.product_cat != null) {
         _hashCode += 29 * _hashCode + this.product_cat.hashCode();
      }

      if (this.make != null) {
         _hashCode += 29 * _hashCode + this.make.hashCode();
      }

      if (this.region_code != null) {
         _hashCode += 29 * _hashCode + this.region_code.hashCode();
      }

      if (this.branch_code != null) {
         _hashCode += 29 * _hashCode + this.branch_code.hashCode();
      }

      if (this.policy_count != null) {
         _hashCode += 29 * _hashCode + this.policy_count.hashCode();
      }

      if (this.gwp != null) {
         _hashCode += 29 * _hashCode + this.gwp.hashCode();
      }

      if (this.loss_ratio != null) {
         _hashCode += 29 * _hashCode + this.loss_ratio.hashCode();
      }

      if (this.avg_od_discount != null) {
         _hashCode += 29 * _hashCode + this.avg_od_discount.hashCode();
      }

      return _hashCode;
   }

   public String toString() {
      StringBuilder pw = new StringBuilder(200);
      pw.append("com.motor.agent.MWAgentBean ::");
      pw.append("bas_code=" + this.bas_code + "\n");
      pw.append("agent_code=" + this.agent_code + "\n");
      pw.append("product_cat=" + this.product_cat + "\n");
      pw.append("make=" + this.make + "\n");
      pw.append("region_code=" + this.region_code + "\n");
      pw.append("branch_code=" + this.branch_code + "\n");
      pw.append("policy_count=" + this.policy_count + "\n");
      pw.append("gwp=" + this.gwp + "\n");
      pw.append("loss_ratio=" + this.loss_ratio + "\n");
      pw.append("avg_od_discount=" + this.avg_od_discount + "\n");
      return pw.toString();
   }

   public boolean checkIsModified() {
      return this._modified;
   }

   public boolean checkIsDeleted() {
      return this._deleted;
   }

   public boolean checkIsNew() {
      return this._new;
   }

   public void markDeleted() {
      this._deleted = true;
   }

   public void markNew() {
      this._new = true;
   }

   public void markModified() {
      this._modified = true;
   }

   public void resetFlags() {
      this._modified = false;
      this._new = false;
      this._deleted = false;
      this.bas_codeModified = false;
      this.agent_codeModified = false;
      this.product_catModified = false;
      this.makeModified = false;
      this.region_codeModified = false;
      this.branch_codeModified = false;
      this.policy_countModified = false;
      this.gwpModified = false;
      this.loss_ratioModified = false;
      this.avg_od_discountModified = false;
   }
}
