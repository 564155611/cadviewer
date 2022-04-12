
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class LoadRedlinesServlet extends HttpServlet {


public final void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException
{

        String fileName = request.getParameter("file");
        fileName =  fileName.trim();
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		/*
		response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		*/
		
		PrintWriter out = response.getWriter();

		try{
			if (fileName.indexOf("http")==0){  // URL

				URL url = new URL(fileName);
				InputStream intemp = url.openStream();
				int counter = 0;
				int c = 0;
				do {
					c = intemp.read();
					counter++;
					//System.out.println("==============> " + c);
					if (c !=-1) {
						out.write((byte) c);
					}
				} while(c != -1);

			}
			else{
//				while (fileName.indexOf("\\")>-1){
//					fileName = fileName.substring(0,fileName.indexOf('\\'))+"/"+fileName.substring(fileName.indexOf('\\')+1);
//				}
//
//		out.println("2 "+fileName);

				String fileContent = readFile(fileName);
				out.print(fileContent);
			}
		}
		catch (Exception e){
			out.print("null");
		}

}

public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName = request.getParameter("file");
        fileName =  fileName.trim();
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		
/*		
		response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
*/

		PrintWriter out = response.getWriter();

		try{
			if (fileName.indexOf("http")==0){  // URL
				URL url = new URL(fileName);
				InputStream intemp = url.openStream();
				int counter = 0;
				int c = 0;
				do {
					c = intemp.read();
					counter++;
					//System.out.println("==============> " + c);
					if (c !=-1) {
						out.write((byte) c);
					}
				} while(c != -1);

			}
			else{
				String fileContent = readFile(fileName);
				out.print(fileContent);
			}
		}
		catch (Exception e){
			out.print("null");
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
       // return(e.printStackTrace());
        return(e.toString());
    }
    return content;
}


}