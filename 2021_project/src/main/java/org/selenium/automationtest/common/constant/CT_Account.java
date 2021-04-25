package org.selenium.automationtest.common.constant;

import org.openqa.selenium.By;

public class CT_Account {
	public static By ELEMENT_USERNAME_TEXTBOX = By.xpath("//*[@id='username']|//*[@class='user-name']/input");
	public static By ELEMENT_PASSWORD_TEXTBOX = By.xpath("//*[@id='password']|//*[@class='password']/input");
	public static By ELEMENT_LOGIN_BUTTON = By.xpath("//*[@value='ĐĂNG NHẬP' or @value='Log in' or @value='Login']|//button[contains(text(),'Đăng nhập') or text()='Login']");
	public static By ELEMENT_LOGOUT_ICON = By.xpath("//*[@class='fa fa-sign-out-alt']");
	public static By ELEMENT_LOGOUT_LINK = By.xpath("//*[text()='Logout']/..");

	// -----------------------VTP--------------------------------------------------
	public static By ELEMENT_ACCOUNTTYPE_TEXTBOX_VTP = By.xpath("//*[text()='Loại tài khoản']/..//select");
	public static By ELEMENT_USERNAME_TEXTBOX_VTP = By.xpath("//*[text()='Tài khoản']/..//input");
	public static By ELEMENT_PASSWORD_TEXTBOX_VTP = By.xpath("//*[text()='Mật khẩu']/..//input");
	public static By ELEMENT_LOGIN_BUTTON_VTP = By.xpath("//*[@class='btn btn-large btn-blue position-relative' and text()='Đăng nhập ']");

}
