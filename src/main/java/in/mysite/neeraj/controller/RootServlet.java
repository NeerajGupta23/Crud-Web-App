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

@WebServlet(urlPatterns = { "*.perform" }, loadOnStartup = 1)
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

		StringBuffer requestURL = request.getRequestURL();
		String type = requestURL.substring(38);

		switch (type.replaceFirst(".perform", "")) {

		case "create":
			insertStudent(request, response);
			break;

		case "delete":
			deleteStudent(request, response);
			break;
		}

	}

	public boolean deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		StudentVO student = new StudentVO();
		String id = request.getParameter("id");
		StudentDTO studentDTO = new StudentDTO();
		
		try {
			studentDTO.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Number Id.....");
			return false;
		}
		
		
		// continue from tommorrow
		return true;
	}

	public boolean insertStudent(HttpServletRequest request, HttpServletResponse response) {
		StudentVO student = new StudentVO();
		student.setName(request.getParameter("name"));
		student.setAge(request.getParameter("age"));
		student.setAddress(request.getParameter("address"));
		insertStudent(request, response);

		StudentDTO studentDTO = putIntoStudentDTO(student);
		boolean flag = false;

		if (studentDTO != null) {
			IStudentService studentServiceObject = ServiceFactory.getStudentServiceObject();
			flag = studentServiceObject.insertStudent(studentDTO);
		}

		try {
			response.sendRedirect(request.getHeader("referer"));
		} catch (IOException e) {
			System.out.println("Problem At Insert module");
			e.printStackTrace();
		}
		return flag;
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
