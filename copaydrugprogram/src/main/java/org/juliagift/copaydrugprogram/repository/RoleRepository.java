package org.juliagift.copaydrugprogram.repository;

import org.juliagift.copaydrugprogram.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	// Returns a role, given the role name.
	Role findByName(String name);
}
