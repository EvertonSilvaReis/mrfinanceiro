package com.mrsoftware.MRFinanceiro.util;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.interfaces.IEnumLabel;
import jakarta.annotation.PostConstruct;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MensagemUtils {
  private static MessageSource MESSAGE_SOURCE;

  private final MessageSource resourceBundle;

  /**
   * Cria uma referencia estatica ao title source, para que a internacionalizacao das enums possa
   * ser invocada por objetos que nao possam ter o componente {@link MensagemUtils} injetado
   * (objetos que nao sao beans spring, DTOs, por exemplo)
   */
  @PostConstruct
  public void init() {
    MESSAGE_SOURCE = resourceBundle;
  }

  /**
   * Obtem uma mensagem internacionalizada a partir de sua chave
   *
   * @param chave A chave da mensagem
   * @param args Os argumentos para montagem da mensagem
   * @return A mensagem montada
   */
  public String getMensagem(String chave, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return resourceBundle.getMessage(chave, args, locale);
  }

  /**
   * Obtem uma mensagem internacionalizada a partir de sua chave
   *
   * @param chave A chave da mensagem
   * @return A mensagem montada
   */
  public static String getMensagem(String chave) {
    Locale locale = LocaleContextHolder.getLocale();
    return MESSAGE_SOURCE.getMessage(chave, null, locale);
  }

  /**
   * Obtem o label de enumeracao
   *
   * @param e O valor da enumeracao
   * @param <E> O tipo da enumeracao
   * @return O label internacionalizado da enumeracao
   */
  public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e) {
    Locale locale = LocaleContextHolder.getLocale();
    String messageKey = "enum." + e.getClass().getSimpleName() + "." + ((Enum) e).name();
    return MESSAGE_SOURCE.getMessage(messageKey, null, locale);
  }

  public static <E extends Enum<E>> String getEnumLabel(IEnumLabel<E> e, String... params) {
    Locale locale = LocaleContextHolder.getLocale();
    String messageKey = "enum." + e.getClass().getSimpleName() + "." + ((Enum) e).name();
    return MESSAGE_SOURCE.getMessage(messageKey, params, locale);
  }
}
