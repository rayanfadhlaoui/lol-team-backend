package com.lolteam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.dao.LolTeamUserDao;
import com.lolteam.entities.LolTeamUserEntity;

@RestController
@RequestMapping(value="/sessions")
public class SessionControler {
	
	@Autowired
	LolTeamUserDao userDao;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public LolTeamUserEntity create(@RequestBody  LolTeamUserEntity user) {
		LolTeamUserEntity result = userDao.getUserFromUsernameAndPassord(user).orElse(new LolTeamUserEntity(-1l));
		return result;
	}
}