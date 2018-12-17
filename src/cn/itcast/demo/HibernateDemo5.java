package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.Card;
import cn.itcast.entity.Person;

// 这个类就专门用来演示 一对一关系：
// 一个人只能有一张卡
// 一张卡只能对应一个人
// 注意：因为是一对一的关系，所以其实并没有什么外键不外键的， person 的主键值只要和 card 的主键值一样就是对应关系
// 这种对应关系我们一般很少用，了解一下就好了

public class HibernateDemo5 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	// 演示保存数据
	@Test
	public void testOneToOne() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		// 创建一个 person 对象
		Person p1 = new Person();
		p1.setName("Jack");
		session.save(p1);
		
		// 创建一个 card 对象
		Card c1 = new Card();
		// 设置关联对象 p1
		c1.setPerson(p1);
		// 设置用户名和密码
		c1.setUsername("Jacky007");
		c1.setPassword("123456");
		session.save(c1);
		
		transaction.commit();
		session.close();
	}
	
	// 演示通过person 对象获取关联对象 card
	@Test
	public void testOneToOne2() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Person p = session.get(Person.class, 1);
		System.out.println(p.getName());
		System.out.println(p.getCard().getUsername());
		System.out.println(p.getCard().getPassword());
		
		transaction.commit();
		session.close();
	}
	
	// 演示通过  card 对象获取关联对象 person
	@Test
	public void testOneToOne3() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Card card = session.get(Card.class, 1);
		System.out.println(card.getUsername());
		System.out.println(card.getPassword());
		System.out.println(card.getPerson().getName());
		
		transaction.commit();
		session.close();
	}
	
	// 演示通过删除 person 对象关联删除 card 对象
	@Test
	public void testOneToOne4() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Person p = session.get(Person.class, 1);
		session.delete(p);
		
		transaction.rollback();
		session.close();
	}
	
}
