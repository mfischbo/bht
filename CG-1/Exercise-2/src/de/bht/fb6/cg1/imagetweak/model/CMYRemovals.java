package de.bht.fb6.cg1.imagetweak.model;

/**
 * Model class to reflect the changes in CMY Channel removement
 * @author M. Fischboeck
 *
 */

public class CMYRemovals {

	private boolean	cyan;
	private boolean magenta;
	private boolean	yellow;
	
	private boolean changed;
	
	/**
	 * Default constructor
	 */
	public CMYRemovals() {
		this.cyan = false;
		this.magenta = false;
		this.yellow = false;
		this.changed = false;
	}
	
	/**
	 * Constructor with all components specified
	 * @param cyan Remove cyan component
	 * @param magenta Remove magenta component
	 * @param yellow Remove yellow component
	 */
	public CMYRemovals(final boolean cyan, final boolean magenta, final boolean yellow) {
		this.cyan = cyan;
		this.magenta = magenta;
		this.yellow = yellow;
	}
	
	/**
	 * States if cyan is removed
	 * @return True if removed
	 */
	public boolean isCyan() {
		return cyan;
	}

	/**
	 * Sets cyan to be removed
	 * @param cyan True if should be removed
	 */
	public void setCyan(boolean cyan) {
		if (this.cyan != cyan) {
			this.cyan = cyan;
			this.changed = true;
		}
	}

	/**
	 * States if magenta is removed
	 * @return True if removed
	 */
	public boolean isMagenta() {
		return magenta;
	}

	/**
	 * Sets magenta to be removed
	 * @param magenta True if should be removed
	 */
	public void setMagenta(boolean magenta) {
		if (magenta != this.magenta) {
		this.magenta = magenta;
		this.changed = true;
		}
	}

	/**
	 * States if yellow is removed
	 * @return True if removed
	 */
	public boolean isYellow() {
		return yellow;
	}

	/**
	 * Sets yellow to be removed
	 * @param yellow True if should be removed
	 */
	public void setYellow(boolean yellow) {
		if (yellow != this.yellow) { 
		this.yellow = yellow;
		this.changed = true;
		}
	}

	/**
	 * States if any changes have been applied to the model
	 * @return True if changes has occured
	 */
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * Sets the internal state to be changed
	 * @param changed True if state has changed, false otherwise
	 */
	public void setChanged(final boolean changed) {
		this.changed = changed;
	}


	@Override
	public String toString() {
		return "CMYRemovals [cyan=" + cyan + ", magenta=" + magenta
				+ ", yellow=" + yellow + ", changed=" + changed + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (changed ? 1231 : 1237);
		result = prime * result + (cyan ? 1231 : 1237);
		result = prime * result + (magenta ? 1231 : 1237);
		result = prime * result + (yellow ? 1231 : 1237);
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
		CMYRemovals other = (CMYRemovals) obj;
		if (changed != other.changed)
			return false;
		if (cyan != other.cyan)
			return false;
		if (magenta != other.magenta)
			return false;
		if (yellow != other.yellow)
			return false;
		return true;
	}

}
