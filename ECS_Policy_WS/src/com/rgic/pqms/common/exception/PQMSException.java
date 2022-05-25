package com.rgic.pqms.common.exception;

public class PQMSException extends Exception {
   private Exception exception;

   public PQMSException(Exception exception) {
      this.exception = exception;
   }

   public String getMessage() {
      return this.exception.getMessage();
   }
}
