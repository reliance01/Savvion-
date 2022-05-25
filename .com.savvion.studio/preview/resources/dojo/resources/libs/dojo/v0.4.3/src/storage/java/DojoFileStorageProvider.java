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
 *//**
	This is a simple class that can load, save, and remove 
	files from the native file system. It is needed by Safari and Opera
	for the dojo.storage.browser.FileStorageProvider, since both of
	these platforms have no native way to talk to the file system
	for file:// URLs. Safari supports LiveConnect, but only for talking
	to an applet, not for generic scripting by JavaScript, so we must
	have an applet.

	@author Brad Neuberg, bkn3@columbia.edu
*/

import java.io.*;
import java.util.*;

public class DojoFileStorageProvider{
	public String load(String filePath) 
			throws IOException, FileNotFoundException{
		StringBuffer results = new StringBuffer();
		BufferedReader reader = new BufferedReader(
					new FileReader(filePath));	
		String line = null;
		while((line = reader.readLine()) != null){
			results.append(line);
		}

		reader.close();

		return results.toString();
	}

	public void save(String filePath, String content) 
			throws IOException, FileNotFoundException{
		PrintWriter writer = new PrintWriter(
					new BufferedWriter(
						new FileWriter(filePath, false)));
		writer.print(content);

		writer.close();
	}

	public void remove(String filePath)
			throws IOException, FileNotFoundException{
		File f = new File(filePath);

		if(f.exists() == false || f.isDirectory()){
			return;
		}

		if(f.exists() && f.isFile()){
			f.delete();
		}
	}
}
