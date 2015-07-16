package de.bht.fb6.cg1.colorconverter.model;

/**
 * Interface for color space implementation that can be displayed with the ColorSliderPanel.
 * 
 * @author M. Fischboeck
 *
 */
public interface IColor {

	/**
	 * Returns an array of identifier chars that is the acronym for the color space.
	 * The order in which they are returned must match the order of the 
	 * values returned by {@link #getParameters()}
	 * @return Char[] representing the name of the color space
	 */
	char[] 	getIdentifiers();
	
	/**
	 * Returns an array of integer values between 0 and 255 representing the current color.
	 * The order of the values applies to the order of the letters in the color space.
	 * <b>Example RGB:</b> R = retval[0]; G = retval[1]; B = retval[2]
	 * @return Array of integers between 0 and 255 representing the current color
	 */
	int[]	getParameters();
	
	/**
	 * Sets the values of the color.
	 * @param parameters Array of integers between 0 and 255
	 * @throws IllegalArgumentException If one ore more of the parameters are out of range
	 */
	void setParameters(final int[] parameters) throws IllegalArgumentException;
	
	/**
	 * Returns an array of integers between 0 and 255 that is this colors RGB representation.
	 * @return Integers representing this colors RGB values
	 */
	public int[] toRGB();
	
	/**
	 * Sets the current color to the values specified by the param array.
	 * Translation between the implementing color space and the RGB values must be calculated
	 * internally. 
	 * @param params
	 * @throws IllegalArgumentException If one or more parameters are out of the range of [0;255]
	 */
	public void  fromRGB(final int[] params) throws IllegalArgumentException;
}
