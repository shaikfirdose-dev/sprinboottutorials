package com.firdose.springSecurity.Security;

import com.firdose.springSecurity.Security.entity.User;
import com.firdose.springSecurity.Security.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		User user = new User(4L, "shaik@gmail.com","lhdasds","dsdsd");
		String token = jwtService.generateAccessToken(user);
		System.out.println(token);

		Long id = jwtService.generateUserIdFromToken(token);
		System.out.println(id);
	}

}
