package de.bht.vs.ex2.model;

/**
 * POJO for holding an entry in the phonebook
 * @author M. Fischboeck
 *
 */

public class Entry {

	private String name;
	private String number;
	
	public Entry(String name, String number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Entry [name=" + name + ", number=" + number + "]";
	}
}
