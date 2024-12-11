package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class DemoApplication implements ErrorController {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String handleError() {
		System.out.println("Error");
        return "forward:/browser/index.html";
    }
}
