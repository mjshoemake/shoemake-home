package mjs.framework;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.net.URL;

import mjs.core.ServletStarterTest;
import mjs.database.Field;
import mjs.setup.SetupServlet;
import mjs.utils.FieldDefs;
import mjs.utils.LogUtils;
import mjs.xml.CastorObjectConverter;

public class CastorTest extends ServletStarterTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    /**
     * Test method.
     */
    @Test
    public void testConnect() {

        try {

        	FieldDefs defs = new FieldDefs();
        	ArrayList<Field> items = new ArrayList<Field>();
        	Field next = new Field("field1", "int", "99,999,999,990", 50, true, false, "", "Field #1", false, null);
        	Field next2 = new Field("field2", "boolean", "Ja/Nein", 4, false, false, "", "Field #2", true, null);
        	items.add(next);
        	items.add(next2);
        	defs.setItems(items);
            URL mappingUrl = SetupServlet.class.getResource("/mjs/database/FieldDefMapping.xml");
        	
        	String xml = CastorObjectConverter.convertObjectToXML(defs, mappingUrl);
        	System.out.println(xml);
        	
        	Object obj = CastorObjectConverter.convertXMLToObject(xml, FieldDefs.class, mappingUrl);
            String[] lines = LogUtils.dataToStrings(obj);
        	System.out.println("Output:");
            for (int i=0; i <= lines.length-1; i++) {
            	System.out.println("   " + lines[i]);
            }
        	
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }
    
}
