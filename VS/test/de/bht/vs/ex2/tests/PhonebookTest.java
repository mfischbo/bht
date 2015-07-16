/**
 * 
 */
package de.bht.vs.ex2.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bht.vs.ex2.PhoneBook;

/**
 * @author M. Fischboeck
 *
 */
public class PhonebookTest {

	@Test
	public void canParseEntries() throws Exception {
		PhoneBook pb = new PhoneBook();
		assertEquals(6, pb.getEntries().size());
	}
}
