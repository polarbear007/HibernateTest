package cn.itcast.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.entity.Course;
import cn.itcast.entity.User;

public class HibernateDemo10 {
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
	
	// 使用 Criteria 查询全表
	@Test
	public void test1() {
		// 添加 class 对象
		List<User> list = session.createCriteria(User.class).list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	@Test
	public void test2() {
		// 添加一个po类的全路径名
		List<User> list = session.createCriteria("cn.itcast.entity.User").list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 添加  userId = 10 的查询条件
	@Test
	public void test3() {
		User user = (User)session.createCriteria(User.class)
									.add(Restrictions.eq("userId", 10))
									.uniqueResult();
		System.out.println(user);
	}
	
	// 添加 userId > 10 and username like %o% 查询条件
	// 注意： 如果我们使用 like()  方法时不指定 MatchMode，则需要自己指定通配符 % 
	//				MatchMode.ANYWHERE   ----->  %xxxx%
	//				MatchMode.START      ----->  %xxxx
	//				MatchMode.END     	 ----->  xxxx%
	//				MatchMode.EXACT      ----->  xxxx, 相当于等号
	@Test
	public void  test4() {
		List<User> list = session.createCriteria(User.class)
											.add(Restrictions.gt("userId", 10))
											.add(Restrictions.like("username", "o", MatchMode.ANYWHERE))
											.list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 添加  userId in (10, 12, 15) 查询条件
	@Test
	public void  test5() {
		List<User> list = session.createCriteria(User.class)
											.add(Restrictions.in("userId", new Object[] {10, 12, 15}))
											.list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 多个查询条件可以相互嵌套
	@Test
	public void tset6() {
		List<User> list = session.createCriteria(User.class)
						.add(Restrictions.and(Restrictions.eq("username", "Tom"), Restrictions.eq("password", "123456")))
						.list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 添加排序
	// 可以通过 Order.desc()  和  Order.asc()  方法来控制排序方向 
	// 【注意】默认是区分大小写的，如果我们不想区分大小写的话，那么可以添加 ignoreCase()  方法
	//        Order 的方法也是支持链式编程的，所以直接添加就可以了。
	@Test
	public void test7() {
		List<User> list = session.createCriteria(User.class)
								.addOrder(Order.desc("username").ignoreCase())
								.list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	// 可以在一个查询中添加多个排序字段
	@Test
	public void tset8() {
		List<User> list = session.createCriteria(User.class)
								.addOrder(Order.desc("username"))
								.addOrder(Order.asc("userId"))
								.list();
		if(list != null && list.size() > 0) {
			for (User user : list) {
				System.out.println(user);
			}
		}
	}
	
	
	// 分组查询
	// 返回值是单行单列的，我们可以直接添加  .uniqueResult(), 然后用  Object 接收
	@Test
	public void test9() {
		Object result = session.createCriteria(User.class)
								.setProjection(Projections.rowCount())
								.uniqueResult();
		System.out.println(result);
	}
	
	// 分组查询
	// 返回值是单行多列的，我们同样可以添加  .uniqueResult() ，然后用 Object[]  接收
	// 其中的 Projections.projectionList()   是指定一个查询列表，里面可以用 add 添加多个字段或者聚合函数
	@Test
	public void test10() {
		Object[] result = (Object[]) session.createCriteria(cn.itcast.entity.Order.class)
									.setProjection(Projections.projectionList()
														.add(Projections.rowCount())
														.add(Projections.avg("price"))
														.add(Projections.sum("price"))
														.add(Projections.min("price"))
														.add(Projections.max("price"))
												)
									.uniqueResult();

		if(result != null) {
			for (Object item : result) {
				System.out.println(item);
			}
		}																
	}
	
	// 分组查询
	// 返回值是多列多行的，那么我们可以添加  .list() 方法，然后用  List<Object[]> 来接收
	//  其中的 Projections.groupProperty()  是指定按哪个字段进行分组的   group by xxx  ，指定那个xxx
	@Test
	public void test11() {
		List<Object[]> result = (List<Object[]>) session.createCriteria(cn.itcast.entity.Order.class)
									.setProjection(Projections.projectionList()
														.add(Projections.property("customer.cid"))
														.add(Projections.rowCount())
														.add(Projections.avg("price"))
														.add(Projections.sum("price"))
														.add(Projections.min("price"))
														.add(Projections.max("price"))
														.add(Projections.groupProperty("customer"))
												)
									.list();

		if(result != null) {
			for (Object[] items : result) {
				for (Object item : items) {
					System.out.println(item);
				}
			}
		}																
	}
	
	// 投影查询（只查询部分字段）
	// 默认情况下，投影查询并不会封装成 po 对象，因为投影查询还可以查聚合函数之类的数据，这些数据跟实体类中的属性并没有一一对应
	// 所以返回的每一行结果都是用 Object[] 来装的
	@Test
	public void test12() {
		List<Object[]> result = session.createCriteria(cn.itcast.entity.Order.class)
			.setProjection(Projections.projectionList()
							.add(Projections.property("oid"))
							.add(Projections.property("price"))
						   )
			.list();
		if(result != null) {
			for (Object[] items : result) {
				for (Object item : items) {
					System.out.println(item);
				}
			}
		}	
	}	
	
	
	// 如果我们想要让投影查询返回的每一行数据自动封装成对象，而不是一个 Object[] 的话，那么就需要自己来指定
	// 封装的策略：
	//		 setResultTransformer(Transformers.aliasToBean(实体类名.class))
	// 【注意】 查询列表每个列都需要设置别名
	@Test
	public void test13() {
		List<cn.itcast.entity.Order> result = session.createCriteria(cn.itcast.entity.Order.class)
			.setProjection(Projections.projectionList()
							.add(Projections.property("oid"), "oid")
							.add(Projections.property("price"), "price")
						   )
			.setResultTransformer(Transformers.aliasToBean(cn.itcast.entity.Order.class))
			.list();
		if(result != null) {
			for (cn.itcast.entity.Order order : result) {
				System.out.println(order);
			}
		}	
	}	
	
	// 关联查询
	// 所谓的关联查询是这样子的： 查询的结果仍然是以最开始的那个 Course 类为主，只不过查询
	// 可以用关联对象的某个属性作为查询条件。 比如下面的查询：使用另一张表的  学生编号 作为查询条件 
	// 【注意】如果你不是以另一张表的某些属性作为查询条件的话，那么完全不需要使用关联查询。
	//        你后期要获取关联对象的值，hibernate 就会自动发送 sql 去获取值了
	//        又或者你把关联对象或者集合设置成不延迟加载，那么查询的时候会自动使用左连接或者子查询获取关联对象的值
	// 【注意】createAlias()  方法本质上跟    createCriteria()  没有什么区别
	// 【注意】 如果关联对象是一个集合，正如下面所示：
	//			Restrictions.eq("stuSet.sid", 2) 这样的条件表示： studentSet 里面只要有一个元素的 sid 值满足条件即可

	@Test
	public void test14() {
		List<Course> list = session.createCriteria(Course.class)
					.createCriteria("studentSet", "stuSet", JoinType.RIGHT_OUTER_JOIN)
					.add(Restrictions.eq("stuSet.sid", 2))
					.list();
		if(list !=null && list.size() > 0) {
			for (Course course : list) {
				System.out.println(course);
			}
		}
	}
}
