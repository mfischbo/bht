package de.bht.fb6.cg1.imagetweak.model;

/**
 * Model class that holds all brightness and contrast properties of the opened image
 * @author M. Fischboeck
 *
 */

public class BCModel {

	/**
	 * Enumeration of available color channels for brightness and contrast
	 * @author M. Fischboeck
	 *
	 */
	public static enum Channel {
		RGB,
		HSV,
		YUV
	}
	
	/* The brightness on the image */
	private int		brightness;
	
	/* The contrast on the image */
	private int		contrast;
	
	/* States if the brightness has been changed recently */
	private boolean	brightnessChanged;
	
	/* States if the contrast has been changed recently */
	private boolean contrastChanged;
	
	/* Holds the channel the operation is executed on */
	private Channel	channel;
	
	/**
	 * Default constructor
	 */
	public BCModel() {
		this.brightness = 50;
		this.contrast = 50;
		this.brightnessChanged = false;
		this.contrastChanged = false;
		this.channel = Channel.RGB;
	}

	/**
	 * Returns the value of the brightness between -50 and 50
	 * @return The brightness value
	 */
	public int getBrightness() {
		return brightness;
	}


	/**
	 * Sets the brightness value of the image
	 * @param brightness The value of the brightness between -50 and 50
	 */
	public void setBrightness(int brightness) {
		
		if (brightness < -50 || brightness > 50)
			throw new IllegalArgumentException("Brigthness must be between -50 and 50");
		
		if (brightness != this.brightness) {
			this.brightness = brightness;
			this.brightnessChanged = true;
		}
	}

	/**
	 * Returns the value of the contrast 
	 * @return The contrast value between -50 and 50
	 */
	public int getContrast() {
		return contrast;
	}


	/**
	 * Sets the value of the contrast on the image
	 * @param contrast The contrast between -50 and 50
	 */
	public void setContrast(int contrast) {
		
		if (contrast > 50 || contrast < -50) 
			throw new IllegalArgumentException("Contrast must be between -50 and 50");
		if (contrast != this.contrast) { 
			this.contrast = contrast;
			this.contrastChanged = true;
		}
	}

	/**
	 * Returns if the brightness has been changed
	 * @return True if the brightness has been changed, false otherwise
	 */
	public boolean isBrightnessChanged() {
		return this.brightnessChanged;
	}
	
	
	/**
	 * Returns if the contrast has been changed
	 * @return True if the contrast has been changed, false otherwise
	 */
	public boolean isContrastChanged() {
		return this.contrastChanged;
	}
	
	/**
	 * Returns the Channgel the operation will be executed on
	 * @return The color channel of the operation
	 */
	public Channel getChannel() {
		return this.channel;
	}
	
	/**
	 * Sets the state of the change in brightness
	 * @param changed True if the brightness has been changed
	 */
	public void setBrightnessChanged(final boolean changed) {
		this.brightnessChanged = changed;
	}
	
	/**
	 * Sets the state of the change in contrast
	 * @param changed
	 */
	public void setContrastChanged(final boolean changed) {
		this.contrastChanged = changed;
	}
	
	/**
	 * Sets the channel of the operation will be executed on
	 * @param channel 
	 */
	public void setChannels(final Channel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "BCModel [brightness=" + brightness + ", contrast=" + contrast
				+ "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brightness;
		result = prime * result + contrast;
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
		BCModel other = (BCModel) obj;
		if (brightness != other.brightness)
			return false;
		if (contrast != other.contrast)
			return false;
		return true;
	}
}
