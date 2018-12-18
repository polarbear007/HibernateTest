package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.Customer;
import cn.itcast.entity.Order;

// 一对多关系：
// 一个客户可以有多个订单（一对多）
// 一个订单只能对应一个客户（多对一）
// 在hibernate 中，一对多、多对一是统一来看的
// 在mybatis 中，一对多、多对一是独立来看的，如果在 mybatis 中， 一个订单只能对应一个客户，这是一对一关系

public class HibernateDemo6 {
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	// 保存关联对象
	// 【注意】： 客户对象和订单对象都要分别保存！！！ 不然会报错！1
	// 因为我们双向关联，所以下面的代码执行了 6 次update 语句，其中3次update 语句其实是多余的
	// 所以我们只要单向关联即可，可以订单关联客户，也可以客户关联订单！！
	@Test
	public void testOneToMany() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Customer c = new Customer();
		c.setName("Jacky");
		session.save(c);
		
		Order o1 = new Order();
		o1.setPrice(10.52);
		
		Order o2 = new Order();
		o2.setPrice(100.52);
		
		Order o3 = new Order();
		o3.setPrice(1000.52);
		
		session.save(o1);
		session.save(o2);
		session.save(o3);
		
		// 设置关联关系
		// 客户关联订单
		c.getOrderSet().add(o1);
		c.getOrderSet().add(o2);
		c.getOrderSet().add(o3);
		
		// 订单关联客户
		o1.setCustomer(c);
		o2.setCustomer(c);
		o3.setCustomer(c);
		
		transaction.commit();
		session.close();
	}
	
	// 演示删除
	// 【注意】如果我们删除一方（主表），那么hibernate 会自动先把从表的相关数据的外键先都设置成 null
	//        然后在删除主表的数据。  在mysql 本身，是不允许直接删主表的数据的。
	@Test
	public void testOneToMany2() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Customer customer = session.get(Customer.class, 1);
		session.delete(customer);
		
		transaction.commit();
		session.close();
	}
}
