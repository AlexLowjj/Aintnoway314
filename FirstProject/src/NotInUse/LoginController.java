package NotInUse;

public class LoginController {
	private Login userLoginEnt;

	public LoginController(Login userLoginEnt) {
		this.userLoginEnt = userLoginEnt;
	}

	public String authenticate() {
		return userLoginEnt.authenticate();
	}
}
