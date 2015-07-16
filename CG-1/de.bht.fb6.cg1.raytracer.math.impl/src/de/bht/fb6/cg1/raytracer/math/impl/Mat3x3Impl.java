package de.bht.fb6.cg1.raytracer.math.impl;

import de.bht.fb6.cg1.raytracer.math.Mat3x3;
import de.bht.fb6.cg1.raytracer.math.Point3D;
import de.bht.fb6.cg1.raytracer.math.Vector3D;

/**
 * Implementation of a 3x3 matrix {@link de.bht.fb6.cg1.raytracer.math.Mat3x3}
 * @author M. Fischboeck
 *
 */
public class Mat3x3Impl implements Mat3x3 {

	private final Vector3D[] mat;
	
	/**
	 * Constructor specifying 3 vectors for the matrix columns
	 * @param columns The 3DVectors of the matrix
	 */
	public Mat3x3Impl(final Vector3D[] columns) {
		if (columns.length > 3)
			throw new IllegalArgumentException("Maximum 3 columns can be specified");
		
		mat = columns;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#replaceColumn0(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Mat3x3 replaceColumn0(Vector3D with) {
		Vector3D[] param = new Vector3D[3];
		param[0] = with;
		param[1] = mat[1];
		param[2] = mat[2];
		return new Mat3x3Impl(param);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#replaceColumn1(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Mat3x3 replaceColumn1(Vector3D with) {
		Vector3D[] param = new Vector3D[3];
		param[0] = mat[0];
		param[1] = with;
		param[2] = mat[2];
		return new Mat3x3Impl(param);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#replaceColumn2(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Mat3x3 replaceColumn2(Vector3D with) {
		Vector3D[] param = new Vector3D[3];
		param[0] = mat[0];
		param[1] = mat[1];
		param[2] = with;
		return new Mat3x3Impl(param);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#mul(de.bht.fb6.cg1.raytracer.math.Mat3x3)
	 */
	@Override
	public Mat3x3 mul(Mat3x3 rhs) {
		Mat3x3Impl i = (Mat3x3Impl) rhs;
		Vector3D a = i.getColumn(0);
		Vector3D b = i.getColumn(1);
		Vector3D c = i.getColumn(2);
		
		double ra1 = mat[0].getX() * a.getX() + mat[1].getX() * a.getY() + mat[2].getX() * a.getZ();
		double ra2 = mat[0].getY() * a.getX() + mat[1].getY() * a.getY() + mat[2].getY() * a.getZ();
		double ra3 = mat[0].getZ() * a.getX() + mat[1].getZ() * a.getY() + mat[2].getZ() * a.getZ();
		
		double rb1 = mat[0].getX() * b.getX() + mat[1].getX() * b.getY() + mat[2].getX() * b.getZ();
		double rb2 = mat[0].getY() * b.getX() + mat[1].getY() * b.getY() + mat[2].getY() * b.getZ();
		double rb3 = mat[0].getZ() * b.getX() + mat[1].getZ() * b.getY() + mat[2].getZ() * b.getZ();
		
		double rc1 = mat[0].getX() * c.getX() + mat[1].getX() * c.getY() + mat[2].getX() * c.getZ();
		double rc2 = mat[0].getY() * c.getX() + mat[1].getY() * c.getY() + mat[2].getY() * c.getZ();
		double rc3 = mat[0].getZ() * c.getX() + mat[1].getZ() * c.getY() + mat[2].getZ() * c.getZ();
		
		Vector3D l = new Vector3DImpl(ra1, ra2, ra3);
		Vector3D m = new Vector3DImpl(rb1, rb2, rb3);
		Vector3D r = new Vector3DImpl(rc1, rc2, rc3);
		
		return new Mat3x3Impl(new Vector3D[] {l,m,r});
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#mul(de.bht.fb6.cg1.raytracer.math.Point3D)
	 */
	@Override
	public Point3D mul(Point3D rhs) {
		
		double ra1 = mat[0].getX() * rhs.getX() + mat[1].getX() * rhs.getY() + mat[2].getX() * rhs.getZ();
		double ra2 = mat[0].getY() * rhs.getX() + mat[1].getY() * rhs.getY() + mat[2].getY() * rhs.getZ();
		double ra3 = mat[0].getZ() * rhs.getX() + mat[1].getZ() * rhs.getY() + mat[2].getZ() * rhs.getZ();
		return new Point3DImpl(ra1, ra2, ra3);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#mul(de.bht.fb6.cg1.raytracer.math.Vector3D)
	 */
	@Override
	public Vector3D mul(Vector3D rhs) {
		double ra1 = mat[0].getX() * rhs.getX() + mat[1].getX() * rhs.getY() + mat[2].getX() * rhs.getZ();
		double ra2 = mat[0].getY() * rhs.getX() + mat[1].getY() * rhs.getY() + mat[2].getY() * rhs.getZ();
		double ra3 = mat[0].getZ() * rhs.getX() + mat[1].getZ() * rhs.getY() + mat[2].getZ() * rhs.getZ();
		return new Vector3DImpl(ra1, ra2, ra3);
	}

	/*
	 * (non-Javadoc)
	 * @see de.bht.fb6.cg1.raytracer.math.Mat3x3#getDeterminant()
	 */
	@Override
	public double getDeterminant() {
		double result = mat[0].getX() * mat[1].getY() * mat[2].getZ() +
						mat[0].getY() * mat[1].getZ() * mat[2].getX() +
						mat[0].getZ() * mat[1].getX() * mat[2].getY() -
						
						mat[0].getZ() * mat[1].getY() * mat[2].getX() -
						mat[0].getY() * mat[1].getX() * mat[2].getZ() -
						mat[0].getX() * mat[1].getZ() * mat[2].getY();
		return result;
	}

	/**
	 * Returns the Vector3D of the specified column
	 * @param col The index of the column to be returned
	 * @return The Vector3D in this column
	 */
	public Vector3D getColumn(int col) {
		return mat[col];
	}
	
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(mat[0].getX() + "\t" + mat[1].getX() + "\t" + mat[2].getX() + "\n");
		b.append(mat[0].getY() + "\t" + mat[1].getY() + "\t" + mat[2].getY() + "\n");
		b.append(mat[0].getZ() + "\t" + mat[1].getZ() + "\t" + mat[2].getZ() + "\n");
		return b.toString();
	}
}
