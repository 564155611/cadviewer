import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ListDirectoryContent extends HttpServlet {


    public final void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        String dirPath = request.getParameter("directory");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;utf-8");
        PrintWriter outPut = response.getWriter();

        try {

            File dir = new File(dirPath);
            String[] files = dir.list();
            if (files.length == 0) {
                System.out.println("The directory is empty");
            } else {
                for (String aFile : files) {

                    if (!(aFile.indexOf(".rw") > 0))
                        outPut.println("<br>" + aFile);
                }
            }

        } catch (Exception e) {

            outPut.println("Directory Listing  exception: " + e);

        }
        outPut.close();

    }

    public final void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


        String dirPath = request.getParameter("directory");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;utf-8");
        PrintWriter outPut = response.getWriter();

        try {

            File dir = new File(dirPath);
            String[] files = dir.list();
            if (files.length == 0) {
                System.out.println("The directory is empty");
            } else {
                for (String aFile : files) {

                    if (!(aFile.indexOf(".rw") > 0))
                        outPut.println("<br>" + aFile);
                }
            }

        } catch (Exception e) {

            outPut.println("Directory Listing  exception: " + e);

        }
        outPut.close();

    }

}