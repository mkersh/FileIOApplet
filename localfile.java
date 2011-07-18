// This applet provides facilities to write/copy files to local storage
// It is intended to be used from browser environments.
// Even though this applet will be signed from javascript the browser will not allow applet methods to be called that
// require special priviledges. To work around this problem the applet has to be created for each operation required and
// the parameters passed up in the applet. See init() below for how this works.
import java.applet.*;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.awt.event.*; 
import java.net.*;
import java.io.*;

public class localfile extends Applet {
	public localfile() {
	}

	public void init() {
		String root = getCodeBase().getPath();
		root = System.getProperty("user.home") + "/LocalFileStorage";
		System.out.println(root);
		// command = (write|copy)
		String cmd = getParameter("command"); // NOTE: getParameter method is not available in constructor! Found this out the hard way. 
		// topath = relative path where to place the data
		String fpath = getParameter("topath");
		fpath = root + "/" + fpath;
		// For security do not allow .. in the path
		fpath = fpath.replaceAll("\\.\\.","");
		// Create directories if needed
		CreateDir(fpath);
		// data = for write string of data. For copy url to copy
		String data = getParameter("data"); 
		data = data.replaceAll("\\$q","'");
		
		System.out.println("App init |" + cmd + "|" );
		
		if (cmd.equals("write")){ // note: == does not work here!!
			WriteFile(fpath, data);
		}
		else if (cmd.equals("copy")){
			//System.out.println("localfile: copy Command  ");
			CopyFile(fpath, data);
		}
		else {
			System.out.println("localfile: UNKNOWN Command  " + cmd );
		}
	}

	// NOTE: We could make write public and it would be accessible from browser but would have a reduced security model
	// i.e. you would not be able to write files etc.
	// therefore you need to call this method by passing the write command to the applet. See init()
	private void WriteFile(String iFilePath, String iStr) {
		try { 
			BufferedWriter out = new BufferedWriter(new FileWriter(iFilePath)); 
			out.write(iStr); 
			out.close(); 
			
		} 
		catch (IOException e) {  }
	}
	
	private void CopyFile(String iFilePath, String iUrl) {
		try { 
			URL           url  = new URL(iUrl);
			URLConnection urlC = url.openConnection();
			InputStream is = url.openStream();
			FileOutputStream fos= new FileOutputStream(iFilePath);
			int oneChar;
			while ((oneChar=is.read()) != -1)
			{
				fos.write(oneChar);
			}
			is.close();
			fos.close();
		} 
		catch (IOException e) {  }
	}
	
	private void CreateDir(String iFilePath) {
		File dirPath = new File(iFilePath);
		dirPath = dirPath.getParentFile();
		if (dirPath != null){
			dirPath.mkdirs();
		}
	}
}
