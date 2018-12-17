package cn.itcast.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import cn.itcast.entity.User;

public class HibernateDemo2 {
	private SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	// 插入数据
	@Test
	public void testSave() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = new User();
		user.setUsername("Jack");
		user.setPassword("123456abc");
		
		session.save(user);
		
		transaction.commit();
		
		session.close();
	}
	
	// 根据主键id 获取一个对象（只有一行）
	@Test
	public void testGet() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 7);
		
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		transaction.commit();
		
		session.close();
	}
	
	// 根据主键id 获取一个对象（只有一行）
	// 目前来说，我们还看不出 get 和 load 之间的区别 
	@Test
	public void testLoad() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = session.load(User.class, 7);
		
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		transaction.commit();
		session.close();
	}
	
	// 更新：  修改 id = 7 的记录的 password 值为 123456
	@Test
	public void testUpdate() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = new User();
		// id 必须指定，不然不知道你要更新的是哪一行的数据
		user.setUserId(7);
		// 【注意】虽然我们不修改username ， 但是这里还是必须要指定一下 username ，不然更新完以后
		// 就会把原来的username 用 null 替换
		user.setUsername("Jack");
		// 设置要修改的密码
		user.setPassword("123456");
		// 使用新创建的这个user对象的值去替换数据库里面 id = 7 的那行记录的值
		session.update(user);
		
		transaction.commit();
		session.close();
	}
	
	// 更新的另一个思路： 先查询，再更新
	// 前面的更新思路，我们必须创建一个新的对象，然后用新的对象值替换原来的数据库里面的值
	// 这里有一个问题即：我们只是要修改一下 password 这一列值，却必须指定所有其他的成员变量值
	// 这个案例中，user 对象很简单，只有三列，所以可能没感觉到有什么问题，但是如果对象很复杂，有10列，但是你只想更新
	// 其中的某一列，这样子就很难受了； 所以使用先查询，再更新的思路，就不会有这么多的问题了！！
	@Test
	public void testUpdate2() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = session.get(User.class, 7);
		// 先查询以后，我们就可以获取id=7这一行所有列的值
		// 然后我们只需要修改我们需要修改的这一列即可
		user.setPassword("123456");
		
		// 因为get以后，  id=7 这一行的数据已经保存在一级缓存中了（持久态），所以我们修改了 user 对象以后
		// 不需要刻意去执行 update方法， hibernate 会自动比较一级缓存跟 快照的差别，自动更新
		transaction.commit();
		session.close();
	}
	
	// 删除
	@Test
	public void testDelete() {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		User user = new User();
		user.setUserId(7);
		session.delete(user);
		
		transaction.commit();
		session.close();
	}
	
}
