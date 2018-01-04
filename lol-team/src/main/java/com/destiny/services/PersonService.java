package com.destiny.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.destiny.dao.PersonDao;
import com.destiny.entities.general.Person;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Transactional(readOnly=true)
    public List<Person> getAllPersons() {
        return personDao.rechercherPerson();
    }
}