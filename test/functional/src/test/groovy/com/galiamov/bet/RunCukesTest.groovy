package com.galiamov.bet

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber)
@CucumberOptions(
        strict = true,
        format = ["pretty", "html:build/reports/cucumber"],
        features = ["src/test/resources/cukes"],
        glue = ["src/test/groovy/com/galiamov/bet/stepdefs"]
)
class RunCukesTest {}
