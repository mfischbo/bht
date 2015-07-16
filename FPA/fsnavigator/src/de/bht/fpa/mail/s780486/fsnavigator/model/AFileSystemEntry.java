package de.bht.fpa.mail.s780486.fsnavigator.model;

/**
 * POJO that provides basic information for a file system entry. This class
 * implements the {@link IComposite} interface to build hierarchical structures
 * of AFileSystemEntries. The class is not bound to a specific implementation of
 * a file system, so it can be used for abstraction of different implementations
 * (e.g. remote file systems)
 * 
 * @author M. Fischboeck (780486)
 */

public abstract class AFileSystemEntry implements IComposite {

  protected String name;
  protected String parentPath;
  protected AFileSystemEntry parent;
  protected AFileSystemEntry[] children;

  /**
   * Default constructor
   */
  public AFileSystemEntry() {
    this.name = null;
    this.parent = null;
    this.parentPath = null;
    this.children = new AFileSystemEntry[0];
  }

  /**
   * Constructor providing name, parent and children
   * 
   * @param name
   *          The name of the entry
   * @param parent
   *          The entries parent entry, might be null
   * @param children
   *          The entries child entries, might be null or empty array
   */
  public AFileSystemEntry(String name, String parentPath, AFileSystemEntry parent, AFileSystemEntry[] children) {
    this.name = name;
    this.parentPath = parentPath;
    this.parent = parent;
    if (children == null) {
      this.children = new AFileSystemEntry[0];
    } else {
      this.children = children;
    }
  }

  /**
   * Returns the name of the entry
   * 
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the entry
   * 
   * @param name
   *          The name of the entry
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s780486.fsnavigator.model.IComposite#getParent()
   */
  @Override
  public AFileSystemEntry getParent() {
    return this.parent;
  }

  /**
   * Sets the entries parent entry
   * 
   * @param parent
   *          The parent entry of this entry
   */
  public void setParent(AFileSystemEntry parent) {
    this.parent = parent;
  }

  /**
   * Returns the full path to this entry on the underlying file system
   * 
   * @return The full path to this entry.
   */
  public String getRootPath() {
    return this.parentPath + "/" + this.name;
  }

  /**
   * Sets the path to this entries parent entry
   * 
   * @param parentPath
   *          The path to this entries parent entry
   */
  public void setParentPath(String parentPath) {
    this.parentPath = parentPath;
  }

  /**
   * Returns true if the entry is a directory node
   * 
   * @return True for directories, false otherwise
   */
  public abstract boolean isDirectory();

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s780486.fsnavigator.model.IComposite#getChildren()
   */
  @Override
  public AFileSystemEntry[] getChildren() {
    return this.children;
  }

  /**
   * Sets the child entries of this entry.
   * 
   * @param children
   *          Array of child entries or null.
   */
  public void setChildren(AFileSystemEntry[] children) {
    if (children == null) {
      this.children = new AFileSystemEntry[0];
    } else {
      this.children = children;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s780486.fsnavigator.model.IComposite#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    if (this.children.length > 0) {
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    return "[AFileSystemEntry: " + this.parentPath + "/" + this.name + ", children: " + this.children.length + "]";
  }
}
