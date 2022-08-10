# test-utilities  
> version 1.0

This is a working version of test framework for API testing.
It includes features of API call json body customization, multiple environment support, data storage usage, 
BDD test features support with serenity test reporting, different test suite strategy via @tag, enables logs for convenient debugging. 

## Getting started

In order to run project successfully, you need to clone it as Maven project,
and run com.lt.testframework.testrunner.TestRunner as JUnit configuration.   

When it is executed you will see 4 cucumber tests marked as @regression from BasicFeatureTests.feature will be run. 
If you would like to run different set of tests please change @regression tag in TestRunner.java to desired tag (e.g. @TC01 or @negativetest etc.).

After execuyion a Test Summary Report is generated in target folder.  

## Development approach

### Built With
This framework is based on most popular toools like jUnit, cucumber, json-simple (Google), httpclient (apache). 

### Prerequisites
As prerequisites your machine needs to have java and maven installed.

### Building
Building project is very simple - it is just regular maven commands mvn install, mvn verify

## Configuration
Currently, project support default environment configuration "stageconf".
it possible to change configuration by adding new package and provide its name as VM options in test configutration. 
For example, in VM options you can add -Dconf=src/test/resources/env/stageconf/

## Tests
All tests are written in BDD format and can be found in BasicFeatureTests.feature
This feature file has 3 scenarios for API call response validation.
The 1st scenario is Outline scenario that represents example of multiple data set in one scenario.  

## Api Reference
In this test project two open APIs were used to give example of possible tests.
An API call for google translate service  - https://translation.googleapis.com/language/translate/v2
Documentation: https://cloud.google.com/translate/docs/basic/translating-text
An API call for apple artist lookup in itunes library - https://itunes.apple.com/lookup?id={artistId}
Documentation: https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/LookupExamples.html