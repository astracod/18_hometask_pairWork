package com.newspring.delivery.controllers;

import com.newspring.delivery.dto.common.OnlyStatusResponse;
import com.newspring.delivery.exceptions.RequestProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestProcessingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    OnlyStatusResponse onError(RequestProcessingException ex){
        log.error("Error information : ", ex);
        OnlyStatusResponse response = new OnlyStatusResponse();
        response.setStatus(OnlyStatusResponse.Status.FAIL);
        response.setMessage(ex.getMessage());
        return response;
    }
}
