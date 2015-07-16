package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

/**
 * Filter that accepts all
 * {@link de.bht.fpa.mail.common.s000000.mail.model.Message}'s with a matching
 * Recipient property. The filter inspects the recipients name and email
 * address.
 * 
 * @author M. Fischboeck
 * 
 */
public class RecipientsFilter extends SimpleFilter {

  /**
   * Constructor with a specified value and a operation
   * 
   * @param value
   *          The value to match the recipient name and email address
   * @param operation
   *          The operation of the filter
   */
  public RecipientsFilter(String value, Operation operation) {
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
      List<Recipient> recips = m.getRecipients();

      for (final Recipient r : recips) {

        String mail = r.getEmail();
        String name = r.getPersonal();

        if (this.operation == Operation.IS) {
          if (mail.equals(value) || name.equals(value)) {
            retval.add(m);
          }
        }

        if (this.operation == Operation.CONTAINS) {
          if (mail.contains(value) || name.contains(value)) {
            retval.add(m);
            break;
          }
        }

        if (this.operation == Operation.CONTAINS_NOT) {
          if (!mail.contains(value) && !name.contains(value)) {
            retval.add(m);
            break;
          }
        }

        if (this.operation == Operation.STARTS_WITH) {
          if (mail.startsWith(value) || name.startsWith(value)) {
            retval.add(m);
            break;
          }
        }

        if (this.operation == Operation.ENDS_WITH) {
          if (mail.endsWith(value) || name.endsWith(value)) {
            retval.add(m);
            break;
          }
        }
      }
    }
    return retval;
  }
}
