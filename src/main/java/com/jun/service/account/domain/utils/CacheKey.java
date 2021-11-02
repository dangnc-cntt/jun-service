package com.jun.service.account.domain.utils;

public class CacheKey {

  private static String prefix = "jun";

  public static String genSessionKey(String uuid) {
    return prefix + ":account_session:" + uuid;
  }

  public static String genListSessionKey(Integer tenantId) {
    return prefix + ":account_session_list_" + tenantId;
  }

  public static String genForgotPasswordKey(String username) {
    return prefix + "_forgot_password:user_name:" + username;
  }

  public static String genChangePasswordKey(String username) {
    return prefix + "_change_password:user_name:" + username;
  }

  public static String genRegEmailKey(String username) {
    return prefix + "_reg_email:user_name:" + username;
  }

  public static String genActiveVoucherByIdKey(String voucherId) {
    return prefix + "::active:voucher:" + voucherId;
  }

  public static String genVoucherListKey() {
    return prefix + "::active:voucher:list";
  }

  public static String genAccountByIdKey(int accountId) {
    return prefix + "::account_by_id:" + accountId;
  }

  public static String genAccountByUserNameKey(String userName) {
    return prefix + "::account_by_username:" + userName;
  }

  public static String genListCategoryKey() {
    return prefix + "::category:list";
  }

  public static String genCategoryIdKey(int id) {
    return prefix + "::category:" + id;
  }

  public static String genListProductByCategoryKey(int categoryId) {
    return prefix + "::products:category:" + categoryId;
  }

  public static String genProductKey(int productId) {
    return prefix + "::product:" + productId;
  }

  public static String genListProductIsHotKey() {
    return prefix + "::products:hot";
  }

  public static String genListOptionProductIdKey(int productId) {
    return prefix + "::options:product:" + productId;
  }
}
