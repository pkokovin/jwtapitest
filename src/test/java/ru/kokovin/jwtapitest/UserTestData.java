package ru.kokovin.jwtapitest;

import ru.kokovin.jwtapitest.dto.EmailRequestDto;
import ru.kokovin.jwtapitest.dto.UserDto;

public class UserTestData {
  public static UserDto USER_DTO_1 = new UserDto("Ivan", "testmail@ya.ru", 35);
  public static UserDto USER_DTO_2 = new UserDto("Mike", "mike@ya.ru", 33);
  public static UserDto USER_DTO_DUBLICATE_EMAIL_2 = new UserDto("Sith", "mike@ya.ru", 33);
  public static UserDto INCORRECT_EMAIL_FORMAT_USER_DTO = new UserDto("John", "joeeya.ru", 33);
  public static UserDto USER_DTO_WITH_DIFF_CASE_EMAIL = new UserDto("Mike", "bikE@ya.ru", 33);
  public static EmailRequestDto EMAIL_REQUEST_DTO_USER_DTO_1 =
      new EmailRequestDto("testmail@ya.ru");
  public static EmailRequestDto EMAIL_REQUEST_DTO_USER_NOT_EXIST =
      new EmailRequestDto("testmail5@ya.ru");
  public static EmailRequestDto EMAIL_REQUEST_DTO_USER_UPPERCASE =
      new EmailRequestDto("TesTmail@Ya.ru");
}
