package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter that inspects a
 * {@link de.bht.fpa.mail.common.s000000.mail.model.Message}'s Text.
 * 
 * @author M. Fischboeck
 * 
 */
public class TextFilter extends SimpleFilter {

  /**
   * Constructor specifying the value and occurrence of the filter pattern
   * 
   * @param value
   *          The value to filter for
   * @param operation
   *          The occurrence of the value
   */
  public TextFilter(String value, Operation operation) {
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
      String text = m.getText();

      if (this.operation == Operation.IS) {
        if (text.equals(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.CONTAINS) {
        if (text.contains(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.CONTAINS_NOT) {
        if (!text.contains(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.STARTS_WITH) {
        if (text.startsWith(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.ENDS_WITH) {
        if (text.endsWith(value)) {
          retval.add(m);
        }
      }
    }
    return retval;
  }
}
