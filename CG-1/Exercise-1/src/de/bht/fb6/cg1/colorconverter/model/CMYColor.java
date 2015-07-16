package de.bht.fb6.cg1.colorconverter.model;

/**
 * Color space implementation for the CMY color space
 * 
 * @author M. Fischboeck
 */

public class CMYColor implements IColor {

	/** The cyan part of the color [0;255]*/
	private int cyan;
	
	/** The magenta part of the color [0;255]*/
	private int magenta;
	
	/** The yellow part of the color [0;255] */
	private int yellow;
	
	
	/**
	 * Constructor providing legal values in the range of [0;255] for all color components
	 * @param cyan Value for the cyan component of the color
	 * @param magenta Value for the magenta component of the color
	 * @param yellow Value for the yellow component of the color
	 * @throws IllegalArgumentException If one or more parameters are out of range
	 */
	public CMYColor(final int cyan, final int magenta, final int yellow) throws IllegalArgumentException {
		if (cyan < 0 || cyan > 255)
			throw new IllegalArgumentException("Value for the component 'cyan' is out of range [0;255]");
		
		if (magenta < 0 || magenta > 255) 
			throw new IllegalArgumentException("Value for the component 'magenta' is out of range [0;255]");
		
		if (yellow < 0 || yellow > 255)
			throw new IllegalArgumentException("Value for the component 'yellow' is out of range [0;255]");
		
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getIdentifiers()
	 */
	@Override
	public char[] getIdentifiers() {
		return new char[] {'C', 'M', 'Y'};
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getParameters()
	 */
	@Override
	public int[] getParameters() {
		return new int[] {this.cyan, this.magenta, this.yellow};
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#setParameters(int[])
	 */
	@Override
	public void setParameters(final int[] parameters) throws IllegalArgumentException {
		
		if (parameters[0] < 0 || parameters[0] > 255)
			throw new IllegalArgumentException("Value for the component 'cyan' is out of range [0;255]");
		
		if (parameters[1] < 0 || parameters[1] > 255) 
			throw new IllegalArgumentException("Value for the component 'magenta' is out of range [0;255]");
		
		if (parameters[2] < 0 || parameters[2] > 255)
			throw new IllegalArgumentException("Value for the component 'yellow' is out of range [0;255]");
		
		this.cyan = parameters[0];
		this.magenta = parameters[1];
		this.yellow = parameters[2];
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#toRGB()
	 */
	@Override
	public int[] toRGB() {

		final int r = 255 - this.cyan;
		final int g = 255 - this.magenta;
		final int b = 255 - this.yellow;
		
		return new int[] {r,g,b};
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#fromRGB(int[])
	 */
	@Override
	public void fromRGB(final int[] params) {
		this.cyan = 	255 - params[0];
		this.magenta = 	255 - params[1];
		this.yellow = 	255 - params[2];
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cyan;
		result = prime * result + magenta;
		result = prime * result + yellow;
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
		CMYColor other = (CMYColor) obj;
		if (cyan != other.cyan)
			return false;
		if (magenta != other.magenta)
			return false;
		if (yellow != other.yellow)
			return false;
		return true;
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CMYColor [cyan=" + cyan + ", magenta=" + magenta + ", yellow="
				+ yellow + "]";
	}
	
}
