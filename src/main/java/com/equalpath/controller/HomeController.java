package com.equalpath.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    @Operation(
            summary = "Redirecionar para Swagger UI",
            description = "Endpoint raiz da API. Redireciona automaticamente para a interface do Swagger."
    )
    public String index() {
        return "redirect:/swagger-ui.html";
    }
}
