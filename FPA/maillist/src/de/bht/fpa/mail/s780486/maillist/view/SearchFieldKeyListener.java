package de.bht.fpa.mail.s780486.maillist.view;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Text;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s780486.maillist.MailListDateFormatter;

/**
 * KeyListener implementation for the search bar in the maillist. The listener
 * applies a filter on all messages that contain the text provided by the
 * textfield
 * 
 * @author M. Fischboeck
 * 
 */

public class SearchFieldKeyListener implements KeyListener {

  private final StructuredViewer viewer;

  /**
   * Constructor providing the viewer
   * 
   * @param viewer
   *          The viewer to apply filters on
   */
  public SearchFieldKeyListener(StructuredViewer viewer) {
    this.viewer = viewer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent
   * )
   */
  @Override
  public void keyPressed(KeyEvent e) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent
   * )
   */
  @Override
  public void keyReleased(KeyEvent e) {
    Text txt = (Text) e.getSource();
    final String currentText = txt.getText();

    // remove all applied filters
    for (ViewerFilter f : viewer.getFilters()) {
      viewer.removeFilter(f);
    }

    viewer.addFilter(new ViewerFilter() {

      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        Message m = (Message) element;

        if (m.getSubject().contains(currentText)) {
          return true;
        }

        if (m.getText().contains(currentText)) {
          return true;
        }

        if (m.getSender().getEmail().contains(currentText)) {
          return true;
        }

        if (m.getSender().getPersonal().contains(currentText)) {
          return true;
        }

        for (Recipient r : m.getRecipients()) {
          if (r.getEmail() != null && r.getEmail().contains(currentText)) {
            return true;
          }

          if (r.getPersonal() != null && r.getPersonal().contains(currentText)) {
            return true;
          }
        }

        MailListDateFormatter df = new MailListDateFormatter();
        if (df.format(m.getSent()).contains(currentText)) {
          return true;
        }

        if (df.format(m.getReceived()).contains(currentText)) {
          return true;
        }
        return false;

      }
    });
  }

}
