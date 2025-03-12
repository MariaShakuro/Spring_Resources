package com.example.core.cucumber;

import io.cucumber.junit.CucumberOptions;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberOptions(
        features = "src/test/resources/cucumber",
        glue = "com.example.core.cucumber",
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
@SpringBootTest
public class CucumberTestRunner extends BaseTest {

}

