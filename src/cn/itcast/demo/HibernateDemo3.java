package cn.itcast.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.User;

public class HibernateDemo3 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	// 为了测试 hql ，我们先添加一些数据
	@Test
	public void addUser() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		session.save(new User("Rose", "123456"));
		session.save(new User("Jack", "123456"));
		session.save(new User("Tom", "123456"));
		session.save(new User("Jerry", "123456"));
		session.save(new User("Eric", "123456"));
		session.save(new User("Lily", "123456"));
		session.save(new User("Amy", "123456"));
		session.save(new User("John", "123456"));
		session.save(new User("Adolf", "123456"));
		
		transaction.commit();
		session.close();
	}
	
	// 使用 hql 查询所有的数据
	@Test
	public void testCreateQuery() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<User> list = session.createQuery("from User").list();
		if(list != null) {
			for (User user : list) {
				System.out.println(user.getUserId()+ "---" +user.getUsername() + "---" + user.getPassword());
			}
		}
		
		transaction.commit();
		session.close();
	}
	
	// 使用分页查询
	@Test
	public void testPageQuery() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<User> list = session.createQuery("from User").setFirstResult(3).setMaxResults(3).list();
		if(list != null) {
			for (User user : list) {
				System.out.println(user.getUserId()+ "---" +user.getUsername() + "---" + user.getPassword());
			}
		}
		
		transaction.commit();
		session.close();
	}
	
	// 使用条件查询
	@Test
	public void testConditionQuery(){
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<User> list = session.createQuery("from User where userId > 12").list();
		if(list != null) {
			for (User user : list) {
				System.out.println(user);
			}
		}
		
		transaction.commit();
		session.close();
	}
	
	// 使用条件模糊查询
	@Test
	public void testLikeQuery() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<User> list = session.createQuery("from User where username like '%a%'").list();
		if(list != null) {
			for (User user : list) {
				System.out.println(user);
			}
		}
		
		transaction.commit();
		session.close();
	}
	
	// 使用别名
	@Test
	public void testAlias() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<User> list = session.createQuery("from User u where u.userId between 11 and 16").list();
		if(list != null) {
			for (User user : list) {
				System.out.println(user);
			}
		}
		
		transaction.commit();
		session.close();
	}
	
	// 使用 hql 查询出单行的结果集
	@Test
	public void testUniqueResult() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = (User)session.createQuery("from User where username = 'Jack'").uniqueResult();
		System.out.println(user);
		
		transaction.commit();
		session.close();
	}
	
	// 使用hql 执行 prepareStatement,防止sql 注入
	@Test
	public void testPrepareStatement() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		//【注意】  如果我们想要给预编译的sql 绑定参数，参数的索引是从0开始的
		List<User> list = session.createQuery("from User where userId in(?, ?)")
								.setInteger(0, 11)
								.setInteger(1, 12)
								.list();
		if(list != null) {
			for (User user : list) {
				System.out.println(user);
			}
		}
		
		transaction.commit();
		session.close();
	}
}
