package com.lolteam.services.riotApi;

import net.rithms.riot.api.RiotApiException;

public interface RiotSupplier<T> {
	public T get() throws RiotApiException;
}
