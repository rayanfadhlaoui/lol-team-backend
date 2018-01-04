package com.destiny.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.destiny.dao.NameDao;
import com.destiny.entities.general.Name;

@Service
public class NamesService {

	private List<Name> listLastName;
	private Map<String, List<Name>> listFirstNameByOrigine = new HashMap<>();

	@Autowired
	private NameDao nameDao;

	public List<Name> getAllFirstNames(String origine) {
		if (listFirstNameByOrigine.get(origine) == null) {
			listFirstNameByOrigine.put(origine, nameDao.getFirstNamesByOrigine(origine));
		}

		return listFirstNameByOrigine.get(origine);
	}

	public List<Name> getAllLastNames() {
		if (listLastName == null) {
			listLastName = nameDao.getAllLastNames();
		}
		return listLastName;
	}

	public void save(Name name) {
		nameDao.save(name);
	}
}
