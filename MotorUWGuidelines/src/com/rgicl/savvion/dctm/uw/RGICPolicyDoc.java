package com.rgicl.savvion.dctm.uw;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;
/** 
 * Only javadoc-style comments will be preserved
 */
public class RGICPolicyDoc {
  private String prevPolicyNumber;
  public void setPrevPolicyNumber(  String prevPolicyNumber){
    this.prevPolicyNumber=prevPolicyNumber;
  }
  public String getPrevPolicyNumber(){
    return this.prevPolicyNumber;
  }
  public void getPolicyDoc(){
  }
}
