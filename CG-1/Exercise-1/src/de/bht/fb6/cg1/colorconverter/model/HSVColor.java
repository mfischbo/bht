package de.bht.fb6.cg1.colorconverter.model;


/**
 * Class that implements the HSV color space
 * @author M. Fischboeck
 *
 */
public class HSVColor implements IColor {

	/** The hue component of the color */
	private int	hue;
	
	/** The saturation of the color */
	private int saturation;
	
	/** The value of the color */
	private int value;
	
	
	/**
	 * Constructor providing values for all components in the range [0;255]
	 * @param hue The value for the hue component
	 * @param saturation The value for the saturation component
	 * @param value The value for the value component 
	 * @throws IllegalArgumentException If one or more values are out of the range [0;255]
	 */
	public HSVColor(final int hue, final int saturation, final int value) throws IllegalArgumentException {
		
		if (hue < 0 || hue > 255)
			throw new IllegalArgumentException("Value for the component 'hue' is out of the range [0;255]");
		if (saturation < 0 || saturation > 255)
			throw new IllegalArgumentException("Value for the component 'saturation' is out of the range [0;255]");
		if (value < 0 || value > 255)
			throw new IllegalArgumentException("Value for the component 'value' is out of the range [0;255]");
		
		this.hue = hue;
		this.saturation = saturation;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getIdentifiers()
	 */
	@Override
	public char[] getIdentifiers() {
		return new char[] {'H', 'S', 'V'};
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getParameters()
	 */
	@Override
	public int[] getParameters() {
		return new int[] {this.hue, this.saturation, this.value};
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#setParameters(int[])
	 */
	@Override
	public void setParameters(final int[] parameters) throws IllegalArgumentException {
		
		if (parameters[0] < 0 || parameters[0] > 255)
			throw new IllegalArgumentException("Value for the component 'hue' is out of the range [0;255]");
		if (parameters[1] < 0 || parameters[1] > 255)
			throw new IllegalArgumentException("Value for the component 'saturation' is out of the range [0;255]");
		if (parameters[2] < 0 || parameters[2] > 255)
			throw new IllegalArgumentException("Value for the component 'value' is out of the range [0;255]");
		
		this.hue = parameters[0];
		this.saturation = parameters[1];
		this.value = parameters[2];
		
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#toRGB()
	 */
	@Override
	public int[] toRGB() {

		final double[] rgb = new double[3];
		
		final double qH = this.hue * (360.0 / 255.0);
		final double qS = this.saturation / 255.0;
		final double qV = this.value / 255.0;
		
		if (qH < 120) {
			rgb[0] = (120 - qH) / 60.0;
			rgb[1] = qH / 60.0;
			rgb[2] = 0.0;
		} else if (qH < 240) {
			rgb[0] = 0.0;
			rgb[1] = (240 - qH) / 60.0;
			rgb[2] = (qH - 120) / 60.0;
		} else {
			rgb[0] = (qH - 240) / 60.0;
			rgb[1] = 0.0;
			rgb[2] = (360 - qH) / 60.0;
		}
		
		rgb[0] = Math.min(rgb[0], 1.0);
		rgb[1] = Math.min(rgb[1], 1.0);
		rgb[2] = Math.min(rgb[2], 1.0);
		
		double[] ret = new double[3];
		ret[0] = ((1 - qS + qS * rgb[0]) * qV) * 255;
		ret[1] = ((1 - qS + qS * rgb[1]) * qV) * 255;
		ret[2] = ((1 - qS + qS * rgb[2]) * qV) * 255;
		
		return new int[] {(int) ret[0], (int) ret[1], (int) ret[2]};
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#fromRGB(int[])
	 */
	@Override
	public void fromRGB(final int[] params) {
		final double min;
		final double max;
		final double delta;
		
		min = Math.min(params[0], Math.min(params[1], params[2]));
		max = Math.max(params[0], Math.max(params[1], params[2]));
		delta = max - min;
		
		this.value = (int) max;
		this.saturation = 0;
		if (max > 0) 
			this.saturation = (int) ((delta / max) * 255);
		
		double deg = 0;
		if (delta > 0) {
			
			if (((int) max) == params[0])
				deg = ((params[1] - params[2]) / delta) % 6;
			
			if (((int) max) == params[1])
				deg = ((params[2] - params[0]) / delta) + 2;
			
			if (((int) max) == params[2])
				deg = ((params[0] - params[1]) / delta) + 4;
			
			deg = (deg * 60) % 360;
			this.hue = (int) (deg * (255.0/360.0));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HSVColor [hue=" + hue + ", saturation=" + saturation
				+ ", value=" + value + "]";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hue;
		result = prime * result + saturation;
		result = prime * result + value;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HSVColor other = (HSVColor) obj;
		if (hue != other.hue)
			return false;
		if (saturation != other.saturation)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	

}
