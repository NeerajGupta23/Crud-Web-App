package in.mysite.neeraj.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import in.mysite.neeraj.bo.StudentBO;
import in.mysite.neeraj.util.Utility;

public class StudentRepositoryImpl implements IStudentRepository {

	private String SQL_STUDENT_INSERT_QUERY = "insert into student(`sname`, `sage`, `saddress`) values(?, ?, ?)";

	@Override
	public boolean insertStudent(StudentBO student) {
		// Insert the Student's name, age and address into database from StudentBO class
		Connection connection = Utility.getConnection();
		PreparedStatement preparedStatement = null;
		int updatedRow = 0;

		try {
			
			preparedStatement = connection.prepareStatement(SQL_STUDENT_INSERT_QUERY);
			preparedStatement.setString(1, student.getName());
			preparedStatement.setInt(2, student.getAge());
			preparedStatement.setString(3, student.getAddress());

			updatedRow = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Utility.closeResources(connection, preparedStatement, null);
		}

		// query successfully inserted a row
		if (updatedRow == 1) {
			return true;
		}

		// query failure
		return false;
	}

}
