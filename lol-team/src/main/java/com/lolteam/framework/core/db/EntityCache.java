package com.lolteam.framework.core.db;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

import com.lolteam.entities.GenericEntity;

public class EntityCache<T, R extends GenericEntity> {

	Set<R> cachedEntities = new HashSet<>();
	private Function<T, R> entityLoaderFunction;
	private BiPredicate<T, R> filterBiPredicate;
	private Consumer<R> saveConsumer;

	public EntityCache(Function<T, R> entityLoaderFunction, BiPredicate<T, R> filterBiPredicate) {
		this(entityLoaderFunction, filterBiPredicate, entity ->  {});	
	}
	
	public EntityCache(Function<T, R> entityLoaderFunction, BiPredicate<T, R> filterBiPredicate, Consumer<R> saveConsumer) {
		this.entityLoaderFunction = entityLoaderFunction;
		this.filterBiPredicate = filterBiPredicate;
		this.saveConsumer = saveConsumer;
	}
	
	/** Can return <b>null</b> if the data is impossible to reach.*/
	synchronized public R get(T value) {
		return cachedEntities.stream()
				.filter(entity -> filterBiPredicate.test(value, entity))
				.findFirst()
				.orElseGet(() -> {
					R entity = entityLoaderFunction.apply(value);
					if(entity == null) {
						return null;
					}
					cachedEntities.add(entity);
					return entity;
				});
	}

	public void addAndSave(R entity) {
		cachedEntities.add(entity);
		saveConsumer.accept(entity);
	}

}
