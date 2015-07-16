package de.bht.fb6.cg1.colorconverter.model;

/**
 * Implementation of the RGB color space
 * @author M. Fischboeck
 *
 */

public class RGBColor implements IColor {

	/** The red component of the color */
	private int red;
	
	/** The green component of the color */
	private int green;
	
	/** The blue component of the color */
	private int blue;
	
	/**
	 * Constructor providing values for all components in a range of [0;255]
	 * @param red The value of the red component
	 * @param green The value of the green component
	 * @param blue The value of the blue component
	 * @throws IllegalArgumentException If one or more values are out of range [0;255]
	 */
	public RGBColor(final int red, final int green, final int blue) throws IllegalArgumentException {
		
		if (red < 0 || red > 255)
			throw new IllegalArgumentException("The value for component 'red' is out of range [0;255]");
		
		if (green < 0 || green > 255)
			throw new IllegalArgumentException("The value for component 'green' is out of range [0;255]");
		
		if (blue < 0 || blue > 255)
			throw new IllegalArgumentException("The value for component 'blue' is out of range [0;255]");
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getParameters()
	 */
	@Override
	public int[] getParameters() {
		return new int[] {this.red, this.green, this.blue};
	}


	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getIdentifiers()
	 */
	@Override
	public char[] getIdentifiers() {
		return new char[] {'R', 'G', 'B'};
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#setParameters(int[])
	 */
	@Override
	public void setParameters(final int[] parameters) throws IllegalArgumentException {

		if (parameters[0] < 0 || parameters[0] > 255)
			throw new IllegalArgumentException("The value for component 'red' is out of range [0;255]");
		
		if (parameters[1] < 0 || parameters[1] > 255)
			throw new IllegalArgumentException("The value for component 'green' is out of range [0;255]");
		
		if (parameters[2] < 0 || parameters[2] > 255)
			throw new IllegalArgumentException("The value for component 'blue' is out of range [0;255]");
		
		this.red = parameters[0];
		this.green = parameters[1];
		this.blue = parameters[2];
	}


	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#toRGB()
	 */
	@Override
	public int[] toRGB() {
		return getParameters();
	}


	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#fromRGB(int[])
	 */
	@Override
	public void fromRGB(final int[] params) {
		setParameters(params);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RGBColor [red=" + red + ", green=" + green + ", blue=" + blue
				+ "]";
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + red;
		return result;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RGBColor other = (RGBColor) obj;
		if (blue != other.blue)
			return false;
		if (green != other.green)
			return false;
		if (red != other.red)
			return false;
		return true;
	}

}
