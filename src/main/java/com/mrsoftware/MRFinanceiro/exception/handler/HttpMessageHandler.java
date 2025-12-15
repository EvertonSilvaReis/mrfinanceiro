package com.mrsoftware.MRFinanceiro.exception.handler;

import static java.lang.String.format;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mrsoftware.MRFinanceiro.dtos.erros.ErroDTO;
import com.mrsoftware.MRFinanceiro.dtos.erros.ErrosDTO;
import com.mrsoftware.MRFinanceiro.exception.ValidacaoException;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.util.MensagemUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class HttpMessageHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleHttpMessageException(HttpMessageNotReadableException ex) {
    return prepararValidacaoHandler(ex);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handlerMismatchException(MethodArgumentTypeMismatchException ex) {
    return prepararValidacaoHandler(ex);
  }

  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<Object> handlerMissingPathVariableException(
      MissingPathVariableException ex) {
    return prepararValidacaoHandler(ex);
  }

  private ResponseEntity<Object> prepararValidacaoHandler(Exception ex) {
    List<ErroDTO> erros = new ArrayList<>();
    String mensagem = EValidacao.ENTRADA_DADOS_INVALIDA.getDescricao();

    List<JsonMappingException.Reference> path;

    if (ex instanceof MissingPathVariableException) {
      var reference =
          new JsonMappingException.Reference(
              ex, ((MissingPathVariableException) ex).getVariableName());
      path = new ArrayList<>();
      path.add(reference);
      mensagem = prepararMensagem(ex, path);
    }

    if (ex.getCause() instanceof MismatchedInputException) {
      path = ((MismatchedInputException) ex.getCause()).getPath();
      mensagem = prepararMensagem(ex, path);
    }

    if (ex.getCause() instanceof NumberFormatException) {
      mensagem =
          format(
              MensagemUtils.getMensagem(("Campo.invalido")),
              ((MethodArgumentTypeMismatchException) ex).getName());
    }

    erros.add(
        ErroDTO.builder()
            .codigo(EValidacao.ENTRADA_DADOS_INVALIDA.getCodigo())
            .mensagem(mensagem)
            .build());

    return new ResponseEntity<>(ErrosDTO.builder().erros(erros).build(), HttpStatus.BAD_REQUEST);
  }

  private String prepararMensagem(Exception ex, List<JsonMappingException.Reference> path) {
    String mensagem;
    if (Objects.isNull(path))
      throw new ValidacaoException(EValidacao.CAMPO_INVALIDO_NAO_IDENTIFICADO);

    String grupos = null;
    String propriedade = null;

    for (int i = 0; i < path.size(); i++) {
      String campo = path.get(i).getFieldName();
      if (Objects.nonNull(campo)) {
        if (path.size() == 1 || i == path.size() - 1) {
          propriedade = campo;
          break;
        }

        grupos = Objects.isNull(grupos) ? campo : grupos.concat(path.get(i).getFieldName());
        grupos = grupos.replace(".", " > ");
      }
    }

    String valorErro = obterValorErro(ex);
    mensagem =
        format(
            MensagemUtils.getMensagem(("Lista.generico.invalido")), propriedade, grupos, valorErro);

    if (Objects.isNull(grupos)) {
      mensagem =
          format(MensagemUtils.getMensagem(("Campo.generico.invalido")), propriedade, valorErro);
    }
    return mensagem;
  }

  private String obterValorErro(Exception ex) {
    try {
      JsonParser parser = (JsonParser) ((MismatchedInputException) ex.getCause()).getProcessor();
      return parser.getText();
    } catch (Exception e) {
      return "não identificado";
    }
  }
}
