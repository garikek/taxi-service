package com.software.modsen.ratingservice.component.driver;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/driver-rating.feature",
        glue = "com.software.modsen.ratingservice.component.driver"
)
public class CucumberComponentTest {
}
