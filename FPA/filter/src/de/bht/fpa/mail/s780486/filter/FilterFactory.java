package de.bht.fpa.mail.s780486.filter;

import java.util.List;

import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterGroupType;
import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.FilterType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s780486.filter.SimpleFilter.Operation;

/**
 * Factory to generate instances of
 * {@link de.bht.fpa.mail.common.filter.IFilter} objects based on a
 * {@link de.bht.fpa.mail.s000000.common.filter.FilterGroupType} and a list of
 * {@link de.bht.fpa.mail.s000000.common.filter.FilterCombination}.
 * 
 * When no filter combinations are provided the factory will return a NullFilter
 * which does not filter out any content.
 * 
 * @author M. Fischboeck (780486)
 * 
 */

public final class FilterFactory {

  /**
   * Returns an instance of an IFilter implementing filter
   * 
   * @param type
   *          The type of grouping will result in a Union or Intersection of the
   *          filters
   * @param combination
   *          The combination of provided filters
   * @return The IFilter object which is capable to filter the input
   */
  public static IFilter getInstance(FilterGroupType type, List<FilterCombination> combination) {

    IFilter[] filters = new IFilter[combination.size()];
    for (int i = 0; i < combination.size(); ++i) {

      FilterCombination fc = combination.get(i);
      IFilter temp = null;

      // Importance Filter
      if (fc.getFilterType() == FilterType.IMPORTANCE) {
        temp = new ImportanceFilter((Importance) fc.getFilterValue());
      }

      // Read Filter
      if (fc.getFilterType() == FilterType.READ) {
        temp = new ReadFilter((Boolean) fc.getFilterValue());
      }

      // Sender Filter
      if (fc.getFilterType() == FilterType.SENDER) {
        Operation op = getOperation(fc.getFilterOperator());
        temp = new SenderFilter((String) fc.getFilterValue(), op);
      }

      // Recpients Filter
      if (fc.getFilterType() == FilterType.RECIPIENTS) {
        Operation op = getOperation(fc.getFilterOperator());
        temp = new RecipientsFilter((String) fc.getFilterValue(), op);
      }

      // Subject Filter
      if (fc.getFilterType() == FilterType.SUBJECT) {
        Operation op = getOperation(fc.getFilterOperator());
        temp = new SubjectFilter((String) fc.getFilterValue(), op);
      }

      // Text Filter
      if (fc.getFilterType() == FilterType.TEXT) {
        Operation op = getOperation(fc.getFilterOperator());
        temp = new TextFilter((String) fc.getFilterValue(), op);
      }

      if (temp == null) {
        temp = new NullFilter();
      }

      filters[i] = temp;
    }

    // check for union or intersection
    if (type == FilterGroupType.UNION) {
      Union u = new Union(filters);
      return u;
    }

    if (type == FilterGroupType.INTERSECTION) {
      Intersection i = new Intersection(filters);
      return i;
    }

    return new NullFilter();
  }

  /**
   * Translates the provided FilterOperator into a Operation
   * 
   * @param operator
   *          The operator to translate
   * @return The translated {@link Operation}
   */
  private static final Operation getOperation(FilterOperator operator) {
    if (operator == FilterOperator.CONTAINS) {
      return Operation.CONTAINS;
    }

    if (operator == FilterOperator.CONTAINS_NOT) {
      return Operation.CONTAINS_NOT;
    }

    if (operator == FilterOperator.ENDS_WITH) {
      return Operation.ENDS_WITH;
    }

    if (operator == FilterOperator.STARTS_WITH) {
      return Operation.STARTS_WITH;
    }

    if (operator == FilterOperator.IS) {
      return Operation.IS;
    }

    throw new IllegalArgumentException("Unsupported Filter Operator");
  }
}
