package com.hkit.lifetime.controller;

import com.hkit.lifetime.dto.AccountDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class AccountController {
    @GetMapping("login")
    public void login(@ModelAttribute AccountDto accountDto) {

    }
}
