package de.bht.fb6.cg1.imagetweak.model;

import java.util.Stack;

import ij.ImagePlus;

/**
 * Model container class that holds references to all sub model classes.
 * @author M. Fischboeck
 *
 */

public class ImageModel {

	private ImagePlus		image;
	private RGBRemovals		rgbRemovals;
	private CMYRemovals		cmyRemovals;
	private YUVRemovals		yuvRemovals;
	private BCModel			bcModel;
	private DimensionModel	dimensions;
	private HistogramOptions	hOptions;
	
	private Stack<ImagePlus>	stack;
	
	
	/**
	 * Default constructor
	 */
	public ImageModel() {
		this.image = null;
		this.rgbRemovals = new RGBRemovals();
		this.cmyRemovals = new CMYRemovals();
		this.yuvRemovals = new YUVRemovals();
		this.bcModel     = new BCModel();
		this.stack = new Stack<ImagePlus>();
		this.hOptions = new HistogramOptions();
		
	}
	
	/**
	 * Sets the image that will be manipulated
	 * @param image The image to be manipulated - not null
	 */
	public void setImage(ImagePlus image) {
		if (image == null)
			throw new IllegalArgumentException("Image can't be null");
		
		this.image = image;
		
		this.dimensions = new DimensionModel(image.getWidth(), image.getHeight(), false);
	}
	
	/**
	 * Returns the image to manipulate
	 * @return The image to manipulate
	 */
	public ImagePlus getImage() {
		return this.image;
	}
	
	/**
	 * Returns the RGB Removal selection model
	 * @return The model
	 */
	public RGBRemovals getRGBRemovals() {
		return this.rgbRemovals;
	}
	
	/**
	 * Returns the CMYRemoval selection model
	 * @return The model
	 */
	public CMYRemovals getCMYRemovals() {
		return this.cmyRemovals;
	}
	
	/**
	 * Returns the YUV Removal selection model
	 * @return The model
	 */
	public YUVRemovals getYUVRemovals() {
		return this.yuvRemovals;
	}
	
	/**
	 * Returns the brightness and contrast selection model
	 * @return The model
	 */
	public BCModel getBrightnessAndContrast() {
		return this.bcModel;
	}
	
	/**
	 * Returns the internal stack for non-destructive processing
	 * @return The internal image stack
	 */
	public Stack<ImagePlus> getStack() {
		return this.stack;
	}
	
	/**
	 * Returns the selections for the historgram generation
	 * @return The model
	 */
	public HistogramOptions getHistogramOptions() {
		return this.hOptions;
	}
	
	/**
	 * Returns the dimension model of the image
	 * @return The model
	 */
	public DimensionModel getDimensions() {
		return this.dimensions;
	}
}
