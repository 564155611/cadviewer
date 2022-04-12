
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets.*;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;

public class LoadServlet extends HttpServlet {


public final void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException
{

        String fileName = request.getParameter("file");
        fileName =  fileName.trim();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
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

public final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName = request.getParameter("file");
        fileName =  fileName.trim();
		response.setContentType("APPLICATION/OCTET-STREAM");
		// PrintWriter out = response.getWriter();
		OutputStream outputstream=response.getOutputStream();

		boolean cvjs_debug = false; 

		File fdf = null;
		FileOutputStream fileOut = null;

		byte[] contentInBytes;
		String stringContent;

		//We make a log file in every case!						
		fdf = File.createTempFile("LoadServlet-log_",".txt");
		fileOut = new FileOutputStream( fdf);

		if (cvjs_debug){
			stringContent = " /n "+fileName;
			contentInBytes	= stringContent.getBytes();
			fileOut.write(contentInBytes);
		}

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
						outputstream.write((byte) c);
					}
				} while(c != -1);

			}
			else{
			
				final int bufferSize = 1024;
				final char[] buffer = new char[bufferSize];
				final StringBuilder out = new StringBuilder();
				Reader in = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
				int charsRead;
				while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
					out.append(buffer, 0, charsRead);
				}
				stringContent = out.toString();			
				contentInBytes	= stringContent.getBytes("UTF-8");
				outputstream.write(contentInBytes);

			
				if (cvjs_debug){
					stringContent = out.toString();			
					contentInBytes	= stringContent.getBytes("UTF-8");
					fileOut.write(contentInBytes);	
				}
			

			}
						
		fileOut.close();
		
		}
		catch (Exception e){
			//out.print("null");
			String error = "Error";
			outputstream.write(error.getBytes("UTF-8"));
		}
}


private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

public static final Charset UTF_8 = Charset.forName("UTF-8");

String decodeUTF8(byte[] bytes) {
    return new String(bytes, UTF8_CHARSET);
}

byte[] encodeUTF8(String string) {
    return string.getBytes(UTF8_CHARSET);
}

public byte[] readByteArray(String filename)
{
	byte[] bytes = null;
    File file = new File(filename); //for ex foo.txt
    FileReader reader = null;
    try {
        reader = new FileReader(file);
        char[] chars = new char[(int) file.length()];
        reader.read(chars);
		bytes = toBytes(chars);

    }catch (Exception e) {
        e.printStackTrace();
    }
    return bytes;
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
		byte[] bytes = toBytes(chars);
        content = new String(bytes, UTF_8);
        reader.close();

    }catch (Exception e) {
        e.printStackTrace();
    }
    return content;
}


byte[] toBytes(char[] chars) {
  CharBuffer charBuffer = CharBuffer.wrap(chars);
  ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
  byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
            byteBuffer.position(), byteBuffer.limit());
  Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
  return bytes;
}


}