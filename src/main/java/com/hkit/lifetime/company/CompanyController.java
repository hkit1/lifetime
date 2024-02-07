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

    /*
        company register
     */
    @PostMapping("/api/company/create")
    public String companyRegister(CompanyDto companyDto) {

        companyService.registerCompany(companyDto);

        return "home";
    }

    @PostMapping("/api/company/register/account")
    public String companyLecturerRegister(@RequestParam(name = "name") String name,
                                          @RequestParam(name = "id") String id){
        companyService.registerLecturer(name, id);
        return "home";
    }

    /*
        company delete
     */
    @PostMapping("/api/company/delete")
    public String companyDelete(@RequestParam(name = "id") String id) {
        companyService.deleteCompany(id);
        return "home";
    }

    /*
        company authorization
     */
    @PostMapping("/api/company/accept")
    public String companyAuthorization(@RequestParam(name = "id") String id){
        companyService.authorization(id);
        return "home";
    }

    @PostMapping("/api/company/denied")
    public String companyDenied(@RequestParam(name = "id") String id){
        companyService.denied(id);
        return "home";
    }

}
