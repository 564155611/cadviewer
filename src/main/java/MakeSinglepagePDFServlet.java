// File Name Single Page PDF

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Map;

 
public class MakeSinglepagePDFServlet extends HttpServlet {

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
   }

      
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      	  
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();	  

		String serverPath = "";
		int numberOfFiles = 1;
		String originalFileName = "";

		// Single page only		
		String fileNameArray0 = "";
		String rotationArray0 = "";
		String paperSizeArray0 = "";
		String pageResolutionArray0 = "";
		
		String html_file = "";
						
		try{
	
			// Read in parameters
		  
			serverPath = request.getParameter("serverPath");
			originalFileName = request.getParameter("org_fileName_0");

			// Single page only		
			fileNameArray0 = request.getParameter("fileName_0");
			rotationArray0 = request.getParameter("rotation_0");
			paperSizeArray0 = request.getParameter("page_format_0");
			pageResolutionArray0 = request.getParameter("page_resolution_0");
			//out.write("1 "+serverPath+"  "+originalFileName+" "+fileNameArray0+" "+rotationArray0+" "+paperSizeArray0+" "+pageResolutionArray0);
			
			
			serverPath = decode(serverPath);
			
			
			
			// load the base64 string  
			String base64_string = readFile( serverPath + "/" + fileNameArray0 + "_base64.png");			
			
			String[] tempArray;
			tempArray = base64_string.split(",");
			// Get bytes from string
			//byte[] byteArray = Base64.decodeBase64(tempArray[1].getBytes());

			byte[] decoded = Base64.getDecoder().decode(tempArray[1]);
			
			OutputStream os = null;
			try {
				os = new FileOutputStream(serverPath + "/" + fileNameArray0 + ".png");
				os.write(decoded);
			} finally {
				os.close();
			}

						
			String html_content = "<!DOCTYPE html><html><title>CADViewer JS - Print</title><head>";
			html_content = html_content + "<script src=\"../javascripts/jquery-2.2.3.js\" type=\"text/javascript\"></script>";
			html_content = html_content +  "<body><div id=\"pdf\"><object width=\"1654\" height=\"2339\" type=\"application/pdf\" data=\"" + "./" + fileNameArray0 + ".pdf" + "\" id=\"pdf_content\"><p>Please install a PDF viewer, the CADViewer JS batch file PDF cannot be displayed.</p></object></div></body></html>";

			html_file = serverPath + "/" + fileNameArray0 + ".html";

			
			
			FileWriter fw = new FileWriter(html_file,false);  //the save will create a new file
			fw.write(html_content);//appends the string to the file
			fw.close();
			
					

			String converterLocation = "";
			String ax_executable = "";
			String fileLocation = "";
			String licenseLocation = "";
			String xpathLocation = "";

			if (getServletConfig().getInitParameter("converterLocation") != null)
				converterLocation = getServletConfig().getInitParameter("converterLocation");

			if (getServletConfig().getInitParameter("ax_executable") != null)
				ax_executable = getServletConfig().getInitParameter("ax_executable");

			if (getServletConfig().getInitParameter("fileLocation") != null)
				fileLocation = getServletConfig().getInitParameter("fileLocation");

			if (getServletConfig().getInitParameter("licenseLocation") != null)
				licenseLocation = getServletConfig().getInitParameter("licenseLocation");

			if (getServletConfig().getInitParameter("xpathLocation") != null)
				xpathLocation = getServletConfig().getInitParameter("xpathLocation");


	//   Here we change to calling bat file to convert server side			
			
			String[] str_arr = new String[6];   
			str_arr[0] =  converterLocation + ax_executable;
			str_arr[1] =  "-i=\""+fileLocation + "/" + fileNameArray0 + ".png"+"\"";
			str_arr[2] =  "-0=\""+fileLocation + "/" + fileNameArray0 + ".png"+"\"";
			str_arr[3] =  "-f=pdf";
			str_arr[4] =  "-lpath=\""+licenseLocation+"\"";    
			str_arr[5] =  "-xpath=\""+xpathLocation+"\"";    
			str_arr[6] =  "-"+paperSizeArray0;    
			str_arr[6] =  "-"+rotationArray0;    
									
			String txt_file = serverPath + "/" + fileNameArray0 + ".txt";
			
			String command_line = str_arr[0]+" "+str_arr[1]+" "+str_arr[2]+" "+str_arr[3]+" "+str_arr[4]+" "+str_arr[5];
			fw = new FileWriter(txt_file,false);  //the save will create a new file
			fw.write(command_line);//appends the string to the file
			fw.close();
			
			int exitCode = -1;
			boolean cvjs_debug = true;
			
			ProcessBuilder pb = new ProcessBuilder(str_arr);
			pb.command(str_arr);		 
			Map<String, String> env = pb.environment();
			pb.directory(new File(fileLocation));

			Process p = pb.start();	
			 //InputStream shellIn = p.getInputStream();		 
			 //exitCode = p.exitValue();
			exitCode = p.waitFor();
	
			out.write(fileNameArray0 + ".html");
			out.close();
						
		}
		catch(Exception e)
		{

			out.write(fileNameArray0 + ".html");
			out.write(serverPath+"  "+originalFileName+" "+fileNameArray0+" "+rotationArray0+" "+paperSizeArray0+" "+pageResolutionArray0);
			out.write("Exception: "+e);
			out.close();
		}

	  
   }
   
   
   
 
 public String readFile(String filename)
{
    String content = null;
    File file = new File(filename); //for ex foo.txt
    FileReader reader = null;
    try {
        reader = new FileReader(file);
        char[] chars = new char[(int) file.length()];
        reader.read(chars);
        content = new String(chars);
        reader.close();

    }catch (Exception e) {
        e.printStackTrace();
    }
    return content;
}

 
      public static String decode(String url)  

      {  

                try {  

                     String prevURL="";  

                     String decodeURL=url;  

                     while(!prevURL.equals(decodeURL))  

                     {  

                          prevURL=decodeURL;  

                          decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );  

                     }  

                     return decodeURL;  

                } catch (UnsupportedEncodingException e) {  

                     return "Issue while decoding" +e.getMessage();  

                }  

      }

 
   
   
} 