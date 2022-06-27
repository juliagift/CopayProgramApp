package org.juliagift.copaydrugprogram.service;

import java.util.List;

import org.juliagift.copaydrugprogram.model.Pharmacy;
import org.juliagift.copaydrugprogram.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PharmacyServiceImpl implements PharmacyService {
	
	@Autowired
	private PharmacyRepository pharmacyRepository;

	// Return a pharmacy by a given pharmacy ID.
	@Override
	public Pharmacy findPharmacyById(Long id) throws NotFoundException {
		return pharmacyRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}

	// Return all the pharmacies in the database.
	@Override
	public List<Pharmacy> getAllPharmacies() throws NotFoundException {
		return pharmacyRepository.findAll();
	}

	// Write a pharmacy to the database.
	@Override
	public Pharmacy savePharmacy(Pharmacy pharmacy) {	
		return pharmacyRepository.save(pharmacy);
	}
}
