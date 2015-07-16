package de.bht.fb6.cg1.colorconverter.model;

import Jama.Matrix;

/**
 * Class that represents the YUV color space.
 * <b>Note:</b><br/>
 * Values are internally handled in a range of [0;255] for all parmeters.
 * 
 * @author M. Fischboeck
 *
 */

public class YUVColor implements IColor {

	/** The lumina component of the color */
	private int lumina;
	
	/** The U-Chroma component of the color */
	private int uChroma;
	
	/** The V-Chroma component of the color */
	private int vChroma;
	
	
	/**
	 * Constructor providing values for all components in a range of [0;255]
	 * @param lumina The value for the lumina component
	 * @param uChroma The value for the U-Chroma component
	 * @param vChroma The value for the V-Chroma component
	 * @throws IllegalArgumentException If one or more values are out of range: [0;255]
	 */
	public YUVColor(final int lumina, final int uChroma, final int vChroma) throws IllegalArgumentException {
		
		if (lumina < 0 || lumina > 255) 
			throw new IllegalArgumentException("The value for the component 'lumina' is out of range [0;255]");
		if (uChroma < 0 || uChroma > 255) 
			throw new IllegalArgumentException("The value for the component 'V-Chroma' is out of range [0;255]");
		if (vChroma < 0 || vChroma > 255) 
			throw new IllegalArgumentException("The value for the component 'U-Chroma' is out of range [0;255]");
		
		this.lumina = lumina;
		this.vChroma = vChroma;
		this.uChroma = uChroma;
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YUVColor [lumina=" + lumina + ", uChroma=" + uChroma
				+ ", vChroma=" + vChroma + "]";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lumina;
		result = prime * result + uChroma;
		result = prime * result + vChroma;
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
		YUVColor other = (YUVColor) obj;
		if (lumina != other.lumina)
			return false;
		if (uChroma != other.uChroma)
			return false;
		if (vChroma != other.vChroma)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getParameters()
	 */
	@Override
	public int[] getParameters() {
		return new int[] {this.lumina, this.uChroma, this.vChroma};
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#getIdentifiers()
	 */
	@Override
	public char[] getIdentifiers() {
		return new char[] {'Y', 'U', 'V'};
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#setParameters(int[])
	 */
	@Override
	public void setParameters(final int[] parameters) throws IllegalArgumentException {
		
		if (parameters[0] < 0 || parameters[0] > 255) 
			throw new IllegalArgumentException("The value for the component 'lumina' is out of range [0;255]");
		if (parameters[1] < 0 || parameters[1] > 255) 
			throw new IllegalArgumentException("The value for the component 'V-Chroma' is out of range [0;255]");
		if (parameters[2] < 0 || parameters[2] > 255) 
			throw new IllegalArgumentException("The value for the component 'U-Chroma' is out of range [0;255]");
		
		this.lumina = parameters[0];
		this.uChroma = parameters[1];
		this.vChroma = parameters[2];
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#toRGB()
	 */
	@Override
	public int[] toRGB() {
		
		final double[][] coeffs = {{1.0, 0.0, 1.13983}, {1.0, -0.39465, -0.58060}, {1.0, 2.03211, 0.0}};
		
		final double[] colYUV = new double[3];
		
		// multiplication with coefficient matrix requires values between [-256;255] for U/V
		colYUV[0] = this.lumina;
		colYUV[1] = -256.0 + this.uChroma*2.0;  
		colYUV[2] = -256.0 + this.vChroma*2.0;		
		
		final Matrix coeffMatrix = new Matrix(coeffs);
		final Matrix colorMatrix = new Matrix(colYUV, 3);
		final Matrix result      = coeffMatrix.times(colorMatrix);

		int r = (int) result.get(0,0);
		int g = (int) result.get(1,0);
		int b = (int) result.get(2,0);
		
		// protection from rounding errors on boundaries
		r = Math.min(255, Math.max(r, 0));
		g = Math.min(255, Math.max(g, 0));
		b = Math.min(255, Math.max(b, 0));
		
		return new int[] {r, g, b};
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.colorconverter.model.IColor#fromRGB(int[])
	 */
	@Override
	public void fromRGB(int[] params) {
		
		final double[] cols = new double[] {params[0], params[1], params[2]};
		final double[][] coeffs = {{0.299, 0.587, 0.114}, {-0.14713, -0.28886, 0.436}, {0.615, -0.51499, -0.10001}};
		
		final Matrix coeffMatrix = new Matrix(coeffs);
		final Matrix colorMatrix = new Matrix(cols, 3);
		final Matrix result = coeffMatrix.times(colorMatrix);
				
		this.lumina = (int) (result.get(0,0));
		this.uChroma= (int) (result.get(1,0));
		this.vChroma= (int) (result.get(2,0));
	}
}
