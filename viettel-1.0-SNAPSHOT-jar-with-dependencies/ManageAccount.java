package org.selenium.automationtest.common;

import static org.selenium.automationtest.common.TestLogger.*;

import org.openqa.selenium.WebDriver;

import static org.selenium.automationtest.common.constant.CT_Account.*;

/**
 * @author lientm
 * @date 18-Nov-2014
 */
public class ManageAccount extends CommonBase {
	
	public ManageAccount (WebDriver dr) {
		driver = dr;
	}
	
	/**
	 * login to system
	 * @param user
	 * @param pass
	 */
	public void login(String user, String pass){
		String mt = System.getProperty("SystemType");
		String m = mt != null ? mt : "Test";
		type(ELEMENT_USERNAME_TEXTBOX, user, true);
		type(ELEMENT_PASSWORD_TEXTBOX, pass, true);
		if (!m.equalsIgnoreCase("Test")){
			info("Login vao he thong that voi user " + user);
			click(ELEMENT_LOGIN_BUTTON_THAT);
		}else {
			info("Login vao he thong test voi user " + user);
			click(ELEMENT_LOGIN_BUTTON);
		}
		waitForPageLoaded(driver);
	}
	
	/**
	 * logout system
	 */
	public void logout(){
		mouseOver(ELEMENT_LOGOUT_LINK, true);
		click(ELEMENT_LOGOUT_LINK);
		getElementPresent(ELEMENT_USERNAME_TEXTBOX);
	}
}
