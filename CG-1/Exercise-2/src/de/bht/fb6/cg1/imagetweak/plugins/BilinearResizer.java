package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.DimensionModel;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/**
 * Plugin that resizes the image using bilinear interpolation
 * Note: This plugin does not work correctly, but makes funny effects ;)
 * @author M. Fischboeck
 *
 */
public class BilinearResizer implements PlugIn {

	private final ImageModel model;
	

	/**
	 * Constructor providing the image model to work on
	 * @param model The image model to work on
	 */
	public BilinearResizer(final ImageModel model) {
		this.model = model;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ij.plugin.PlugIn#run(java.lang.String)
	 */
	@Override
	public void run(String arg0) {
		
		DimensionModel dm = this.model.getDimensions();
		ImagePlus output = NewImage.createRGBImage("", dm.getWidth(), dm.getHeight(), 1, NewImage.FILL_BLACK);
		
		ImageProcessor ipOld = this.model.getImage().getProcessor();
		ImageProcessor ipNew = output.getProcessor();
		
		final int[] pixOld = (int[]) ipOld.getPixels();
		final int[] pixNew = (int[]) ipNew.getPixels();
		
		int oldw = this.model.getImage().getWidth();
		int oldh = this.model.getImage().getHeight();
		
		for (int x = 0; x < dm.getWidth(); ++x) {
			for (int y = 0; y < dm.getHeight(); ++y) {
				
				// calculate the source of the pixel
				double posx = (x * (dm.getWidth() - 1.d)) / (oldw - 1.d);
				double posy = (y * (dm.getHeight() -1.d)) / (oldh - 1.d);
				
				
				double nx = posx - Math.floor(posx);
				double ny = posy - Math.floor(posy);
				
				int fy = (int) Math.floor(posy);
				int fx = (int) Math.floor(posx);
				int cy = (int) Math.ceil(posy);
				int cx = (int) Math.ceil(posx);
				
				// I(floor(x), floor(y))
				int rf = (pixOld[fy + fx *  oldw] & 0x00FF0000) >> 16;
				int gf = (pixOld[fy + fx *  oldw] & 0x0000FF00) >> 8;
				int bf = (pixOld[fy + fx *  oldw] & 0x000000FF);
				
				// I(ceil(x), ceil(y))
				int rc = (pixOld[cy + cx * oldw] & 0x00FF0000) >> 16;
				int gc = (pixOld[cy + cx + oldw] & 0x0000FF00) >> 8;
				int bc = (pixOld[cy + cx + oldw] & 0x000000FF);
				
				// I(floor(x), ceil(y))
				int rcf = (pixOld[cy + fx + oldw] & 0x00FF0000) >> 16;
				int gcf = (pixOld[cy + fx + oldw] & 0x0000FF00) >> 8;
				int bcf = (pixOld[cy + fx + oldw] & 0x000000FF);
				
				double ar = (1.d - nx * rf + nx * rc);
				double ag = (1.d - nx * gf + nx * gc);
				double ab = (1.d - nx * bf + nx * bc);
				
				double br = (1.d - nx * rcf + nx * rc);
				double bg = (1.d - nx * gcf + nx * gc);
				double bb = (1.d - nx * bcf + nx * bc);
				
				int pr = (int) (1.d - ny * ar + ny * br);
				int pg = (int) (1.d - ny * ag + ny + bg);
				int pb = (int) (1.d - ny * ab + ny + bb);
				
				pixNew[y + x * dm.getWidth()] = 0x00 | pr << 16 | pg << 8 | pb;
			}
		}
		
		this.model.setImage(output);
	}

}
