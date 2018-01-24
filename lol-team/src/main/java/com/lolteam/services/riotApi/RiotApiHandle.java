package com.lolteam.services.riotApi;

import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.request.ratelimit.RateLimitException;

class RiotApiHandle {

	/**
	 * Handles native riot api.
	 * 
	 * @return the result if everything goes well,
	 * if a RateLimiteException is thrown, the method will try again went possible.
	 * Otherwise returns null*/
	public <R> R execute(RiotSupplier<R> supplier) {
		try {
			return supplier.get();
		} catch (RiotApiException e) {
			return handleRiotApiException(e, supplier);
		}
	}
	
	private <R> R handleRiotApiException(RiotApiException e, RiotSupplier<R> supplier) {
		if(e instanceof RateLimitException) {
			RateLimitException rateExeption = (RateLimitException) e;
			System.out.println("Need to reconnect after "+rateExeption.getRetryAfter()+  " s");
			try {
				Thread.sleep(rateExeption.getRetryAfter() * 1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return execute(supplier);
		}
		//todo log error
		return null;
	}
	
	

}
