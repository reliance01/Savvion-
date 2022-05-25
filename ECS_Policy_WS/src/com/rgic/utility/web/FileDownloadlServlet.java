package com.rgic.utility.web;

import com.savvion.sbm.bizsolo.util.SBMConf;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadlServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public void init(ServletConfig arg0) throws ServletException {
      super.init(arg0);
      System.out.println("Initializing " + this.getClass());
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String reportName = request.getParameter("reportname");
      String applicationName = request.getParameter("applicationName");
      response.setContentType("application/vnd.ms-excel");
      System.out.println("Report Name: " + reportName);
      String filename = SBMConf.SBM_WEBAPPDIR + "/bpmportal/" + applicationName + "/reports/" + reportName;
      System.out.println("File Location: " + filename);
      response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
      OutputStream o = response.getOutputStream();
      FileInputStream fis = new FileInputStream(filename);
      byte[] buf = new byte['耀'];
      boolean var9 = false;

      int nRead;
      while((nRead = fis.read(buf)) != -1) {
         o.write(buf, 0, nRead);
      }

      o.flush();
      o.close();
   }
}
