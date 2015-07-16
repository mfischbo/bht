package de.bht.fb6.cg1.imagetweak.model;


/**
 * Model class to reflect the options from the HistogramDialog
 * @author M. Fischboeck
 *
 */
public class HistogramOptions {

	public static enum Channel {
		RGB, 
		HSV,
		YUV
	}
	
	private Channel	histogramChannel;
	
	/**
	 * Default constructor
	 */
	public HistogramOptions() {
		this.histogramChannel = Channel.RGB;
	}
	
	/**
	 * Sets the colorspace to be used calculating the histogram
	 * @param channel The colorspace to use
	 */
	public void setHistogramChannel(final Channel channel) {
		this.histogramChannel = channel;
	}
	
	/**
	 * Returns the selected colorspace to be used
	 * @return
	 */
	public Channel getHistogramChannel() {
		return this.histogramChannel;
	}


	@Override
	public String toString() {
		return "HistogramOptions [histogramChannel=" + histogramChannel + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((histogramChannel == null) ? 0 : histogramChannel.hashCode());
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
		HistogramOptions other = (HistogramOptions) obj;
		if (histogramChannel != other.histogramChannel)
			return false;
		return true;
	}
	
	
}