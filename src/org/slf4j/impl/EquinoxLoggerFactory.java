package org.slf4j.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.equinox.log.ExtendedLogService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Log4jLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriate named {@link Log4jLoggerAdapter} instance.
 *
 * @author Philippe Marschall
 */
final class EquinoxLoggerFactory implements ILoggerFactory {

  // TODO use weak values? ExtendedLogServiceImpl doesn't use weak values as well
  // TODO synchronized instead of ConcurrentMap? ExtendedLogServiceImpl is synchronized as well
  // TODO remove BasicReadWriteLock from Equinox

  private final ConcurrentMap<String, Logger> loggerMap;

  private final ExtendedLogService logService;

  EquinoxLoggerFactory() {
    this.logService = EquinoxSLF4JBundleActivator.getDefaultInstance().getExtendedLogService();
    this.loggerMap = new ConcurrentHashMap<>();
  }

  @Override
  public Logger getLogger(String name) {
    return this.loggerMap.computeIfAbsent(name, n -> {
      org.eclipse.equinox.log.Logger equinoxLogger = this.logService.getLogger(n);
      return new EquinoxLoggerAdapter(name, equinoxLogger);
      });
  }

}
