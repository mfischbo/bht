package de.bht.fb6.cg1.imagetweak.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.bht.fb6.cg1.imagetweak.model.HSVColor;

public class HSVColorTest {

	
	@Test
	public void convertsToRGB() {
		HSVColor cl = new HSVColor(18, 193, 221);
		
		int[] retval = cl.toRGB();
		assertEquals(221, retval[0]);
		assertEquals(127, retval[1]);
		assertEquals(53, retval[2]);
	}
	
	
	@Test
	public void convertsFromRGB2() {
		HSVColor cl = new HSVColor(0, 0, 0);
		cl.fromRGB(new int[] {254, 252, 253});
		int[] rgb = cl.toRGB();
		
	}
	
	@Test
	public void convertsFromRGB() {
		HSVColor cl = new HSVColor(0, 0, 0);
		cl.fromRGB(new int[] {221, 127, 53});
	
		int[] cols = cl.getParameters();
		int[] val  = {(26 * 255) / 360, (76 * 255) / 100, (87 * 255) / 100};
		
		assertEquals(val[0], cols[0], 1.0);
		assertEquals(val[1], cols[1], 1.0);
		assertEquals(val[2], cols[2], 1.0);
		
	}
}
