package springbootmonolithic.domain.healthcheck.query.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/check")
public class HealthCheckQueryController {

    @GetMapping
    public String healthCheck() {
        return "query OK";
    }
}
