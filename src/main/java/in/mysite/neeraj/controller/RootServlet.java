package in.mysite.neeraj.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.factory.ServiceFactory;
import in.mysite.neeraj.service.IStudentService;
import in.mysite.neeraj.vo.StudentVO;

@WebServlet(urlPatterns = { "*.perform" }, loadOnStartup = 1)
public class RootServlet extends HttpServlet implements IStudentController {

	private static final long serialVersionUID = 1L;

	@Resource(name = "JNDI")
	public static DataSource dataSource;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer requestURL = request.getRequestURL();
		String type = requestURL.substring(38);
		StudentDTO studentDTO = null;
		HttpSession currSession = request.getSession();
		currSession.setAttribute("backURL", request.getHeader("referer"));

		switch (type.replaceFirst(".perform", "")) {

		case "create":
			insertStudent(request, response);
			break;

		case "delete":
			deleteStudent(request, response);
			break;

		case "read":
			studentDTO = readStudent(request, response);

			if (studentDTO != null) {
				currSession.setAttribute("student", studentDTO);

				if (studentDTO.getName() == null && studentDTO.getAge() == null && studentDTO.getAddress() == null) {
					response.sendRedirect("../idNotExist.jsp");
				} else {
					response.sendRedirect("../showStudentData.jsp");
				}

			}
			break;

		case "update":
			studentDTO = readStudent(request, response);

			if (studentDTO != null) {
				currSession.setAttribute("student", studentDTO);

				if (studentDTO.getName() == null && studentDTO.getAge() == null && studentDTO.getAddress() == null) {
					response.sendRedirect("../idNotExist.jsp");
				} else {
					response.sendRedirect("../showStudentDataInForm.jsp");
				}

			}
			break;

		case "updateIt":
			System.out.println("Coming...");
			updateStudent(request, response);
			break;
		}

	}

	private Boolean updateStudent(HttpServletRequest request, HttpServletResponse response) {
		StudentVO student = new StudentVO();
		student.setName(request.getParameter("sname"));
		student.setAge(request.getParameter("sage"));
		student.setAddress(request.getParameter("saddress"));
		student.setId(request.getParameter("sid"));

		StudentDTO studentDTO = putIntoStudentDTO(student);
		studentDTO.setId(Integer.parseInt(student.getId()));
		boolean flag = false;

		if (studentDTO != null) {
			IStudentService studentServiceObject = ServiceFactory.getStudentServiceObject();
			flag = studentServiceObject.updateStudent(studentDTO);
		}

		try {
			response.sendRedirect(request.getParameter("urlLoc"));
		} catch (IOException e) {
			System.out.println("Problem At Insert module");
			e.printStackTrace();
		}
		return flag;
	}

	private StudentDTO readStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");

		if (id.equals("")) {
			response.sendRedirect(request.getHeader("referer"));
			return null;
		}

		StudentDTO studentDTO = new StudentDTO();

		try {
			studentDTO.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Number Id.....");
			studentDTO = null;
		}

		if (studentDTO != null) {
			IStudentService studentServiceObject = ServiceFactory.getStudentServiceObject();
			studentDTO = studentServiceObject.readStudent(studentDTO);
		}

		return studentDTO;
	}

	public boolean deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if (id.equals("")) {
			try {
				response.sendRedirect(request.getHeader("referer"));
			} catch (IOException e) {
				System.out.println("There is no value given for id...");
				e.printStackTrace();
			}
		}

		StudentDTO studentDTO = new StudentDTO();
		Boolean flag = true;

		try {
			studentDTO.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Number Id.....");
			return false;
		}

		if (studentDTO != null) {
			IStudentService studentServiceObject = ServiceFactory.getStudentServiceObject();
			flag = studentServiceObject.deleteStudent(studentDTO);
		}

		if (!flag) {
			try {
				request.getSession().setAttribute("student", studentDTO);
				response.sendRedirect("../idNotExist.jsp");
			} catch (NumberFormatException | IOException e) {
				System.out.println("Deletion Page not exist Problem");
				e.printStackTrace();
			}
		} else {
			try {
				response.sendRedirect(request.getHeader("referer"));
			} catch (IOException e) {
				System.out.println("Problem At Delete module");
				e.printStackTrace();
			}
		}

		return flag;
	}

	public boolean insertStudent(HttpServletRequest request, HttpServletResponse response) {
		StudentVO student = new StudentVO();
		student.setName(request.getParameter("name"));
		student.setAge(request.getParameter("age"));
		student.setAddress(request.getParameter("address"));

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
