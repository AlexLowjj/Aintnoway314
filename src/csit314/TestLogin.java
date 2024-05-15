package csit314;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import control.LoginController;
import entity.UserAccount;

public class TestLogin {
	private UserAccount userAccount;
	private LoginController loginController;

	@Before
	public void initialize() {
		userAccount = new UserAccount();
		loginController = new LoginController(userAccount);
	}
	@After
	public void reset() {
		userAccount = null;
		loginController = null;
	}
	@Test
	public void testAdminLogin() {
		String info = loginController.authenticate("admin@test.com", "adminpass");
		assertEquals("62-Admin", info);
	}
	@Test
	public void testAgentLogin() {
		String info = loginController.authenticate("agent@test.com", "agentpass");
		assertEquals("63-Agent", info);
	}
	@Test
	public void testBuyerLogin() {
		String info = loginController.authenticate("buyer@test.com", "buyerpass");
		assertEquals("64-Buyer", info);
	}
	@Test
	public void testSellerLogin() {
		String info = loginController.authenticate("seller@test.com", "sellerpass");
		assertEquals("65-Seller", info);
	}
}