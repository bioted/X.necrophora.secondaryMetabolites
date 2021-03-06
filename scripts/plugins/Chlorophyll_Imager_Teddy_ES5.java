import ij.*;
import ij.gui.*;
import java.awt.Label;
import ij.measure.*;
import ij.plugin.*;
import ij.plugin.filter.*;
import ij.plugin.frame.*;
import ij.process.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

/**
* This plugin measures red, green and blue channels of an RGB image,
* and estimates chlorophyll contents of Arabidopsis seedlings grown
* on plates for one to two weeks. The detailed method was described
* in Ying L et al.(submitted).
*
*
* This plugin is licensed under the Creative Commons CC BY 3.0 US license,
* see https://creativecommons.org/licenses/by/3.0/us/
*
* Code by Tyson Hedrick and Amanda Lohmann*/


public class Chlorophyll_Imager implements PlugIn {

	int gridHeight;
	int gridWidth;
	double rScale;
	double gScale;
	double bScale;
	double rCoeff;
	double gCoeff;
	double bCoeff;
	double rgbConst;
	boolean makeGrayscale;
	ImagePlus imp2;
	ImagePlus imp;
	ImagePlus imp3;
	Roi[] rois;
	RoiManager manager;

	public void run(String arg) {
		this.imp2 = IJ.getImage();	// grab the open image
		this.imp = imp2.duplicate();	// copy of the original color image (for getting a binary mask)
		this.imp3 = imp2.duplicate();	// copy of the original color image (for converting to grayscale)
		
		//initialize processor and pixel array
		ImageProcessor ip2 = this.imp2.getProcessor();
		ip2.convertToRGB(); // we'll need it in color
		int[] pixels = {255,255,255}; // initialize as white pixels
		
		//check if the image has already been analyzed by looking for the blue
		//pixel in the upper left corner set at the completion of a trial
		int[] testPixel = ip2.getPixel(0, 0, pixels);
		if (testPixel[0]==0 && testPixel[1]==0 && testPixel[2] == 255){ //is upper left corner (0,0,255) blue?
			IJ.error("This image appears to already have chlorophyll values! Please reopen the image if you'd like to run this plugin on it again.");
			return;
		}
		
		//get user input (dimensions & color refs) and create ROI list
		startDialog();
		findROIs();
		
		/* process ROI list; extract bounding box center, average pixel values
		* in the red, green, blue channel ignoring pixels with an intensity
		* value that suggests they are part of the background
		*
		* Code based in part on the Calculate_Mean.java example plugin */
		
		ImageProcessor mask = null;
		Rectangle r = null;
		double[][] roiStats = new double[rois.length][4]; // array for roi statistics
		
		for (int i=0; i<rois.length; i++) { // for each ROI in the list
			imp2.setRoi(rois[i]); // push into the color image
			
			Roi roi = imp2.getRoi(); // grab the current ROI
			
			if (roi!=null && !roi.isArea()) roi = null; // error checking
			mask = roi!=null?roi.getMask():null; // get a mask of the roi
			r = roi!=null?roi.getBounds():new Rectangle(0,0,ip2.getWidth(),ip2.getHeight()); // get bounding rectangle
			double redSum = 0;
			double greenSum = 0;
			double blueSum = 0;
			int count = 0;
			
			/* loop over the dimensions of the rectangle, accumulating pixel
			* values which are masked and not (by intensity) part of the
			* background. Note that we do this instead of using builtin methods
			* because the ROIs have been dilated to merge nearby regions and
			* therefore may contain some white or nearly white background
			* pixels. */
			for (int y=0; y<r.height; y++) {
				for (int x=0; x<r.width; x++) {
					if (mask==null||mask.getPixel(x,y)!=0 ) {
						pixels = ip2.getPixel(x+r.x, y+r.y, pixels);
						if (pixels[0]<=200 && pixels[1]<= 200 && pixels[2] <= 200){
							count++;
							redSum += pixels[0];
							greenSum += pixels[1];
							blueSum += pixels[2];
						} // end of not-background if
					} // end of in-mask if
				} // end of per-x loop
			} // end of per-y loop
			
			roiStats[i][0] = r.x+r.width/2; // x position (column)
			roiStats[i][1] = r.y+r.height/2; // y position (row)
			roiStats[i][2] = i; // roi index
			
			// note that the magic numbers 243, 243 & 242 are the rgb values for
			// white in the colorchecker chart
			roiStats[i][3] = Math.exp(rCoeff*(redSum/count)*243/rScale + gCoeff*(greenSum/count)*243/gScale + bCoeff*(blueSum/count)*242/bScale + rgbConst); // chlorophyll estimate
			Math.exp(rCoeff*pixels[0]*243/rScale + gCoeff*pixels[1]*243/gScale + bCoeff*pixels[2]*242/bScale + rgbConst);
		} // end of per-roi loop
		
		/* create the expected locations of the analysis grid and assign each grid cell to an ROI result */
		double[] gridXCenters = new double[gridWidth]; // column
		double[] gridYCenters = new double[gridHeight]; // row
		
		for (int i=0; i<gridWidth; i++){ // column
			gridXCenters[i] = i*(double)imp2.getWidth()/gridWidth+((double)imp2.getWidth()/gridWidth)/2;
		}
		
		for (int i=0; i<gridHeight; i++){ // row
			gridYCenters[i] = i*(double)imp2.getHeight()/gridHeight+((double)imp2.getHeight()/gridHeight)/2;
		}
		
		/* create output array by matching grid centers to nearest roiStats entry */
		double[][] chOut = new double[gridHeight][gridWidth];
		for (int i=0; i<gridHeight; i++) {  // row
			for (int j=0; j<gridWidth; j++) {  // column
				double d0 = Double.MAX_VALUE;
				double d1 = Double.MAX_VALUE;
				for (int k=0; k<rois.length; k++) {
					d1 = Math.sqrt(Math.pow(roiStats[k][0]-gridXCenters[j],2) + Math.pow(roiStats[k][1]-gridYCenters[i],2));
					if (d1 < d0) {
						d0 = d1;
						chOut[i][j]=roiStats[k][3];
					}
				}
			}
		}
		
		/** Annotate image on screen */
		ip2.setFont(new Font("SansSerif", Font.PLAIN, 48));
		ip2.setColor(Color.red);
		for (int i=0; i<gridHeight; i++) { // row
			for (int j=0; j<gridWidth; j++) { // column
				ip2.moveTo((int) gridXCenters[j],(int) gridYCenters[i]);
				ip2.drawString(String.format("%.1f",chOut[i][j]));
			}
		}
		imp2.updateAndDraw();
		
		tagCorner(); //add blue pixels to upper left corner to mark image as analyzed
		
		showResults(chOut);  // Create and display results table
		
		// create and show the grayscale direct encode of Chlorophyll if called for in the gui
		if (makeGrayscale) {
			ImageProcessor  ip3 = this.imp3.getProcessor().convertToFloat();
			double[][] grayArray = new double[imp3.getWidth()][imp3.getHeight()]; // array for grayscale output
			double max = 0; // max grayscale value
			
			// set a new gray value based on the rgb value in each pixel
			for (int i=0; i<imp3.getWidth(); i++) {
				for (int j=0; j<imp3.getHeight(); j++) {
					pixels=ip3.getPixel(i,j,pixels);
					grayArray[i][j]=Math.exp(rCoeff*pixels[0]*243/rScale + gCoeff*pixels[1]*243/gScale + bCoeff*pixels[2]*242/bScale + rgbConst);
					if (grayArray[i][j]>max){
						max = grayArray[i][j];
					}
				}
			} 
			
			// normalize to 0-255
			for (int i=0; i<imp3.getWidth(); i++) {
				for (int j=0; j<imp3.getHeight(); j++) {
					grayArray[i][j]=255*grayArray[i][j]/max;
				}
			}
			
			// convert the image to grayscale
			ImageConverter ic3 = new ImageConverter(imp3);
			ic3.convertToGray8();
			ip3 = this.imp3.getProcessor();
			
			for (int i=0; i<imp3.getWidth(); i++) {
				for (int j=0; j<imp3.getHeight(); j++) {
					ip3.putPixel(i,j,(int)grayArray[i][j]);
				}
			}
			
			// show the new grayscale image
			String newName=imp2.getTitle();
			newName= newName.substring(0, newName.lastIndexOf("."));
			//newName=newName += "_normalized_chl";
			newName=newName += String.format("__chl=pixelValue_multiplied_by_%.1f",max/255);
			ImagePlus imp4= new ImagePlus(newName, ip3);
			
			imp4.show();
		}
		
		// clean up
		this.imp.flush();
		this.imp.close();
		
		try {
			this.manager.reset();  // not always available but needed when present (yes in 1.50i, no in 1.48k)
		} catch (java.lang.NoSuchMethodError e) {
			// do nothing
		} finally {
			// do nothing
		}

		this.manager.close();
		
		
		
	} // end of run method

