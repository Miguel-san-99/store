package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{
    
}
