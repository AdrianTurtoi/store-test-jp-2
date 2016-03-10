package com.msys.entity;
 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USERS")

public class User {
	
 	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, Long phoneNo,
			String email, String adress, Long identityNo, Date birtday, byte administrator) {
		super();		
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.adress = adress;
		this.identityNo = identityNo;
		this.birtday = birtday;
		this.administrator = administrator;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "user_id")
	private Long id;

	@NotNull
	@Column(name = "user_name")
	private String userName;

	@NotNull
	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_no")
	private Long phoneNo;

	@NotNull
	@Column(name = "email")
	private String email;

	@Column(name = "adress")
	private String adress;

	@Column(name = "identity_no")
	private Long identityNo;

	@Column(name = "birthday")
	private Date birtday;

	@NotNull
	@Column(name = "administrator")
	private byte administrator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public Long getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(Long identityNo) {
		this.identityNo = identityNo;
	}

	public Date getBirtday() {
		return birtday;
	}

	public void setBirtday(Date birtday) {
		this.birtday = birtday;
	}

	public byte getAdministrator() {
		return administrator;
	}

	public void setAdministrator(byte administrator) {
		this.administrator = administrator;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNo=" + phoneNo + ", email=" + email + ", adress=" + adress
				+ ", identityNo=" + identityNo + ", birtday=" + birtday + ", administrator=" + administrator + "]";
	}

}
