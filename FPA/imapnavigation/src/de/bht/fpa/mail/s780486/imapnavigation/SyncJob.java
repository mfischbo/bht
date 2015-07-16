package de.bht.fpa.mail.s780486.imapnavigation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.imapsync.SynchronizationException;
import de.bht.fpa.mail.s000000.common.mail.model.Account;

/**
 * Job that synchronizes the given account using the IMAP protocol
 * 
 * @author M. Fischboeck
 * 
 */

public class SyncJob extends Job {

  /* The account that is beeing synchronized */
  private Account syncAccount;

  /**
   * Default constructor
   * 
   * @param name
   *          The name of the job
   */
  public SyncJob(String name) {
    super(name);
  }

  /**
   * Constructor providing the name and the account
   * 
   * @param name
   *          The name of the job
   * @param account
   *          The account to synchronize
   */
  public SyncJob(String name, Account account) {
    super(name);
    this.syncAccount = account;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor
   * )
   */
  @Override
  protected IStatus run(IProgressMonitor monitor) {

    if (this.syncAccount == null) {
      return new Status(Status.ERROR, "de.bht.fpa.mail.s780486.imapnavigation",
          "There is no account to be synchronized!");
    }

    try {
      ImapHelper.syncAllFoldersToAccount(this.syncAccount, monitor);
    } catch (SynchronizationException ex) {
      ex.printStackTrace();
    }

    return Status.OK_STATUS;
  }

  public Account getAccount() {
    return this.syncAccount;
  }

}
