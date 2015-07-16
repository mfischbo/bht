package de.bht.fpa.mail.s780486.fsnavigator.views;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s780486.fsnavigator.BaseDirectoryHistoryProvider;
import de.bht.fpa.mail.s780486.fsnavigator.FpaMailerLabelProvider;
import de.bht.fpa.mail.s780486.fsnavigator.TreeDataContentProvider;
import de.bht.fpa.mail.s780486.fsnavigator.handlers.BaseDirectoryHistoryHandler;
import de.bht.fpa.mail.s780486.fsnavigator.handlers.SetDirectoryHandler;
import de.bht.fpa.mail.s780486.fsnavigator.model.FileSystemFile;

/**
 * View that is capable of displaying a tree like structure. The view utilizes a
 * {@link org.eclipse.jface.viewers.TreeViewer} for it's general operation.
 * However this implementation listens to DoubleClickEvents used for displaying
 * children of an entry.<br/>
 * <br/>
 * <br/>
 * The NavigationView registers as IExecutionListener and is called before and
 * after the framework calls
 * {@link org.eclipse.core.commands.AbstractHandler#execute(ExecutionEvent)}
 * 
 * @author M. Fischboeck (780486)
 * 
 */

public class NavigationView extends ViewPart implements IExecutionListener {

  public static final String ID = "de.bht.fpa.mailer.s780486.fsnavigator";

  /* The key where the current base directory is stored in the memento */
  private static final String KEY_BASEDIR_PATH = "_BASEDIR_PATH";

  /* The key where the history for base directories is stored */
  private static final String KEY_HISTORY_ENTRIES = "_HISTORY_ENTRIES";

  /* The string to separate multiple entries in the history */
  private static final String KEY_HISTORY_SEPARATOR = ",#,";

  /* The actual tree viewer implementation */
  private TreeViewer viewer;

  /* The underlying content provider */
  private final TreeDataContentProvider cntProvider;

  /* The underlying history data provider */
  private final BaseDirectoryHistoryProvider history;

  /* The current base path of the view */
  private String basePath;

  /**
   * Default constructor
   */
  public NavigationView() {
    super();

    this.basePath = System.getProperty("user.home");
    this.cntProvider = new TreeDataContentProvider();
    this.history = new BaseDirectoryHistoryProvider();
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

    if (memento != null) {

      // load the previously set basePath
      final String path = memento.getString(this.getClass().getName() + KEY_BASEDIR_PATH);
      if (path != null && path.length() > 0) {
        this.basePath = path;
      }

      // load all history entries from the memento
      String entries = memento.getString(this.getClass().getName() + KEY_HISTORY_ENTRIES);
      if (entries != null && entries.length() > 0) {
        String[] clean = entries.split(KEY_HISTORY_SEPARATOR);
        for (final String s : clean) {
          if (s.length() > 0) {
            history.addEntry(s);
          }
        }
      }
    }

    // register as ICommandExecutionListener
    ICommandService cmdService = (ICommandService) this.getSite().getService(ICommandService.class);
    if (cmdService != null) {
      cmdService.addExecutionListener(this);
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

    // basic initialization
    this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    this.viewer.setContentProvider(this.cntProvider);
    this.viewer.setLabelProvider(new FpaMailerLabelProvider());

    // add a viewer filter to filter out files
    this.viewer.addFilter(new ViewerFilter() {

      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (element != null && element instanceof FileSystemFile) {
          return false;
        }
        return true;
      }

    });

    // init from the base directory
    this.viewer.setInput(this.basePath);

    // register the view as broadcaster for selection events
    getSite().setSelectionProvider(this.viewer);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    this.viewer.getControl().setFocus();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.part.ViewPart#saveState(org.eclipse.ui.IMemento)
   */
  @Override
  public void saveState(IMemento memento) {

    // save the current base directory
    if (this.basePath.length() > 0) {
      memento.putString(this.getClass().getName() + KEY_BASEDIR_PATH, basePath);
    }

    // store the history in the memento
    String[] entries = history.getEntries();
    StringBuffer b = new StringBuffer();
    for (final String s : entries) {
      b.append(s);
      b.append(KEY_HISTORY_SEPARATOR);
    }
    memento.putString(this.getClass().getName() + KEY_HISTORY_ENTRIES, b.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#notHandled(java.lang.String,
   * org.eclipse.core.commands.NotHandledException)
   */
  @Override
  public void notHandled(String commandId, NotHandledException exception) {
    // currently not used
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#postExecuteFailure(java.lang
   * .String, org.eclipse.core.commands.ExecutionException)
   */
  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
    // currently not used
  }

  /**
   * Updates the view on successful executions of commands that affect the base
   * directory
   */
  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {

    if (commandId.equals(SetDirectoryHandler.ID) || commandId.equals(BaseDirectoryHistoryHandler.ID)) {
      if (returnValue != null && returnValue instanceof String) {
        this.viewer.setInput(returnValue);
        this.basePath = (String) returnValue;
        history.addEntry((String) returnValue);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.commands.IExecutionListener#preExecute(java.lang.String,
   * org.eclipse.core.commands.ExecutionEvent)
   */
  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
    // currently not used
  }

}
