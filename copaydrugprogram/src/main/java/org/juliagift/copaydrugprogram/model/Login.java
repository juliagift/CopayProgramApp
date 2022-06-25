package org.juliagift.copaydrugprogram.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table (name = "login")
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_id")
	private Long loginId;
	
	@NotEmpty(message = "Please enter your email.")
	private String email;
	
	@NotEmpty(message = "Please enter your password.")
	@Column(name = "pass")
	private String password;

	public Login(String email,String password) {
		this.email = email;
		this.password = password;
	}

	
}
