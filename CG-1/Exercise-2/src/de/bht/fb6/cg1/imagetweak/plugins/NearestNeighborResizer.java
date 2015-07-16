package de.bht.fb6.cg1.imagetweak.plugins;

import de.bht.fb6.cg1.imagetweak.model.DimensionModel;
import de.bht.fb6.cg1.imagetweak.model.ImageModel;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/**
 * Plugin that resizes the image using nearest neighbor algorithm
 * @author M. Fischboeck
 *
 */
public class NearestNeighborResizer implements PlugIn {

	private final ImageModel model;
	
	/**
	 * Constructor specifying the model to work on
	 * @param model The image model to work on
	 */
	public NearestNeighborResizer(final ImageModel model) {
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
		
		for (int x = 0; x < dm.getWidth(); ++x) {
			for (int y = 0; y < dm.getHeight(); ++y) {
				
				final int oldX = (x * ipOld.getWidth()) / dm.getWidth();
				final int oldY = (y * ipOld.getHeight() / dm.getHeight());
				
				pixNew[y * dm.getWidth() + x] = pixOld[ oldY * ipOld.getWidth() + oldX];
				
			}
		}
		output.getProcessor().setPixels(pixNew);
		this.model.setImage(output);
	}
}
