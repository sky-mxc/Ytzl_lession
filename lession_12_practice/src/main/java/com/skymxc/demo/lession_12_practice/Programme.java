package com.skymxc.demo.lession_12_practice;

/**
 * Programme entity. @author MyEclipse Persistence Tools
 */

public class Programme implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer type;
	private String title;
	private Integer lessonPeriod;
	private Float price;
	private String thumbnail;
	private String form;
	private Integer classificationId;
	private String lecturerIds;
	private Integer numberOfStudents;

	// Constructors

	/** default constructor */
	public Programme() {
	}

	/** minimal constructor */
	public Programme(Integer type, String title, Integer lessonPeriod,
			Float price) {
		this.type = type;
		this.title = title;
		this.lessonPeriod = lessonPeriod;
		this.price = price;
	}

	/** full constructor */
	public Programme(Integer type, String title, Integer lessonPeriod,
			Float price, String thumbnail, String form, String lecturerIds) {
		this.type = type;
		this.title = title;
		this.lessonPeriod = lessonPeriod;
		this.price = price;
		this.thumbnail = thumbnail;
		this.form = form;
		this.lecturerIds = lecturerIds;
	}

	// Property accessors

	
	public Integer getClassificationId() {
		return classificationId;
	}

	public Integer getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(Integer numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLessonPeriod() {
		return this.lessonPeriod;
	}

	public void setLessonPeriod(Integer lessonPeriod) {
		this.lessonPeriod = lessonPeriod;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getForm() {
		return this.form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getLecturerIds() {
		return this.lecturerIds;
	}

	public void setLecturerIds(String lecturerIds) {
		this.lecturerIds = lecturerIds;
	}

}