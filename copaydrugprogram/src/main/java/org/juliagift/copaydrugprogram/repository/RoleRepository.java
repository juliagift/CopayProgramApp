package org.juliagift.copaydrugprogram.repository;

import org.juliagift.copaydrugprogram.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Query(value = "FROM Role WHERE name = :name")
	Role findByName(String name);
	

}
