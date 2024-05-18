package in.mysite.neeraj.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.mysite.neeraj.bo.StudentBO;
import in.mysite.neeraj.controller.RootServlet;
import in.mysite.neeraj.util.Utility;

public class StudentRepositoryImpl implements IStudentRepository {

	private String SQL_STUDENT_INSERT_QUERY = "insert into student(`sname`, `sage`, `saddress`, `status`) values(?, ?, ?, ?)";
	private String SQL_STUDENT_DELETE_QUERY = "delete from student where sid = ?";
	private String SQL_STUDENT_READ_QUERY = "select sname, saddress, sage from student where sid = ?";
	private String SQL_STUDENT_UPDATE_QUERY = "update student set sname = ?, sage = ?, saddress = ?, status = ? where sid = ?";

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
			preparedStatement.setString(4, student.getStatus());

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

	@Override
	public boolean deleteStudent(StudentBO student) {
		Connection connection = Utility.getConnection();
		PreparedStatement preparedStatement = null;
		int deletedRow = 0;

		try {
			preparedStatement = connection.prepareStatement(SQL_STUDENT_DELETE_QUERY);
			preparedStatement.setInt(1, student.getId());

			deletedRow = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Utility.closeResources(connection, preparedStatement, null);
		}

		// query successfully deleted a row
		if (deletedRow == 1) {
			return true;
		}

		// query failure
		return false;

	}

	@Override
	public StudentBO readStudent(StudentBO student) {
		Connection connection = Utility.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(SQL_STUDENT_READ_QUERY);
			preparedStatement.setInt(1, student.getId());

			resultSet = preparedStatement.executeQuery();

			// query successfully read a row
			if (resultSet.next()) {
				student.setName(resultSet.getString("sname"));
				student.setAge(resultSet.getInt("sage"));
				student.setAddress(resultSet.getString("saddress"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Utility.closeResources(connection, preparedStatement, resultSet);
		}

		// query failure
		return student;
	}

	@Override
	public boolean updateStudent(StudentBO student) {
		Connection connection = Utility.getConnection();
		PreparedStatement preparedStatement = null;
		int updatedRow = 0;

		try {

			preparedStatement = connection.prepareStatement(SQL_STUDENT_UPDATE_QUERY);
			preparedStatement.setString(1, student.getName());
			preparedStatement.setInt(2, student.getAge());
			preparedStatement.setString(3, student.getAddress());
			preparedStatement.setString(4, student.getStatus());
			preparedStatement.setInt(5, student.getId());

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
