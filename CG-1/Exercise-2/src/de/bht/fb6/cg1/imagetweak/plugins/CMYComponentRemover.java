package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/**
 * Plugin implmentation to remove any of the CMY channels using CMY colorspace
 * @author M. Fischboeck
 *
 */
public class CMYComponentRemover implements PlugIn {

	private ImageModel model;
	
	/**
	 * Constructor specifying the model to work on
	 * @param model The image model to work on
	 */
	public CMYComponentRemover(ImageModel model) {
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
		
		final boolean cm = model.getCMYRemovals().isCyan();
		final boolean mm = model.getCMYRemovals().isMagenta();
		final boolean ym = model.getCMYRemovals().isYellow();
		
		for (int x=0; x < px.length; ++x) {
			
			int c = 255 - (px[x] & 0x00FF0000) >> 16;
			int m = 255 - (px[x] & 0x0000FF00) >> 8;
			int y = 255 - (px[x] & 0x000000FF);
			
			if (cm) c = 255;
			if (mm) m = 255;
			if (ym) y = 255;
			
			px[x] = 0xFF << 24 | c << 16 | m << 8 | y;
		}
		ip.setPixels(px);
	}

}
