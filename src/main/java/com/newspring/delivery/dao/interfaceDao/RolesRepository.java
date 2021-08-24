package com.newspring.delivery.dao.interfaceDao;

import com.newspring.delivery.entities.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role,Long> {
}
