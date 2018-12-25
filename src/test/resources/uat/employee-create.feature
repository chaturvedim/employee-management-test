Feature: Creating Employees in Employee Management System 

Background:	
	Given There is an employee management system 
	Given I delete all employee
	When I retrieve all employees 
	Then I get 0 employees 

Scenario: Create employee positive test
	When I create an employee with Manjari and Legal 
	Then An employee created with Manjari and Legal 
	When I retrieve all employees 
	Then I get 1 employees 
	When I create an employee with Gaurav and Finance 
	Then An employee created with Gaurav and Finance 
	When I retrieve all employees 
	Then I get 2 employees 
	When I update created employee with Manjari1 and Finance 
	Then An employee got updated as Manjari1 and Finance 
	When I retrieve all employees 
	Then I get 2 employees
	When I delete employee
	Then An employee got deleted
	When I retrieve all employees 
	Then I get 1 employees
	When I create an employee with Gaurav and Finance 
	Then An employee created with Gaurav and Finance
	When I create an employee with Manjari and Finance 
	Then An employee created with Manjari and Finance
	
Scenario: Create employee negative test
    When I create an employee with Manjari and Legal1 
    Then I get bad input response
    When I create an employee with Manjari and Legal 
    Then An employee created with Manjari and Legal 
    When I update created employee with Manjari and Finance1 
    Then I get bad input response
    When I update unknown employee
    Then I get employee not found response  
    When I delete an unknown employee
    Then I get employee not found response 