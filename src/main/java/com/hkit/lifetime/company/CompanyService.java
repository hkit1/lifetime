package com.hkit.lifetime.company;

import com.hkit.lifetime.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void register(CompanyDto companyDto) {
        Optional<Company> byName = companyRepository.findByName(companyDto.name());
        if (byName.isEmpty()) {
            Company company = Company.toCompany(companyDto);
            companyRepository.save(company);
        }
    }

    public void delete(String id) {
        Optional<Company> byName = companyRepository.findByName(id);
        if (byName.isPresent()) {
            companyRepository.delete(byName.get());
        }
    }

    public CompanyDto existenceCheck(String name) {
        List<Company> allCompany = companyRepository.findAll();
        if (allCompany.isEmpty()) {
            return null;
        } else {
            for (Company company : allCompany ) {
                if (company.getName().equals(name)){
                    return new CompanyDto(company.getName());
                }
            }
        }
        return null;
    }
}
