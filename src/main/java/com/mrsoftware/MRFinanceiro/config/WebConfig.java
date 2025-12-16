package com.mrsoftware.MRFinanceiro.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class WebConfig {
  public static final String CHARSET_ECODING = "UTF-8";

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("pt_BR"));
    return cookieLocaleResolver;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:msgs/mensagens");
    messageSource.setUseCodeAsDefaultMessage(true);
    messageSource.setDefaultEncoding(CHARSET_ECODING);
    messageSource.setCacheSeconds(0);
    return messageSource;
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("MRFinanceiro API")
                .version("1.0.0")
                .description("Documentação da API do Sistema MRFinanceiro.")
                .termsOfService("http://meu-servico.com/terms"));
  }
}
