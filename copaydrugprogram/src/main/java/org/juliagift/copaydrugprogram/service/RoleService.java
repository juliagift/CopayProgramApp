package org.juliagift.copaydrugprogram.service;

import org.juliagift.copaydrugprogram.model.Role;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface RoleService {

	// Returns a role, given the role name.
	Role findByName(String name) throws NotFoundException;
}
