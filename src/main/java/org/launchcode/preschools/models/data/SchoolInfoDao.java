package org.launchcode.preschools.models.data;

import org.launchcode.preschools.models.forms.Admin.SchoolInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SchoolInfoDao extends CrudRepository <SchoolInfo, Integer>{
}
