package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import cn.itcast.entity.User;

public class HibernateDemo1 {
	public static void main(String[] args) {
		User user = new User();
		user.setUsername("Rose");
		user.setPassword("123456");
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		session.save(user);
		
		transaction.commit();
		session.close();
		factory.close();
	}
}
