package org.juliagift.copaydrugprogram.service;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface PharmacyService {
	
	// Return a pharmacy by a given pharmacy ID.
	Pharmacy findPharmacyById(Long id) throws NotFoundException;
	
	// Return all the pharmacies in the database.
	List<Pharmacy> getAllPharmacies() throws NotFoundException;
	
	// Write a pharmacy to the database.
	Pharmacy savePharmacy(Pharmacy pharmacy);
}
