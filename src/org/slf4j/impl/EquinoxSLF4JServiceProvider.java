package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

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
    return "2.0.6";
  }

  @Override
  public void initialize() {
    this.loggerFactory = new EquinoxLoggerFactory();
    this.markerFactory = new SimpleMarkerFactory();
    this.mdcAdapter = new BasicMDCAdapter();
  }

}
