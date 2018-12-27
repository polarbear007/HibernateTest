package cn.itcast.entity;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private Integer cid;
	private String name;
	private Set<Order> orderSet = new HashSet<>();

	public Customer() {
		super();
	}

	public Customer(Integer cid, String name, Set<Order> orderSet) {
		super();
		this.cid = cid;
		this.name = name;
		this.orderSet = orderSet;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}	
	
}
