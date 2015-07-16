package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter that accepts {@link de.bht.fpa.mail.common.s000000.mail.model.Message}
 * 's where the criteria matches all of the applied filters. (Filter
 * conjunction)
 * 
 * @author M. Fischboeck
 * 
 */
public class Intersection implements IFilter {

  IFilter[] parents;

  /**
   * Constructor with a specified set of filters to intersect
   * 
   * @param filters
   *          The filters to intersect
   */
  public Intersection(IFilter... filters) {
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
    Iterator<Message> it = messagesToFilter.iterator();
    while (it.hasNext()) {
      retval.add(it.next());
    }

    for (IFilter f : this.parents) {
      retval = (HashSet<Message>) f.filter(retval);
    }

    return retval;
  }
}
