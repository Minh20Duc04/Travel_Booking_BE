package com.TravelBookingSystem_BE.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HotelNotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HotelNotFoundException.class)
    public Map<String, String> exceptionHandler(HotelNotFoundException exception)
    {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage: ", exception.getMessage());
        return errorMap;
    }

}
