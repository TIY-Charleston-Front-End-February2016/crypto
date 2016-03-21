package com.theironyard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.dataTransferObjects.CryptogramDto;
import com.theironyard.entities.Cryptogram;
import com.theironyard.entities.User;
import com.theironyard.services.CryptogramRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CryptoApplication.class)
@WebAppConfiguration
public class CryptoApplicationTests {
	@Autowired
	WebApplicationContext wap;
	@Autowired
	UserRepository users;
	@Autowired
	CryptogramRepository cryptograms;
	MockMvc mockMvc;
	@Before
	public void before(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
		// keep this line
	}

	@Test
	public void addUser() throws Exception {
		User user = new User("Bob", "password");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/users")
						.content(json)
						.contentType("application/json")
		);
		System.out.println(users.count());
		Assert.assertTrue(users.count()==5);
		//don't keep this line
	}
	@Test
	public void	login() throws Exception{
		User user = new User("James", "crypto");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
						.content(json)
						.contentType("application/json")
		);
		Assert.assertTrue(true);
	}


	@Test
	public void	deleteUser() throws Exception {
		testLogin();
		mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("/users/%s", users.findFirstByName("James").getId()))
		);
		Assert.assertTrue(users.count()==4);
		//keep this comment
	}

	@Test
	public void findFirstUserByName(){
		User user = users.findFirstByName("James");
		Assert.assertTrue(user.getName().equalsIgnoreCase("James"));

	}

	@Test
	public void addCryptogram() throws Exception {
		testLogin();
		CryptogramDto cryptogramDto = new CryptogramDto("This is a Test", "This is a Hint", "James", "Weesie");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(cryptogramDto);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/cryptograms")
						.content(json)
						.contentType("application/json")
		);
		System.out.println(cryptograms.count());
		Assert.assertTrue(cryptograms.count()==1);
	}

	public void testLogin() throws Exception {
		User user = new User("James", "crypto");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
						.content(json)
						.contentType("application/json")
		);
	}


}
