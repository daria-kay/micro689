package com.darakay.micro689.repo;

import com.darakay.micro689.domain.Partner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends CrudRepository<Partner, Integer> {
    Partner findById(int id);
}
