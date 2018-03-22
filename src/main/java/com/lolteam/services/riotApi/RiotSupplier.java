package com.lolteam.services.riotApi;

import net.rithms.riot.api.RiotApiException;

/** A supplier that can handle {@link RiotApiException}*/
public interface RiotSupplier<T> {
	
	/** 
	 * Supplier a value.
	 * 
	 * @return The value.
	 * 
	 * @throws RiotApiException If a error occurs while using the Riot API
	 */
	public T get() throws RiotApiException;
}
