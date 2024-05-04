package in.mysite.neeraj.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.factory.ServiceFactory;
import in.mysite.neeraj.service.IStudentService;
import in.mysite.neeraj.vo.StudentVO;

@WebServlet(urlPatterns = {"*.perform"}, loadOnStartup = 1)
public class RootServlet extends HttpServlet implements IStudentController {
	private static final long serialVersionUID = 1L;

	public RootServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StudentVO student = new StudentVO();
		student.setName(request.getParameter("name"));
		student.setAge(request.getParameter("age"));
		student.setAddress(request.getParameter("address"));

		StudentDTO studentDTO = putIntoStudentDTO(student);

		if (studentDTO != null) {
			IStudentService studentServiceObject = ServiceFactory.getStudentServiceObject();
			studentServiceObject.insertStudent(studentDTO);
		}

		response.sendRedirect(request.getHeader("referer"));
	}

	public StudentDTO putIntoStudentDTO(StudentVO student) {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setName(student.getName());

		String age = student.getAge();
		try {
			studentDTO.setAge(Integer.parseInt(age));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Number.....");
			return null;
		}

		studentDTO.setAddress(student.getAddress());
		return studentDTO;
	}
}
