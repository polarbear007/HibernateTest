package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.User;

// 测试二级缓存的存在 
public class HibernateDemo9 {
	@Test
	public void testSecondCache() throws Exception {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, 10);
		System.out.println(user);
		transaction.commit();
		session.close();
		
		Session session2 = factory.openSession();
		Transaction transaction2 = session2.beginTransaction();
		User user2 = session2.get(User.class, 10);
		System.out.println(user2 == user);
		transaction2.commit();
		session2.close();
		
	}
}
