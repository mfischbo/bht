package de.bht.fpa.mail.s780486.fsnavigator;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class to provide a runtime storage for the base directory history.
 * 
 * @author M. Fischboeck (780486)
 * 
 */

public class BaseDirectoryHistoryProvider {

  /* The static data container */
  private static List<String> entries = new ArrayList<String>(5);

  /**
   * Default constructor
   */
  public BaseDirectoryHistoryProvider() {

  }

  /**
   * Constructor that provides entries to populate the data storage
   * 
   * @param data
   *          The entries to populate the storage with
   */
  public BaseDirectoryHistoryProvider(final String[] data) {
    if (data != null) {
      for (final String s : data) {
        if (!entries.contains(s)) {
          entries.add(s);
        }
      }
    }
  }

  /**
   * Adds an entry to the history. Note that the entry is only added if it's not
   * already contained
   * 
   * @param entry
   *          The entry to add
   */
  public void addEntry(final String entry) {
    if (entry != null && !entries.contains(entry)) {
      entries.add(entry);
    }
  }

  /**
   * Returns all entries from the history
   * 
   * @return The entries from the history
   */
  public String[] getEntries() {
    return entries.toArray(new String[entries.size()]);
  }
}
