package com.jfennelly.minikube.control.panel.api.controllers;

import com.jfennelly.minikube.control.panel.api.exceptions.MinikubeCPException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class KubernetesController {
    private final static String MINIKUBE_START = "minikube start";
    private final static String MINIKUBE_STOP = "minikube stop";
    private final static String MINIKUBE_DELETE = "minikube delete";
    private final static String MINIKUBE_STATUS = "minikube status";

    @PostMapping("/kube/start")
    public ResponseEntity<?> startKubernetesCluster(){
        return runCommand(MINIKUBE_START);
    }

    @PostMapping("/kube/stop")
    public ResponseEntity<?> stopKubernetesCluster(){
        return runCommand(MINIKUBE_STOP);
    }

    @DeleteMapping("/kube/delete")
    public ResponseEntity<?> deleteKubernetesCluster(){
        return runCommand(MINIKUBE_DELETE);
    }

    @GetMapping("/kube/status")
    public ResponseEntity<?> getMinikubeStatus(){
        try{
            ProcessBuilder pb = new ProcessBuilder();
            pb.command(MINIKUBE_STATUS);
            Process proc = pb.start();
            int exitCode = proc.waitFor();
            return ResponseEntity.ok(exitCode);
        } catch(IOException | InterruptedException e){
            throw new MinikubeCPException(HttpStatus.INTERNAL_SERVER_ERROR, "Command Error", e.getLocalizedMessage());
        }
    }

    private ResponseEntity<?> runCommand(String command) {
        try{
            LOG.debug("running command: " + command);
            ProcessBuilder pb = new ProcessBuilder();
            pb.command(command);
            Process proc = pb.start();
            int exitCode = proc.waitFor();
            if(exitCode == 0) {
                return ResponseEntity.ok().build();
            } else {
                throw new MinikubeCPException(HttpStatus.INTERNAL_SERVER_ERROR, "Command Error", String.valueOf(exitCode));
            }

        } catch(IOException | InterruptedException e){
            throw new MinikubeCPException(HttpStatus.INTERNAL_SERVER_ERROR, "Command Error", e.getLocalizedMessage());
        }
    }
}
