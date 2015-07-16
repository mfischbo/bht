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
import de.bht.fpa.mail.s780486.filter.Intersection;
import de.bht.fpa.mail.s780486.filter.ReadFilter;
import de.bht.fpa.mail.s780486.filter.SenderFilter;
import de.bht.fpa.mail.s780486.filter.SimpleFilter.Operation;
import de.bht.fpa.mail.s780486.filter.SubjectFilter;
import de.bht.fpa.mail.s780486.filter.Union;

/**
 * Test cases for the filter intersection
 * 
 * @author M. Fischboeck
 * 
 */
public class IntersectionFilterTest {

  private List<Message> messages;

  @Before
  public void setUp() {
    RandomTestDataProvider provider = new RandomTestDataProvider(2);
    messages = provider.getMessages();

    Sender s1 = new Sender();
    s1.setEmail("nobody@example.com");
    s1.setPersonal("Mike Tyson");
    messages.get(0).setSender(s1);
    messages.get(0).setRead(false);
    messages.get(0).setImportance(Importance.LOW);
    messages.get(0).setSubject("Hello World");

    Sender s2 = new Sender();
    s2.setEmail("john.doe@example.com");
    s2.setPersonal("John Doe");
    messages.get(1).setSender(s2);
    messages.get(1).setRead(false);
    messages.get(1).setImportance(Importance.NORMAL);
    messages.get(1).setSubject("Facebooks now on stock market");
  }

  @Test
  public void isNameTest() {
    SenderFilter sf = new SenderFilter("nobody@example.com", Operation.IS);
    ReadFilter rf = new ReadFilter(false);
    ImportanceFilter imf = new ImportanceFilter(Importance.LOW);
    SubjectFilter sjf = new SubjectFilter("World", Operation.CONTAINS);

    Intersection is = new Intersection(sf, rf, imf, sjf);
    Set<Message> result = is.filter(messages);
    assertEquals(1, result.size());
  }

  @Test
  public void isNotMatchingAnyTest() {
    SenderFilter sf = new SenderFilter("nobody@example.com", Operation.IS);
    ReadFilter rf = new ReadFilter(true);
    ImportanceFilter imf = new ImportanceFilter(Importance.LOW);
    SubjectFilter sjf = new SubjectFilter("World", Operation.CONTAINS);

    Intersection is = new Intersection(sf, rf, imf, sjf);
    Set<Message> result = is.filter(messages);
    assertEquals(0, result.size());
  }

  @Test
  public void isMatchingCompletely() {
    SenderFilter sf1 = new SenderFilter("Mike Tyson", Operation.IS);
    ImportanceFilter imf1 = new ImportanceFilter(Importance.LOW);
    SubjectFilter sjf1 = new SubjectFilter("World", Operation.CONTAINS);
    Intersection i1 = new Intersection(sf1, imf1, sjf1);
    assertEquals(1, i1.filter(messages).size());

    SenderFilter sf2 = new SenderFilter("John Doe", Operation.IS);
    ImportanceFilter imf2 = new ImportanceFilter(Importance.NORMAL);
    SubjectFilter sjf2 = new SubjectFilter("Face", Operation.STARTS_WITH);
    Intersection i2 = new Intersection(sf2, imf2, sjf2);
    assertEquals(1, i2.filter(messages).size());

    Union u = new Union(i1, i2);
    Set<Message> result = u.filter(messages);
    assertEquals(2, result.size());
  }
}
