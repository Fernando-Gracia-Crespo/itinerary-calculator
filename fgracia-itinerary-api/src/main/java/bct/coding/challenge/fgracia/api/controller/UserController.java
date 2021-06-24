package bct.coding.challenge.fgracia.api.controller;

import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bct.coding.challenge.fgracia.api.exception.BadLoginException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@PostMapping("api/user")
	public String login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		// We have two users, test/test and admin/1234, otherwise we return bad credentials
		BiPredicate<String, String> pr = (u,p) -> ("test".equals(u) && "test".equals(p)) || ("admin".equals(u) && "1234".equals(p));
		if(pr.test(username, pwd)) {
			return getJWTToken(username);
		}
		throw new BadLoginException();
	}
	
	
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("apiJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
	
	
		
}
