package de.bht.vs.ex2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bht.vs.ex2.model.Entry;

/**
 * Class that represent a phonebook containing {@link Entry} objects.
 * @author M. Fischboeck
 *
 */
public class PhoneBook {

	/* Static list of entries, contained in this phonebook */
	private static List<Entry> entries = new ArrayList<Entry>();
	
	private static boolean initialized = false;
	
	/**
	 * Default constructor, reads from an XML file
	 */
	public PhoneBook() {
		
		if (!initialized) {
			try {
				InputStream in = getClass().getResourceAsStream("/de/bht/vs/ex2/resources/PhoneBook.xml");
				DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder     = fact.newDocumentBuilder();
				Document d = builder.parse(in);
				
				NodeList nl = d.getChildNodes().item(0).getChildNodes();
				for (int i=0; i < nl.getLength(); i++) {
					Node n = nl.item(i);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						// this is an entry. Read the Attribute and text content
						String name = n.getAttributes().getNamedItem("name").getTextContent();
						String number= n.getTextContent();
						entries.add(new Entry(name, number));
					}
				}
				PhoneBook.initialized = true;
			} catch (Exception ex) {
				System.err.println("Error during phonebook initialization. Cause: " + ex.getMessage());
			}
		} 
	}
	
	/**
	 * Returns a list of entries contained in the book
	 * @return A list of entries
	 */
	public List<Entry> getEntries() {
		return entries;
	}
}
