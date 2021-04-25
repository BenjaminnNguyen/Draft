package ReadExcelFiles;
import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Object.Step;
import Object.Element;
public class ReadExcel {

	public List<Step> getStep(String linkFile, int index)
	{
		List<Step> Steps = new ArrayList<Step>();
		try  
		{  
		File file = new File(linkFile);   
		FileInputStream fis = new FileInputStream(file);     
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);  
		
		XSSFSheet sheet = wb.getSheetAt(index);  
		for(int i=1; i<10000;i++)
		{
			Row row=sheet.getRow(i);// đọc dòng excel
			Cell cell=row.getCell(2);// đọc ô thứ 3 của dòng
			if(cell!=null)// nếu khác rỗng thì dòng đó có step
			{
				Step step = new Step();
				if(row.getCell(0)!=null)
				{
					step.setId(row.getCell(0).getStringCellValue());					
				}
				if(row.getCell(1)!=null)
				{
					step.setDescription(row.getCell(1).getStringCellValue());	
				}
				if(row.getCell(2)!=null)
				{
					step.setAction(row.getCell(2).getStringCellValue());
				}
				if(row.getCell(3)!=null)
				{
					step.setElement(row.getCell(3).getStringCellValue());
				}
				if(row.getCell(4)!=null)
				{
					step.setValue(row.getCell(4).getStringCellValue());
				}

				Steps.add(step);
			}
			else 
			{
				wb.close();
				break;
			}
		}
		}catch(Exception  e) {
			
		}
		
		return Steps;
	}
	public List<Element> getElement(String linkFile, int index)
	{
		List<Element> Elements = new ArrayList<Element>();
		try  
		{  
		File file = new File(linkFile);   
		FileInputStream fis = new FileInputStream(file);     
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);  
		
		XSSFSheet sheet = wb.getSheetAt(index);  
		for(int i=1; i<10000;i++)
		{
			Row row=sheet.getRow(i);// đọc dòng excel
			Cell cell=row.getCell(2);// đọc ô thứ 3 của dòng
			if(cell!=null)// nếu khác rỗng thì dòng đó có element
			{
				Element element = new Element();
				if(row.getCell(0)!=null)
				{
					element.setName(row.getCell(0).getStringCellValue());					
				}
				if(row.getCell(1)!=null)
				{
					element.setId(row.getCell(1).getStringCellValue());	
				}
				if(row.getCell(2)!=null)
				{
					element.setXpath(row.getCell(2).getStringCellValue());
				}

				Elements.add(element);
			}
			else 
			{
				wb.close();
				break;
			}
		}
		}catch(Exception  e) {
			
		}
		
		return Elements;
	}
}