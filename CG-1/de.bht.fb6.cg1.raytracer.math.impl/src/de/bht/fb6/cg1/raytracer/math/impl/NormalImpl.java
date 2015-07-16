package de.bht.fb6.cg1.raytracer.math.impl;

import de.bht.fb6.cg1.raytracer.math.Normal;
import de.bht.fb6.cg1.raytracer.math.Point3D;
import de.bht.fb6.cg1.raytracer.math.Vector3D;

/**
 * Implementation of {@link de.bht.fb6.cg1.raytracer.math.Normal}
 * @author M. Fischboeck
 *
 */
public class NormalImpl implements Normal {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Constructor specifying x, y and z coordinate of the normale
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 */
	public NormalImpl(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#mul(double)
	 */
	@Override
	public Normal mul(double rhs) {
		return new NormalImpl(x * rhs, y * rhs, z * rhs);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#add(de.bht.fb6.cg1.raytracer.math.Normal)
	 */
	@Override
	public Normal add(Normal rhs) {
		return new NormalImpl(x + rhs.getX(), y + rhs.getY(), z + rhs.getZ());
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#normalized()
	 */
	@Override
	public Normal normalized() {
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		return new NormalImpl(x / magnitude, y / magnitude, z / magnitude);
	}

	@Override
	public Vector3D reflect(Vector3D v) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#asVector()
	 */
	@Override
	public Vector3D asVector() {
		return new Vector3DImpl(x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#asPoint()
	 */
	@Override
	public Point3D asPoint() {
		return new Point3DImpl(x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#getX()
	 */
	@Override
	public double getX() {
		return this.x;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#getY()
	 */
	@Override
	public double getY() {
		return this.y;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Normal#getZ()
	 */
	@Override
	public double getZ() {
		return this.z;
	}

}
