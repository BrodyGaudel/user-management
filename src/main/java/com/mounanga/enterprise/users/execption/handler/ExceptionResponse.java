package com.mounanga.enterprise.users.execption.handler;


import java.util.Map;
import java.util.Set;

public record ExceptionResponse(Integer code,
                                String message,
                                String description,
                                Set<String> validationErrors,
                                Map<String, String> errors)  {

}
