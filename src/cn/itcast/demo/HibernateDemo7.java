package cn.itcast.demo;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.Course;
import cn.itcast.entity.Student;

// 演示：多对多关系
// 一个学生可以选多门课程
// 一门课程可以有多个学生一起上
// 【注意】 多对多会产生中间表
//        多对多的关系可以转化成两个一对多的关联
//        学生----中间表      ===》 一对多
//        课程----中间表      ===》 一对多
public class HibernateDemo7 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	// 保存数据
	@Test
	public void testManyToMany() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		// 先创建对象，并保存到数据库
		Student stu1 = new Student();
		stu1.setName("小明");
		session.save(stu1);
		
		Student stu2 = new Student();
		stu2.setName("小花");
		session.save(stu2);
		
		Course c1 = new Course();
		c1.setCname("语文");
		c1.setTeacher("老王");
		session.save(c1);
		
		Course c2 = new Course();
		c2.setCname("数学");
		c2.setTeacher("老李");
		session.save(c2);
		
		Course c3 = new Course();
		c3.setCname("英语");
		c3.setTeacher("老刘");
		session.save(c3);
		
		// 设置关联关系
		// 【注意】： 先要不要双向关联，一对一或者一对多的时候，设置关联只会产生update 语句
		// 但是在多对多的时候，因为会产生中间表，关联一次就会向中间表插入一条新数据
		// 如果双向关联的话，那么就会往中间表插入两条相同的数据，从而造成主键重复的问题
		// 我们这里统一：由学生去关联课程
		stu1.getCourseSet().add(c1);
		stu1.getCourseSet().add(c2);
		stu1.getCourseSet().add(c3);
		
		stu2.getCourseSet().add(c2);
		stu2.getCourseSet().add(c3);
		
		transaction.commit();
		session.close();
	}
	
	// 读取关联数据
	@Test
	public void testManyToMany2() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Student stu = session.get(Student.class, 1);
		System.out.println(stu.getName());
		Set<Course> set = stu.getCourseSet();
		for (Course course : set) {
			System.out.println(course.getCname() + "----" + course.getTeacher());
		}
		
		transaction.commit();
		session.close();
	}
}
