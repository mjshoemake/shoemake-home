package mjs.framework;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(CastorTest.class);
        suite.addTestSuite(EncryptionTest.class);
        suite.addTestSuite(DBConnectTest.class);
        suite.addTestSuite(PaginatedListTagTest.class);
        suite.addTestSuite(RecipeActionsTest.class);
        suite.addTestSuite(SetupServletTest.class);
        suite.addTestSuite(StringUtilsTest.class);
        suite.addTestSuite(TableDataManagerTest.class);
        suite.addTestSuite(TagExpressionHandlerTest.class);
        //$JUnit-END$
        return suite;
    }

}
