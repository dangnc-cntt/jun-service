package com.jun.service.account.domain.utils;

import com.jun.service.account.domain.entities.types.RoomType;

public class RoomUtils {

  public static String roomId(String id, RoomType roomType) {
    return String.format("%s_%s", roomType.toString().toLowerCase(), id);
  }

  public static String roomId(String id, String roomType) {
    return roomId(id, RoomType.valueOf(roomType));
  }

  private static String withPrefix(String id, String prefix) {
    return prefix + id;
  }
}
