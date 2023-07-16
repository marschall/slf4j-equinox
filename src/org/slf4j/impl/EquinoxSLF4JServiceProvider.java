package org.slf4j.impl;

import org.eclipse.equinox.log.ExtendedLogService;
import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * Implementation of {@link SLF4JServiceProvider} that brides to {@link ExtendedLogService}
 * <p>
 * Registers the {@link ILoggerFactory}, {@link IMarkerFactory} and {@link MDCAdapter}.
 * 
 * @see EquinoxLoggerFactory
 */
public class EquinoxSLF4JServiceProvider implements SLF4JServiceProvider {

  private volatile ILoggerFactory loggerFactory;
  private volatile IMarkerFactory markerFactory;
  private volatile MDCAdapter mdcAdapter;

  @Override
  public ILoggerFactory getLoggerFactory() {
    return this.loggerFactory;
  }

  @Override
  public IMarkerFactory getMarkerFactory() {
    return this.markerFactory;
  }

  @Override
  public MDCAdapter getMDCAdapter() {
    return this.mdcAdapter;
  }

  @Override
  public String getRequestedApiVersion() {
    return "2.0.0";
  }

  @Override
  public void initialize() {
    this.loggerFactory = new EquinoxLoggerFactory();
    this.markerFactory = new BasicMarkerFactory();
    this.mdcAdapter = new BasicMDCAdapter();
  }

}
