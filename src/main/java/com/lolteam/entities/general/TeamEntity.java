package com.lolteam.entities.general;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "TeamEntity")
@Table(name = "team")
@NamedQueries({
	@NamedQuery(name = "team.findTeamByUserId", query = "SELECT t FROM TeamEntity t WHERE t.user.id = :userId"),
})
public class TeamEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id")
	private LolTeamUserEntity user;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)  
	@JoinTable(name="team_summoner", joinColumns=@JoinColumn(name="team_id"), inverseJoinColumns=@JoinColumn(name="summoner_id"))
	private Set<SummonerEntity> summoners = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LolTeamUserEntity getUser() {
		return user;
	}

	public void setUser(LolTeamUserEntity user) {
		this.user = user;
	}

	public Set<SummonerEntity> getSummoners() {
		return summoners;
	}

	public void setSummoners(Set<SummonerEntity> summoners) {
		this.summoners = summoners;
	}

	public void addSummoner(SummonerEntity summoner) {
		summoners.add(summoner);
	}
}
