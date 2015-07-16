package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/**
 * PlugIn to remove color channels separately from the image using RGB Colorspace.
 * @author M. Fischboeck
 *
 */
public final class RGBComponentRemover implements PlugIn {


	private final ImageModel model;
	
	/**
	 * Constructor providing the underlaying image model
	 * @param model
	 */
	public RGBComponentRemover(ImageModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * @see ij.plugin.PlugIn#run(java.lang.String)
	 */
	@Override
	public void run(String arg0) {
				
		ImageProcessor ip = model.getImage().getProcessor();
		int[] px = (int[]) ip.getPixels();
		
		final boolean rm = model.getRGBRemovals().isRed();
		final boolean gm = model.getRGBRemovals().isGreen();
		final boolean bm = model.getRGBRemovals().isBlue();
		
		for (int x=0; x < px.length; ++x) {
			
			int r = (px[x] & 0x00FF0000) >> 16;
			int g = (px[x] & 0x0000FF00) >> 8;
			int b = px[x] & 0x000000FF;
			
			if (rm) r = 0;
			if (gm) g = 0;
			if (bm) b = 0;
			
			px[x] = 0xFF << 24 | r << 16 | g << 8 | b;
		}
		ip.setPixels(px);
	}
}
