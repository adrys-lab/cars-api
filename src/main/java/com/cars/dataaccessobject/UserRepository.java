package com.cars.dataaccessobject;

import org.springframework.stereotype.Repository;

import com.cars.domainobject.user.UserDO;

@Repository
public interface UserRepository extends GlobalRepository<UserDO> {
    UserDO findByUsername(String username);
}
