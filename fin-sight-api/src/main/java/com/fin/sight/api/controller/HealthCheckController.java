package com.fin.sight.api.controller;

import com.fin.sight.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("healthCheckController")
@RequestMapping(path = Constants.HEALTH_CHECK_API_PATH)
@Slf4j
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<Void> pingCheck() {
        return ResponseEntity.ok().build();
    }
}
