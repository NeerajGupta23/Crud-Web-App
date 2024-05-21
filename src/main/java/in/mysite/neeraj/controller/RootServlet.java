package in.mysite.neeraj.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.factory.ServiceFactory;
import in.mysite.neeraj.service.IStudentService;
import in.mysite.neeraj.vo.StudentVO;

@WebServlet(urlPatterns = { "*.perform" }, loadOnStartup = 1)
public class RootServlet extends HttpServlet implements IStudentController {

	private static final long serialVersionUID = 1L;
	private static String msg = "";
	private static String msgForId = "";

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuffer requestURL = request.getRequestURL();
		String type = requestURL.substring(38);

		String sid = request.getParameter("id");
		HttpSession currSession = request.getSession();
		currSession.setAttribute("backURL", request.getHeader("referer"));

		Boolean flag = false;
		String printingMessage = null;
		StudentDTO studentDTO = null;

		switch (type.replaceFirst(".perform", "")) {

		case "create":
			flag = insertStudent(request, response);
			if (flag) {
				printingMessage = " is successfully Inserted...";
				sid = findMaxId();
			}
			break;

		case "delete":
			flag = deleteStudent(request, response);
			if (flag) {
				printingMessage = " is successfully Deleted...";
			} else if (msgForId.equals("invalid")) {
				msgForId = "consumed";
				response.sendRedirect(request.getHeader("referer"));
			}
			break;

		case "updateIt":
			System.out.println("Coming...");
			flag = updateStudent(request, response);
			if (flag) {
				printingMessage = " is successfully Updated...";
				sid = request.getParameter("sid");
			}
			break;

		case "read":
			studentDTO = readStudent(request, response);
			if (studentDTO != null) {
				currSession.setAttribute("student", studentDTO);
				response.sendRedirect("../showStudentData.jsp");
			} else if (msg.equals("No Data")) {
				msg = "consumed";
				studentDTO = new StudentDTO();
				studentDTO.setId(Integer.parseInt(request.getParameter("id")));

				currSession.setAttribute("student", studentDTO);
				response.sendRedirect("../idNotExist.jsp");
			} else if (msgForId.equals("invalid")) {
				msgForId = "consumed";
				response.sendRedirect(request.getHeader("referer"));
			}
			break;

		case "update":
			studentDTO = readStudent(request, response);
			if (studentDTO != null) {
				currSession.setAttribute("student", studentDTO);
				response.sendRedirect("../showStudentDataInForm.jsp");
			} else if (msg.equals("No Data")) {
				msg = "consumed";
				studentDTO = new StudentDTO();
				studentDTO.setId(Integer.parseInt(request.getParameter("id")));

				currSession.setAttribute("student", studentDTO);
				response.sendRedirect("../idNotExist.jsp");
			} else if (msgForId.equals("invalid")) {
				msgForId = "consumed";
				response.sendRedirect(request.getHeader("referer"));
			}
			break;
		}

		if (flag) {
			flag = false;
			printingMessage = "Row with id " + sid + printingMessage;

			request.getSession().setAttribute("message", printingMessage);
			response.sendRedirect("../successfull.jsp");
		}

	}

	private String findMaxId() {
		IStudentService serviceObject = ServiceFactory.getStudentServiceObject();
		return serviceObject.getMaxId();
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

		return flag;
	}

	private StudentDTO readStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");

		if (id.equals("")) {
			msgForId = "invalid";
			return null;
		}

		StudentDTO studentDTO = new StudentDTO();

		try {
			studentDTO.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Number Id.....");

			response.sendRedirect(request.getHeader("referer"));
			return null;
		}

		if (studentDTO != null) {
			IStudentService studentServiceObject = ServiceFactory.getStudentServiceObject();
			studentDTO = studentServiceObject.readStudent(studentDTO);
			msg = "No Data";
		}

		return studentDTO;
	}

	public boolean deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if (id.equals("")) {
			msgForId = "invalid";
			return false;
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
		} else {
			try {
				response.sendRedirect(request.getHeader("referer"));
			} catch (IOException e) {
				e.printStackTrace();
			}
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
