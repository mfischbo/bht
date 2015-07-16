package de.bht.fb6.cg1.raytracer.math.impl;

import de.bht.fb6.cg1.raytracer.math.Normal;
import de.bht.fb6.cg1.raytracer.math.Point3D;
import de.bht.fb6.cg1.raytracer.math.Vector3D;

public class Vector3DImpl implements Vector3D {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Constructor specifying x, y and z values of the 3D Vector
	 * @param x The x value of the vector
	 * @param y The y value of the vector
	 * @param z The z value of the vector
	 */
	public Vector3DImpl(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#getMagnitude()
	 */
	@Override
	public double getMagnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#add(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Vector3D add(Vector3D rhs) {
		double nx = x + rhs.getX();
		double ny = y + rhs.getY();
		double nz = z + rhs.getZ();
		return new Vector3DImpl(nx, ny, nz);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#add(de.bht.fb6.cg1.raytracer.math.Normal)
	 */
	@Override
	public Vector3D add(Normal rhs) {
		double nx = x + rhs.getX();
		double ny = y + rhs.getY();
		double nz = z + rhs.getZ();
		return new Vector3DImpl(nx, ny, nz);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#sub(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Vector3D sub(Vector3D rhs) {
		double nx = x - rhs.getX();
		double ny = y - rhs.getY();
		double nz = z - rhs.getZ();
		return new Vector3DImpl(nx, ny, nz);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#mul(double)
	 */
	@Override
	public Vector3D mul(double rhs) {
		double nx = rhs * x;
		double ny = rhs * y;
		double nz = rhs * z;
		return new Vector3DImpl(nx, ny, nz);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#div(double)
	 */
	@Override
	public Vector3D div(double rhs) {
		double nx = x / rhs;
		double ny = y / rhs;
		double nz = z / rhs;
		return new Vector3DImpl(nx, ny, nz);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#dot(de.bht.fb6.cg1.raytracer.math.Normal)
	 */
	@Override
	public double dot(Normal rhs) {
		return x * rhs.getX() + y * rhs.getY() + z * rhs.getZ();
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#dot(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public double dot(Vector3D rhs) {
		return x * rhs.getX() + y * rhs.getY() + z * rhs.getZ();
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#cross(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Vector3D cross(Vector3D rhs) {
		double nx = y * rhs.getZ() - z * rhs.getY();
		double ny = z * rhs.getX() - x * rhs.getZ();
		double nz = x * rhs.getY() - y * rhs.getX();
		return new Vector3DImpl(nx, ny, nz);
	}

	@Override
	public Normal asNormal() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#asPoint()
	 */
	@Override
	public Point3D asPoint() {
		return new Point3DImpl(x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#normalized()
	 */
	@Override
	public Vector3D normalized() {
		return div(getMagnitude());
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#getX()
	 */
	@Override
	public double getX() {
		return this.x;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#getY()
	 */
	@Override
	public double getY() {
		return this.y;
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Vector3D#getZ()
	 */
	@Override
	public double getZ() {
		return this.z;
	}

}
