package org.juliagift.copaydrugprogram.service;

import org.juliagift.copaydrugprogram.model.Role;
import org.juliagift.copaydrugprogram.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	// Returns a role, given the role name.
	@Override
	public Role findByName(String name) throws NotFoundException {
		Role role = roleRepository.findByName(name);
		
		if(role == null) {
			throw new NotFoundException();
		}
		return role;
	}
}
