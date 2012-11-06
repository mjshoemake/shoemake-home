package mjs.mocks;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * The mocked up version of the RequestDispatcher interface.  
 * This is used to simulate a real world environment without 
 * an actual servlet container (for use with JUnit).
 */
public class MockRequestDispatcher implements RequestDispatcher
{

   public void forward(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
   {
   }

   public void include(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
   {
   }
}
