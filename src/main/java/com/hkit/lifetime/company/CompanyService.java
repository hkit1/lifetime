package com.hkit.lifetime.company;

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
    public void register(CompanyDto companyDto) {
        Optional<Company> findCompany = companyRepository.findByName(companyDto.id());
        if (findCompany.isEmpty()) {
            Company company = Company.toCompany(companyDto);
            companyRepository.save(company);
        }
    }

    public void lecturerRegister(String name, String id){
        Optional<Account> findAccount = accountRepository.findAccountById(id);
        if (findAccount.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found");
        }

        Optional<Company> findCompany = companyRepository.findByName(name);
        if (findCompany.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found");
        }

        CompanyAccountList companyAccountList = new CompanyAccountList(findAccount.get(), findCompany.get());
        companyAccountListRepository.save(companyAccountList);
    }

    /*
        company delete method
     */
    public void delete(String id) {
        Optional<Company> findCompany = companyRepository.findById(id);
        if (findCompany.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found");
        }

        Company company = findCompany.get();
        List<CompanyAccountList> findCompanyAccounts = companyAccountListRepository.findByCompany_Id(id);
        if (!findCompanyAccounts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Account Existence");
        }

        companyRepository.delete(company);
    }


    /*
        company authorization method
     */
    public void authorization(String id) {
        Optional<Company> findCompany = companyRepository.findById(id);
        if (findCompany.isPresent()){
            Company company = findCompany.get();
            company.authorizationCompany();
            companyRepository.save(company);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found");
        }
    }

    public void denied(String id){
        Optional<Company> findCompany = companyRepository.findById(id);
        if (findCompany.isPresent()){
            Company company = findCompany.get();
            company.deniedCompany();
            companyRepository.save(company);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company Not Found");
        }
    }
}
