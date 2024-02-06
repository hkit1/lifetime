package com.hkit.lifetime.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("register")
    public String inRegister() {
        return "register";
    }

    @PostMapping("/api/account/register")
    public String registerAccount(AccountDto accountDto) {
        String msg = accountService.duplicateCheck(accountDto.id());
        if (msg.equals("Ok")) {
            AccountDto encodeAccount = encodePw(accountDto);
            accountService.register(encodeAccount);
            return "home";
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in Use");
        }

    }

    @PostMapping("/api/account/check")
    public String idCheck(@RequestParam(name = "id") String id){
        String msg = accountService.duplicateCheck(id);
        if (msg.equals("Ok")){
            return "home";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in Use");
        }
    }

    @PostMapping("/api/account/delete")
    public String deleteAccount(@RequestParam(name = "sessionId") String id) {
        accountService.delete(id);
        return "home";
    }

    @PostMapping("/api/account/update")
    public String updateAccount(AccountDto accountDto) {
        accountService.update(accountDto);
        return "home";
    }

    /*
        input pw encode
     */
    private AccountDto encodePw(AccountDto accountDto) {
        return new AccountDto(
                null,
                accountDto.name(),
                accountDto.id(),
                bCryptPasswordEncoder.encode(accountDto.pw()),
                accountDto.birth(),
                accountDto.email(),
                accountDto.tel(),
                accountDto.gender(),
                accountDto.address1(),
                accountDto.address2()
        );
    }

}
