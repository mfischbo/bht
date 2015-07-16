package de.bht.vs.ex2;

import java.util.ArrayList;
import java.util.List;

import de.bht.vs.ex2.model.Entry;

/**
 * The searcher implementation to search entries in the phone book.
 * @author M. Fischboeck
 *
 */
public class Searcher extends Thread implements Runnable {

	/**
	 * Enumeration for the mode to search.
	 * @author M. Fischboeck
	 *
	 */
	public enum SearchOn {
		Name,
		Number
	};
	
	
	/* The entry to search for */
	private Entry searchFor;
	
	/* The data source to search on */
	private PhoneBook ds;
	
	/* The result that is being returned */
	private List<Entry> result;
	
	/* The field to search on */
	private SearchOn field;
	
	
	/**
	 * Constructor providing all required parameters for a search
	 * @param search The entry to search for
	 * @param ds The datasource phone book
	 * @param field The field to search on
	 */
	public Searcher(Entry search, PhoneBook ds, SearchOn field) {
		this.searchFor = search;
		this.ds = ds;
		this.result = new ArrayList<Entry>();
		this.field = field;
	}
	
	
	/**
	 * Executes the search on the provided phone book
	 */
	@Override
	public void run() {
		List<Entry> entries = this.ds.getEntries();
		switch (this.field) {
		case Name:
			System.out.println("Searching by name ... ");
			for (Entry e : entries) {
				if (e.getName().equals(this.searchFor.getName()))
					this.result.add(e);
			}
			break;
			
		case Number:
			System.out.println("Searching by number ... ");
			for (Entry e : entries) {
				if (e.getNumber().equals(this.searchFor.getNumber()))
					this.result.add(e);
			}
			break;
		}
	}
	
	/**
	 * Returns a list of entries that are matching the search criteria
	 * @return List of matching entries
	 */
	public List<Entry> getResult() {
		return this.result;
	}
}
