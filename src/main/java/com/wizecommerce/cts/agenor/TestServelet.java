package com.wizecommerce.cts.agenor;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wizecommerce.cts.utils.ChangeRecord;

/**
 * Servlet implementation class TestServelet
 */
public class TestServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServelet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ChangeRecord changeRecord = new ChangeRecord();
		request.setAttribute("startOffset", 10);
		request.setAttribute("maxResult", 10);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");
		requestDispatcher.forward(request, response);
	}

}
