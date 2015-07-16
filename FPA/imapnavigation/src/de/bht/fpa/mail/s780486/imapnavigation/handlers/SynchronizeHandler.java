package de.bht.fpa.mail.s780486.imapnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s780486.imapnavigation.ImapView;
import de.bht.fpa.mail.s780486.imapnavigation.SyncJob;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SynchronizeHandler extends AbstractHandler {

  public static final String IMAP_SYNC_CMD = "IMAP_SYNC_CMD";

  public static final String COMMAND_ID = "de.bht.fpa.mail.s780486.imapnavigation.commands.synchronizeIMAP";

  /**
   * The constructor.
   */
  public SynchronizeHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    final Account acc = ImapHelper.getAccount(ImapView.REMOTE_IMAP_NAME);
    Job job = new SyncJob("IMAP Synchronization", acc);
    job.setUser(true);
    job.addJobChangeListener(new ImapSyncJobChangeListener());
    return job;
  }
}
