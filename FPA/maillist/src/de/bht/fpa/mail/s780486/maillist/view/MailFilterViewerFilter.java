package de.bht.fpa.mail.s780486.maillist.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * ViewerFilter that holds a set of messages the user filter combination has
 * been applied to. This implementation decides if a Message will be shown in
 * the TableViewer based upon it's existence in the provided set. This approach
 * avoids calling the whole IFilter infrastructure for each single message
 * 
 * @author M. Fischboeck
 * 
 */
public class MailFilterViewerFilter extends ViewerFilter {

  private final IFilter filter;

  /**
   * Constructor providing a set of already filtered messages
   * 
   */
  public MailFilterViewerFilter(final IFilter filter) {
    this.filter = filter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
   * .Viewer, java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    Message m = (Message) element;
    List<Message> container = new ArrayList<Message>(1);
    container.add(m);
    Set<Message> retval = this.filter.filter(container);
    return retval.size() > 0;
  }

}
