package in.mysite.neeraj.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@NamedQuery(name = "HQL_FIND_MAX_ID", query = "select max(id) from StudentBO")
public class StudentBO {
	@Id
	@Column(name = "sid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "sname", length = 20)
	String name;

	@Column(name = "sage")
	Integer age;

	@Column(name = "saddress", length = 20)
	String address;

	@Column(name = "status", length = 30)
	String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
