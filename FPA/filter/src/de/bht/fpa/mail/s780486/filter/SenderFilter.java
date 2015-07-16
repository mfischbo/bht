package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter that accepts all
 * {@link de.bht.fpa.mail.common.s000000.mail.model.Message}'s with a matching
 * Sender property. The filter inspects the senders name and email address.
 * 
 * @author M. Fischboeck
 * 
 */
public class SenderFilter extends SimpleFilter {

  /**
   * Constructor specifying the value to match and it's occurrence
   * 
   * @param value
   *          The value to match
   * @param operation
   *          The occurrence of the value
   */
  public SenderFilter(String value, Operation operation) {
    super(value, operation);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {

    HashSet<Message> retval = new HashSet<Message>();
    Iterator<Message> it = messagesToFilter.iterator();

    while (it.hasNext()) {
      Message m = it.next();
      String sender = m.getSender().getEmail();
      String name = m.getSender().getPersonal();

      if (this.operation == Operation.IS) {
        if (sender.equals(value) || name.equals(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.CONTAINS) {
        if (sender.contains(value) || name.contains(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.CONTAINS_NOT) {
        if (!sender.contains(value) && !name.contains(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.STARTS_WITH) {
        if (sender.startsWith(value) || name.startsWith(value)) {
          retval.add(m);
        }
      }

      if (this.operation == Operation.ENDS_WITH) {
        if (sender.endsWith(value) || name.endsWith(value)) {
          retval.add(m);
        }
      }
    }
    return retval;
  }
}
