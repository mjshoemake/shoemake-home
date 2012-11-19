package mjs.framework;

import org.junit.Before;
import org.junit.Test;

import mjs.core.ServletStarterTest;
import mjs.database.Field;
import mjs.tags.ShowPaginatedListTag;

public class PaginatedListTagTest extends ServletStarterTest {

    @Before
    public void setUp() throws Exception {
//        setUpEnvironment();
    }

    /**
     * Test method.
     */
    @Test
    public void testConvertFieldWidth() {

        try {
         ShowPaginatedListTag tag = new ShowPaginatedListTag();
         Field field = new Field("meals_pk", 
                                 "int",
                                 "",
                                 0,
                                 false,
                                 false,
                                 "120px",
                                 "Meal", 
                                 false,
                                 null);     
         
         String newWidth = tag.getFilterFieldWidth(field);
         System.out.println("StartWidth: " + field.getListColumnWidth());
         System.out.println("New Width:  " + newWidth);
         
        	System.out.println("Test complete.  Exiting.");

        } catch (Throwable e) {
            e.printStackTrace();
            assertFailed("Execution with no exceptions.  " + e.getMessage());
        } finally {
            //reportResults();         	
        }
    }

}
