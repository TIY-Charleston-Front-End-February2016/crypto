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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public void aaddUser() throws Exception {
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
	public void	blogin() throws Exception{
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
	public void	zdeleteUser() throws Exception {
		int id = users.findFirstByName("James").getId();
		mockMvc.perform(
				MockMvcRequestBuilders.delete(String.format("/users/%s", id)).sessionAttr("user", users.findFirstByName("James"))
		);
		Assert.assertTrue(users.count()==4);
		//keep this comment
	}

	@Test
	public void dfindFirstUserByName(){
		User user = users.findFirstByName("James");
		Assert.assertTrue(user.getName().equalsIgnoreCase("James"));

	}

	@Test
	public void eaddCryptogram() throws Exception {
		CryptogramDto cryptogramDto = new CryptogramDto("This is a Test", "This is a Hint", "James", "Weesie");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(cryptogramDto);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/cryptograms")
						.content(json)
						.contentType("application/json")
				.sessionAttr("user", users.findFirstByName("James"))
		);
		System.out.println(cryptograms.count());
		Assert.assertTrue(cryptograms.count()==1);
	}

	@Test
	public void fgetCryptogramsBySender() throws Exception {
		Assert.assertTrue(cryptograms.findBySender(users.findFirstByName("James")).size()==1);
	}

	@Test
	public void ggetCryptogramsByRecipient() throws Exception{
		Assert.assertTrue(cryptograms.findByRecipient(users.findFirstByName("Weesie")).size()==1);
	}

	public void htestLogin() throws Exception {
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
