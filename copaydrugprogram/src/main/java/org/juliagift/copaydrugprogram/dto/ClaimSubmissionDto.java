package org.juliagift.copaydrugprogram.dto;

import org.juliagift.copaydrugprogram.model.Pharmacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClaimSubmissionDto {

	private Long pharmacyId;
}
