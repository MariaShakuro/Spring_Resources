package com.example.core;

import com.example.core.cucumber.BaseTest;
import io.cucumber.junit.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberOptions(
        features = "src/test/resources/cucumber",
        glue = "com.example.core.cucumber",
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
@SpringBootTest
@ActiveProfiles("test")
public class CucumberTestRunner extends BaseTest {

}

