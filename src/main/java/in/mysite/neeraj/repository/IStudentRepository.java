package in.mysite.neeraj.repository;

import in.mysite.neeraj.bo.StudentBO;

public interface IStudentRepository {
	boolean insertStudent(StudentBO student);

	boolean deleteStudent(StudentBO student);

	boolean updateStudent(StudentBO student);

	StudentBO readStudent(StudentBO student);
}
