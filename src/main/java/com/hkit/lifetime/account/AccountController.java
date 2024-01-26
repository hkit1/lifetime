package com.hkit.lifetime.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("register")
    public String inRegister(){
        return "register";
    }

    @PostMapping("/api/account/register")
    public String register(AccountDto accountDto){

        AccountDto encodeAccount = encodePw(accountDto);
        accountService.save(encodeAccount);

        return "home";
    }

    @PostMapping("/api/account/delete")
    public String accountDelete(@RequestParam(name = "sessionId") String id){
        accountService.delete(id);
        return "home";
    }

    @PostMapping("/api/account/update")
    public String accountUpdate(){
        accountService.update();
        return "home";
    }

    /*
        input pw encode
     */
    private AccountDto encodePw(AccountDto accountDto){

        return new AccountDto(
                null,
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
