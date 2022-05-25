package com.rgic.utility.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.savvion.sbm.bizsolo.util.SBMConf;
import java.net.*;

public class FileDownloadlServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		super.init(arg0);
		System.out.println ("Initializing "+getClass());
	}


	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException
   {
		
		
		
		String reportName = request.getParameter("reportname");
		String  applicationName= request.getParameter("applicationName");
		
		
		response.setContentType("application/vnd.ms-excel");
		
		
		
		//String wedAppDir = "D:/IBM/WebSphere/AppServer/profiles/sbm70/installedApps/RGICsbm70cell/sbm_war.ear/sbm.war";
	    String filename = SBMConf.SBM_WEBAPPDIR+"/bpmportal/"+applicationName+"/reports/"+reportName;
	    	    
	    response.setHeader ("Content-Disposition", "attachment; filename=\""+reportName+"\"");
	    PrintWriter out = response.getWriter();
        out.println ("<script>");
        out.println ("parent.window.close()");
        out.println ("</script>"); 
	    //"<A href=\""+bean.getPropString("UploadScreen")+"\"
	    OutputStream o = response.getOutputStream();
	    FileInputStream fis = new FileInputStream( filename );
	    byte[] buf = new byte[32 * 1024]; // 32k buffer
	    int nRead = 0;
	    while( ( nRead = fis.read(buf) ) != -1 )
	    {
	        o.write( buf, 0, nRead);
	    }
	    o.flush();
	    o.close();  
}
}

