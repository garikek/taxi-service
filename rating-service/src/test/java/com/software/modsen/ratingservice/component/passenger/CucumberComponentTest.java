package com.software.modsen.ratingservice.component.passenger;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/passenger-rating.feature",
        glue = "com.software.modsen.ratingservice.component.passenger"
)
public class CucumberComponentTest {
}
