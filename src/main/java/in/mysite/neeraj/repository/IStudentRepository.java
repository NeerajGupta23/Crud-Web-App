package in.mysite.neeraj.repository;

import in.mysite.neeraj.bo.StudentBO;

public interface IStudentRepository {
	boolean insertStudent(StudentBO student);
}
