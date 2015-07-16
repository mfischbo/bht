package de.bht.fpa.mail.s780486.filter.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.filter.FilterGroupType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s780486.filter.FilterFactory;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class FilterDialogHandler extends AbstractHandler {

  public static final String COMMAND_ID = "de.bht.fpa.mail.s780486.filter.handlers.FilterDialogCommand";

  /**
   * The constructor.
   */
  public FilterDialogHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

    FilterDialog dialog = new FilterDialog(window.getShell());
    dialog.create();
    dialog.setBlockOnOpen(true);
    dialog.open();

    List<FilterCombination> combination = dialog.getFilterCombinations();
    FilterGroupType type = dialog.getFilterGroupType();

    if (combination != null) {
      IFilter retval = FilterFactory.getInstance(type, combination);
      return retval;
    }

    return null;
  }
}
