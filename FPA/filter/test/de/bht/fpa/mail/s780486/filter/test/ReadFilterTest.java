package de.bht.fpa.mail.s780486.filter.test;

/**
 * Test cases for the ReadFilter
 * @author M. Fischboeck
 */
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s780486.filter.ReadFilter;

public class ReadFilterTest {

  private List<Message> messages;

  @Before
  public void setUp() {
    RandomTestDataProvider provider = new RandomTestDataProvider(3);
    messages = provider.getMessages();
    messages.get(0).setRead(true);
    messages.get(1).setRead(false);
    messages.get(2).setRead(false);
  }

  @Test
  public void isReadFilterWorks() {
    ReadFilter rf = new ReadFilter(true);
    Set<Message> retval = rf.filter(messages);
    assertEquals(1, retval.size());

    rf = new ReadFilter(false);
    retval = rf.filter(messages);
    assertEquals(2, retval.size());
  }
}
