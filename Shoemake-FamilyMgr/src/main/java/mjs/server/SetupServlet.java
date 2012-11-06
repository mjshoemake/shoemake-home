package mjs.server;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import mjs.server.ServerSetup;

/**
 * Setup Servlet.
 */
public final class SetupServlet extends HttpServlet implements Servlet {
	
    static final long serialVersionUID = 9020182288989758191L;

    /**
     * The ServerSetup object used to create connections, load
     * config files, etc.
     */
    private ServerSetup setup = new ServerSetup();
    
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);
		try {
            setup.init();
		} catch (Exception e) {
		    throw new ServletException(e.getMessage(), e);
		}
	}

    
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
        setup.destroy();   		
	}
	
}