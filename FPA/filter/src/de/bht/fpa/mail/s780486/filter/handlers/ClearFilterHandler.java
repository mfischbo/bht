package de.bht.fpa.mail.s780486.filter.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import de.bht.fpa.mail.s780486.filter.NullFilter;

/**
 * Handler that is executed on the "Clear Filter..." menu entry. The handler
 * returns NullFilter to reset all filter settings
 * 
 * @author M. Fischboeck
 * 
 */

public class ClearFilterHandler extends AbstractHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands
   * .ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    return new NullFilter();
  }

}
