

import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.ejb.bizlogic.manager.BizLogicManager;
import com.savvion.ejb.bizlogic.manager.BizLogicManagerHome;
import com.savvion.sbm.bizlogic.server.svo.*;
import com.savvion.sbm.bizlogic.util.*;
import com.savvion.sbm.util.*;
import com.savvion.sbm.bizlogic.server.svo.DocumentDS;

/**
 * Auto-generated
 */
public class custom_adapter {
	private String sum;
	private String value1;
	private String value2;

	public String getSum() {
		return this.sum;
	}

	public String getValue1() {
		return this.value1;
	}

	public String getValue2() {
		return this.value2;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public void addValues() {
		
		this.sum = value1+value2;
	}

	public void PAKcallerID(String processInstanceName, String workstepName, java.util.Properties bizLogicHost) {
	}
}