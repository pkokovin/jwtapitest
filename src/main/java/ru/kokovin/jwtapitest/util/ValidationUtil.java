package ru.kokovin.jwtapitest.util;

public class ValidationUtil {

  private ValidationUtil() {}

  /** Getting root cause of Exception. http://stackoverflow.com/a/28565320/548473. */
  public static Throwable getRootCause(Throwable t) {
    Throwable result = t;
    Throwable cause;

    while (null != (cause = result.getCause()) && (result != cause)) {
      result = cause;
    }
    return result;
  }
}
