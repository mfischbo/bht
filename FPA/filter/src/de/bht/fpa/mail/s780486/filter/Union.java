package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Unites a set of filters and returns all
 * {@link de.bht.fpa.mail.common.s000000.mail.model.Message}'s that are returned
 * by any of the specified filters (disjunction filter).
 * 
 * @author M. Fischboeck
 * 
 */
public class Union implements IFilter {

  protected IFilter[] parents;

  /**
   * Constructor providing a unspecified amount of filters to unite
   * 
   * @param filters
   *          The filters results will be united for
   */
  public Union(IFilter... filters) {
    this.parents = filters;
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

    HashSet<Message> m = new HashSet<Message>();
    Iterator<Message> it = messagesToFilter.iterator();
    while (it.hasNext()) {
      m.add(it.next());
    }

    for (final IFilter f : this.parents) {
      HashSet<Message> result = (HashSet<Message>) f.filter(m);

      for (Message message : result) {
        if (!retval.contains(message)) {
          retval.add(message);
        }
      }
    }
    return retval;
  }
}
