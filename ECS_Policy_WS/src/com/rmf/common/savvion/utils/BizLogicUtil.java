package com.rmf.common.savvion.utils;

import com.rmf.common.util.PropertyReader;
import com.savvion.sbm.bizlogic.server.ejb.BLServer;
import com.savvion.sbm.bizlogic.server.ejb.BLServerHome;
import com.savvion.sbm.bizlogic.util.Session;
import com.savvion.sbm.util.PService;
import com.savvion.sbm.util.SBMHomeFactory;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

public class BizLogicUtil {
   private static final String CLASS_NAME = "BizLogicUtil";
   private static BizLogicUtil singleton;
   private static Session session;
   private static BLServer blServer;
   private static final String blUser = PropertyReader.getBizlogicUserName();
   private static final String blPwd = PService.self().decrypt(PropertyReader.getBizlogicPassword());
   private static Logger logger = PropertyReader.getBlutilLogger();

   private BizLogicUtil() {
      connectToBLServer();
   }

   private static void connectToBLServer() {
      String METHOD_NAME = "connectToBLServer";
      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "connectToBLServer", 1));

      try {
         blServer = lookupBLServer();
         session = blServer.connect(blUser, blPwd);
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }

      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "connectToBLServer", 2));
   }

   private static BLServer lookupBLServer() throws Exception {
      String METHOD_NAME = "lookupBLServer";
      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "lookupBLServer", 1));
      BLServerHome blHome = (BLServerHome)SBMHomeFactory.self().lookupHome(BLServerHome.class);
      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "lookupBLServer", 2));
      return blHome.create();
   }

   public static synchronized BizLogicUtil getInstance() throws RemoteException {
      String METHOD_NAME = "getInstance";
      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "getInstance", 1));
      if (singleton == null) {
         singleton = new BizLogicUtil();
      } else if (blServer != null) {
         if (!blServer.isSessionValid(session)) {
            connectToBLServer();
         }
      } else {
         connectToBLServer();
      }

      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "getInstance", 2));
      return singleton;
   }

   public synchronized BLServer getBLServer() {
      return blServer;
   }

   public synchronized Session getBLSession() {
      return session;
   }

   public synchronized void disconnectBLServer() {
      String METHOD_NAME = "disconnectBLServer";
      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "disconnectBLServer", 1));

      try {
         blServer.disConnect(session);
      } catch (RemoteException var3) {
         var3.printStackTrace();
      }

      logger.debug(com.rmf.savvion.util.StaticFuncs.buildLogStatement("BizLogicUtil", "disconnectBLServer", 2));
   }
}
