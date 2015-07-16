package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.HSVColor;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import de.bht.fb6.cg1.imagetweak.model.YUVColor;
import de.bht.fb6.cg1.imagetweak.model.BCModel.Channel;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ColorProcessor;

/**
 * Plugin implementation to change the brightness on the image
 * @author M. Fischboeck
 *
 */
public class BrightnessPlugin implements PlugIn {

	private ImageModel model;
	
	/**
	 * Constructor specifying the model to work on
	 * @param model The image model to work on
	 */
	public BrightnessPlugin(final ImageModel model) {
		this.model = model;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see ij.plugin.PlugIn#run(java.lang.String)
	 */
	@Override
	public void run(String arg0) {
		
		// get pixels and calculate the new brightness
		ColorProcessor proc = (ColorProcessor) this.model.getImage().getProcessor();
		
		
		
		int[] px = (int[]) proc.getPixels();
		
		// create reference image on the stack
		int[] refpixels;
		int stackSize = this.model.getStack().size();
		if (stackSize == 0) {
			ImagePlus refImage = this.model.getImage().duplicate();
			refpixels = (int[]) refImage.getProcessor().getPixels();
			this.model.getStack().add(refImage);
			
		} else {
			ImagePlus refImage = this.model.getStack().peek();
			refpixels = (int[]) refImage.getProcessor().getPixels();
		}
		
		
		// brightness in RGB - Mode
		if (model.getBrightnessAndContrast().getChannel().equals(Channel.RGB)) {
		
			final float h = (model.getBrightnessAndContrast().getBrightness() / 50.0f) * 127;
			for (int x=0; x < proc.getPixelCount(); ++x) {
				
				int r = (px[x] & 0x00FF0000) >> 16;
				int g = (px[x] & 0x0000FF00) >> 8;
				int b = (px[x] & 0x000000FF);
				
				int rr = (refpixels[x] & 0x00FF0000) >> 16;
				int rg = (refpixels[x] & 0x0000FF00) >> 8;
				int rb = refpixels[x] & 0x000000FF;
				
				r = rr + (int) h;
				if (r < 0) r = 0;
				else if (r > 255) r = 255;
				
				g = rg + (int) h;
				if (g < 0) g = 0;
				else if (g > 255) g = 255;
				
				b = rb + (int) h;
				if (b < 0) b = 0;
				else if (b > 255) b = 255;
				
				px[x] = 0xFF << 24 | r << 16 | g << 8 | b;
			}
		}
		
		// brightness in HSV Mode
		if (model.getBrightnessAndContrast().getChannel().equals(Channel.HSV)) {
			
			final float h = (model.getBrightnessAndContrast().getBrightness() / 50.0f) * 127.f;
			for (int x=0; x < proc.getPixelCount(); ++x) {
				
				int r = (px[x] & 0x00FF0000) >> 16;
				int g = (px[x] & 0x0000FF00) >> 8;
				int b = (px[x] & 0x000000FF);
			
				int rr = (refpixels[x] & 0x00FF0000) >> 16;
				int rg = (refpixels[x] & 0x0000FF00) >> 8;
				int rb = refpixels[x] & 0x000000FF;
			
				/* Using java.awt.Color */
				/*
				float[] param = new float[3];
				param = Color.RGBtoHSB(r, g, b, param);
				
				float[] rparam = new float[3];
				rparam = Color.RGBtoHSB(rr, rg, rb, rparam);
				
				param[2] = rparam[2] + h;
				if (param[2] < 0) param[2] = 0;
				if (param[2] > 1.0f) param[2] = 1.0f;
				
				int rgb = Color.HSBtoRGB(param[0], param[1], param[2]);
				px[x] = rgb;
				*/
				
				HSVColor col = new HSVColor(0, 0, 0);
				col.fromRGB(new int[] {r, g, b});
				
				HSVColor rcol = new HSVColor(0, 0, 0);
				rcol.fromRGB(new int[] {rr, rg, rb});
				
				int[] params = col.getParameters();
				int[] rparams = rcol.getParameters();
				
				params[2] = rparams[2] + (int) h;
				if (params[2] < 0) params[2] = 0;
				if (params[2] > 255) params[2] = 255;
				
				col.setParameters(params);
				int[] rgb = col.toRGB();
				px[x] = 0xFF << 24 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
			}
		}
		
		// brightness in YUV mode
		if (model.getBrightnessAndContrast().getChannel().equals(Channel.YUV)) {
			
			final float h = (model.getBrightnessAndContrast().getBrightness() / 50.0f) * 127.0f;

			for (int x=0; x < proc.getPixelCount(); ++x) {

				int r = (px[x] & 0x00FF0000) >> 16;
				int g = (px[x] & 0x0000FF00) >> 8;
				int b = (px[x] & 0x000000FF);
		
				int rr = (refpixels[x] & 0x00FF0000) >> 16;
				int rg = (refpixels[x] & 0x0000FF00) >> 8;
				int rb = refpixels[x] & 0x000000FF;

				YUVColor yuvc = new YUVColor(0, 0, 0);
				yuvc.fromRGB(new int[] {r, g, b});
				
				YUVColor ryuvc = new YUVColor(0, 0, 0);
				ryuvc.fromRGB(new int[] {rr, rg, rb});
				
				int[] yuv = yuvc.getParameters();
				int[] ryuv = ryuvc.getParameters();
				
				yuv[0] = ryuv[0] + (int) h;
				if (yuv[0] < 0) yuv[0] = 0;
				if (yuv[0] > 255) yuv[0] = 255;
				
				yuvc.setParameters(yuv);
				int[] rgb = yuvc.toRGB();
				
				px[x] = 0xFF << 24 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
			}
		}
	}
}
