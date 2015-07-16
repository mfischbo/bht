package de.bht.fb6.cg1.raytracer.math.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Jama.Matrix;

import de.bht.fb6.cg1.raytracer.math.Point3D;
import de.bht.fb6.cg1.raytracer.math.Vector3D;
import de.bht.fb6.cg1.raytracer.math.impl.Mat3x3Impl;
import de.bht.fb6.cg1.raytracer.math.impl.Point3DImpl;
import de.bht.fb6.cg1.raytracer.math.impl.Vector3DImpl;

public class Mat3x3Test {

	private Vector3D a = new Vector3DImpl(1, 2, 3);
	private Vector3D b = new Vector3DImpl(2, 2, 2);
	private Vector3D c = new Vector3DImpl(1, 1, 1);
	
	
	@Test
	public void multiplicateWithMatrixTest() {
		
		Mat3x3Impl mat1 = new Mat3x3Impl(new Vector3D[] {a, b, c});
		Mat3x3Impl mat2 = new Mat3x3Impl(new Vector3D[] {a, b, c});
		
		Mat3x3Impl res = (Mat3x3Impl) mat1.mul(mat2);
		System.out.println(res.toString());
		
		
		Matrix t = new Matrix(3, 3);
		t.set(0, 0, 1);
		t.set(1, 0, 2);
		t.set(2, 0, 3);
		
		t.set(0, 1, 2);
		t.set(1, 1, 2);
		t.set(2, 1, 2);
		
		t.set(0, 2, 1);
		t.set(1, 2, 1);
		t.set(2, 2, 1);
		
		Matrix k = t.copy();
		Matrix res2 = k.times(t);
		res2.print(5, 1);
		System.out.println("---------------------------------------------");
	}
	
	
	@Test
	public void mulWithPoint3DTest() {
		
		Mat3x3Impl mat = new Mat3x3Impl(new Vector3D[] {a, b, c});
		Point3DImpl p  = new Point3DImpl(1, 2, 3);
		
		Point3D res = mat.mul(p);
		System.out.println("\t" + res.getX() + "\n\t" + res.getY() + "\n\t" + res.getZ());
		
		Matrix t = new Matrix(3, 3);
		t.set(0, 0, 1);
		t.set(1, 0, 2);
		t.set(2, 0, 3);
		
		t.set(0, 1, 2);
		t.set(1, 1, 2);
		t.set(2, 1, 2);
		
		t.set(0, 2, 1);
		t.set(1, 2, 1);
		t.set(2, 2, 1);
		
		Matrix k = new Matrix(3, 1);
		k.set(0, 0, 1);
		k.set(1, 0, 2);
		k.set(2, 0, 3);
		
		Matrix res2 = t.times(k);
		res2.print(5, 1);
		System.out.println("---------------------------------------------");
	}
	
	
	@Test
	public void mulWithVector3DTest() {
		
		Mat3x3Impl mat = new Mat3x3Impl(new Vector3D[] {a, b, c});
		
		Vector3DImpl p  = new Vector3DImpl(1, 2, 3);
		
		Vector3D res = mat.mul(p);
		System.out.println("\t" + res.getX() + "\n\t" + res.getY() + "\n\t" + res.getZ());
		
		Matrix t = new Matrix(3, 3);
		t.set(0, 0, 1);
		t.set(1, 0, 2);
		t.set(2, 0, 3);
		
		t.set(0, 1, 2);
		t.set(1, 1, 2);
		t.set(2, 1, 2);
		
		t.set(0, 2, 1);
		t.set(1, 2, 1);
		t.set(2, 2, 1);
		
		Matrix k = new Matrix(3, 1);
		k.set(0, 0, 1);
		k.set(1, 0, 2);
		k.set(2, 0, 3);
		
		Matrix res2 = t.times(k);
		res2.print(5, 1);
		System.out.println("---------------------------------------------");
	}
	
	
	@Test
	public void getDeterminanteTest() {
		
		Vector3D x = new Vector3DImpl(14, 12, 28);
		Vector3D y = new Vector3DImpl(12, 22, -14);
		Vector3D z = new Vector3DImpl(11, -13, -14);
		
		Mat3x3Impl mat = new Mat3x3Impl(new Vector3D[] {x, y, z});
		double res1 = mat.getDeterminant();
		
		Matrix t = new Matrix(3, 3);
		t.set(0, 0, 14);
		t.set(1, 0, 12);
		t.set(2, 0, 28);
		
		t.set(0, 1, 12);
		t.set(1, 1, 22);
		t.set(2, 1, -14);
		
		t.set(0, 2, 11);
		t.set(1, 2, -13);
		t.set(2, 2, -14);
		double res2 = t.det();
		assertEquals(res2, res1, 1);
		
		System.out.println(res1 + " == " + res2);
	}
}
