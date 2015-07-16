package de.bht.fpa.mail.s780486.imapnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

/**
 * Label provider to be used for IMAP accounts and folders. The provider
 * provides labels and images for the following types:
 * {@link de.bht.fpa.mail.s000000.common.mail.model.Account} and
 * {@link de.bht.fpa.mail.s000000.common.mail.model.Folder}
 * 
 * @author M. Fischboeck
 * 
 */

public class ImapLabelProvider extends LabelProvider {

  private final Image accountImage;
  private final Image folderImage;

  public ImapLabelProvider() {

    this.accountImage = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "/icons/mailbox.png")
        .createImage();

    this.folderImage = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "/icons/folder.png")
        .createImage();
  }

  @Override
  public String getText(Object element) {

    if (element instanceof Account) {
      return ((Account) element).getName();
    }

    if (element instanceof Folder) {
      return ((Folder) element).getFullName();
    }

    return "";
  }

  @Override
  public Image getImage(Object element) {

    if (element instanceof Account) {
      return this.accountImage;
    }

    if (element instanceof Folder) {
      return this.folderImage;
    }

    return null;
  }
}
