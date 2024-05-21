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

		Integer age = student.getAge();
		String status = null;
		studentBO.setAge(age);

		if (age >= 18) {
			status = "Eligible";
		} else {
			status = "Not Eligible";
		}
		studentBO.setStatus(status);
		studentBO.setAddress(student.getAddress());

		IStudentRepository studentRepoObject = RepositoryFactory.getStudentRepoObject();
		return studentRepoObject.insertStudent(studentBO);
	}

	@Override
	public boolean deleteStudent(StudentDTO student) {
		StudentBO studentBO = new StudentBO();
		studentBO.setId(student.getId());

		IStudentRepository studentRepoObject = RepositoryFactory.getStudentRepoObject();
		return studentRepoObject.deleteStudent(studentBO);
	}

	@Override
	public StudentDTO readStudent(StudentDTO student) {
		StudentBO studentBO = new StudentBO();
		studentBO.setId(student.getId());

		IStudentRepository studentRepoObject = RepositoryFactory.getStudentRepoObject();
		studentBO = studentRepoObject.readStudent(studentBO);

		if(studentBO == null) {
			return null;
		}
		
		student.setName(studentBO.getName());
		student.setAge(studentBO.getAge());
		student.setAddress(studentBO.getAddress());
		return student;
	}

	@Override
	public boolean updateStudent(StudentDTO student) {
		StudentBO studentBO = new StudentBO();
		studentBO.setId(student.getId());
		studentBO.setName(student.getName());

		Integer age = student.getAge();
		String status = null;
		studentBO.setAge(age);

		if (age >= 18) {
			status = "Eligible";
		} else {
			status = "Not Eligible";
		}
		studentBO.setStatus(status);
		studentBO.setAddress(student.getAddress());

		IStudentRepository studentRepoObject = RepositoryFactory.getStudentRepoObject();
		return studentRepoObject.updateStudent(studentBO);
	}

	@Override
	public String getMaxId() {
		IStudentRepository repoFactory = RepositoryFactory.getStudentRepoObject();
		return repoFactory.getMaxId();
	}

}
