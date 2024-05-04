package in.mysite.neeraj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.vo.StudentVO;

public interface IStudentController {
	boolean insertStudent(HttpServletRequest request, HttpServletResponse response);
	boolean deleteStudent(HttpServletRequest request, HttpServletResponse response);
}
