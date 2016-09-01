package com.skymxc.demo.lession_12_practice;

// default package

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String nick;
	private String photo;
	private String studentId;
	private Integer gender;
	private String phone;
	private String email;
	private String password;
	private String signature;
	private Float balance;
	private Integer goldCoin;
	private Integer type;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String nick, Integer gender, String phone, String password,
			Float balance, Integer goldCoin, Integer type) {
		this.nick = nick;
		this.gender = gender;
		this.phone = phone;
		this.password = password;
		this.balance = balance;
		this.goldCoin = goldCoin;
		this.type = type;
	}

	/** full constructor */
	public User(String nick, String photo, String studentId, Integer gender,
			String phone, String email, String password, String signature,
			Float balance, Integer goldCoin, Integer type) {
		this.nick = nick;
		this.photo = photo;
		this.studentId = studentId;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.signature = signature;
		this.balance = balance;
		this.goldCoin = goldCoin;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Float getBalance() {
		return this.balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Integer getGoldCoin() {
		return this.goldCoin;
	}

	public void setGoldCoin(Integer goldCoin) {
		this.goldCoin = goldCoin;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}