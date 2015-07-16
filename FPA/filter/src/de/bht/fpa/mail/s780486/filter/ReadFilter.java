package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter that accepts all
 * {@link de.bht.fpa.mail.common.s000000.mail.model.Message} whom's Read
 * criteria is matching the provided filter value
 * 
 * @author M. Fischboeck
 * 
 */
public class ReadFilter implements IFilter {

  protected boolean read;

  /**
   * Constructor specifying whether Read mails shall be accepted or not
   * 
   * @param read
   *          If true, read messages are accepted
   */
  public ReadFilter(boolean read) {
    this.read = read;
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
      boolean r = m.isRead();

      if (r == this.read) {
        retval.add(m);
      }
    }
    return retval;
  }
}
