package com.lolteam.framework.core.db;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

import com.lolteam.entities.general.GenericEntity;

/** 
 * A cache for entites to shorten database access.
 * Usually defined on the service associated to the entity. 
 */
public class EntityCache<T, R extends GenericEntity> {

	Set<R> cachedEntities = new HashSet<>();
	
	private Function<T, R> entityLoaderFunction;
	
	private BiPredicate<T, R> filterBiPredicate;
	
	private Consumer<R> saveConsumer;

	public EntityCache(Function<T, R> entityLoaderFunction, BiPredicate<T, R> filterBiPredicate) {
		this(entityLoaderFunction, filterBiPredicate, entity ->  {});	
	}
	
	/** Constructor
	 * 
	 * @param entityLoaderFunction 
	 * 		Function used to load an entity if not present in the cache.
	 * @param filterBiPredicate 
	 * 		BiPredicate used to know how to associate a value with the all the entities present in the cache.
	 * @param saveConsumer 
	 * 		Consumer used to save the entity in database. */
	public EntityCache(Function<T, R> entityLoaderFunction, BiPredicate<T, R> filterBiPredicate, Consumer<R> saveConsumer) {
		this.entityLoaderFunction = entityLoaderFunction;
		this.filterBiPredicate = filterBiPredicate;
		this.saveConsumer = saveConsumer;
	}
	
	/** Tries to returns the entity associated to the value from the cache or 
	 * tries to load it from the entityLoader.
	 * <b> warning</b> if no entityLoader has been provided or if no match were found, 
	 * the method will return <b>null</b>
	 * 
	 *  @param value 
	 *  	The value associated to the entity.
	 *  @return The entity
	 */
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

	/** save the entity in the database and add's it to the cache.
	 * 
	 *  @param entity 
	 *  	The entity to save.
	 */
	public void addAndSave(R entity) {
		cachedEntities.add(entity);
		saveConsumer.accept(entity);
	}

}
