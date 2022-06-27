package org.juliagift.copaydrugprogram.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table (name = "claim")
public class Claim {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "claim_id")
	private Long claimId;
	
	@NotNull
	@Column(name = "txn_status")
	private String status;
	
	@NotNull
	@Column(name = "drug_cost_at_claim")
	private Double drugCostAtClaim;
	
	@NotNull
	@Column(name = "patient_payment")
	private Double patientPayment;
	
	@NotNull
	@Column(name = "manufacturer_payment")
	private Double manufacturerPayment;
	
	@NotNull
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;
	
	@OneToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "card_id")
	private Card card;
	
	@OneToOne(targetEntity = Pharmacy.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "pharmacy_id")
	private Pharmacy pharmacy;

}
