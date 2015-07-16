package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter that inspects a
 * {@link de.bht.fpa.mail.common.s000000.mail.model.Message}'s subject.
 * 
 * @author M. Fischboeck
 * 
 */
public class SubjectFilter extends SimpleFilter {

  /**
   * Constructor specifying value and occurrence of the filter
   * 
   * @param value
   *          The value to filter for
   * @param operation
   *          The occurrence of the value
   */
  public SubjectFilter(String value, Operation operation) {
    super(value, operation);
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s780486.filter.SimpleFilter#filter(java.lang.Iterable)
   */
  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {

    HashSet<Message> retval = new HashSet<Message>();
    Iterator<Message> it = messagesToFilter.iterator();

    while (it.hasNext()) {
      Message m = it.next();
      String subject = m.getSubject();

      if (this.operation == Operation.IS) {
        if (subject.equals(value)) {
          retval.add(m);
          break;
        }
      }

      if (this.operation == Operation.CONTAINS) {
        if (subject.contains(value)) {
          retval.add(m);
          break;
        }
      }

      if (this.operation == Operation.CONTAINS_NOT) {
        if (!subject.contains(value)) {
          retval.add(m);
          break;
        }
      }

      if (this.operation == Operation.STARTS_WITH) {
        if (subject.startsWith(value)) {
          retval.add(m);
          break;
        }
      }

      if (this.operation == Operation.ENDS_WITH) {
        if (subject.endsWith(value)) {
          retval.add(m);
          break;
        }
      }
    }
    return retval;
  }
}
