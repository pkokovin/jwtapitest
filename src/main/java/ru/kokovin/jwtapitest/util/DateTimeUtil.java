package ru.kokovin.jwtapitest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtil {
  private DateTimeUtil() {}

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");

  public static String dateToString(Date date) {
    return simpleDateFormat.format(date);
  }

  /** Convert String to Date. */
  public static Date stringToDate(String date) {
    Date result = null;
    try {
      result = simpleDateFormat.parse(date);
    } catch (ParseException e) {
      log.info("Wrong input date format: {}", date);
      e.printStackTrace();
    }
    return result;
  }
}
