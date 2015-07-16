package de.bht.fpa.mail.s780486.imapnavigation.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Handler for the synchronize command
 * 
 * @author M. Fischboeck
 * 
 */
public class SynchronizeExecutionListener implements IExecutionListener {

  @Override
  public void notHandled(String commandId, NotHandledException exception) {
  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {

    if (commandId.equals(SynchronizeHandler.COMMAND_ID)) {
      Job job = (Job) returnValue;
      job.schedule();
    }
  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
  }

}
