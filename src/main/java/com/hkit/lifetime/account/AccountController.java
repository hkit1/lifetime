package com.hkit.lifetime.account;

import com.hkit.lifetime.company.CompanyDto;
import com.hkit.lifetime.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final CompanyService companyService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("register")
    public String inRegister() {
        return "register";
    }

    @PostMapping("/api/account/register")
    public String accountRegister(AccountDto accountDto) {

        AccountDto encodeAccount = encodePw(accountDto);
        accountService.save(encodeAccount);

        return "home";
    }

    @PostMapping("/api/account/admin/register")
    public String adminRegister(AccountDto accountDto){
        CompanyDto byCompanyName = companyService.existenceCheck(accountDto.company());
        if (byCompanyName == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company not found");
        }
        AccountDto encodeAccount = encodePw(accountDto);
        accountService.save(encodeAccount);
        return "home";
    }

    @PostMapping("/api/account/delete")
    public String accountDelete(@RequestParam(name = "sessionId") String id) {
        accountService.delete(id);
        return "home";
    }

    @PostMapping("/api/account/update")
    public String accountUpdate(AccountDto accountDto) {
        accountService.update(accountDto);
        return "home";
    }

    /*
        input pw encode
     */
    private AccountDto encodePw(AccountDto accountDto) {

        return new AccountDto(
                null,
                accountDto.id(),
                bCryptPasswordEncoder.encode(accountDto.pw()),
                accountDto.name(),
                accountDto.birth(),
                accountDto.email(),
                accountDto.tel(),
                accountDto.gender(),
                accountDto.address1(),
                accountDto.address2(),
                accountDto.role(),
                accountDto.company()
        );
    }

}
