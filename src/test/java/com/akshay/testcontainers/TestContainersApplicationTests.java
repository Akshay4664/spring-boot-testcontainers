package com.akshay.testcontainers;

import com.akshay.testcontainers.contollers.CourseController;
import com.akshay.testcontainers.entity.Course;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TestContainersApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");


	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
	}

	@Test
	public void addNewCourseTest() throws Exception {
		Course course = Course.builder().courseName("C++").build();

		mockMvc.perform(MockMvcRequestBuilders
						.post("/courses")
						.contentType("application/json")
						.content(asJsonString(course))
						.accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@BeforeAll
	static void beforeAll() {
		mySQLContainer.start();
	}

	@AfterAll
	static void afterAll() {
		mySQLContainer.stop();
	}

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(CourseController.class)
				.build();
	}

	@Test
	public void getAllTheCoursesTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/courses")
						.accept("application/json")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1));
	}

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return null;
	}
}
