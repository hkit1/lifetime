package com.hkit.lifetime.account;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.invalidate();
        request.getSession(true);
        return "redirect:/";
    }

    @GetMapping("/account/register/agree")
    public String agree(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null){
            return "home";
        }

        return "register_clause";
    }

    @GetMapping("/account/register/choose")
    public String choose(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null){
            return "home";
        }

        return "register_division";
    }

    @GetMapping("/account/register")
    public String inRegister(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpServletRequest request) {
        if (userDetails != null){
            return "home";
        }

        model.addAttribute("address", "/register/complete");
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

    @GetMapping("/api/account/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") String id) {
        accountService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
