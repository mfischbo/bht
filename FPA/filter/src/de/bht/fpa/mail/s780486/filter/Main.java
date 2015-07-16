package de.bht.fpa.mail.s780486.filter;

import java.util.HashSet;
import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s780486.filter.SimpleFilter.Operation;

/**
 * Main class to see the filters in operation.
 * 
 * @author M. Fischboeck
 * 
 */
public class Main {

  /**
   * Main method to see the filters in operation.
   * 
   * @param args
   */
  public static void main(String[] args) {

    // generate a couple of messages
    int datasize = 10;
    RandomTestDataProvider provider = new RandomTestDataProvider(datasize);
    List<Message> messages = provider.getMessages();

    // check how many are read
    int isRead = 0;
    int isNotRead = 0;
    for (Message m : messages) {
      if (!m.isRead()) {
        isNotRead++;
      } else {
        isRead++;
      }
    }

    // apply the ReadFilter and check the results
    ReadFilter read = new ReadFilter(true);
    HashSet<Message> filtered = (HashSet<Message>) read.filter(messages);
    for (Message m : filtered) {
      System.out.println("Message read: " + m.isRead());
    }
    System.out.println("Messages : " + datasize + "\tRead: " + isRead + "\tNot read:" + isNotRead + "\t Result: "
        + (isRead + isNotRead));
    System.out.println("\n\n");

    // setup for the SenderFilter
    String senderContains = "stulle";
    String recipContains = "schwarz";
    System.out.println("Sender must contain [" + senderContains + "] or recipient must contain [" + recipContains
        + "]\n\n");

    // unite the SenderFilters and check their results
    Union u = new Union(new SenderFilter(senderContains, Operation.CONTAINS), new RecipientsFilter(recipContains,
        Operation.CONTAINS));
    filtered = (HashSet<Message>) u.filter(messages);
    for (Message m : filtered) {
      System.out.println("Sender contains   : " + m.getSender().getPersonal() + "<" + m.getSender().getEmail() + ">\t");
      for (Recipient r : m.getRecipients()) {
        System.out.println("Recipient contains : " + r.getPersonal() + "<" + r.getEmail() + ">");
      }
      System.out.println("\n\n\n");
    }

    // check the intersection filter
    System.out.println("INTERSECTION TEST");
    String senderStarts = "schmidt";
    System.out.println("Filter messages where sender starts with [" + senderStarts + "] and message is read");

    // apply the intersection on a SenderFilter and a ReadFilter
    Intersection is = new Intersection(new SenderFilter(senderStarts, Operation.STARTS_WITH), new ReadFilter(true));
    filtered = (HashSet<Message>) is.filter(messages);
    for (Message m : filtered) {
      System.out.println("Sender: " + m.getSender().getPersonal() + " <" + m.getSender().getEmail() + ">");
      System.out.println("Message is read  : " + m.isRead());
    }

  }
}
