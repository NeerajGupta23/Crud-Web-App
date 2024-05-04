package in.mysite.neeraj.controller;

import in.mysite.neeraj.dto.StudentDTO;
import in.mysite.neeraj.vo.StudentVO;

public interface IStudentController {
	StudentDTO putIntoStudentDTO(StudentVO student);
}
