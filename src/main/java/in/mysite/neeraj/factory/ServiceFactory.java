package in.mysite.neeraj.factory;

import in.mysite.neeraj.service.IStudentService;
import in.mysite.neeraj.service.StudentServiceImpl;

public class ServiceFactory {
	private static IStudentService studentService = null;

	public static IStudentService getStudentServiceObject() {
		// gives only one object of StudentService class -> singleton pattern

		if (studentService == null) {
			studentService = new StudentServiceImpl();
		}

		return studentService;
	}
}
