package de.bht.fpa.mail.s780486.maillist.view;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s780486.common.IMailMessageProvider;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

/**
 * SelectionListener implementation that is called whenever a selection event is
 * broadcasted. The implementation will decide if the selection is an instance
 * of the {@link de.bht.fpa.mail.s780486.common.IMailMessageProvider}. If so all
 * messages are pulled from the provider and displayed in the TableView
 * 
 * @author M. Fischboeck
 * 
 */
public class TreeViewSelectionListener implements ISelectionListener {

  /* Reference to the TreeBuilder that displays all messages */
  private final TableViewerBuilder builder;

  /**
   * Constructor specifying the TableViewerBuilder to be used for displaying the
   * messages
   * 
   * @param builder
   *          The builder
   */
  public TreeViewSelectionListener(TableViewerBuilder builder) {
    this.builder = builder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.
   * IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
   */
  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {

    IStructuredSelection stsel = (IStructuredSelection) selection;

    if (stsel.getFirstElement() instanceof IMailMessageProvider) {
      IMailMessageProvider provider = (IMailMessageProvider) stsel.getFirstElement();

      ArrayList<Message> messages = new ArrayList<Message>();
      Iterator<Message> it = provider.getMessages();
      while (it.hasNext()) {
        messages.add(it.next());
      }
      builder.setInput(messages);
    }

    if (stsel.getFirstElement() instanceof Folder) {
      Folder inner = (Folder) stsel.getFirstElement();
      builder.setInput(inner.getMessages());
    }
  }
}
