import com.savvion.sbm.bizlogic.util.*;

public class RestartExternalService {
	public void exe() {
		System.out.println("Resuming external services");
		BLUtil.self().setClustering(true);
		BLControl.doRestartExternalServices();
		BLUtil.self().setClustering(false);
		System.out.println("Done Resuming external services");
	}

	public void PAKcallerID(String processInstanceName, String workstepName,
			java.util.Properties bizLogicHost) {
	}
}