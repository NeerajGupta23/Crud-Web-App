package in.mysite.neeraj.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import in.mysite.neeraj.bo.StudentBO;

public class Utility {
	private static SessionFactory factory = null;

	static {
		factory = new Configuration().configure("/in/mysite/neeraj/xml/hibernate.cfg.xml")
				.addAnnotatedClass(StudentBO.class).buildSessionFactory();
	}

	public static Session getSession() {
		return factory.openSession();
	}

}
