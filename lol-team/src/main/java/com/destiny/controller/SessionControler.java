package com.destiny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.destiny.dao.DestinyUserDao;
import com.destiny.entities.general.DestinyUser;

@RestController
@RequestMapping(value="/sessions")
public class SessionControler {
	
	@Autowired
	DestinyUserDao userDao;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody DestinyUser generate(@RequestBody DestinyUser user) {
		return userDao.getUserFromUsernameAndPassord(user);
	}

}