package in.mysite.neeraj.factory;

import in.mysite.neeraj.repository.IStudentRepository;
import in.mysite.neeraj.repository.StudentRepositoryImpl;

public class RepositoryFactory {

	private static IStudentRepository studentrepository = null;

	public static IStudentRepository getStudentRepoObject() {
		// gives only one object of StudentRepository class -> singleton pattern

		if (studentrepository == null) {
			studentrepository = new StudentRepositoryImpl();
		}

		return studentrepository;
	}
}
