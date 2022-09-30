package com.jfennelly.minikube.control.panel.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MinikubeCPException extends RuntimeException {
    private HttpStatus status;
    private String type;
    private String message;

    public MinikubeCPException(HttpStatus status, String type, String message) {
        super(message);
        this.status = status;
        this.type = type;
        this.message = message;
    }
}
