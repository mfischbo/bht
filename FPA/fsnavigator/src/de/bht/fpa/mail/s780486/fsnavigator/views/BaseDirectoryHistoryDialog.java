package de.bht.fpa.mail.s780486.fsnavigator.views;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import de.bht.fpa.mail.s780486.fsnavigator.BaseDirectoryHistoryProvider;

/**
 * Dialog that displays the history of base directories. Each time a new base
 * directory has been set using the appropriate command an entry to the history
 * is added. This dialog allows selection of entries that where selected in the
 * past.
 * 
 * @author M. Fischboeck (780486)
 * 
 */

public class BaseDirectoryHistoryDialog extends Dialog implements ISelectionChangedListener {

  /* Stores the last selection from the dialog */
  private String lastSelection;

  /**
   * Constructor that provides a shell
   * 
   * @param parentShell
   *          The parent shell to provide
   */
  public BaseDirectoryHistoryDialog(Shell parentShell) {
    super(parentShell);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
   * .Composite)
   */
  @Override
  public Control createDialogArea(Composite parent) {
    Composite container = (Composite) super.createDialogArea(parent);
    container.setLayout(new GridLayout(1, false));

    ListViewer listViewer = new ListViewer(container, SWT.BORDER | SWT.V_SCROLL);
    listViewer.addSelectionChangedListener(this);

    List list = listViewer.getList();
    list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    // lookup the history provider for any previous contents
    BaseDirectoryHistoryProvider provider = new BaseDirectoryHistoryProvider();
    String[] entries = provider.getEntries();
    if (entries.length == 0) {
      list.add("No history available...");
      list.setEnabled(false);
    } else {
      for (final String s : entries) {
        list.add(s);
      }
    }
    return container;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org
   * .eclipse.jface.viewers.SelectionChangedEvent)
   */
  @Override
  public void selectionChanged(SelectionChangedEvent event) {
    ListViewer viewer = (ListViewer) event.getSource();
    List list = viewer.getList();

    String[] selection = list.getSelection();
    if (selection.length > 0) {
      this.lastSelection = selection[0];
    }
  }

  /**
   * Returns the selection that was taken by the user
   * 
   * @return The users last selection
   */
  public String getSelection() {
    return this.lastSelection;
  }
}
