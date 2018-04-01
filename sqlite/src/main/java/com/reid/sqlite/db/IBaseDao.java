package com.reid.sqlite.db;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface IBaseDao<T> {

    /**
     * Insert long.
     *
     * @param entity the entry
     * @return @Target(ElementType.TYPE)
     */
    Long insert(T entity);

    /**
     * Update long.
     *
     * @param entity the entity
     * @param where  the where
     * @return the long
     */
    Long update(T entity, T where);
}
