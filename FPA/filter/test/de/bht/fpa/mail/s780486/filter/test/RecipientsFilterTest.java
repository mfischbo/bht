package de.bht.fpa.mail.s780486.filter.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s780486.filter.RecipientsFilter;
import de.bht.fpa.mail.s780486.filter.SimpleFilter.Operation;

/**
 * Test cases for the RecipientsFilter
 * 
 * @author M. Fischboeck
 * 
 */
public class RecipientsFilterTest {

  private List<Message> messages;

  @Before
  public void setUp() {
    RandomTestDataProvider provider = new RandomTestDataProvider(2);
    messages = provider.getMessages();

    Recipient r1 = new Recipient();
    r1.setEmail("nerd@example.com");
    r1.setPersonal("Mike Tyson");

    Recipient r2 = new Recipient();
    r2.setEmail("nobody@example2.com");
    r2.setPersonal("John Doe");

    List<Recipient> rec1 = new ArrayList<Recipient>(1);
    rec1.add(r1);
    messages.get(0).setRecipients(rec1);

    List<Recipient> rec2 = new ArrayList<Recipient>(1);
    rec2.add(r2);
    messages.get(1).setRecipients(rec2);
  }

  @Test
  public void isInEmailFilter() {
    RecipientsFilter rf = new RecipientsFilter("nerd@example.com", Operation.IS);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void isInNameFilter() {
    RecipientsFilter rf = new RecipientsFilter("Mike Tyson", Operation.IS);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void containsEmailFilter() {
    RecipientsFilter rf = new RecipientsFilter("rd@exa", Operation.CONTAINS);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void containsNameFilter() {
    RecipientsFilter rf = new RecipientsFilter("ke", Operation.CONTAINS);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void containsNotEmailFilter() {
    RecipientsFilter rf = new RecipientsFilter("ody@exa", Operation.CONTAINS_NOT);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void containsNotNameFilter() {
    RecipientsFilter rf = new RecipientsFilter("hn", Operation.CONTAINS_NOT);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void startsWithEmailFilter() {
    RecipientsFilter rf = new RecipientsFilter("nerd", Operation.STARTS_WITH);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void startsWithNameFilter() {
    RecipientsFilter rf = new RecipientsFilter("Mik", Operation.STARTS_WITH);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void endsWithEmailFilter() {
    RecipientsFilter rf = new RecipientsFilter("ample.com", Operation.ENDS_WITH);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void endsWithNameFilter() {
    RecipientsFilter rf = new RecipientsFilter("yson", Operation.ENDS_WITH);
    Set<Message> result = rf.filter(messages);
    assertEquals(1, result.size());
  }

}
