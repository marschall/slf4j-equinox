package org.slf4j.impl;

import org.slf4j.IMarkerFactory;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MarkerFactoryBinder;

/**
 *
 * The binding of {@link MarkerFactory} class with an actual instance of
 * {@link IMarkerFactory} is performed using information returned by this class.
 *
 * @author Philippe Marschall
 */
public class StaticMarkerBinder implements MarkerFactoryBinder {

  /**
   * The unique instance of this class.
   */
  public static final StaticMarkerBinder SINGLETON = new StaticMarkerBinder();

  final IMarkerFactory markerFactory;

  private StaticMarkerBinder() {
    this.markerFactory = new BasicMarkerFactory();
  }

  /**
   * Currently this method always returns an instance of
   * {@link BasicMarkerFactory}.
   */
  @Override
  public IMarkerFactory getMarkerFactory() {
    return this.markerFactory;
  }

  /**
   * Currently, this method returns the class name of
   * {@link BasicMarkerFactory}.
   */
  @Override
  public String getMarkerFactoryClassStr() {
    return BasicMarkerFactory.class.getName();
  }

}
