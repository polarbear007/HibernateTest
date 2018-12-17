package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.User;

public class HibernateDemo4 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	@Test
	public void testSessionCache() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		// 两次查询，因为查询的都是同一个对象，只发送一次sql 语句
		System.out.println("第一次查询：");
		User user1 = session.get(User.class, 11);
		System.out.println("---------------------");
		System.out.println("第二次查询");
		User user2 = session.get(User.class, 11);
		System.out.println("---------------------");
		System.out.println(user1 == user2);
		
		transaction.commit();
		session.close();
	}
	
	// 两个查询分别在两个不同的连接中获取，那么就无法使用同一份session缓存
	// 需要查询两次， 两个 user 对象的地址值也不相同
	@Test
	public void testSessionCache2() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		System.out.println("第一次查询：");
		User user1 = session.get(User.class, 11);
		System.out.println("---------------------");
		
		transaction.commit();
		session.close();
		
		Session session2 = factory.openSession();
		Transaction transaction2 = session2.beginTransaction();
		System.out.println("第二次查询");
		User user2 = session2.get(User.class, 11);
		System.out.println("---------------------");
		
		System.out.println(user1 == user2);
		
		transaction2.commit();
		session2.close();
	}
	
	// 我们总共做了4次更新，更新的是同一个对象
	// 因为有session缓存，我们四次更改其实都是操作 session 缓存里面的对象
	// 最后commit 的时候，会刷出缓存，跟快照比较，如果跟快照数据不一致，就会执行更新
	// 注意： 只拿 session 缓存里面最新的数据跟快照比较，因此，虽然我们执行了四次update，但是只执行一个update语句
	@Test
	public void testSessionCache3() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 12);
		user.setUsername("张三");
		session.update(user);
		
		user.setUsername("李四");
		session.update(user);
		
		user.setUsername("王五");
		session.update(user);
		
		user.setUsername("赵六");
		session.update(user);
		
		transaction.commit();
		session.close();
	}
}
