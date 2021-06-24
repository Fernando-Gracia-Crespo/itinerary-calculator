package bct.coding.challenge.fgracia.calculator.auth;

import lombok.Data;

@Data
public class Credentials {

	private String user;
	private String password;
	private String token;
	
	public Credentials() {
	}
	
	public Credentials(String user,String password){
		this.user = user;
		this.password = password;
	}

}
