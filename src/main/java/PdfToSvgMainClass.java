
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.TranscoderSupport;
import org.apache.fop.svg.PDFTranscoder;


import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;



import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;


import org.apache.pdfbox.pdmodel.PDDocument;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.print.*;
import java.io.OutputStreamWriter;


class PdfToSvgMainClass {

    public static void main(String[] args) {


//	String dirPath = "xC:/xampp/htdocs/CV-JS_2_3_6x/drawings/svg/xx";
//	String  pdfFile= "xC:/xampp/tomcat/webapps/CV-JS_2_3_6x/drawings/pdf/TD.pdf";

	String dirPath = args[0];
	String pdfFile = args[1];
	String pdfFileName = args[2];

		dirPath = dirPath.replaceAll("%20", " ");  
		pdfFile = pdfFile.replaceAll("%20", " ");  
		pdfFileName = pdfFileName.replaceAll("%20", " ");  
	
	
	try{

		// 2018-03-20
		//org.apache.fop.svg.PDFTranscoder.KEY_DEVICE_RESOLUTION = 300.0;

	
		PDDocument document = PDDocument.load( pdfFile );
		DOMImplementation domImpl =
			GenericDOMImplementation.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document svgDocument = domImpl.createDocument(svgNS, "svg", null);

		SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(svgDocument);
		ctx.setEmbeddedFontsOn(true);
		
		
		// test default 2018-12-13
//		ctx.setEmbeddedFontsOn(false);
		

		// Ask the test to render into the SVG Graphics2D implementation.

		// i = 0 -> i = 1  ?       2018-03-27
		
			for(int i = 1 ; i < document.getNumberOfPages() ; i++){
				String svgFName = dirPath+"/"+ pdfFileName + "_" +(i+1)+"_org.svg";
				(new File(svgFName)).createNewFile();

				System.out.println("created: "+svgFName);
				
				// Create an instance of the SVG Generator.
				SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx,false);
				
				
//				SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx, Options.getBoolean("EMBEDDED_SVG_FONTS", true));				
//				SVGGraphics2D svgGenerator = new SVGGraphics2D(ctx, true);				
				
				
				Printable page  = document.getPrintable(i);
				
				page.print(svgGenerator, document.getPageFormat(i), i);
				boolean useCSS = true;
				
				//Writer out = new OutputStreamWriter(System.out, "UTF-8");
				svgGenerator.stream(svgFName, useCSS);
				
				
				String content = readFile(svgFName);				
				String noCrispEdges = content.replaceAll("shape-rendering=\"crispEdges\"", "");
				noCrispEdges = noCrispEdges.replaceAll("shape-rendering:crispEdges;", "");
				// now we make the real file
				svgFName = dirPath+"/"+ pdfFileName + "_" +(i+1)+".svg";
				
				// overwrite the old file
				File f = new File(svgFName);
				PrintWriter pw = new PrintWriter(new FileWriter(f));
				pw.print(noCrispEdges);
				pw.close();


				svgFName = dirPath+"/"+ pdfFileName + "_" +(i+1)+"_org.svg";
				try {
					File filePath = new File(svgFName);
					filePath.delete();
				} catch (Exception x) {
					System.out.println(x);
				} 

												
			}
	}
	catch(Exception e){

		System.out.println("Exception: "+e);

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

