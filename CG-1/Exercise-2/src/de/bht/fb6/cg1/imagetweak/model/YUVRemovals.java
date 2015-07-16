package de.bht.fb6.cg1.imagetweak.model;

/**
 * Model class to hold the state of the YUV component remover dialog
 * @author M. Fischboeck
 *
 */
public class YUVRemovals {

	private boolean lumina;
	private boolean uChroma;
	private boolean vChroma;

	private boolean changed;

	/**
	 * Default constructor
	 */
	public YUVRemovals() {
		this.lumina = false;
		this.uChroma = false;
		this.vChroma = false;
		this.changed = false;
	}
	
	/**
	 * Constructor specifying all parameters
	 * @param lumina States if lumina shall be removed
	 * @param uChroma States if uChroma shall be removed
	 * @param vChroma States if vChroma shall be removed
	 */
	public YUVRemovals(final boolean lumina, final boolean uChroma,
			final boolean vChroma) {
		this.lumina = lumina;
		this.vChroma = vChroma;
		this.uChroma = uChroma;
		
		this.changed = false;
	}

	/**
	 * Returns true if lumina will be / is removed
	 * @return True if removed, false otherwise
	 */
	public boolean isLumina() {
		return lumina;
	}

	/**
	 * Sets the state if lumina shall be removed
	 * @param lumina True if lumina shall be removed
	 */
	public void setLumina(boolean lumina) {
		if (this.lumina != lumina) {
			this.lumina = lumina;
			this.changed = true;
		}
	}

	/**
	 * Returns true if uChroma will be / is removed
	 * @return True if removed, false otherwise
	 */
	public boolean isuChroma() {
		return uChroma;
	}

	/**
	 * Sets the state if uChroma shall be removed
	 * @param lumina True if uChroma shall be removed
	 */
	public void setuChroma(boolean uChroma) {
		if (uChroma != this.uChroma) {
			this.uChroma = uChroma;
			this.changed = true;
		}
	}

	/**
	 * Returns true if vChroma will be / is removed
	 * @return True if removed, false otherwise
	 */
	public boolean isvChroma() {
		return vChroma;
	}

	/**
	 * Sets the state if vChroma shall be removed
	 * @param lumina True if vChroma shall be removed
	 */
	public void setvChroma(boolean vChroma) {
		if (this.vChroma != vChroma) {
			this.vChroma = vChroma;
			this.changed = true;
		}
	}

	/**
	 * Returns true if the internal state has changed since the last operation
	 * @return True if changed, false otherwise
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the internal state to be changed or not
	 * @param changed True if changed, false otherwise
	 */
	public void setChanged(final boolean changed) {
		this.changed = changed;
	}
	
	@Override
	public String toString() {
		return "YUVRemovals [lumina=" + lumina + ", uChroma=" + uChroma
				+ ", vChroma=" + vChroma + ", changed=" + changed + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changed ? 1231 : 1237);
		result = prime * result + (lumina ? 1231 : 1237);
		result = prime * result + (uChroma ? 1231 : 1237);
		result = prime * result + (vChroma ? 1231 : 1237);
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
		YUVRemovals other = (YUVRemovals) obj;
		if (changed != other.changed)
			return false;
		if (lumina != other.lumina)
			return false;
		if (uChroma != other.uChroma)
			return false;
		if (vChroma != other.vChroma)
			return false;
		return true;
	}
}
