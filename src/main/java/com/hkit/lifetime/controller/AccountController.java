package com.hkit.lifetime.controller;

import com.hkit.lifetime.dto.AccountDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AccountController {
    @GetMapping("login")
    public void login(@ModelAttribute AccountDto accountDto) {

    }
}
