package com.hkit.lifetime.company;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        byName.ifPresent(companyRepository::delete);
    }
}
