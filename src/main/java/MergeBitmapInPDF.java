  
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
  
import java.awt.image.BufferedImage;
  
import java.io.File; 
import java.io.IOException;
import java.io.*; 
import java.net.*;
import javax.imageio.ImageIO;


public class MergeBitmapInPDF
 {
   public static void main(String[] args) throws IOException {

    try {



   
		System.out.println("1: ");
   
		String imagePath = args[0];
		String fileName = args[1];

		System.out.println("2: ");

		imagePath = imagePath.replaceAll("%20", " ");  
		fileName = fileName.replaceAll("%20", " ");  

		InputStream in = new FileInputStream(imagePath);
        BufferedImage bimg = ImageIO.read(in);
        float width = bimg.getWidth();
        float height = bimg.getHeight();
        in.close();
 		
			
		System.out.println("3: ");

		//Creating PDF document object 
		PDDocument doc = new PDDocument();

		 //Creating a blank page 
		 PDPage blankPage = new PDPage(new PDRectangle(width, height));

		System.out.println("4: ");
		 
		 //Adding the blank page to the document
		 doc.addPage( blankPage );

		 
		System.out.println("5: ");
		 
		//Retrieving the page
		PDPage page = doc.getPage(0);
		
		System.out.println("6: ");
		
		
		//Creating PDImageXObject object
		PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath,doc);

		//creating the PDPageContentStream object
		PDPageContentStream contents = new PDPageContentStream(doc, page);

		//Drawing the image in the PDF document
		contents.drawImage(pdImage, 0, 0);

		//Closing the PDPageContentStream object
		contents.close();		

		//Saving the document
		doc.save(fileName);

		//Closing the document
		doc.close();
	
    }catch (Exception e) {
        e.printStackTrace();
    }
   
   
   
	  
   }
   
   
   
   
   	public static String readFile(String filename)
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


}