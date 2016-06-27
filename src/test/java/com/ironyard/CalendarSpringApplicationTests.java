package com.ironyard;

import com.ironyard.services.EventsRepository;
import com.ironyard.services.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CalendarSpringApplication.class)
@WebAppConfiguration
public class CalendarSpringApplicationTests
{

	@Autowired
	UserRepository users;

	@Autowired
	EventsRepository events;


	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@Before
	public void before()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}


	@Test
	public void testLogin() throws Exception
	{
		String username = "WhoGoesThere";
		String password = "logThis";
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
						.param("username", username)
						.param("password",password));
		Assert.assertTrue(users.count() == 1);

	}

	@Test
	public void testCreateEvent() throws Exception
	{
		testLogin();

		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-event")
						.param("description", "Help Me!!!  OH NO, MISTER BILL!!!!")
						.param("time",LocalDateTime.now().toString())
						.sessionAttr("username", "WhoGoesThere")
		);

		Assert.assertTrue(events.count() == 1);
	}

}
