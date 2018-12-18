package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.User;

// 本意是想模拟多个线程共享一个session 对象的，但是好像不怎么成功！ 
// 没有报错，但也没有出现并发修改的问题，甚至可以说连sql 语句都没有发送成功。
// 可能是因为一个session 对象无法同时开启两次事务吧。
//不知道内部的原理，先这样子吧

class MyRunnable implements Runnable{
	private Session session;

	public MyRunnable(Session session) {
		super();
		this.session = session;
	}

	@Override
	public void run() {
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, 8);
		System.out.println(user.getUsername());
		user.setUsername(Thread.currentThread().getName());
		transaction.commit();
		session.close();
	}
	
}

public class HibernateDemo8 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	@Test
	public void testSession() {
		// 首先，我们要创建一个  session 对象
		Session session = factory.openSession();
		
		// 然后创建两个线程，并让这两个线程共享session 对象
		MyRunnable mr = new MyRunnable(session);
		Thread t1 = new Thread(mr, "线程1");
		Thread t2 = new Thread(mr, "线程2");
		
		t1.start();
		t2.start();
	}
	
	@Test
	public void testSession2() {
		Session session = factory.openSession();
		System.out.println(session.get(User.class, 12));
		
		session.close();
	}
}
