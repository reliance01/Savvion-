package com.rgi.icm.ws.stubs;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.axis.encoding.AnyContentType;
import org.apache.axis.message.MessageElement;

public class GetPolicyProposalNoResponseGetPolicyProposalNoResult implements Serializable, AnyContentType {
   private MessageElement[] _any;
   private Object __equalsCalc = null;
   private boolean __hashCodeCalc = false;

   public GetPolicyProposalNoResponseGetPolicyProposalNoResult() {
   }

   public GetPolicyProposalNoResponseGetPolicyProposalNoResult(MessageElement[] _any) {
      this._any = _any;
   }

   public MessageElement[] get_any() {
      return this._any;
   }

   public void set_any(MessageElement[] _any) {
      this._any = _any;
   }

   public synchronized boolean equals(Object obj) {
      if (!(obj instanceof GetPolicyProposalNoResponseGetPolicyProposalNoResult)) {
         return false;
      } else {
         GetPolicyProposalNoResponseGetPolicyProposalNoResult other = (GetPolicyProposalNoResponseGetPolicyProposalNoResult)obj;
         if (obj == null) {
            return false;
         } else if (this == obj) {
            return true;
         } else if (this.__equalsCalc != null) {
            return this.__equalsCalc == obj;
         } else {
            this.__equalsCalc = obj;
            boolean _equals = this._any == null && other.get_any() == null || this._any != null && Arrays.equals(this._any, other.get_any());
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
         if (this.get_any() != null) {
            for(int i = 0; i < Array.getLength(this.get_any()); ++i) {
               Object obj = Array.get(this.get_any(), i);
               if (obj != null && !obj.getClass().isArray()) {
                  _hashCode += obj.hashCode();
               }
            }
         }

         this.__hashCodeCalc = false;
         return _hashCode;
      }
   }
}
