package com.app.myco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.properties")
public class UserManagementApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("TESTING GET COUNTRY LIST OPERATION")
	@Order(1)
	public void testGetAllCountries() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/country/list");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
	}

	@Test
	@DisplayName("TESTING GET STATE LIST OPERATION")
	@Order(2)
	public void testGetStatesByCountry() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/country/states/{id}",
				1);
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
	}

	@Test
	@DisplayName("TESTING GET CITY LIST OPERATION")
	@Order(3)
	public void testGetCitiesByState() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/state/cities/{id}",
				1);
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
	}

	@Test
	@DisplayName("TESTING USER LOGIN OPERATION")
	@Order(4)
	public void testUserLogin() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "    \"email\" : \"ybinds@yahoo.com\",\n" + "    \"password\" : \"ajay@123\"\n" + "}");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if (!response.getContentAsString().contains("Welcome")) {
			fail("INVALID CREDENTIALS");
		}
	}

	@Test
	@Disabled
	@DisplayName("TESTING FOR DUPLICATE USER EXISTS OPERATION")
	@Order(5)
	public void testCheckDuplicateUser() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("http://localhost:8080/user/checkDuplicate/{email}", "ybinds@yahoo.com");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if (response.getContentAsString().contains("already")) {
			fail("Email already in use");
		}
	}

	@Test
	@DisplayName("TESTING FOR DUPLICATE USER EXISTS OPERATION")
	@Order(6)
	public void testCheckDuplicateUserUnique() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("http://localhost:8080/user/checkDuplicate/{email}", "ybinds@gmail.com");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());
		if (response.getContentAsString().contains("already")) {
			fail("Email already in use");
		}
	}

	@Test
	@Disabled
	@DisplayName("TESTING CREATE USER OPERATION")
	@Order(7)
	public void testCreateUser() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/user/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "    \"userEmail\": \"dhanush@gmail.com\",\n"
						+ "    \"userFirstName\" : \"Dhanush\",\n" + "    \"userLastName\" : \"Domala\",\n"
						+ "    \"userPhNo\" : 7894561230,\n" + "    \"userDob\" : \"09/25/1997\",\n"
						+ "    \"userGender\" : \"Male\",\n" + "    \"userCountryId\" : {\n"
						+ "        \"countryId\" : 1,\n" + "        \"countryName\" : \"India\"\n" + "    },\n"
						+ "    \"userStateId\" : {\n" + "        \"stateId\" : 2,\n"
						+ "        \"stateName\" : \"Maharashtra\"\n" + "    },\n" + "    \"userCityId\" : {\n"
						+ "        \"cityId\" : 2,\n" + "        \"cityName\" : \"Mumbai\"\n" + "    }\n" + "}");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertNotNull(response.getContentAsString());

		if (!response.getContentAsString().contains("created")) {
			fail("FAILED TO CREATE USER");
		}

	}
	
	@Test
	@DisplayName("TESTING UNLOCK USER OPERATION")
	@Order(8)
	public void testUnlockUserAccount() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/user/unlock")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"email\" : \"chandu@gmail.com\",\n"
						+ "    \"oldPassword\" : \"ac6216YOQt\",\n"
						+ "    \"newPassword\" : \"chandu@123\"\n"
						+ "}");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());

		if (! response.getContentAsString().contains("unlocked")) {
			fail("FAILED TO UNLOCK ACCOUNT");
		}

	}
	
	@Test
	@DisplayName("TESTING FORGOT PASSWORD OPERATION")
	@Order(9)
	public void testForgotPassword() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("http://localhost:8080/user/forgotPassword/{email}","ybinds@yahoo.com");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getContentAsString());

		if (! response.getContentAsString().contains("email")) {
			fail("FAILED TO SEND PASSWORD");
		}

	}
}
