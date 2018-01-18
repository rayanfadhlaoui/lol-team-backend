package com.lolteam.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

//import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lolteam.entities.MatchEntity;
import com.lolteam.services.RiotApiService;

import lol.api.factory.LolApiFactory;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.constant.Platform;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:com/lolteam/applicationcontext.xml")
@TestInstance(Lifecycle.PER_CLASS)
public class TeamStatusSynchronizerControllerTest {

	@Mock
	private RiotApiService riotApiServiceMock;

	@InjectMocks
	private TeamStatusSynchronizerController controller;

	private LolApiFactory lolApiFactory;

	@BeforeAll
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.lolApiFactory = new LolApiFactory();
	}

	@Test
	@DisplayName("Test findRecentTeamMatches returns one result")
	public void testOnCommunMatch() throws RiotApiException {

		mockSummoners();
		mockMatches();

		List<MatchEntity> findRecentTeamMatches = controller.findRecentTeamMatches();
		assertAll(
				() -> assertEquals(1, findRecentTeamMatches.size()), 
				() -> assertEquals(5, findRecentTeamMatches.get(0).getId().intValue()));
	}

	private void mockSummoners() {
		Supplier<Long> idSupplier = new IdSupplier();
		Arrays.asList("Dremsy", "0Kordan0", "Victoriusss", "Luidji94", "Wasagreen").forEach(summonerName -> {
			try {
				Mockito.when(riotApiServiceMock.getSummonerByName(Mockito.eq(Platform.EUW), Mockito.eq(summonerName)))
						.thenReturn(lolApiFactory.createSummoner(idSupplier.get()));
			} catch (RiotApiException e) {
				fail(e.getMessage());
			}
		});
	}

	private void mockMatches() throws RiotApiException {
		MatchList matchList1 = lolApiFactory.createMatchList(1, 2, 3, 4, 5);
		Mockito.when(riotApiServiceMock.getMatchListByAccountId(Mockito.any(Platform.class), Mockito.eq(1l))).thenReturn(matchList1);

		MatchList matchList2 = lolApiFactory.createMatchList(5, 6, 7, 8, 9);
		Mockito.when(riotApiServiceMock.getMatchListByAccountId(Mockito.any(Platform.class), Mockito.eq(2l))).thenReturn(matchList2);

		MatchList matchList3 = lolApiFactory.createMatchList(5, 7, 8, 9, 10);
		Mockito.when(riotApiServiceMock.getMatchListByAccountId(Mockito.any(Platform.class), Mockito.eq(3l))).thenReturn(matchList3);

		MatchList matchList4 = lolApiFactory.createMatchList(5, 7, 8, 9, 10);
		Mockito.when(riotApiServiceMock.getMatchListByAccountId(Mockito.any(Platform.class), Mockito.eq(4l))).thenReturn(matchList4);

		MatchList matchList5 = lolApiFactory.createMatchList(5, 7, 8, 9, 10);
		Mockito.when(riotApiServiceMock.getMatchListByAccountId(Mockito.any(Platform.class), Mockito.eq(5l))).thenReturn(matchList5);
	}
	
	public class IdSupplier implements Supplier<Long> {
		
		private long currentId;

		public IdSupplier() {
			currentId = 1;
		}

		@Override
		public Long get() {
			final long value = currentId;
			currentId++;
			return value;
		}

	}
}
