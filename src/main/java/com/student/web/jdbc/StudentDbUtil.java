package com.student.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception {
		
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			// get a Connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			String sql = "select * from student order by last_name";
			
			myStmt = myConn.createStatement();
			
			// excute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
					// retrieve data from result set row
					
					int id = myRs .getInt("id");
					String firstName = myRs.getString("first_name");
					String lastName = myRs.getString("last_name");
					String email = myRs.getString("email");
					
					
					// create new student object
					Student tempStudent = new Student(id, firstName, lastName, email);
					
					// add it to the list of students
					students.add(tempStudent);
			}
			
			return students;
		}
		finally {
			// Close HSBC object
			close(myConn, myStmt, myRs);
		}
	
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		
		try {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();	
			}
			if (myConn != null) {
				myConn.close();
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addStudent(Student theStudent) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
		// get DB connection
			myConn = dataSource.getConnection();
			
		// create SQL for insert
		String sql = "insert into student "
					+ "(first_name, last_name, email) "
					+ "values (?,?,?)";
			
		myStmt = myConn.prepareStatement(sql);
		// set parameter value for the student
		myStmt.setString(1, theStudent.getFirstName());
		myStmt.setString(2, theStudent.getLastName());
		myStmt.setString(3, theStudent.getEmail());
		
		
		
		// excute sql insert
		myStmt.execute();
		
		
		}
		finally {
		// clean up JSBC object
			close(myConn, myStmt, null);
		}
		}
}



