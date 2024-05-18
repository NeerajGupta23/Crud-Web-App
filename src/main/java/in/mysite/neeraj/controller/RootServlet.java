package in.mysite.neeraj.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.factory.ServiceFactory;
import in.mysite.neeraj.service.IStudentService;
import in.mysite.neeraj.vo.StudentVO;

@WebServlet(urlPatterns = { "*.perform" }, loadOnStartup = 1)
public class RootServlet extends HttpServlet implements IStudentController {
	
	private static final long serialVersionUID = 1L;
	private String deleteId = null;
	
	@Resource(name = "JNDI")
	public static DataSource dataSource;

	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer requestURL = request.getRequestURL();
		String type = requestURL.substring(38);
		StudentDTO studentDTO = null;

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
				if (studentDTO.getName() == null && studentDTO.getAge() == null && studentDTO.getAddress() == null) {
					idNotExist(request, response, studentDTO.getId());
				} else {
					presentInUI(request, response, studentDTO);
				}
			}
			break;

		case "update":
			studentDTO = readStudent(request, response);
			if (studentDTO != null) {
				if (studentDTO.getName() == null && studentDTO.getAge() == null && studentDTO.getAddress() == null) {
					idNotExist(request, response, studentDTO.getId());
				} else {
					presentInForm(request, response, studentDTO);
				}
			}
			break;

		case "updateIt":
			updateStudent(request, response);
			break;
		}

	}

	private void idNotExist(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<body>");
		out.println("<style>");
		out.println("body { " + "	   background-color: rgb(252, 255, 96);" + "    color: rgb(88, 0, 0);"
				+ "    border: 5px solid rgb(255, 88, 116);" + "    border-radius: 10px;" + "    text-align: center;"
				+ "	   padding-top: 5%;" + "    font-size: 35px; " + "	   margin: 0%;" + " }");

		out.println("        #back {\r\n" + "            font-size: 20px;\r\n" + "            padding: 10px;\r\n"
				+ "            padding-right: 12px;\r\n" + "            border-radius: 10px;\r\n"
				+ "            background-color: #a7d4ff;\r\n" + "            text-decoration: none;" + "        }\r\n"
				+ "\r\n" + "        #back:hover {\r\n" + "            background-color: #67b6ff;\r\n" + "        }\r\n"
				+ "\r\n" + "        #back:focus {\r\n"
				+ " color: rgb(88, 0, 0);           border: 3px solid #166cff;\r\n"
				+ "            border-top: 4px solid #166cff;\r\n" + "            border-left: 4px solid #166cff;\r\n"
				+ "        }");
		out.println("</style>");

		out.println("<h3>No Data is Associated with id :: " + id + "</h3>");
		out.println("<br><br><br>");
		out.println("<a id='back' href='" + request.getHeader("referer") + "'>Back</a>");
		out.println("</body>");
		out.flush();
		out.close();
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

	private void presentInForm(HttpServletRequest request, HttpServletResponse response, StudentDTO studentDTO)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<body>");
		out.println("<style>");
		out.println("body { " + "	   background-color: rgb(252, 255, 96);" + "    color: rgb(88, 0, 0);"
				+ "    border: 5px solid rgb(255, 88, 116);" + "    border-radius: 10px;" + "    text-align: center;"
				+ "	   padding-top: 5%;" + "    font-size: 35px; " + "	   margin: 0%;" + " }");

		out.println("th, td { " + "    text-align: center;" + "	   padding: 10px;" + "    font-size: 30px; " + " }");

		out.println("        #update {\r\n" + "            font-size: 20px;\r\n" + "            padding: 10px;\r\n"
				+ "            margin-top: 40px;\r\n" + "            padding-right: 12px;\r\n"
				+ "            border-radius: 10px;\r\n" + "            background-color: #a7d4ff;\r\n"
				+ "            text-decoration: none;" + "        }\r\n" + "\r\n" + "        #update:hover {\r\n"
				+ "            background-color: #67b6ff;\r\n" + "        }\r\n" + "\r\n"
				+ "        #update:focus {\r\n" + " color: rgb(88, 0, 0);           border: 3px solid #166cff;\r\n"
				+ "            border-top: 4px solid #166cff;\r\n" + "            border-left: 4px solid #166cff;\r\n"
				+ "        }");
		out.println("</style>");

		out.println("<h3>Data Associated with id :: " + studentDTO.getId() + "</h3>");
		out.println("<form action='./updateIt.perform' method='post'>");
		out.println("<table align='center' >");
		out.println("<tr><th>Name </th><td><input type='text' id='sname' name='sname' value='" + studentDTO.getName()
				+ "'></td></tr>");
		out.println("<tr><th>Age </th><td><input type='number' id='sage' name='sage' value='" + studentDTO.getAge()
				+ "'></td></tr>");
		out.println("<tr><th>Address </th><td><input type='text' id='saddress' name='saddress' value='"
				+ studentDTO.getAddress() + "'></td></tr>");
		out.println("<input type='hidden' id='sid' name='sid' value='" + studentDTO.getId() + "' hidden>");
		out.println(
				"<input type='hidden' id='urlLoc' name='urlLoc' value='" + request.getHeader("referer") + "' hidden>");
		out.println("<tr><th></th><td><input type='submit' id='update' value='Update'></td></tr>");
		out.println("</table>");
		out.println("</body>");
		out.flush();
		out.close();
	}

	private void presentInUI(HttpServletRequest request, HttpServletResponse response, StudentDTO studentDTO)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<body>");
		out.println("<style>");
		out.println("body { " + "	   background-color: rgb(252, 255, 96);" + "    color: rgb(88, 0, 0);"
				+ "    border: 5px solid rgb(255, 88, 116);" + "    border-radius: 10px;" + "    text-align: center;"
				+ "	   padding-top: 5%;" + "    font-size: 35px; " + "	   margin: 0%;" + " }");

		out.println("th, td { " + "    text-align: center;" + "	   padding: 10px;" + "    font-size: 30px; " + " }");

		out.println("        #back {\r\n" + "            font-size: 20px;\r\n" + "            padding: 10px;\r\n"
				+ "            padding-right: 12px;\r\n" + "            border-radius: 10px;\r\n"
				+ "            background-color: #a7d4ff;\r\n" + "            text-decoration: none;" + "        }\r\n"
				+ "\r\n" + "        #back:hover {\r\n" + "            background-color: #67b6ff;\r\n" + "        }\r\n"
				+ "\r\n" + "        #back:focus {\r\n"
				+ " color: rgb(88, 0, 0);           border: 3px solid #166cff;\r\n"
				+ "            border-top: 4px solid #166cff;\r\n" + "            border-left: 4px solid #166cff;\r\n"
				+ "        }");
		out.println("</style>");

		out.println("<h3>Data Associated with id :: " + studentDTO.getId() + "</h3>");
		out.println("<table align='center'>");
		out.println("<tr>" + "<th>Name </th>" + "<th>Age </th>" + "<th>Address </th>" + "</tr>");
		out.println("<tr>" + "<td>" + studentDTO.getName() + "</td>" + "<td>" + studentDTO.getAge() + "</td>" + "<td>"
				+ studentDTO.getAddress() + "</td>" + "</tr>");
		out.println("</table>");
		out.println("<br><br><br><br>");
		out.println("<a id='back' href='" + request.getHeader("referer") + "'>Back</a>");
		out.println("</body>");
		out.flush();
		out.close();
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
		deleteId = id;

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
				idNotExist(request, response, Integer.parseInt(deleteId));
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
