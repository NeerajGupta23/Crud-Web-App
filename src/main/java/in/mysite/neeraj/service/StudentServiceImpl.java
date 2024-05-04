package in.mysite.neeraj.service;

import in.mysite.neeraj.bo.StudentBO;
import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.factory.RepositoryFactory;
import in.mysite.neeraj.repository.IStudentRepository;

public class StudentServiceImpl implements IStudentService {

	@Override
	public boolean insertStudent(StudentDTO student) {
		// add studentDTO object data into studentBO object and call StudentRepository

		StudentBO studentBO = new StudentBO();
		studentBO.setName(student.getName());
		studentBO.setAge(student.getAge());
		studentBO.setAddress(student.getAddress());
		
		IStudentRepository studentRepoObject = RepositoryFactory.getStudentRepoObject();
		return studentRepoObject.insertStudent(studentBO);
	}

}
