package de.bht.fb6.cg1.raytracer.math.impl;

import de.bht.fb6.cg1.raytracer.math.Normal;
import de.bht.fb6.cg1.raytracer.math.Point3D;
import de.bht.fb6.cg1.raytracer.math.Vector3D;

/**
 * Implementation of {@link de.bht.fb6.cg1.raytracer.math.Point3D}
 *  
 * @author M. Fischboeck
 *
 */

public class Point3DImpl implements Point3D {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Constructor specifying x, y and z coordinate of the 3D Point
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param z The z coordinate
	 */
	public Point3DImpl(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#add(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Point3D add(Vector3D rhs) {
		return new Point3DImpl(x + rhs.getX(), y + rhs.getY(), z + rhs.getZ());
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#sub(de.bht.fb6.cg1.raytracer.math.Point3D)
	 */
	@Override
	public Vector3D sub(Point3D rhs) {
		return new Vector3DImpl(x - rhs.getX(), y - rhs.getY(), z - rhs.getZ());
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#sub(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Point3D sub(Vector3D rhs) {
		return new Point3DImpl(x - rhs.getX(), y - rhs.getY(), z - rhs.getZ());
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#dot(de.bht.fb6.cg1.raytracer.math.Normal)
	 */
	@Override
	public double dot(Normal rhs) {
		return x * rhs.getX() + y * rhs.getY() + z * rhs.getZ();
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#asVector()
	 */
	@Override
	public Vector3D asVector() {
		return new Vector3DImpl(x, y, z);
	}

	
	@Override
	public Normal asNormal() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#getX()
	 */
	@Override
	public double getX() {
		return this.x;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#getY()
	 */
	@Override
	public double getY() {
		return this.y;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Point3D#getZ()
	 */
	@Override
	public double getZ() {
		return this.z;
	}

}
