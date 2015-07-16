package de.bht.fpa.mail.s780486.imapnavigation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.builder.AccountBuilder;
import de.bht.fpa.mail.s780486.imapnavigation.handlers.SynchronizeExecutionListener;

/**
 * Tree viewer for IMAP Accounts
 * 
 * @author M. Fischboeck
 * 
 */
public class ImapView extends ViewPart {

  public static final String VIEW_TITLE = "Imap Navigation";

  public static final String REMOTE_IMAP_NAME = "Google Mail";

  /* The underlaying tree viewer */
  private TreeViewer treeViewer;

  /* The content provider for IMAP Accounts */
  private final ImapContentProvider cntProvider;

  /* The label provider */
  private final ImapLabelProvider labelProvider;

  /* List of accounts to be displayed */
  private final List<Account> accounts;

  /**
   * Default constructor
   */
  public ImapView() {
    super();

    // setup all providers
    this.cntProvider = new ImapContentProvider();
    this.labelProvider = new ImapLabelProvider();

    // setup some dummy and a remote account
    accounts = new ArrayList<Account>(3);
    accounts.add(ImapContentProvider.createDummyAccount());
    accounts.add(ImapContentProvider.createDummyAccount());

    // try to load the remote account
    Account remote = ImapHelper.getAccount(REMOTE_IMAP_NAME);
    if (remote != null) {
      accounts.add(remote);
    } else {

      AccountBuilder b = AccountBuilder.newAccountBuilder();
      // @formatter:off
      remote = 
          b.name(REMOTE_IMAP_NAME)
          .host("imap.gmail.com")
          .username("bhtfpa@googlemail.com")
          .password("B-BgxkT_anr2bubbyTLM")
          .build();
      // @formatter:on
      try {
        ImapHelper.saveAccount(remote);
        remote = ImapHelper.getAccount(remote.getName());
      } catch (Exception pq) {
        System.err.println(pq.getMessage());
      }
      accounts.add(remote);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite,
   * org.eclipse.ui.IMemento)
   */
  @Override
  public void init(IViewSite site, IMemento memento) throws PartInitException {
    super.init(site, memento);

    ICommandService cmd = (ICommandService) this.getSite().getService(ICommandService.class);
    if (cmd != null) {
      cmd.addExecutionListener(new SynchronizeExecutionListener());
    }
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

    this.treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    this.treeViewer.setContentProvider(this.cntProvider);
    this.treeViewer.setLabelProvider(this.labelProvider);

    this.treeViewer.setInput(accounts);

    // set as broadcaster for selection events
    getSite().setSelectionProvider(this.treeViewer);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    this.treeViewer.getControl().setFocus();
  }

  /**
   * Replaces the view internal account with an updated Account. The account is
   * pulled by either matching name or matching ID
   * 
   * @param account
   *          The updated account
   */
  public void updateAccount(Account account) {

    for (Account c : this.accounts) {
      if (account.getName().equals(c.getName()) || account.getId() == c.getId()) {
        int idx = this.accounts.indexOf(c);
        this.accounts.remove(c);
        this.accounts.add(idx, account);

        ImapHelper.saveAccount(account);
        break;
      }
    }
    this.treeViewer.refresh();
  }

}
