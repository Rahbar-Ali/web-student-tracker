package com.student.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// Create our Student db Util and pass the connection pool 
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
		//Read the "command" parameter
		String theCommand = request.getParameter("command");
			
		
		// if "command" is missing
		if(theCommand == null) {
			theCommand = "LIST";
		}
		
		// Route to the method
		switch (theCommand) {
		
		case "LIST":
			ListStudents(request, response);
			break;
		case "ADD":
			addStudent(request, response);
			break;
		default:
			ListStudents(request, response);
		

		}
		// List student in MVC	
		ListStudents(request, response);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student info from HTML FORM
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a new student object
		Student theStudent = new Student(firstName, lastName, email);
		
		
		//add the student to the DATABASE
		studentDbUtil.addStudent(theStudent);
		
		// send back to the main page (JSP)		
		ListStudents(request,response);
		
	}



	private void ListStudents(HttpServletRequest request, HttpServletResponse response) 
	
		throws Exception{
		
		// get student from db util
		List<Student> students = studentDbUtil.getStudents();
		
		
		// add to request object
		request.setAttribute("STUDENT_LIST", students);
		
		
		
		// send to jsp view page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
		
		
	}

}
