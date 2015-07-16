package de.bht.fb6.cg1.imagetweak.model;


/**
 * Model class to hold the current images properties related to it's size
 * @author M. Fischboeck
 *
 */
public class DimensionModel {

	/* Contstant defining Nearest Neighbor resizing algorithm */
	public static final short RESIZE_NN = 0;
	/* Constant defining Bilinear interpolation resizing */
	public static final short RESIZE_BILINEAR = 1;
	
	private int 	width;
	private int 	height;
	private short	algorithm;	

	private boolean changed;
	
	/**
	 * Constructor specifying all properties on the dimension
	 * @param width The new width of the image
	 * @param height The new height of the image
	 * @param keepAspect States if the aspect should be kept
	 */
	public DimensionModel(final int width, final int height, final boolean keepAspect) {
		this.width = width;
		this.height = height;
		this.changed = false;
		this.algorithm = RESIZE_BILINEAR;
	}

	/**
	 * Returns the width of the image
	 * @return The images width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the new width of the image 
	 * @param width The width of the image > 0
	 */
	public void setWidth(final int width) {
		if (width < 0)
			throw new IllegalArgumentException("Image width must be > 0");
		
		if (width != this.width) {
			this.width = width;
			this.changed = true;
		}
	}

	/**
	 * Returns the height of the new image
	 * @return The images height
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * Sets the height of the new image
	 * @param height The images height > 0
	 */
	public void setHeight(final int height) {
		if (height < 0)
			throw new IllegalArgumentException("Image height must be > 0");
		
		if (this.height != height) {
			this.height = height;
			this.changed = true;
		}
	}

	/**
	 * Returns true if the last set operation has changed the model
	 * @return True if the model has been changed
	 */
	public boolean isChanged() {
		return changed;
	}


	/**
	 * Sets the internal state of the model to be changed
	 * @param changed 
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	
	/**
	 * Sets the algorithm that is used for resizing the image
	 * @param algorithm either {
	 */
	public void setAlgorithm(final short algorithm) {
		if (algorithm != RESIZE_NN && algorithm != RESIZE_BILINEAR) {
			throw new IllegalArgumentException("Unknown resize Algorithm!");
		}
		
		this.algorithm = algorithm;
	}
	
	/**
	 * Returns the algorithm that will be used for scaling the image
	 * @return The algorithm
	 */
	public short getAlgorithm() {
		return this.algorithm;
	}
	

	@Override
	public String toString() {
		return "DimensionModel [width=" + width + ", height=" + height
				+ ", changed=" + changed + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changed ? 1231 : 1237);
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DimensionModel other = (DimensionModel) obj;
		if (changed != other.changed)
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}


}
