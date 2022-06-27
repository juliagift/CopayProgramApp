package org.juliagift.copaydrugprogram.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;

	@NotNull
	private String gender;

	@NotNull
	@Column(name = "phone_number")
	private String phoneNumber;

	@NotNull
	private String address1;

	private String address2;

	@NotNull
	private String city;

	@NotNull
	private String state;

	@NotNull
	private String zip5;

	private String zip4;
	
	@OneToOne(targetEntity = Login.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "login_id")
	private Login login;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	public User(@NotNull String firstName, @NotNull String lastName, @NotNull Date dob, @NotNull String gender,
			@NotNull String phoneNumber, @NotNull String address1, String address2, @NotNull String city,
			@NotNull String state, @NotNull String zip5, String zip4, Login login, Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip5 = zip5;
		this.zip4 = zip4;
		this.login = login;
		this.roles = roles;
	}
}
