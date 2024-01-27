package com.hkit.lifetime;

import com.hkit.lifetime.company.CompanyRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static com.hkit.lifetime.AccountTests.createAccountInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTests {
    @Autowired
    WebApplicationContext context;
    MockMvc mockmvc;

    @Autowired
    CompanyRepository repository;

    @BeforeEach
    void setUp() {
        this.mockmvc = MockMvcBuilders.webAppContextSetup(context).build();
        repository.deleteAll();
    }

    @Test
    void createCompany() throws Exception {

    }

    @Test
    void deleteCompany() throws Exception {

    }

    @Test
    void updateCompany(){

    }
}
