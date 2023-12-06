package com.bit.lot.flower.auth.common.util;

public class OauthInfoConvertor {

  public static String convertInternationalPhoneNumberToDomestic(String phoneNumber) {
    String numericPhone = phoneNumber.replaceAll("[^0-9]", "");
      numericPhone =  "0"+ numericPhone.substring(2) ;
    return numericPhone;
  }

}

