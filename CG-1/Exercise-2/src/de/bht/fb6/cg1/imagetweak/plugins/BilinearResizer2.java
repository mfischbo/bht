package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.DimensionModel;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.PlugIn;

/**
 * Plugin implementation that resizes an image using bilinear interpolation
 * @author M. Fischboeck
 *
 */
public class BilinearResizer2 implements PlugIn {

	private ImageModel model;
	
	/**
	 * Constructor specifying the model to work on
	 * @param model The image model to work on
	 */
	public BilinearResizer2(final ImageModel model) {
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
		
		final int[] pixOld = (int[]) this.model.getImage().getProcessor().getPixels();
		final int[] pixNew = (int[]) output.getProcessor().getPixels();
		
		int oldw = this.model.getImage().getWidth();
		int oldh = this.model.getImage().getHeight();

		double ratioX = (oldw - 1.d) / (dm.getWidth() - 1.d);
		double ratioY = (oldh - 1.d) / (dm.getHeight() -1.d);
		
		for (int ix = 0; ix < dm.getWidth(); ++ix) {
			for (int iy = 0; iy < dm.getHeight(); ++iy) {
				
				double x = ratioX * ix;
				double y = ratioY * iy;
				
				double nx = x - Math.floor(x);
				double ny = y - Math.floor(y);
				
				int fy = (int) Math.floor(y);
				int fx = (int) Math.floor(x);
				int cy = (int) Math.ceil(y);
				int cx = (int) Math.ceil(x);
				
				// I(floor(x), floor(y))
				int color = pixOld[fy * oldw + fx];
				int fr = (color & 0x00FF0000) >> 16;
				int fg = (color & 0x0000FF00) >> 8;
				int fb = (color & 0x000000FF);
				
				// I(ceil(x), ceil(y))
				color = pixOld[cy * oldw + cx];
				int cr = (color & 0x00FF0000) >> 16;
				int cg = (color & 0x0000FF00) >> 8;
				int cb = (color & 0x000000FF);
				
				// I(floor(x), ceil(y))
				color = pixOld[cy * oldw + fx];
				int mr = (color & 0x00FF0000) >> 16;
				int mg = (color & 0x0000FF00) >> 8;
				int mb = (color & 0x000000FF);
				
				// calculate a on all three channels
				double ra = (1 - nx) * fr + nx * mr;
				double ga = (1 - nx) * fg + nx * mg;
				double ba = (1 - nx) * fb + nx * mb;
				
				// calculate b on all three channels
				double rb = (1 - nx) * mr + nx * cr;
				double gb = (1 - nx) * mg + nx * cg;
				double bb = (1 - nx) * mb + nx * cb;
				
				// calculate value for the new point
				int pr = (int) ((1 - ny) * (ra + ny * rb));
				int pg = (int) ((1 - ny) * (ga + ny * gb));
				int pb = (int) ((1 - ny) * (ba + ny * bb));
				
				pixNew[iy * dm.getWidth() + ix] = 0x00 | pr << 16 | pg << 8 | pb;
			}
		}
		
		this.model.setImage(output);
	}

}
