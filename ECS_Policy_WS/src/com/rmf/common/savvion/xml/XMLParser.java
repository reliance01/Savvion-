package com.rmf.common.savvion.xml;

import com.rmf.common.savvion.emailer.EmailSenderPropertyReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.dom.DOMXPath;
import org.jaxen.function.StringFunction;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {
   private Hashtable htXMLInfo;
   private boolean bXMLread = false;
   private static XMLParser xmlParser;
   private Logger logger = EmailSenderPropertyReader.getLogger();

   private XMLParser() {
   }

   public void read(String sFilePath) {
      if (!this.isXMLread()) {
         FileInputStream fis = null;

         try {
            fis = new FileInputStream(new File(sFilePath));
            this.parse(fis);
            this.setXMLread(true);
            this.logger.debug("XMLParser.read() : XML Info read Successfully...\n" + this.htXMLInfo);
         } catch (Exception var11) {
            this.setXMLread(false);
            this.logger.error("XMLParser.read() : Error in reading XML file ", var11);
         } finally {
            try {
               this.logger.debug("XMLParser.read() : Closing the stream");
               fis.close();
            } catch (Exception var10) {
               this.logger.error("XMLParser.read() : Error in closing stream ", var10);
            }

         }

      }
   }

   public static XMLParser getInstance() {
      if (xmlParser == null) {
         xmlParser = new XMLParser();
      }

      return xmlParser;
   }

   private void parse(InputStream inputStream) throws IOException, SAXException, TransformerException, ParserConfigurationException, JaxenException {
      DocumentBuilderFactory factory = null;
      Node doc = null;
      this.htXMLInfo = new Hashtable();
      DocumentBuilder builder = null;
      DOMXPath expression = null;
      InputSource data = null;

      try {
         factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware(true);
         builder = factory.newDocumentBuilder();
         data = new InputSource(inputStream);
         this.logger.debug("data" + data);
         doc = builder.parse(data);
         expression = new DOMXPath("/emailer/process-template");
         List results = expression.selectNodes(doc);
         Iterator iterator = results.iterator();
         int i = 0;

         while(iterator.hasNext()) {
            Node result = (Node)iterator.next();
            ++i;
            String value = result.getAttributes().getNamedItem("value").getNodeValue().trim();
            this.htXMLInfo.put(value, this.handleProcessTremplateTag(i, result, expression.toString(), value));
         }
      } catch (Exception var15) {
         var15.printStackTrace();
      } finally {
         inputStream.close();
      }

   }

   private EmailerPTInfo handleProcessTremplateTag(int position, Node node, String sXpath, String sPTName) throws JaxenException {
      DOMXPath expression = null;
      EmailerPTInfo ptInfo = new EmailerPTInfo();
      ptInfo.setPTName(sPTName);
      expression = new DOMXPath(sXpath + "[" + position + "]/" + "workstep-name");
      List results = expression.selectNodes(node);
      Iterator iterator = results.iterator();
      int i = 0;

      while(iterator.hasNext()) {
         Node result = (Node)iterator.next();
         ++i;
         String value = result.getAttributes().getNamedItem("value").getNodeValue().trim();
         ptInfo.addWSInformation(value, this.handleWorkStepTag(i, result, expression.toString(), value));
      }

      return ptInfo;
   }

   private EmailerWSInfo handleWorkStepTag(int position, Node node, String sXpath, String sWSname) throws JaxenException {
      String output = "";
      DOMXPath expression = null;
      Navigator navigator = null;
      EmailerWSInfo wsInfo = new EmailerWSInfo();
      wsInfo.setWSName(sWSname);
      expression = new DOMXPath(sXpath + "[" + position + "]/" + "to-emailid");
      navigator = expression.getNavigator();
      Node childNode = (Node)expression.selectSingleNode(node);
      output = StringFunction.evaluate(childNode, navigator);
      if (output != null) {
         wsInfo.setEmailTo(output.trim());
      }

      expression = new DOMXPath(sXpath + "[" + position + "]/" + "cc-emailid");
      navigator = expression.getNavigator();
      childNode = (Node)expression.selectSingleNode(node);
      output = StringFunction.evaluate(childNode, navigator);
      if (output != null) {
         wsInfo.setEmailCC(output.trim());
      }

      expression = new DOMXPath(sXpath + "[" + position + "]/" + "bcc-emailid");
      navigator = expression.getNavigator();
      childNode = (Node)expression.selectSingleNode(node);
      output = StringFunction.evaluate(childNode, navigator);
      if (output != null) {
         wsInfo.setEmailBCC(output.trim());
      }

      expression = new DOMXPath(sXpath + "[" + position + "]/" + "subject");
      navigator = expression.getNavigator();
      childNode = (Node)expression.selectSingleNode(node);
      output = StringFunction.evaluate(childNode, navigator);
      if (output != null) {
         wsInfo.setSubject(output.trim());
      }

      expression = new DOMXPath(sXpath + "[" + position + "]/" + "template-files-list");
      childNode = (Node)expression.selectSingleNode(node);
      wsInfo.setTemplateFileList(this.populateList(1, childNode, expression.toString()));
      expression = new DOMXPath(sXpath + "[" + position + "]/" + "dataslot-list");
      childNode = (Node)expression.selectSingleNode(node);
      wsInfo.setDataSlotList(this.populateList(1, childNode, expression.toString()));
      return wsInfo;
   }

   private Vector populateList(int position, Node node, String sXpath) throws JaxenException {
      DOMXPath expression = null;
      Navigator navigator = null;
      Vector vector = new Vector();
      expression = new DOMXPath(sXpath + "[" + position + "]/" + "attribute");
      navigator = expression.getNavigator();
      List results = expression.selectNodes(node);
      Iterator iterator = results.iterator();
      int var9 = 0;

      while(iterator.hasNext()) {
         Node result = (Node)iterator.next();
         ++var9;
         String output = StringFunction.evaluate(result, navigator);
         if (output != null) {
            vector.add(output.trim());
         }
      }

      return vector;
   }

   public boolean isXMLread() {
      return this.bXMLread;
   }

   private void setXMLread(boolean lread) {
      this.bXMLread = lread;
   }

   public EmailerPTInfo getPTInfo(String PTName) {
      return (EmailerPTInfo)this.htXMLInfo.get(PTName);
   }
}
