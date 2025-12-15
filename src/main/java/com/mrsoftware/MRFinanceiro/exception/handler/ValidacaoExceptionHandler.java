package com.mrsoftware.MRFinanceiro.exception.handler;

import com.mrsoftware.MRFinanceiro.dtos.erros.ErroDTO;
import com.mrsoftware.MRFinanceiro.dtos.erros.ErrosDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ValidacaoExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrosDTO> badRequestExcpetion(BadRequestException exception) {
    List<ErroDTO> erros = new ArrayList<>();

    erros.add(new ErroDTO(exception.codigo(), exception.getMensagem()));
    log.error(exception.getMensagem());
    return new ResponseEntity<>(new ErrosDTO(erros), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
    List<ErroDTO> erros = new ArrayList<>();
    erros.add(new ErroDTO(ex.codigo(), ex.getMensagem()));

    log.error(ex.getMensagem());
    return new ResponseEntity<>(new ErrosDTO(erros), HttpStatus.NOT_FOUND);
  }
}