	public void startDialog(){ //user dialog to get dimensions and color refs
		GenericDialog gd = new GenericDialog("Chlorophyll Imager inputs");
		gd.addMessage("-- Input image grid layout specification --");
		gd.addNumericField("Number of data rows: ",6,0);
		gd.addNumericField("Number of data columns: ",6,0);
		gd.addMessage("-- RGB value of the ColorChecker white panel --");
		gd.addNumericField("r =",243,3);
		gd.addNumericField("g =",243,3);
		gd.addNumericField("b =",242,3);
		gd.addCheckbox("Make normalized grayscale image",false);
		gd.addMessage("-- Coefficient values for chlorophyll estimation --");
		gd.addNumericField("a1 =",-0.028,4);
		gd.addNumericField("a2 =",0.019,4);
		gd.addNumericField("a3 =",-0.003,4);
		gd.addNumericField("a4 =",5.78,3);
		gd.addMessage("Chlorophyll Imager v1.0");
		gd.addMessage("see Ying L et al (submitted) for details");
		gd.showDialog();
		
		// process user inputs
		if (gd.wasCanceled()) {
			IJ.error("Chlorophyll Imager plugin canceled!");
			return;
		}
		
		this.gridHeight = (int) gd.getNextNumber();  // row
		this.gridWidth = (int) gd.getNextNumber();  // column
		this.rScale = gd.getNextNumber();
		this.gScale = gd.getNextNumber();
		this.bScale = gd.getNextNumber();
		this.rCoeff = gd.getNextNumber();
		this.gCoeff = gd.getNextNumber();
		this.bCoeff = gd.getNextNumber();
		this.rgbConst = gd.getNextNumber();
		this.makeGrayscale = gd.getNextBoolean();
	}

