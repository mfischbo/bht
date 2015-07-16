package de.bht.fpa.mail.s780486.fsnavigator.model;

/**
 * POJO to represent a file in a FileSystem
 * 
 * @author M. Fischboeck (780486)
 * 
 */
public class FileSystemFile extends AFileSystemEntry {

  /* The extension of the file */
  public String extension;

  /**
   * Default constructor
   */
  public FileSystemFile() {
    super();
    this.extension = "";
  }

  /**
   * Constructor with all required fields for full initialization
   * 
   * @param name
   *          The name of the file
   * @param parentPath
   *          The path of the containing directory
   * @param parent
   *          The parent of this file
   */
  public FileSystemFile(String name, String parentPath, AFileSystemEntry parent) {
    super(name, parentPath, parent, null);

    if (name != null && name.length() > 0) {
      this.extension = name.substring(name.lastIndexOf('.') + 1);
    }
  }

  /**
   * Sets the extension of this file
   * 
   * @param extension
   */
  public void setExtension(String extension) {
    this.extension = extension;
  }

  /**
   * Returns the extension of this file
   * 
   * @return The files extension
   */
  public String getExtension() {
    return this.extension;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.bht.fpa.mail.s780486.fsnavigator.model.AFileSystemEntry#isDirectory()
   */
  @Override
  public boolean isDirectory() {
    return false;
  }

  @Override
  public String toString() {
    return "[AFileSystemEntry: " + this.parentPath + "/" + this.name + ", children: " + this.children.length
        + ", directory : false, extension: " + this.extension + "]";
  }

}
