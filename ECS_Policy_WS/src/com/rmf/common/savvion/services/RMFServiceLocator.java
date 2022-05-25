package com.rmf.common.savvion.services;

import com.savvion.util.ResourceUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RMFServiceLocator {
   private static final String SBM_WS_FACTORY_INITIAL = "sbm.websphere.factory.initial";
   private static final String SBM_WS_PROVIDER_URL = "sbm.websphere.provider.url";
   private static final String SBM_WS_PRINCIPAL = "sbm.websphere.principal";
   private static final String SBM_WS_CREDENTIALS = "sbm.websphere.credentials";
   private static final String SBM_JNDI_PROP_FILE_NAME = "sbmjndi.properties";
   private static RMFServiceLocator locator;
   private Map<String, Object> cache;
   private InitialContext ic;

   static {
      try {
         locator = new RMFServiceLocator();
      } catch (Exception var1) {
         System.out.println("RMFServiceLocator :: static : Error while creating the RMFServiceLocator");
         var1.printStackTrace(System.err);
      }

   }

   private void init() throws NamingException {
      Properties prop = new Properties();
      Properties jndiProps = ResourceUtil.getResourceAsProperties("sbmjndi.properties");
      prop.setProperty("java.naming.factory.initial", jndiProps.getProperty("sbm.websphere.factory.initial"));
      prop.setProperty("java.naming.provider.url", jndiProps.getProperty("sbm.websphere.provider.url"));
      prop.setProperty("java.naming.security.principal", jndiProps.getProperty("sbm.websphere.principal"));
      prop.setProperty("java.naming.security.credentials", jndiProps.getProperty("sbm.websphere.credentials"));
      this.ic = new InitialContext(prop);
   }

   private Object getResource(String resourceName) throws NamingException {
      Object resource = null;
      if (this.ic != null) {
         resource = this.ic.lookup(resourceName);
      } else {
         this.init();
         resource = this.ic.lookup(resourceName);
      }

      return resource;
   }

   private RMFServiceLocator() throws Exception {
      try {
         this.init();
         this.cache = Collections.synchronizedMap(new HashMap());
      } catch (NamingException var2) {
         throw new Exception(var2);
      } catch (Exception var3) {
         throw new Exception(var3);
      }
   }

   public static RMFServiceLocator getInstance() {
      if (locator == null) {
         try {
            locator = new RMFServiceLocator();
         } catch (Exception var1) {
            System.out.println("Error while creating RMFServiceLocator Instance");
            var1.printStackTrace();
         }
      }

      return locator;
   }

   public QueueConnectionFactory getQueueConnectionFactory(String queueConnFactoryName) throws Exception {
      QueueConnectionFactory factory = null;

      try {
         if (this.cache.containsKey(queueConnFactoryName)) {
            factory = (QueueConnectionFactory)this.cache.get(queueConnFactoryName);
            if (factory == null) {
               this.cache.remove(queueConnFactoryName);
               factory = (QueueConnectionFactory)this.getResource(queueConnFactoryName);
               this.cache.put(queueConnFactoryName, factory);
            }
         } else {
            factory = (QueueConnectionFactory)this.ic.lookup(queueConnFactoryName);
            this.cache.put(queueConnFactoryName, factory);
         }

         return factory;
      } catch (NamingException var4) {
         throw new Exception(var4);
      } catch (Exception var5) {
         throw new Exception(var5);
      }
   }

   public Queue getQueue(String queueName) throws Exception {
      Queue queue = null;

      try {
         if (this.cache.containsKey(queueName)) {
            queue = (Queue)this.cache.get(queueName);
            if (queue == null) {
               this.cache.remove(queueName);
               queue = (Queue)this.getResource(queueName);
               this.cache.put(queueName, queue);
            }
         } else {
            queue = (Queue)this.ic.lookup(queueName);
            this.cache.put(queueName, queue);
         }

         return queue;
      } catch (NamingException var4) {
         throw new Exception(var4);
      } catch (Exception var5) {
         throw new Exception(var5);
      }
   }

   public DataSource getDataSource(String dataSourceName) throws Exception {
      DataSource dataSource = null;

      try {
         if (this.cache.containsKey(dataSourceName)) {
            dataSource = (DataSource)this.cache.get(dataSourceName);
         } else {
            dataSource = (DataSource)this.ic.lookup(dataSourceName);
            this.cache.put(dataSourceName, dataSource);
         }

         return dataSource;
      } catch (NamingException var4) {
         throw new Exception(var4);
      } catch (Exception var5) {
         throw new Exception(var5);
      }
   }
}
