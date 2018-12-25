package uat;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UatSteps {

	private Response response;
	private int createdEmployeeId;

	@Given("^There is an employee management system$")
	public void there_is_an_employee_management_system() throws Exception {
		Response response = RestAssured.when().get("http://localhost:8080/health");
		Assert.assertEquals(200, response.statusCode());
	}

	@When("^I retrieve all employees$")
	public void i_retrieve_all_employees() throws Exception {
		response = RestAssured.when().get("http://localhost:8080/employees");
		Assert.assertEquals(200, response.statusCode());
	}

	@Then("^I get (\\d+) employees$")
	public void i_get_employees(int employees) throws Exception {
		Assert.assertEquals(employees, response.jsonPath().getList("$").size());
	}

	@When("^I create an employee with (.*) and (.*)$")
	public void i_create_an_employee_with_and(String name, String department) throws Exception {
		JsonObject empDetails = new JsonObject();
		empDetails.addProperty("name", name);
		empDetails.addProperty("department", department);

		response = RestAssured.given().body(empDetails.toString()).contentType(ContentType.JSON).when()
				.post("http://localhost:8080/employees");
	}

	@Then("^An employee created with (.*) and (.*)$")
	public void an_employee_created_with_and(String name, String department) throws Exception {
		Assert.assertEquals(201, response.getStatusCode());
		Assert.assertEquals(name, response.jsonPath().get("name"));
		Assert.assertEquals(department, response.jsonPath().get("department"));
		createdEmployeeId = response.jsonPath().getInt("id");
		Assert.assertTrue(createdEmployeeId > 0);
	}

	@When("^I update created employee with (.*) and (.*)$")
	public void i_update_created_employee_with_and(String name, String department) throws Exception {
		JsonObject employeeUpdate = new JsonObject();
		employeeUpdate.addProperty("id", createdEmployeeId);
		employeeUpdate.addProperty("name", name);
		employeeUpdate.addProperty("department", department);

		response = RestAssured.given().body(employeeUpdate.toString()).contentType(ContentType.JSON).when()
				.put("http://localhost:8080/employees/{id}", createdEmployeeId);
	}

	@Then("^An employee got updated as (.*) and (.*)$")
	public void an_employee_got_updated_as_and(String name, String department) throws Exception {
		Assert.assertEquals(200, response.getStatusCode());
		Assert.assertEquals(name, response.jsonPath().get("name"));
		Assert.assertEquals(department, response.jsonPath().get("department"));
		Assert.assertNotNull(response.jsonPath().getInt("id"));
	}

	@Given("^I delete all employee$")
	public void i_delete_all_employee() throws Exception {
		response = RestAssured.when().delete("http://localhost:8080/employees");
		Assert.assertEquals(200, response.statusCode());
	}

	@When("^I delete employee$")
	public void i_delete_employee() throws Exception {
		response = RestAssured.given().contentType(ContentType.JSON).when()
				.delete("http://localhost:8080/employees/{id}", createdEmployeeId);
	}

	@Then("^An employee got deleted$")
	public void an_employee_got_deleted() throws Exception {
		Assert.assertEquals(200, response.statusCode());
	}

	@Then("^I get bad input response$")
	public void i_get_bad_input_response() throws Exception {
		Assert.assertEquals(400, response.getStatusCode());
	}

	@When("^I update unknown employee$")
	public void i_update_unknown_employee() throws Exception {
		JsonObject employeeUpdate = new JsonObject();
		employeeUpdate.addProperty("id", -1);
		employeeUpdate.addProperty("name", "Manjari");
		employeeUpdate.addProperty("department", "Legal");
		response = RestAssured.given().body(employeeUpdate.toString()).contentType(ContentType.JSON)
				.put("http://localhost:8080/employees/-1");
	}

	@Then("^I get employee not found response$")
	public void i_get_employee_not_found_response() throws Exception {
		Assert.assertEquals(404, response.getStatusCode());
	}

	@When("^I delete an unknown employee$")
	public void i_delete_an_unknown_employee() throws Exception {
		response = RestAssured.given().contentType(ContentType.JSON).when()
				.delete("http://localhost:8080/employees/-1");
	}

}
