package cn.itcast.entity;

public class Order {
	private Integer oid;
	private Double price;
	private Customer customer;

	public Order() {
		super();
	}

	public Order(Integer oid, Double price, Customer customer) {
		super();
		this.oid = oid;
		this.price = price;
		this.customer = customer;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", price=" + price + ", customer=" + customer + "]";
	}

	
}
