package Respository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import Config.KeyWordAction;
import Object.Element;
import Object.Step;
import ReadExcelFiles.ReadExcel;

public class RegisterPage {
	WebDriver driver;
	KeyWordAction kw =new KeyWordAction(driver);
	List<Element> elements=null;
	@Test
	public void LoginScript() {
		String excelPath=System.getProperty("user.dir")+"\\Data\\Register.xlsx";
		ReadExcel re= new ReadExcel();
		elements= re.getElement(excelPath, 1);
		int CasePass=0;
		int CaseFail=0;
		List<Step>Steps= re.getStep(excelPath, 0);
		String CaseID="";
		for(Step s : Steps)
			{
				if(s.getId()!=null)
				{
					CaseID=s.getId();
					System.out.println("Case: "+s.getId());
					System.out.println("Description: "+s.getDescription());
				}
				switch(s.getAction())
				{
					case "OpenBrowser":						
						kw.OpenBrowser(s.getValue());
						System.out.println("===============Mo trinh duyet: "+s.getValue());break;
					case "Navigate":
						System.out.println("===============Ket noi toi: "+s.getValue());
						kw.Navigate(s.getValue());break;
					case "SetText":
						System.out.println("===============Set "+s.getElement()+": " +s.getValue());
						kw.SetText(getAddress( s.getElement()), s.getValue());break;
					case "Click":
						System.out.println("===============Click vao: "+s.getElement());
						kw.Click(getAddress( s.getElement()));break;
					case "VerifyTitle":
						System.out.println("===============So sanh Title voi: "+s.getValue());
					    if(kw.VerifyTitle(s.getValue()))
					    	{
					    	System.out.println("===============Case:"+CaseID+" Pass");
					    	CasePass++;
					    	}
					    else {
					    	System.out.println("===============Case:"+CaseID+" Fail");
					    	CaseFail++;
					    }
					    break;
					case "VerifyElementText":
						System.out.println("===============So sanh text cua "+s.getElement()+": "+s.getValue());
					    if(kw.VerifyElementText(getAddress( s.getElement()),s.getValue()))
					    	{
					    	System.out.println("===============Case:"+CaseID+" Pass");
					    	CasePass++;
					    	}
					    else {
					    	System.out.println("===============Case:"+CaseID+" Fail");
					    	CaseFail++;
					    }
					    break;
					case "CloseBrowser":
						System.out.println("===============Close Browser");
						kw.Close();break;
					default: System.out.println("===============Miss action: "+s.getAction());
				}			
			}
		System.out.println("===============Tong case : "+(CasePass+CaseFail));
		System.out.println("===============Tong case pass: "+CasePass);
		System.out.println("===============Tong case fail: "+CaseFail);
	}
	public String getAddress(String name)
	{
		String result="";
		for(Element element: elements)
		{
			if(element.getName().equals(name))
			{
				if(element.getId()!=null)
				{
					result=element.getId();
				}else
				{
					result=element.getXpath();
				}	
			}			
		}
		return result;
	}
}
