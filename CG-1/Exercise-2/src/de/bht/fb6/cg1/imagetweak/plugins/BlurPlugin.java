package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import ij.plugin.PlugIn;


/**
 * Plugin that uses a convolution matrix to process a blur effect on the image
 * @author M. Fischboeck
 *
 */
public class BlurPlugin implements PlugIn {

	private ImageModel model;

	/**
	 * Constructor specifying the model to work on
	 * @param model The image model to work on
	 */
	public BlurPlugin(final ImageModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * @see ij.plugin.PlugIn#run(java.lang.String)
	 */
	@Override
	public void run(String arg0) {

		int w = model.getImage().getWidth();

		final int[] p = (int[]) this.model.getImage().getProcessor()
				.getPixels();

		for (int i = 0; i < p.length; ++i) {

			int divisor = 0;

			int sumR = 0;
			int sumG = 0;
			int sumB = 0;

			int[] kernel = new int[9];
			kernel[0] = i - w - 1;
			kernel[1] = i - w;
			kernel[2] = i - w + 1;
			kernel[3] = i - 1;
			kernel[4] = i;
			kernel[5] = i + 1;
			kernel[6] = i + w - 1;
			kernel[7] = i + w;
			kernel[8] = i + w + 1;

			for (final int c : kernel) {
				if (c < 0 || c >= p.length)
					continue;
				else {
					sumR += (p[c] & 0x00FF0000) >> 16;
					sumG += (p[c] & 0x0000FF00) >> 8;
					sumB += (p[c] & 0x000000FF);
					divisor++;
				}
			}

			int[] nc = new int[3];
			nc[0] = (int) sumR / divisor;
			nc[1] = (int) sumG / divisor;
			nc[2] = (int) sumB / divisor;
			p[i] = 0xFF << 24 | nc[0] << 16 | nc[1] << 8 | nc[2];
		}
	}
}
