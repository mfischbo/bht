package de.bht.fpa.mail.s780486.maillist.view;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.bht.fpa.mail.s780486.maillist.FilterDialogExecutionListener;
import de.bht.fpa.mail.s780486.maillist.MailListDateFormatter;
import de.ralfebert.rcputils.properties.IValue;
import de.ralfebert.rcputils.properties.IValueFormatter;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.layout.grouplayout.GroupLayout;
import org.eclipse.wb.swt.layout.grouplayout.LayoutStyle;

/**
 * MailListView extends a ViewPart to display a list of messages in a table like
 * view.
 * 
 * @author M. Fischboeck
 * 
 */

public class MailListView extends ViewPart {

  private TableViewer tableViewer;
  private Text text;

  // private Text text;

  /**
   * Default constructor
   */
  public MailListView() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
   * .Composite)
   */
  @Override
  public void createPartControl(Composite parent) {

    // laying out a new Group Layout for the both composites
    GroupLayout gl_parent = new GroupLayout(parent);
    Composite cUpper = new Composite(parent, SWT.NONE);
    Composite cLower = new Composite(parent, SWT.NONE);

    gl_parent.setHorizontalGroup(gl_parent.createParallelGroup(GroupLayout.LEADING)
        .add(cUpper, GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
        .add(cLower, GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE));
    gl_parent.setVerticalGroup(gl_parent.createParallelGroup(GroupLayout.LEADING).add(
        gl_parent.createSequentialGroup().add(cUpper, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.RELATED).add(cLower, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)));

    // adding the search field and label to the upper composite
    Label lblSearch = new Label(cUpper, SWT.NONE);
    lblSearch.setBounds(10, 8, 40, 14);
    lblSearch.setText("Search");

    text = new Text(cUpper, SWT.BORDER);
    text.setBounds(56, 5, 528, 19);
    parent.setLayout(gl_parent);

    // adding the table viewer to the lower composite
    TableViewerBuilder t = new TableViewerBuilder(cLower);
    t.createColumn("Importance").bindToValue(MessageValues.IMPORTANCE).build();
    t.createColumn("Received").bindToValue(MessageValues.RECEIVED).format(new MailListDateFormatter())
        .useAsDefaultSortColumn().build();
    t.createColumn("Read").bindToValue(MessageValues.READ).build();

    // Return the E-Mail of the Sender and make it sortable
    t.createColumn("Sender").bindToValue(MessageValues.SENDER).format(new IValueFormatter<Sender, String>() {

      @Override
      public String format(Sender obj) {
        return obj.getEmail();
      }

      @Override
      public Sender parse(String obj) {
        return null;
      }

    }).sortBy(new IValue() {

      @Override
      public Object getValue(Object element) {
        return ((Message) element).getSender().getEmail();
      }

      @Override
      public void setValue(Object element, Object value) {
      }

    }).build();

    // Return the email of the recipient and sort by the first address
    t.createColumn("Recipients").bindToValue(MessageValues.RECIPIENT)
        .format(new IValueFormatter<List<Recipient>, String>() {

          @Override
          public String format(List<Recipient> obj) {

            StringBuffer b = new StringBuffer();
            for (final Recipient r : obj) {
              b.append(r.getEmail());
              b.append(", ");
            }

            String retval = b.toString();
            return retval.substring(0, retval.length() - 2);
          }

          @Override
          public List<Recipient> parse(String obj) {
            return null;
          }

        }).sortBy(new IValue() {

          @Override
          public Object getValue(Object element) {
            return ((Message) element).getRecipients().get(0).getEmail();
          }

          @Override
          public void setValue(Object element, Object value) {
          }

        }).build();

    t.createColumn("Subject").bindToValue(MessageValues.SUBJECT).build();
    t.getTable().setSortColumn(t.getTable().getColumn(1));
    tableViewer = t.getTableViewer();

    // register a key listener for the search field
    text.addKeyListener(new SearchFieldKeyListener(this.tableViewer));

    // register interest on selections
    getSite().getPage().addSelectionListener(new TreeViewSelectionListener(t));

    // register for broadcasting selection events
    getSite().setSelectionProvider(this.tableViewer);

    ICommandService cmdService = (ICommandService) this.getSite().getService(ICommandService.class);
    cmdService.addExecutionListener(new FilterDialogExecutionListener(tableViewer));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    tableViewer.getTable().setFocus();
  }
}