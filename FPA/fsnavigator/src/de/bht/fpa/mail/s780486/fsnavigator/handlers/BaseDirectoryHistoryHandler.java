package de.bht.fpa.mail.s780486.fsnavigator.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s780486.fsnavigator.views.BaseDirectoryHistoryDialog;

/**
 * Handler to be executed when a History Dialog open is requested.
 * 
 * @author M. Fischboeck
 * 
 */
public class BaseDirectoryHistoryHandler extends AbstractHandler {

  /** The id of the command */
  public static final String ID = "de.bht.fpa.mail.s780486.fsnavigator.BaseDirectoryHistoryCommand";

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands
   * .ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    BaseDirectoryHistoryDialog d = new BaseDirectoryHistoryDialog(window.getShell());
    d.setBlockOnOpen(true);
    d.open();
    String selection = d.getSelection();
    return selection;
  }

}
