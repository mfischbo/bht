package de.bht.fpa.mail.s780486.fsnavigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.bht.fpa.mail.s780486.fsnavigator.model.AFileSystemEntry;
import de.bht.fpa.mail.s780486.fsnavigator.model.FileSystemDirectory;
import de.bht.fpa.mail.s780486.fsnavigator.model.FileSystemFile;

/**
 * Provider for default labels and icons as used during the application. Only
 * provides labels and icons for
 * {@link de.bht.fpa.mail.s780486.fsnavigator.model.AFileSystemEntry} objects
 * and derived.
 * 
 * @author M. Fischboeck (780486)
 * 
 */

public class FpaMailerLabelProvider extends LabelProvider {

  /**
   * Default constructor
   */
  public FpaMailerLabelProvider() {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText(Object element) {

    if (element instanceof AFileSystemEntry) {
      return ((AFileSystemEntry) element).getName();
    }

    return "";
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage(Object element) {

    if (element instanceof FileSystemDirectory) {
      return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "/icons/folder.png").createImage();
    }

    if (element instanceof FileSystemFile) {

      FileSystemFile f = (FileSystemFile) element;
      ImageDescriptor desc = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
          "/icons/" + f.getExtension() + ".png");
      if (desc != null) {
        return desc.createImage();
      } else {
        return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "/icons/file.png").createImage();
      }

    }

    return null;
  }
}
