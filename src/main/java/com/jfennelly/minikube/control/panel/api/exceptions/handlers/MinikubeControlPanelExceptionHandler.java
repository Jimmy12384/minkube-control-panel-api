package com.jfennelly.minikube.control.panel.api.exceptions.handlers;

import com.jfennelly.minikube.control.panel.api.exceptions.MinikubeCPException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class MinikubeControlPanelExceptionHandler {
    @ExceptionHandler(MinikubeCPException.class)
    public ResponseEntity<MinikubeCPException> handleException(MinikubeCPException ex){
        return ResponseEntity.badRequest().body(ex);
    }
}
