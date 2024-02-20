package com.hkit.lifetime.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/account/logout")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null){
            session.invalidate();
            return "redirect:/";
        }

        return "redirect:/";
    }

    @GetMapping("/account/register/agree")
    public String agree() {
        return "register_clause";
    }

    @GetMapping("/account/register/choose")
    public String choose() {
        return "register_division";
    }

    @GetMapping("/account/register")
    public String inRegister(Model model) {
        model.addAttribute("address", "/register/comple");
        return "register_input";
    }

    @PostMapping("/account/register")
    public String registerAccount(AccountDto accountDto) {
        if (accountService.duplicateCheck(accountDto.id())) {
            AccountDto encodeAccount = encodePw(accountDto);
            accountService.register(encodeAccount);
            return "redirect:/register/complete";
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in Use");
        }
    }

    @PostMapping("/api/account/check")
    public String idCheck(@RequestParam(name = "id") String id){
        if (accountService.duplicateCheck(id)) {
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
                accountDto.address2(),
                accountDto.created_at()
        );
    }

}
