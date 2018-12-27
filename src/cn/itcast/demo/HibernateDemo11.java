package cn.itcast.demo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.User;

public class HibernateDemo11 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	Session session = null;
	Transaction transaction = null;
	
	@Before
	public void before() {
		session = factory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void after() {
		transaction.commit();
		session.close();
		factory.close();
	}
	
	// 直接通过一个 example 对象，传入多个等值查询条件
	@Test
	public void test1() {
		User user = new User();
		user.setUsername("tom");
		user.setPassword("123456");
		// 通过 user 对象去创建 example 对象
		Example example = Example.create(user);
		
		User result = (User) session.createCriteria(User.class).add(example).uniqueResult();
		
		if(result != null) {
			System.out.println(result);
		}
	}
	
	// 直接通过一个 po 对象，传入多个模糊查询条件
	@Test
	public void test2() {
		User user = new User();
		user.setUsername("tom");
		user.setPassword("123456");
		// 通过 user 对象去创建 example 对象
		Example example = Example.create(user).enableLike(MatchMode.ANYWHERE);
		
		User result = (User) session.createCriteria(User.class).add(example).uniqueResult();
		
		if(result != null) {
			System.out.println(result);
		}
	}
}
