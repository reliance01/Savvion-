package com.rgicl.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListBOAuditParams implements Serializable {
   private static final long serialVersionUID = -1138420975L;
   private boolean _modified = false;
   private boolean _deleted = false;
   private boolean _new = true;
   private ArrayList<BOAuditParams> listBO;
   private boolean listBOModified = false;

   public ListBOAuditParams() {
   }

   public ListBOAuditParams(ArrayList<BOAuditParams> _listBO) {
      this.listBO = _listBO;
   }

   public ArrayList getListBO() {
      return this.listBO;
   }

   public boolean checkIsListBOModified() {
      return this.listBOModified;
   }

   public void setListBO(ArrayList<BOAuditParams> _listBO) {
      if (this.listBO != null || _listBO != null) {
         this.listBO = _listBO;
         this.listBOModified = true;
         this._modified = true;
      }
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof ListBOAuditParams)) {
         return false;
      } else {
         ListBOAuditParams other = (ListBOAuditParams)obj;
         if (other == null) {
            return false;
         } else {
            return this == other ? true : true;
         }
      }
   }

   public int hashCode() {
      int _hashCode = 0;
      if (this.listBO != null) {
         _hashCode += 29 * _hashCode + this.listBO.hashCode();
      }

      return _hashCode;
   }

   public String toString() {
      StringBuilder pw = new StringBuilder(200);
      pw.append("com.rgicl.bo.ListBOAuditParams ::");
      pw.append("listBO=" + this.listBO + "\n");
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
      this.listBOModified = false;
   }
}