	public void findROIs(){ //create ROI list
		//convert to binary
		ImageConverter ic = new ImageConverter(this.imp);
		ic.convertToGray8();
		this.imp.updateAndDraw();
		ImageProcessor ip = imp.getProcessor();
		ip.autoThreshold();
		
		/* dilate thrice to help combine nearby regions into a single object
	for particle identification */
		ip.dilate();
		ip.dilate();
		ip.dilate();
		imp.updateAndDraw();
		
		// get ROI list by running ParticleAnalyzer
		double roiMinSize = 150; // minimum size in pixels
		double roiMaxSize = Double.MAX_VALUE;
		this.manager = new RoiManager(true);
		ParticleAnalyzer.setRoiManager(manager);
		ParticleAnalyzer pa = new ParticleAnalyzer(0, 0, new ResultsTable(), roiMinSize, roiMaxSize, 0, 1);
		pa.analyze(imp);
		this.rois = manager.getRoisAsArray();
	}

	public void tagCorner(){ //set 2x2 blue square in upper left corner to mark as analyzed
		ImageProcessor ip3 = imp2.getProcessor();
		Roi roi = new Roi(0, 0, 2, 2); // x, y, width, height of the rectangle
		ip3.setRoi(roi);
		ip3.setColor(Color.blue);
		ip3.fill();
		imp2.updateAndDraw();
	}

	public void showResults(double[][] chOut){
		ResultsTable rt = new ResultsTable();
		for (int i=0; i<gridHeight; i++) {
			for (int j=0; j<gridWidth; j++) {
				rt.incrementCounter();
				rt.addValue("Row",String.format("%.0f",(double)i+1));
				rt.addValue("Column",String.format("%.0f",(double)j+1));
				rt.addValue("chl",chOut[i][j]);
			}
		}
		rt.show("Chlorophyll content");
	}

} // end of plugin class
