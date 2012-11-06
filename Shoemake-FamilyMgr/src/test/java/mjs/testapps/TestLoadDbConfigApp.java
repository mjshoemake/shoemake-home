package mjs.testapps;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import mjs.database.DatabaseConfig;
import mjs.database.PaginatedList;
import mjs.database.TableConfig;
import mjs.database.TableDataManager;
import mjs.setup.SetupServlet;
import mjs.xml.CastorObjectConverter;

public class TestLoadDbConfigApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		CastorObjectConverter castor = new CastorObjectConverter();
        URL mappingURL = SetupServlet.class.getResource("/mjs/database/DatabaseMapping.xml");
		
        StringBuilder builder = new StringBuilder();

        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        builder.append("<database driver=\"com.mysql.jdbc.Driver\" ");
        builder.append("          url=\"jdbc:mysql://localhost/test\" ");
        builder.append("          userName=\"\" ");
        builder.append("          password=\"Ihcsppp1\" ");
        builder.append("          maxConnections=\"20\" > ");
        builder.append("   <table tableName=\"meals\" ");
        builder.append("          mappingFileName=\"mjs/admin/MealsMapping.xml\" ");
        builder.append("          className=\"mjs.admin.MealForm\" />");
        builder.append("   <table tableName=\"cookbooks\" ");
        builder.append("          mappingFileName=\"mjs/admin/CookbooksMapping.xml\" ");
        builder.append("          className=\"mjs.admin.CookbookForm\" />");
        builder.append("</database>");
        
        try {
		    DatabaseConfig dbConfig = (DatabaseConfig)castor.convertXMLToObject(builder.toString(), DatabaseConfig.class, mappingURL);
		    System.out.println("Config Count: " + dbConfig.getTableConfigs().size());
		    System.out.println("Table Count: " + dbConfig.getTableCount());
		    
		    TableDataManager dbMgr = dbConfig.getTable("MealsMapping.xml");
		    System.out.println("Table Count after getTable: " + dbConfig.getTableCount());
		    dbMgr.open();
		    PaginatedList list = dbMgr.loadList("", 25, 500, "");
		    dbMgr.close();
		    System.out.println("Meal count: " + list.size());
		    
		    
		    
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
	}

}
