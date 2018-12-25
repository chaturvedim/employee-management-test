package uat;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.test.employee.app.EmployeeManagementApplication;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty" })
public class UatTest {

	@BeforeClass
	public static void beforeClass() {
		EmployeeManagementApplication.main(ArrayUtils.EMPTY_STRING_ARRAY);
	}
}
