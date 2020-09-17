# FakeStore Test Automation Demo Project
My functional tests automation demo project of a web shop application.

## General info
The purpose of this project is to develop skills in functional tests' automation. Currently I work professionally as a manual tester and
 would like to push further my career to become a test developer.

## Technologies
* Java - version 10.0.2
* Selenium - version 3.141.59
* JUnit - version 5.2.0
* Maven

## Setup
Please keep in mind that the project uses Selenium Grid and it is necessary to start the hub and node prior to test execution. In order to do so, please undertake following steps:

1. Download and unpack the SeleniumServer.rar file to C:\SeleniumServer\\.  
2. Run starthub.bat file.  
3. Run startnode.bat file.  
4. Create the folder 'screenshots' in C:\\.
	
Now you're good to go with tests execution.

The project is compatible with Chrome and Firefox, tests are being executed with Chrome by default. If you wish to change the browser that will be used to execute tests,
 please go to Configuration.properties and change the 'browser' value from CHROME to FIREFOX (capital letters are required).


## Features
* Test are running in Selenium Grid which means you can connect few nodes to the hub and execute tests simulataneously.
* Test data is located in TestData.properties, you can perform tests with different test data without need to edit the code. Please note that this applies to the tests that do not require specific pre-conditions such as unique test data. 
The unique test data are hardcoded into tests which require them in order to verify certain functionalities of the application.
* You can run tests on Chrome or Firefox, just change the browser's value in Configuration.properties in order to execute tests the browser of your choice.
* If a test result will be different than 'passed' then properly laballed screenshot will be taken and saved locally in your C:\screenshots\ folder.

To-do list:
* More tests are to be added.
* Aleternative project version which will allow potential interested persons to execute tests locally without the need to set up the Selenium Server.

## Status
Project is: _in progress_

## Inspiration
Project based on online commercial test automation in Java course - https://testelka.pl/kurs/selenium/. The FakeStore is the online web shop application made in purpose to practice test automation.

## Contact
Created by Michał Kownacki (https://www.linkedin.com/in/michal-kownacki/) - feel free to contact me!
