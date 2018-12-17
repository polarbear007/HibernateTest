package cn.itcast.entity;

public class Person {
	private Integer pid;
	private String name;
	private Card card;

	public Person() {
		super();
	}

	public Person(Integer pid, String name, Card card) {
		super();
		this.pid = pid;
		this.name = name;
		this.card = card;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
