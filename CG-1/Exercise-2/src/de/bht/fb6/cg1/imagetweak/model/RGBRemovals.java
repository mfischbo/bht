package de.bht.fb6.cg1.imagetweak.model;

/**
 * Holds the state of the RGB Channels in the image.
 * @author M. Fischboeck
 *
 */

public class RGBRemovals {

	private boolean red;
	private boolean green;
	private boolean blue;
	
	private boolean	changed;
	
	/**
	 * Default constructor
	 */
	public RGBRemovals() {
		this.red = false;
		this.green = false;
		this.blue  = false;
		
		this.changed = false;
	}
	
	/**
	 * Constructor specifying each channel if it's either removed or not
	 * @param red The red channel
	 * @param green The green channel
	 * @param blue The blue channel
	 */
	public RGBRemovals(final boolean red, final boolean green, final boolean blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Returns if the red channel is removed
	 * @return true if it's removed
	 */
	public boolean isRed() {
		return red;
	}

	/**
	 * Sets the red channel to be removed or not
	 * @param red The red channels state
	 */
	public void setRed(final boolean red) {
		if (red != this.red) { 
			this.red = red;
			this.changed = true;
		}
	}

	/**
	 * Returns if the green channel is removed
	 * @return True if it's removed
	 */
	public boolean isGreen() {
		return green;
	}

	/**
	 * Sets the green channel to be removed or not
	 * @param green The green channels state
	 */
	public void setGreen(final boolean green) {
		if (green != this.green) {
			this.green = green;
			this.changed = true;
		}
	}

	/**
	 * Returns if the blue channel is removed
	 * @return True if it's removed
	 */
	public boolean isBlue() {
		return blue;
	}

	/**
	 * Sets the blue channel to be removed or not
	 * @param blue The blue channels state
	 */
	public void setBlue(final boolean blue) {
		if (this.blue != blue) {
			this.blue = blue;
			this.changed = true;
		}
	}

	/**
	 * Returns if any changes has been made.
	 * If a channel has been removed once it is always false.
	 * @return Either the image has some changes in channels or not
	 */
	public boolean isChanged() {
		return changed;
	}
	
	public void setChanged(final boolean changed) {
		this.changed = changed;
	}

	
	@Override
	public String toString() {
		return "RGBRemovals [red=" + red + ", green=" + green + ", blue="
				+ blue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (blue ? 1231 : 1237);
		result = prime * result + (green ? 1231 : 1237);
		result = prime * result + (red ? 1231 : 1237);
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
		RGBRemovals other = (RGBRemovals) obj;
		if (blue != other.blue)
			return false;
		if (green != other.green)
			return false;
		if (red != other.red)
			return false;
		return true;
	}
}
