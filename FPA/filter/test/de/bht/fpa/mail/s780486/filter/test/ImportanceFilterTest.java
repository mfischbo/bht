package de.bht.fpa.mail.s780486.filter.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s780486.filter.ImportanceFilter;

/**
 * Test cases for the Importance Filter
 * 
 * @author M. Fischboeck
 * 
 */
public class ImportanceFilterTest {

  private List<Message> messages;

  @Before
  public void setUp() {
    RandomTestDataProvider provider = new RandomTestDataProvider(3);
    messages = provider.getMessages();
    messages.get(0).setImportance(Importance.LOW);
    messages.get(1).setImportance(Importance.NORMAL);
    messages.get(2).setImportance(Importance.HIGH);
  }

  @Test
  public void hasLowPriorityTest() {

    ImportanceFilter test = new ImportanceFilter(Importance.LOW);
    Set<Message> retval = test.filter(messages);
    assertEquals(1, retval.size());
  }

  @Test
  public void hasNormalPriorityTest() {
    ImportanceFilter test = new ImportanceFilter(Importance.NORMAL);
    Set<Message> retval = test.filter(messages);
    assertEquals(1, retval.size());
  }

  @Test
  public void hasHighPriorityTest() {
    ImportanceFilter test = new ImportanceFilter(Importance.HIGH);
    Set<Message> retval = test.filter(messages);
    assertEquals(1, retval.size());
  }
}
