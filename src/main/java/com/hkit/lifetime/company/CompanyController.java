package com.hkit.lifetime.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/api/company/check")
    public ResponseEntity<?> checkCompanyExists(@RequestParam(value = "company") String company) {
        if (companyService.findByName(company).isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    @GetMapping("/api/company/delete/{id}")
    public ResponseEntity<?> companyDelete(@PathVariable String id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
        company authorization
     */
    @GetMapping("/api/company/accept/{id}")
    public ResponseEntity<?> companyAuthorization(@PathVariable String id){
        companyService.authorization(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/company/denied/{id}")
    public ResponseEntity<?> companyDenied(@PathVariable String id){
        companyService.denied(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
