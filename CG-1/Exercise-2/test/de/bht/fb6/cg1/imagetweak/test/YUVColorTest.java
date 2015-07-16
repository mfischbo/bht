package de.bht.fb6.cg1.imagetweak.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bht.fb6.cg1.imagetweak.model.YUVColor;

public class YUVColorTest {

	
	@Test
	public void conversionTest() {
		
		YUVColor yuv = new YUVColor(0, 0, 0);
		yuv.fromRGB(new int[] {255, 255, 255});	
		int[] rgb = yuv.toRGB();
		System.out.println(rgb.toString());
		
		yuv.fromRGB(new int[] {0, 0, 0});
		rgb = yuv.toRGB();
		System.out.println(rgb.toString());
		
		yuv.fromRGB(new int[] {0, 127, 0});
		rgb = yuv.toRGB();
		System.out.println(rgb.toString());
	
	}
	
	
	@Test
	public void conversion2() {
		YUVColor yuv = new YUVColor(0, 0, 0);
		yuv.fromRGB(new int[] {0,255, 0});
		int[] rgb = yuv.toRGB();
		
	}
}
