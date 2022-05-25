/*
 * Copyright (c) 2015 - 2020. Aurea Software, Inc. All Rights Reserved.

 * You are hereby placed on notice that the software, its related technology and
 * services may be covered by one or more United States ("US") and non-US patents.
 * A listing that associates patented and patent-pending products included in the
 * software, software updates, their related technology and services with one or
 * more patent numbers is available for you and the general public's access at
 * www.aurea.com/legal/ (the "Patent Notice") without charge. The association of
 * products-to-patent numbers at the Patent Notice may not be an exclusive listing
 * of associations, and other unlisted patents or pending patents may also be
 * associated with the products. Likewise, the patents or pending patents may also
 * be associated with unlisted products. You agree to regularly review the
 * products-to-patent number(s) association at the Patent Notice to check for
 * updates.
 */

 /*
 ------------------------------------------------------------------------

    Name            : EmailAdapterBL
    
    Author          : Liviu Casapu

    History         : 03-14-2001  Create

    Notes           : BizLogic style (3.0) adapter for sending email

 ------------------------------------------------------------------------*/



import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.tdiinc.common.AdapterInterface;
//import com.tdiinc.BizSolo.Server.BizSoloLog;
import com.tdiinc.BizSolo.beans.BizSoloRuntimeException;
import com.tdiinc.BizSolo.beans.Log;

public class EmailAdapterBL implements AdapterInterface
{

	private String SOURCE;
	private String DESTINATION;
	private String SUBJECT;
	private String BODY;
	private String HOST="mail";
    // BizLogic does not define Integer type, so we use Long instead.
	private Long PORT;

    private String error;
    
    
    public EmailAdapterBL()
    {
    }

    public void setProcessContextData(Hashtable processCtx)
    {
	// not required by BizSolo, only by BizLogic
    }

    
    public void setAllInputDataslots(Hashtable h)
    throws BizSoloRuntimeException
    {
	Field [] myFields = null;
	try {
	    myFields = this.getClass().getDeclaredFields();
	    AccessibleObject.setAccessible(myFields, true);
	} catch (SecurityException se) {
	    // give up, log and pass the error
	    se.printStackTrace();
	    Log.log("Error while accessing object fields",se);
	    throw new BizSoloRuntimeException(se.toString());		
	}
	for (int i=0; i<myFields.length; i++) {
	    Field f = myFields[i];
	    Object myVar = h.get(f.getName());
	    try {
		if(myVar!=null) {
		    Log.log("Initializing variable : "+f.getName());
		    f.set(this, myVar);
		}
	    } catch (Exception ex) {
		 // give up, log and pass the error
		ex.printStackTrace();
		Log.log("", ex);
		throw new BizSoloRuntimeException(ex.toString());
	    }
	}
	AccessibleObject.setAccessible(myFields, false);
    }



    public Hashtable getAllOutputDataslots()
    throws BizSoloRuntimeException
    {
	Hashtable h = new Hashtable();
	Field [] myFields = null;
	try {
	    myFields = this.getClass().getDeclaredFields();
	    AccessibleObject.setAccessible(myFields, true);
	} catch (SecurityException se) {
	    // give up, log and pass the error
	    se.printStackTrace();
	    Log.log("Error while accessing object fields", se);
	    throw new BizSoloRuntimeException(se.toString());		
	} 
	for (int i=0; i<myFields.length; i++) {
	    Field f = myFields[i];
	    try {
		Object myVar = f.get(this);
		if(myVar == null)
		    myVar = "";
		h.put(f.getName(), myVar);
	    } catch (Exception ex) {
		// give up, log and pass the error
		ex.printStackTrace();
		Log.log("", ex);
		throw new BizSoloRuntimeException(ex.toString());
	    }
	}
	AccessibleObject.setAccessible(myFields, false);
	return h;
    }




    
    /**
     * Return  0 - ok
     *         -1 - param errors
     *         -2 - net error
     */
    
    public int send()
    {
	
	Socket socket;
	BufferedReader br;
	DataOutputStream dos;
	String line;
	final String NL = "\n";	
	
	if(SOURCE==null) 
	    {
		setError("Invalid email source");
		return -1;
	    }
	
	if(DESTINATION==null)
	    {
		setError("Invalid email destination");
		return -1;
	    }
	
	if(BODY==null) 
	    {
		setError("Invalid message body");
		return -1;
	    }
	
	if(SUBJECT==null) SUBJECT = "(no subject)";
	if(HOST==null) HOST="mail";
	
	
	
	
	try{
	    socket = new Socket(HOST,PORT.intValue());
	    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    dos = new DataOutputStream(socket.getOutputStream());
	    
	    
	    // first greeting
	    line = br.readLine();
	    //log(line);
	    
	    
	    // HELO 
	    dos.writeBytes("HELO localhost"+NL);
	    line = br.readLine();
	    //log(line);
	    
	    
	    // Mail From
	    dos.writeBytes("MAIL FROM: "+SOURCE+NL);
	    line = br.readLine();
	    //log(line);
	    
	    // Recipient
	    dos.writeBytes("RCPT TO: "+DESTINATION+NL);
	    line = br.readLine();
	    //log(line);
	    
	    
	    // Data
	    dos.writeBytes("DATA"+NL);
	    line=br.readLine();
	    //log(line);
	    
	    
	    dos.writeBytes("Subject: "+SUBJECT+NL);
	    dos.writeBytes(BODY);
	    dos.writeBytes(NL+"."+NL);
	    line=br.readLine();
	    //log(line);
	    
	    // QUIT
	    dos.writeBytes("QUIT"+NL);
	    line=br.readLine();
	    //log(line);
	    
	    
	    dos.close();
	    br.close();
	    socket.close();
	    
	}catch(Exception ex)
	    {
		setError(ex.toString());
		//BizSoloLog.log("BizSolo_ERR_074", ex);
		return -2;
	    }
	
	
	return 0;
	
    }
    
    
    public void setError(String error)
    {
	this.error = error;
    }


    public String getError()
    {
	return error;
    }
    

    /**
     *   Debug & testing code
     *
     */

    public static void main(String[] args)
    {
	if(args.length!=6) 
	    {
		use();
		System.exit(-1);
	    }
	
	EmailAdapterBL emailer = new EmailAdapterBL();

	

	Hashtable input = new Hashtable();
	input.put("HOST",args[0]);
	Long port = new Long(25);
	try{
	    port = Long.valueOf(args[1]);
	}catch(Exception ex)
	    {
		Log.log("Unable to parse port number",ex);
	    }
	input.put("PORT",port);
	input.put("SOURCE",args[2]);
	input.put("DESTINATION",args[3]);
	input.put("SUBJECT",args[4]);
	input.put("BODY",args[5]);

	emailer.setAllInputDataslots(input);
	int rez = emailer.send();

	Hashtable output = emailer.getAllOutputDataslots();


	System.out.println("Rez: "+rez+" Error: "+emailer.getError());

	System.exit(0);
    }
    

    public static void use()
    {
	System.out.println("Use: java EmailAdapterBL <hostname> <port> <source> <destination> <subject> <body>");
	
    }
}
