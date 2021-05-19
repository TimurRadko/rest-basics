package com.epam.esm.link.builder;

/**
 * * This interface describes all operation for building HATEOAS links
 *
 * @param <T> - in arguments passed Entity, for which you need to add a link
 */
public interface LinkBuilder<T> {
  /**
   * * This method describes building HATEOAS links
   *
   * @param t - in arguments passed Entity, for which you need to add a link
   * @return T - return Entity with added a link
   */
  T build(T t);
}
