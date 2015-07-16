package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter that filters {@link de.bht.fpa.mail.s000000.common.mail.model.Message}
 * using their importance.
 * 
 * @author M. Fischboeck
 */

public class ImportanceFilter implements IFilter {

  protected Importance imp;

  /**
   * Default constructor providing the Importance to filter for
   * 
   * @param value
   *          The value the filter accepts
   */
  public ImportanceFilter(Importance value) {
    this.imp = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.bht.fpa.mail.s000000.common.filter.IFilter#filter(java.lang.Iterable)
   */
  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {

    HashSet<Message> retval = new HashSet<Message>();
    Iterator<Message> it = messagesToFilter.iterator();

    while (it.hasNext()) {
      Message m = it.next();
      Importance im = m.getImportance();

      if (im == this.imp) {
        retval.add(m);
      }
    }
    return retval;
  }
}
