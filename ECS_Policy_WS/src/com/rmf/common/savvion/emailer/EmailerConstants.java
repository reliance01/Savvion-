package com.rmf.common.savvion.emailer;

public interface EmailerConstants {
   int EMAIL_SENDER_RETRY_TIME = 15;
   int EMAIL_SENDER_RETRY_COUNT = 3;
   String DATASLOT_START_IDENTIFIER = "${";
   String CUSTOM_START_IDENTIFIER = "$(";
   String CUSTOM_END_IDENTIFIER = ")";
   String DATASLOT_END_IDENTIFIER = "}";
   String PROCESS_INSTANCE = "PID";
   String WORKSTEP_NAME = "WS_NAME";
   String PERFORMER = "WS_PERFORMER";
   String TIME_ELAPSED = "TIME_ELAPSED";
   String MAIL_IDENTIFIER = "@";
   String MAIL_ID_SEPARATOR = ",";
   String NOT_AVAILABLE = "Value NOT Aavailable.";
}
