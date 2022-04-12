
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ListDirectoryContent extends HttpServlet {


public final void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException
{
       String dirPath = request.getParameter("directory");

		PrintWriter outPut = response.getWriter();

		try{

			File dir = new File(dirPath);
			String[] files = dir.list();
			if (files.length == 0) {
				System.out.println("The directory is empty");
			} else {
				for (String aFile : files) {

					if (!(aFile.indexOf(".rw")>0))
						outPut.println("<br>"+aFile);
				}
			}

		}
		catch(Exception e){

				outPut.println("Directory Listing  exception: "+e);

		}
		outPut.close();

}

public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String dirPath = request.getParameter("directory");

		PrintWriter outPut = response.getWriter();

		try{

			File dir = new File(dirPath);
			String[] files = dir.list();
			if (files.length == 0) {
				System.out.println("The directory is empty");
			} else {
				for (String aFile : files) {

					if (!(aFile.indexOf(".rw")>0))
						outPut.println("<br>"+aFile);
				}
			}

		}
		catch(Exception e){

				outPut.println("Directory Listing  exception: "+e);

		}
		outPut.close();

}

}