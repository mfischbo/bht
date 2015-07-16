package de.bht.fpa.mail.s780486.filter;

import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Abstract Filter for each value and operation based filter
 * 
 * @author M. Fischboeck
 * 
 */
public abstract class SimpleFilter implements IFilter {

  /**
   * Enumeration for the occurrence of the applied filter value
   * 
   * @author M. Fischboeck
   * 
   */
  public static enum Operation {
    IS, CONTAINS, CONTAINS_NOT, STARTS_WITH, ENDS_WITH
  }

  /* The value to match for */
  protected String value;

  /* The occrrence of the specified value */
  protected Operation operation;

  /**
   * Constructor providing the value and occurrence
   * 
   * @param value
   *          The value of the filter
   * @param operation
   *          The occurrence where to search for the value
   */
  protected SimpleFilter(String value, Operation operation) {
    this.value = value;
    this.operation = operation;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.bht.fpa.mail.s000000.common.filter.IFilter#filter(java.lang.Iterable)
   */
  @Override
  public abstract Set<Message> filter(Iterable<Message> messagesToFilter);

}
