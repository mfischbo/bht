package de.bht.fpa.mail.s780486.common;

/**
 * Interface that provides the capability of retrieving 
 * {@link de.bht.fpa.mail.common.model.Message}'s from different sources
 */

import java.util.Iterator;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public interface IMailMessageProvider {

  /**
   * Returns an iterator on a set of messages
   * 
   * @return An iterator on messages
   */
  public Iterator<Message> getMessages();
}
