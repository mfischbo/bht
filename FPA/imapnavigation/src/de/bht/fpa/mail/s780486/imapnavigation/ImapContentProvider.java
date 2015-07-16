package de.bht.fpa.mail.s780486.imapnavigation;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.builder.AccountBuilder;
import de.bht.fpa.mail.s000000.common.mail.model.builder.FolderBuilder;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class ImapContentProvider implements ITreeContentProvider {

  private List<Account> accounts;

  @Override
  public void dispose() {
    this.accounts = null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

    if (oldInput == null && newInput instanceof List) {
      this.accounts = (List<Account>) newInput;

    }
  }

  @Override
  public Object[] getElements(Object inputElement) {
    return getChildren(inputElement);
  }

  @Override
  public Object[] getChildren(Object parentElement) {

    if (parentElement instanceof List) {
      return this.accounts.toArray(new Account[this.accounts.size()]);
    }

    if (parentElement instanceof Account) {
      Account inner = (Account) parentElement;
      Folder[] retval = inner.getFolders().toArray(new Folder[inner.getFolders().size()]);
      return retval;
    }

    if (parentElement instanceof Folder) {
      Folder inner = (Folder) parentElement;
      Folder[] retval = inner.getFolders().toArray(new Folder[inner.getFolders().size()]);
      return retval;
    }

    return null;
  }

  @Override
  public Object getParent(Object element) {

    if (element instanceof List) {
      return null;
    }

    if (element instanceof Account) {
      return this.accounts;
    }

    if (element instanceof Folder) {
      return ((Folder) element).getAccount();
    }

    return null;
  }

  @Override
  public boolean hasChildren(Object element) {

    if (element instanceof List) {
      return this.accounts.size() > 0;
    }

    if (element instanceof Account) {
      return ((Account) element).getFolders().size() > 0;
    }

    if (element instanceof Folder) {
      return ((Folder) element).getFolders().size() > 0;
    }

    return false;
  }

  public static Account createDummyAccount() {

    // @formatter:off
    Account retval = AccountBuilder.newAccountBuilder()
      .host("de.somewhere.com") 
      .username("alice") .password("secret") .name("Alice-IMAP") 
      .folder(
          FolderBuilder.newFolderBuilder()
            .fullName("INBOX")
            .builtMessages(new RandomTestDataProvider(20).getMessages()) 
            .folder(
                FolderBuilder.newFolderBuilder()
                  .fullName("Customers")
                  .builtMessages(new RandomTestDataProvider(30).getMessages())
            )
           )
    .folder( 
        FolderBuilder.newFolderBuilder()
          .fullName("Sent")
          .builtMessages(new RandomTestDataProvider(5).getMessages()) )
          .build();
    // @formatter:on

    return retval;
  }
}
