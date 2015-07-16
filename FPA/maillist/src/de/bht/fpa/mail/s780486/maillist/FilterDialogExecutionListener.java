package de.bht.fpa.mail.s780486.maillist;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s780486.maillist.view.MailFilterViewerFilter;

/**
 * Execution listener that applies a new ViewerFilter on the TableViewer when
 * the filter combination has changed.
 * 
 * @author M. Fischboeck
 * 
 */
public class FilterDialogExecutionListener implements IExecutionListener {

  /* The viewer to apply the ViewerFilter on */
  private final StructuredViewer viewer;

  /**
   * Constructor providing the viewer the ViewerFilter will be applied on
   * 
   * @param viewer
   *          The viewer that is going to be filtered
   */
  public FilterDialogExecutionListener(StructuredViewer viewer) {
    this.viewer = viewer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#notHandled(java.lang.String,
   * org.eclipse.core.commands.NotHandledException)
   */
  @Override
  public void notHandled(String commandId, NotHandledException exception) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#postExecuteFailure(java.lang
   * .String, org.eclipse.core.commands.ExecutionException)
   */
  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#postExecuteSuccess(java.lang
   * .String, java.lang.Object)
   */
  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {

    if (returnValue != null && returnValue instanceof IFilter) {

      IFilter filter = (IFilter) returnValue;

      // clear all filters if any filters are set
      ViewerFilter[] appliedFilters = this.viewer.getFilters();
      if (appliedFilters.length > 0) {
        for (ViewerFilter f : appliedFilters) {
          this.viewer.removeFilter(f);
        }
      }

      this.viewer.addFilter(new MailFilterViewerFilter(filter));
      this.viewer.refresh();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#preExecute(java.lang.String,
   * org.eclipse.core.commands.ExecutionEvent)
   */
  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
  }
}
