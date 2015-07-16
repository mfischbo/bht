package de.bht.fpa.mail.s780486.fsnavigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXB;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s780486.common.IMailMessageProvider;

/**
 * POJO to represent a directory within a FileSystem.
 * 
 * @author M. Fischboeck (780486)
 * 
 */
public class FileSystemDirectory extends AFileSystemEntry implements IMailMessageProvider {

  /**
   * Default constructor
   */
  public FileSystemDirectory() {
    super();
  }

  /**
   * Constructor with all described fields
   * 
   * @param name
   *          The name of the directory
   * @param parentPath
   *          The directories parent path
   * @param parent
   *          The directories parent directory or null if this is the root
   *          directory
   * @param children
   *          The children of the directory
   */
  public FileSystemDirectory(String name, String parentPath, AFileSystemEntry parent, AFileSystemEntry[] children) {
    super(name, parentPath, parent, children);
  }

  /**
   * Returns true if every case
   */
  @Override
  public boolean isDirectory() {
    return true;
  }

  @Override
  public String toString() {
    return "[AFileSystemEntry: " + this.parentPath + "/" + this.name + ", children: " + this.children.length
        + ", directory : true]";
  }

  @Override
  public Iterator<Message> getMessages() {

    ArrayList<Message> container = new ArrayList<Message>(this.children.length);
    for (AFileSystemEntry e : this.children) {
      if (!e.isDirectory()) {
        try {
          File f = new File(e.getRootPath());
          Message m = JAXB.unmarshal(f, Message.class);
          container.add(m);
        } catch (Exception pq) {

        }
      }
    }
    return container.iterator();
  }

}
