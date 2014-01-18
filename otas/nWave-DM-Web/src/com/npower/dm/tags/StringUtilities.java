package com.npower.dm.tags;

import org.apache.commons.lang.StringUtils;

public class StringUtilities {
  public static String htmlEncode(String s) {
    if (StringUtils.isEmpty(s))
       return "";

    StringBuffer str = new StringBuffer();

    for (int j = 0; j < s.length(); j++) {
      str.append(htmlEncode(s.charAt(j)));
    }

    return str.toString();
  }

  public static String htmlEncode(char c) {
    switch (c) {
    case '"':
      return "&quot;";
    case '&':
      return "&amp;";
    case '<':
      return "&lt;";
    case '>':
      return "&gt;";
    default:
      return "" + c;
    }
  }

}
