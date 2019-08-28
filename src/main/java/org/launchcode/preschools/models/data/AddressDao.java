package org.launchcode.preschools.models.data;


import org.launchcode.preschools.models.forms.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AddressDao extends CrudRepository<Address, Integer>{
}
