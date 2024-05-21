package in.mysite.neeraj.repository;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import in.mysite.neeraj.bo.StudentBO;
import in.mysite.neeraj.util.Utility;

public class StudentRepositoryImpl implements IStudentRepository {

	@Override
	public boolean insertStudent(StudentBO student) {
		Transaction transaction = null;
		Session session = null;
		Boolean flag = false;

		try {
			session = Utility.getSession();
			transaction = session.beginTransaction();

			session.save(student);
			flag = true;

		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transaction != null) {
				if (flag) {
					transaction.commit();
				} else {
					transaction.rollback();
				}
			}

			if (session != null) {
				session.close();
			}
		}

		return flag;
	}

	@Override
	public boolean deleteStudent(StudentBO student) {
		Transaction transaction = null;
		Session session = null;
		Boolean flag = false;
		StudentBO bo = null;

		try {
			session = Utility.getSession();
			transaction = session.beginTransaction();

			bo = readStudent(student);
			if (bo == null) {
				// data is not present
				return false;
			}

			// data present and below code will remove data
			session.delete(student);
			flag = true;

		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transaction != null) {
				if (flag) {
					transaction.commit();
				} else {
					transaction.rollback();
				}
			}

			if (session != null) {
				session.close();
			}
		}

		return flag;
	}

	@Override
	public StudentBO readStudent(StudentBO student) {
		Session session = null;

		try {
			session = Utility.getSession();
			student = session.get(StudentBO.class, student.getId());

		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (session != null) {
				session.close();
			}
		}

		return student;
	}

	@Override
	public boolean updateStudent(StudentBO student) {
		Transaction transaction = null;
		Session session = null;
		Boolean flag = false;

		try {
			session = Utility.getSession();
			transaction = session.beginTransaction();

			session.saveOrUpdate(student);
			flag = true;

		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transaction != null) {
				if (flag) {
					transaction.commit();
				} else {
					transaction.rollback();
				}
			}

			if (session != null) {
				session.close();
			}
		}

		return flag;
	}

	@Override
	public String getMaxId() {
		Session session = null;
		Query query = null;
		Integer MaxId = null;
		List<Integer> list = null;

		try {
			session = Utility.getSession();
			query = session.getNamedQuery("HQL_FIND_MAX_ID");
			list = query.getResultList();

			MaxId = list.get(0);
		} catch (HibernateException he) {
			he.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return String.valueOf(MaxId);
	}

}
