package in.mysite.neeraj.service;

import in.mysite.neeraj.dto.StudentDTO;

public interface IStudentService {
	boolean insertStudent(StudentDTO student);

	boolean deleteStudent(StudentDTO student);

	boolean updateStudent(StudentDTO student);

	StudentDTO readStudent(StudentDTO student);
}
