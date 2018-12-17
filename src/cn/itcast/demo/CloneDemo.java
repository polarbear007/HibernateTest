package cn.itcast.demo;

import org.junit.Test;

import cn.itcast.entity.User;

// 这个 demo 跟 hibernate 没有太大的关系，只是刚好看到，就测试一下

public class CloneDemo {
	@Test
	public void testClone() throws CloneNotSupportedException {
		User user = new User();
		user.setUserId(1);
		user.setUsername("Rose");
		user.setPassword("123456");
		
		User user2 = user.clone();
		// 克隆出来的对象是一个新的对象，地址值不一样
		System.out.println(user == user2);
		// 成员变量值完全一样
		System.out.println(user.equals(user2));
	}
}
