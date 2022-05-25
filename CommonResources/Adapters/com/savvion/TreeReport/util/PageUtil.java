package com.savvion.TreeReport.util;
/*
 * (C) Copyright 1998-2008 Savvion, Inc.
 * All Rights Reserved.
 *
 * This program is an unpublished copyrighted work which is proprietary
 * to Savvion, Inc. and contains confidential information that is not
 * to be reproduced or disclosed to any other person or entity without
 * prior written consent from Savvion, Inc. in each and every instance.
 *
 * History: Creation -- Savvion India Pvt. Ltd.
 */
public class PageUtil {
	private static PageUtil self = null;
	
	private PageUtil(){
		
	}
	
	public static PageUtil self(){
		if(self == null){
			self = new PageUtil();
		}
		return self;
	}
	
	public boolean isNullorEmpty(String str) {
		if ((null == str) || 
			(PageConstants.NULL.equals(str)) || 
			("".equals(str))) {
			return true;
		}
		return false;
	}
	
	public boolean isNullorAll(String str){
    	if((str == null) || 
    	   (PageConstants.NULL.equals(str)) || 
    	   (PageConstants.ALL.equals(str))){
    		return true;
    	}
    	return false;
    }
}
