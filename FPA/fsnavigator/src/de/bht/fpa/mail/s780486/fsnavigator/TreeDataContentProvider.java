package de.bht.fpa.mail.s780486.fsnavigator;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s780486.fsnavigator.model.AFileSystemEntry;
import de.bht.fpa.mail.s780486.fsnavigator.model.FileSystemDirectory;

/**
 * Provides generation of a hierarchical file system like structure. The
 * provider utilizes static methods from {@link FSReader} in order to obtain
 * data from the disk.
 * 
 * @author M. Fischboeck (780486)
 * 
 */
public class TreeDataContentProvider implements ITreeContentProvider {

  /* The root element of the tree */
  private AFileSystemEntry rootElement;

  /**
   * Constructor providing the reading strategy and if hidden files shall be
   * included
   * 
   * @param readerStrategy
   *          The reader strategy to use
   * @param includeHiddenFiles
   *          Whether to include hidden files or omit them
   */
  public TreeDataContentProvider() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
   * .viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    String oldRoot = null;
    String newRoot = null;

    if (oldInput instanceof FileSystemDirectory) {
      oldRoot = ((FileSystemDirectory) oldInput).getRootPath();
    }

    if (newInput instanceof String) {
      newRoot = (String) newInput;
    } else if (newInput instanceof AFileSystemEntry) {
      newRoot = ((AFileSystemEntry) newInput).getRootPath();
    }

    if (oldRoot == null || !oldRoot.equals(newRoot)) {
      this.rootElement = FSReader.getEntryForPath(newRoot);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object
   * )
   */
  @Override
  public Object[] getElements(Object inputElement) {
    return getChildren(inputElement);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object
   * )
   */
  @Override
  public Object[] getChildren(Object parentElement) {

    if (parentElement instanceof String) {
      this.rootElement = FSReader.getEntryForPath((String) parentElement);
      this.rootElement.setChildren(FSReader.getChildren(this.rootElement));
      return this.rootElement.getChildren();
    }

    if (parentElement instanceof AFileSystemEntry) {
      AFileSystemEntry tmp = (AFileSystemEntry) parentElement;
      tmp.setChildren(FSReader.getChildren(tmp));
      return tmp.getChildren();
    }

    return new Object[0];
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
   */
  @Override
  public Object getParent(Object element) {
    if (element instanceof AFileSystemEntry) {
      return ((AFileSystemEntry) element).getParent();
    }

    return null;
  }

  /**
   * Returns the root element of the managed model
   * 
   * @return The root element of the managed model
   */
  public Object getRootElement() {
    return this.rootElement;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object
   * )
   */
  @Override
  public boolean hasChildren(Object element) {

    AFileSystemEntry tmp = (AFileSystemEntry) element;
    tmp.setChildren(FSReader.getChildren(tmp));
    return tmp.getChildren().length > 0;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  @Override
  public void dispose() {

  }

}
