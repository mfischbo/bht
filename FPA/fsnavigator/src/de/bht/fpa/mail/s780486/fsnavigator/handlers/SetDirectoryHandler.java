package de.bht.fpa.mail.s780486.fsnavigator.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler that is executed when the change of a base directory is requested.
 * 
 */
public class SetDirectoryHandler extends AbstractHandler {

  public static final String ID = "de.bht.fpa.mail.s780486.fsnavigator.commands.setBaseDirectoryDialog";

  /**
   * The constructor.
   */
  public SetDirectoryHandler() {

  }

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

    DirectoryDialog d = new DirectoryDialog(window.getShell());
    d.setFilterPath(System.getProperty("user.home"));
    String newDir = d.open();
    return newDir;
  }
}
