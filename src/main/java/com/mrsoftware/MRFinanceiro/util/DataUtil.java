package com.mrsoftware.MRFinanceiro.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {

  public static LocalDate converterStringParaLocalDate(String data) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(data, formatter);
  }
}
