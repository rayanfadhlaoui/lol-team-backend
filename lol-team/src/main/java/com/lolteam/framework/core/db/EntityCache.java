package com.lolteam.framework.core.db;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

import com.lolteam.entities.GenericEntity;

public class EntityCache<T, R extends GenericEntity> {

	List<R> cachedEntities = new ArrayList<>();
	private Function<T, R> entityLoaderFunction;
	private BiPredicate<T, R> filterBiPredicate;

	public EntityCache(Function<T, R> entityLoaderFunction, BiPredicate<T, R> filterBiPredicate) {
		this.entityLoaderFunction = entityLoaderFunction;
		this.filterBiPredicate = filterBiPredicate;
	}

	public R get(T value) {
		return cachedEntities.stream()
				.filter(entity -> filterBiPredicate.test(value, entity))
				.findFirst()
				.orElseGet(() -> {
					R entity = entityLoaderFunction.apply(value);
					cachedEntities.add(entity);
					return entity;
				});
	}

}
