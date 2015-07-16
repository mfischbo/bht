package de.bht.fpa.mail.s780486.imapnavigation.handlers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s780486.imapnavigation.ImapView;
import de.bht.fpa.mail.s780486.imapnavigation.SyncJob;

/**
 * Listener that is executed whenever a Job is executed
 * 
 * @author M. Fischboeck
 * 
 */

public class ImapSyncJobChangeListener implements IJobChangeListener {

  public ImapSyncJobChangeListener() {
  }

  @Override
  public void aboutToRun(IJobChangeEvent event) {
  }

  @Override
  public void awake(IJobChangeEvent event) {
  }

  @Override
  public void done(IJobChangeEvent event) {

    // only execute if it's a SyncJob
    if (event.getJob() instanceof SyncJob) {

      // only execute if the job was successfully processed
      if (event.getJob().getResult().getSeverity() == IStatus.OK) {
        SyncJob job = (SyncJob) event.getJob();
        final Account syncedAccount = job.getAccount();

        Display.getDefault().syncExec(new Runnable() {

          @Override
          public void run() {

            // retrieve the view to be updated
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages()[0];

            IViewReference[] refs = page.getViewReferences();
            for (IViewReference ref : refs) {
              if (ref.getTitle().equals(ImapView.VIEW_TITLE)) {

                // update it ;)
                ImapView view = (ImapView) ref.getView(false);
                view.updateAccount(syncedAccount);
              }
            }
          }
        });

      } // end if Status == OK
    } // end if is SyncJob
  }

  @Override
  public void running(IJobChangeEvent event) {
  }

  @Override
  public void scheduled(IJobChangeEvent event) {
  }

  @Override
  public void sleeping(IJobChangeEvent event) {
  }
}
