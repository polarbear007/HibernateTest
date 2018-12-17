package cn.itcast.entity;

public class Card {
	private Integer cid;
	private String username;
	private String password;
	
	// 一张卡只能对应一个人
	private Person person;

	public Card() {
		super();
	}

	public Card(Integer cid, String username, String password, Person person) {
		super();
		this.cid = cid;
		this.username = username;
		this.password = password;
		this.person = person;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
