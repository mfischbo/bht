package de.bht.fpa.mail.s780486.maillist;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.ralfebert.rcputils.properties.IValueFormatter;

/**
 * Formats a {@link java.util.Date} into the specified format
 * 
 * @author M. Fischboeck
 */
public class MailListDateFormatter implements IValueFormatter<Date, String> {

  /* The incoming date format */
  public static final String RECEIVED_DATE_FORMAT = "E M d k:m:s z y";

  /* The outgoing date format */
  public static final String MAILLIST_FORMAT = "dd.MM.yyyy";

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.ralfebert.rcputils.properties.IValueFormatter#format(java.lang.Object)
   */
  @Override
  public String format(Date date) {
    SimpleDateFormat d = new SimpleDateFormat(MAILLIST_FORMAT);
    return d.format(date);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * de.ralfebert.rcputils.properties.IValueFormatter#parse(java.lang.Object)
   */
  @Override
  public Date parse(String obj) {
    SimpleDateFormat d = new SimpleDateFormat(RECEIVED_DATE_FORMAT);
    try {
      return d.parse(obj);
    } catch (Exception pq) {
      pq.printStackTrace();
    }
    return null;
  }
}
