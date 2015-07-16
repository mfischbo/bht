package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.model.YUVColor;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/**
 * Plugin to remove YUV Channels separately using the YUV Colorspace
 * @author M. Fischboeck
 *
 */
public class YUVComponentRemover implements PlugIn {

	private ImageModel model;

	/**
	 * Constructor specifying the model to work on
	 * @param model The image model to work on
	 */
	public YUVComponentRemover(ImageModel model) {
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
		
		final boolean ym = model.getYUVRemovals().isLumina();
		final boolean um = model.getYUVRemovals().isuChroma();
		final boolean vm = model.getYUVRemovals().isvChroma();
		
		for (int x=0; x < px.length; ++x) {
			
			int r = (px[x] & 0x00FF0000) >> 16;
			int g = (px[x] & 0x0000FF00) >> 8;
			int b = px[x] & 0x000000FF;
			
			YUVColor yuv = new YUVColor(0, 0, 0);
			yuv.fromRGB(new int[] {r, g, b});
			
			int[] param = yuv.getParameters();
			if (ym) param[0] = 0;
			if (um) param[1] = 0;
			if (vm) param[2] = 0;
			yuv.setParameters(param);
			
			int[] rgb = yuv.toRGB();
			
			px[x] = 0xFF << 24 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
		}
		ip.setPixels(px);
	}

}
