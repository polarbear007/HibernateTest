package cn.itcast.entity;

import java.util.HashSet;
import java.util.Set;

public class Student {
	private Integer sid;
	private String name;
	private Set<Course> courseSet = new HashSet<Course>();

	public Student() {
		super();
	}

	public Student(Integer sid, String name, Set<Course> courseSet) {
		super();
		this.sid = sid;
		this.name = name;
		this.courseSet = courseSet;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Course> getCourseSet() {
		return courseSet;
	}

	public void setCourseSet(Set<Course> courseSet) {
		this.courseSet = courseSet;
	}
	
}
