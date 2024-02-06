package com.hkit.lifetime.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hkit.lifetime.account.Account;
import com.hkit.lifetime.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;
    private final CompanyAccountListRepository companyAccountListRepository;

    /*
        company register method
     */
    public void registerCompany(CompanyDto companyDto) {
        Company findCompany = companyRepository.findByName(companyDto.id())
                .orElseGet(() -> new Company());
        if (findCompany.getName() == null) {
            Company company = Company.toCompany(companyDto);
            companyRepository.save(company);
        }
    }

    public void registerLecturer(String name, String id){
        Account findAccount = accountRepository.findAccountById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found"));

        Company findCompany = companyRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found"));

        CompanyAccountList companyAccountList = new CompanyAccountList(findAccount, findCompany);
        companyAccountListRepository.save(companyAccountList);
    }

    /*
        company delete method
     */
    public void deleteCompany(String id) {
        Company findCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found"));

        List<CompanyAccountList> findCompanyAccounts = companyAccountListRepository.findByCompany_Id(id)
                .orElseGet(() -> new ArrayList<>());
        if (!findCompanyAccounts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Account Existence");
        }

        companyRepository.delete(findCompany);
    }


    /*
        company authorization method
     */
    public void authorization(String id) {
        Company findCompany = companyRepository.findById(id).orElseGet(() -> new Company());
        if (findCompany.getCompanyId() != null){
            findCompany.authorizationCompany();
            companyRepository.save(findCompany);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found");
        }
    }

    public void denied(String id){
        Company findCompany = companyRepository.findById(id).orElseGet(() -> new Company());
        if (findCompany.getCompanyId() != null){
            findCompany.deniedCompany();
            companyRepository.save(findCompany);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found");
        }
    }
}
