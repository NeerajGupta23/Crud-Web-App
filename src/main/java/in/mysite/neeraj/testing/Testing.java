package in.mysite.neeraj.testing;

import in.mysite.neeraj.factory.RepositoryFactory;
import in.mysite.neeraj.util.Utility;

public class Testing {
	public static void main(String[] args) {
		System.out.println(Utility.getSession());
		
		System.out.println(RepositoryFactory.getStudentRepoObject().getMaxId());;
	}
}
