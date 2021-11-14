package com.jun.service.domain.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

  public static int randomBetween(int min, int max) {
    return (int) (Math.random() * (max - min)) + min;
  }

  public static String genUserName(int tenantId, String partnerId) {
    return tenantId + "_" + partnerId;
  }

  public static LocalDate getTodayDateHCMZone() {
    return getTodayDateTimeHCMZone().toLocalDate();
  }

  public static java.time.LocalDateTime getTodayDateTimeHCMZone() {
    return java.time.LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
  }

  public static boolean regexPhoneNumber(String phoneNumber) {
    if (phoneNumber == null) {
      return false;
    }
    String regex = "^0[37859]{1}[0-9]{1}\\d{7}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.find();
  }

  public static boolean regexEmail(String email) {
    if (email == null) {
      return false;
    }
    String regex =
        "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.find();
  }

  public static String generateCode() {
    Random r = new Random(System.currentTimeMillis());
    return String.valueOf(((1 + r.nextInt(2)) * 100000 + r.nextInt(100000)));
  }

  public static String md5Token(String token) {
    return DigestUtils.sha256Hex(token);
  }

  public static boolean regexUrl(String url) {
    if (url == null) {
      return false;
    }
    String regex =
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(url);
    return matcher.find();
  }

  public static boolean compareDate(Date dateField) {
    if (dateField == null) {
      return false;
    }
    long millis = System.currentTimeMillis();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    String stringDate = formatter.format(new Date(millis));
    String inputDate = formatter.format(dateField);
    try {
      Date currentDate = formatter.parse(stringDate);
      Date birthDay = formatter.parse(inputDate);
      int status = birthDay.compareTo(currentDate);
      if (status == 1) {
        return false;
      }
      return true;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return false;
  }
}
