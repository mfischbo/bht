package de.bht.fpa.mail.s780486.filter.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s780486.filter.ImportanceFilter;
import de.bht.fpa.mail.s780486.filter.ReadFilter;
import de.bht.fpa.mail.s780486.filter.SenderFilter;
import de.bht.fpa.mail.s780486.filter.SimpleFilter.Operation;
import de.bht.fpa.mail.s780486.filter.Union;

/**
 * Test cases for the Union Filter
 * 
 * @author M. Fischboeck
 * 
 */
public class UnionFilterTest {

  private List<Message> messages;

  @Before
  public void setUp() {
    RandomTestDataProvider provider = new RandomTestDataProvider(2);
    messages = provider.getMessages();

    messages.get(0).setRead(true);
    messages.get(1).setRead(false);

    Sender s1 = new Sender();
    s1.setEmail("nobody@example.com");
    s1.setPersonal("John Doe");
    messages.get(0).setSender(s1);
    messages.get(0).setImportance(Importance.LOW);

    Sender s2 = new Sender();
    s2.setEmail("nerd@example.com");
    s2.setPersonal("Mike Tyson");
    messages.get(1).setSender(s2);
    messages.get(1).setImportance(Importance.HIGH);
  }

  @Test
  public void isEmailOrReadTest() {
    SenderFilter sf = new SenderFilter("nobody@example.com", Operation.IS);
    ReadFilter rf = new ReadFilter(true);

    Union u = new Union(sf, rf);
    Set<Message> result = u.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void isNotEmailOrNotReadTest() {
    SenderFilter sf = new SenderFilter("john.doe@example.com", Operation.IS);
    ReadFilter rf = new ReadFilter(true);

    Union u = new Union(sf, rf);
    Set<Message> result = u.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void endsWithEmailOrReadTest() {
    SenderFilter sf = new SenderFilter("y@example.com", Operation.ENDS_WITH);
    ReadFilter rf = new ReadFilter(true);
    Union u = new Union(sf, rf);
    Set<Message> result = u.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void hasHighImportanceOrStartsWithEmailTest() {
    SenderFilter sf = new SenderFilter("nobody", Operation.STARTS_WITH);
    ImportanceFilter imf = new ImportanceFilter(Importance.HIGH);
    ReadFilter rf = new ReadFilter(false);

    Union u = new Union(sf, imf, rf);
    Set<Message> result = u.filter(messages);
    assertEquals(2, result.size());
  }

}
