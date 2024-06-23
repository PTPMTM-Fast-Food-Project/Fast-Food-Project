package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.Country;
import com.ecommerce.library.repository.CountryRepository;
import com.ecommerce.library.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.getReferenceById(id);
    }
}
