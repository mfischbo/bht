package de.bht.fpa.mail.s780486.fsnavigator;

import java.io.File;
import de.bht.fpa.mail.s780486.fsnavigator.model.AFileSystemEntry;
import de.bht.fpa.mail.s780486.fsnavigator.model.FileSystemDirectory;
import de.bht.fpa.mail.s780486.fsnavigator.model.FileSystemFile;

/**
 * Utility class to support the generation of a tree for the FileSystem. This
 * class uses java.io.File as it's underlying technology to read from the file
 * system.
 * 
 * @author M. Fischbock (780486)
 */

public final class FSReader {

  private FSReader() {

  }

  /**
   * Returns the children for the specified entry. This only reads the direct
   * children of the specified entry and does not recurse into sub directories.
   * Use this for lazy loading.
   * 
   * @param entry
   *          The entry to get the children for
   * @param onlyMailMessages
   *          States if only mail messages and directories should be returned
   * @return The children of the entry
   */
  public static AFileSystemEntry[] getChildren(AFileSystemEntry entry) {
    String path = entry.getRootPath();

    if (path == null || path.equals("")) {
      return null;
    }

    File f = new File(path);
    if (!f.exists()) {
      throw new RuntimeException("The provided path '" + path + "' does not exist");
    }

    if (!f.isDirectory()) {
      return new AFileSystemEntry[0];
    }

    File[] entries = f.listFiles();

    AFileSystemEntry[] retval = new AFileSystemEntry[entries.length];
    for (int i = 0; i < entries.length; i++) {
      AFileSystemEntry tmp = null;
      if (entries[i].isDirectory()) {
        tmp = new FileSystemDirectory(entries[i].getName(), entries[i].getParent(), entry, null);
      } else {
        tmp = new FileSystemFile(entries[i].getName(), entries[i].getParent(), entry);
      }
      retval[i] = tmp;
    }
    return sort(retval);
  }

  /**
   * This translates a path into a AFileSystemEntry using java.io.File
   * 
   * @param path
   *          The path to get the AFileSystemEntry for
   * @return The AFileSystemEntry representing the specified file system path
   */
  public static AFileSystemEntry getEntryForPath(String path) {
    if (path == null || path.equals("")) {
      return null;
    }

    File f = new File(path);
    if (!f.exists()) {
      throw new RuntimeException("The provided path '" + path + "' does not exist");
    }

    if (f.isDirectory()) {
      return new FileSystemDirectory(f.getName(), f.getParent(), null, null);
    } else {
      return new FileSystemFile(f.getName(), f.getParent(), null);
    }
  }

  /**
   * Orders all entries in such a way that directories are before files.
   * 
   * @param entries
   *          The array of entries to sort
   * @return The sorted array
   */
  private static AFileSystemEntry[] sort(AFileSystemEntry[] entries) {
    AFileSystemEntry[] retval = new AFileSystemEntry[entries.length];

    int i = 0;
    for (AFileSystemEntry e : entries) {
      if (e.isDirectory()) {
        retval[i] = e;
        i++;
      }
    }

    for (AFileSystemEntry e : entries) {
      if (!e.isDirectory()) {
        retval[i] = e;
        i++;
      }
    }
    return retval;
  }
}
