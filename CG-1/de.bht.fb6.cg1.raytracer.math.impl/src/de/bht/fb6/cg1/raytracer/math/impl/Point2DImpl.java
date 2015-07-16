package de.bht.fb6.cg1.raytracer.math.impl;

import de.bht.fb6.cg1.raytracer.math.Point2D;

/**
 * Implementation of {@link de.bht.fb6.cg1.raytracer.math.Point2D}
 * 
 * @author M. Fischboeck
 *
 */
public class Point2DImpl implements Point2D {

	private double x;
	private double y;
	
	/**
	 * Constructor specifying x and y coordinate of the point
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Point2DImpl(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point2D#getX()
	 */
	@Override
	public double getX() {
		return this.x;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point2D#getY()
	 */
	@Override
	public double getY() {
		return this.y;
	}

}
