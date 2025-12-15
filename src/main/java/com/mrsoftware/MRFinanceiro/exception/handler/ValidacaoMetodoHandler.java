package com.mrsoftware.MRFinanceiro.exception.handler;

import com.mrsoftware.MRFinanceiro.dtos.erros.ErroDTO;
import com.mrsoftware.MRFinanceiro.dtos.erros.ErrosDTO;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.util.MensagemUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidacaoMetodoHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({BindException.class})
  public ErrosDTO bindException(BindException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    return processFieldErrors(fieldErrors);
  }

  private ErrosDTO processFieldErrors(List<FieldError> fieldErrors) {
    List<ErroDTO> erros = new ArrayList<>();

    for (FieldError fieldError : fieldErrors) {
      String fieldErro = Objects.requireNonNull(fieldError.getCodes())[0];
      ErroDTO erro =
          new ErroDTO(
              EValidacao.ENTRADA_DADOS_INVALIDA.getCodigo(),
              MensagemUtils.getMensagem(
                  fieldErro.substring(0, fieldErro.indexOf("."))
                      + fieldErro.substring(fieldErro.lastIndexOf("."))));

      log.warn(erro.getMensagem());
      erros.add(erro);
    }

    return new ErrosDTO(erros);
  }
}
