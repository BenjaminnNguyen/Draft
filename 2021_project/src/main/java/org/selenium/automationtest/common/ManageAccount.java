package org.selenium.automationtest.common;

import static org.selenium.automationtest.common.TestLogger.*;

import static org.selenium.automationtest.common.constant.CT_Account.*;

import org.openqa.selenium.WebDriver;

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
	public void login(String user, String pass, String...encrypt){
		String u = System.getProperty("User");
		String p = System.getProperty("Pass");
		String us = (u != null) ? u : user;
		String pa = p != null ? p : pass;
		type(ELEMENT_USERNAME_TEXTBOX, us, true);
		if (encrypt.length == 0){
			type(ELEMENT_PASSWORD_TEXTBOX, pa, true);
		}else{
			if (encrypt[0].equalsIgnoreCase("1")){
				type(ELEMENT_PASSWORD_TEXTBOX, decrypt(pa), true);
			}
		}
		info("Login vao he thong voi user " + us);
		click(ELEMENT_LOGIN_BUTTON);
		waitForElementDisappear(ELEMENT_LOGIN_BUTTON);
	}
	
	/**
	 * logout system
	 */
	public void logout(){
		mouseOver(ELEMENT_LOGOUT_LINK, true);
		click(ELEMENT_LOGOUT_LINK);
		getElementPresent(ELEMENT_USERNAME_TEXTBOX);
	}
	
	public void loginVTP(String typeUser, String user, String pass, String...encrypt){
		String u = System.getProperty("User");
		String p = System.getProperty("Pass");
		String us = (u != null) ? u : user;
		String pa = p != null ? p : pass;
		openPage("dang-nhap", driver);
		selectValueFromDropdown(ELEMENT_ACCOUNTTYPE_TEXTBOX_VTP,typeUser);
		type(ELEMENT_USERNAME_TEXTBOX_VTP, us, true);
		if (encrypt.length == 0){
			type(ELEMENT_PASSWORD_TEXTBOX_VTP, pa, true);
		}else{
			if (encrypt[0].equalsIgnoreCase("1")){
				type(ELEMENT_PASSWORD_TEXTBOX_VTP, decrypt(pa), true);
			}
		}
		info("Login vao he thong voi user " + us);
		click(ELEMENT_LOGIN_BUTTON_VTP);
		waitForElementDisappear(ELEMENT_LOGIN_BUTTON_VTP);
	}
}
