
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class DeleteServlet extends HttpServlet {


public final void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException
{

        String fileName = request.getParameter("file");


		File fdf = null;
		String txtSource = "";
		byte[] bArr = null;
		FileOutputStream fileOut = null;
		String str;


		// delete file
		try
		{
    		File file = new File(fileName);
    		file.delete();
		}
		catch(Exception ioe)
		{
			fdf = File.createTempFile("DeleteServlet-log-err",".txt");
			fileOut = new FileOutputStream( fdf );
			str= fdf.getPath();
			txtSource = "Delete Servlet "+ioe+"  \n";
			bArr = txtSource.getBytes();
			fileOut.write(bArr);
			fileOut.close();
		}


}

public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		File fdf = null;
		String txtSource = "";
		byte[] bArr = null;
		FileOutputStream fileOut = null;
		String str;

		/*****        DEBUG
		fdf = File.createTempFile("DeleteServlet-log",".txt");
		fileOut = new FileOutputStream( fdf );
		str= fdf.getPath();
		txtSource = "Delete Servlet "+fileName +"   "+fileChunk+" \n";
		bArr = txtSource.getBytes();
		fileOut.write(bArr);
		fileOut.close();
		DEBUG  *****/
}

}