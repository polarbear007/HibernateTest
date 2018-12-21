package cn.itcast.demo;

import java.util.List;

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
		c.setName("Rose1");
		
		
		Order o1 = new Order();
		o1.setPrice(102.52);
		
		Order o2 = new Order();
		o2.setPrice(1002.52);
		
		Order o3 = new Order();
		o3.setPrice(10002.52);
		
		// 设置关联关系
		// 客户关联订单
		// 注意：关联的时候不要双向关联，【最好是通过多方去关联一方】
		// 因为外键列在多方,你通过多方关联一方的时候，其实就是明确外键值，插入的时候直接完事儿
		// 反过来，如果你通过一方去关联多方的话，那么你插入一方时不能顺便插入另一张表的外键列的值, 你还得根据
		// orderSet 集合里面的 order 对应的主键id 去多方表更新数据
//		c.getOrderSet().add(o1);
//		c.getOrderSet().add(o2);
//		c.getOrderSet().add(o3);
		
		// 订单关联客户
		o1.setCustomer(c);
		o2.setCustomer(c);
		o3.setCustomer(c);
		
		// 保存对象
		// 保存对象的顺序也是有讲究的：
		// 如果我们先保存一方，那么外键值也就是一方的主键值就明确了，这时候如果你又是通过多方去关联一方
		// 那么在插入order 对象的时候，外键值就是明确的，不需要额外的更新语句
		// 反过来，如果我们先保存多方，这时因为一方还没有保存，主键值还没有产生，所以多方表的外键值只能暂时先写 null
		// 等一方插入数据后，主键值有了，我们再根据关联关系，去更新多方表的外键值
		
		// 【注意】 如果我们遵循这个规则的话,即： 使用多方去关联一方，然后保存对象的时候先保存一方再保存多方
		// 那么本次的插入只会产生四个 Insert 语句，根本不会产生额外的 update 语句
		session.save(c);
		
		session.save(o1);
		session.save(o2);
		session.save(o3);
		
		
		
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
	
	// 测试一下用关联对象的值作为排序的条件
	@Test
	public void testOneToMany3() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<Order> list = session.createQuery("from Order o order by o.customer.name").list();
		if(list != null) {
			for (Order order : list) {
				System.out.println(order.getOid() + "---" + order.getPrice() + "---" + order.getCustomer().getName());
			}
		}
		
		transaction.commit();
		session.close();
	}
}
