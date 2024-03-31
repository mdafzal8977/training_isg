package com.isg.referencedata.geography.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isg.referencedata.geography.country.entity.Country;

public interface CountryRepository  extends JpaRepository<Country, Integer>{

}
