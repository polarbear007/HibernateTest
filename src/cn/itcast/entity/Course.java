package cn.itcast.entity;

import java.util.HashSet;
import java.util.Set;

public class Course {
	private Integer cid;
	private String cname;
	private String teacher;
	private Set<Student> studentSet = new HashSet<>();

	public Course() {
		super();
	}

	public Course(Integer cid, String cname, String teacher, Set<Student> studentSet) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.teacher = teacher;
		this.studentSet = studentSet;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public Set<Student> getStudentSet() {
		return studentSet;
	}

	public void setStudentSet(Set<Student> studentSet) {
		this.studentSet = studentSet;
	}
}
