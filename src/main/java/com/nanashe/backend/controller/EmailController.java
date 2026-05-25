package com.nanashe.backend.controller;

import com.nanashe.backend.service.UserService;
import com.nanashe.backend.util.HtmlTemplateLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailController {
    private final UserService userService;

    private static final String EMAIL_CONFIRMED_TEMPLATE = "email-confirmed.html";

    @ResponseBody
    @GetMapping(value = "/verify", produces = MediaType.TEXT_HTML_VALUE)
    public String verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);

        return HtmlTemplateLoader.loadTemplate(EMAIL_CONFIRMED_TEMPLATE);
    }
}
