package com.hkit.lifetime.company;

import com.hkit.lifetime.account.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/api/company/create")
    public String companyRegister(CompanyDto companyDto) {

        companyService.register(companyDto);

        return "home";
    }

    @PostMapping("/api/company/delete")
    public String companyDelete(@RequestParam(name = "name") String id) {
        companyService.delete(id);
        return "home";
    }

}
