package org.slf4j.impl;

import static org.osgi.service.log.LogService.LOG_DEBUG;
import static org.osgi.service.log.LogService.LOG_ERROR;
import static org.osgi.service.log.LogService.LOG_INFO;
import static org.osgi.service.log.LogService.LOG_WARNING;

import java.util.Objects;

import org.eclipse.equinox.log.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A wrapper over {@linkorg.eclipse.equinox.log.Logger} in
 * conforming to the {@link org.slf4j.Logger} interface.
 *
 * @author Philippe Marschall
 */
final class EquinoxLoggerAdapter extends MarkerIgnoringBase implements org.slf4j.Logger, LocationAwareLogger {

  private static final long serialVersionUID = 1L;

  /**
   * Anything that is not one of LOG_DEBUG, LOG_ERROR, LOG_INFO, LOG_WARNING is trace.
   */
  private static final int LOG_TRACE = LOG_DEBUG + 1;

  //TODO should the marker be the equinox log context?

  private final transient Logger logger;

  EquinoxLoggerAdapter(String name, Logger logger) {
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(logger, "logger");
    this.name = name;
    this.logger = logger;
  }

  @Override
  public void debug(String msg) {
    this.logger.debug(msg);
  }

  @Override
  public void debug(String format, Object arg) {
    this.logger.debug(format, arg);
  }

  @Override
  public void debug(String format, Object... argArray) {
    this.logger.debug(format, argArray);
  }

  @Override
  public void debug(String msg, Throwable t) {
    this.logger.log(null, LOG_DEBUG, msg, t);

  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    this.logger.debug(format, arg1, arg2);
  }

  @Override
  public void error(String msg) {
    this.logger.error(msg);
  }

  @Override
  public void error(String format, Object arg) {
    this.logger.error(format, arg);
  }

  @Override
  public void error(String format, Object... argArray) {
    this.logger.error(format, argArray);
  }

  @Override
  public void error(String msg, Throwable t) {
    this.logger.log(null, LOG_ERROR, msg, t);
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    this.logger.error(format, arg1, arg2);
  }

  @Override
  public void info(String msg) {
    this.logger.info(msg);
  }

  @Override
  public void info(String format, Object arg) {
    this.logger.info(format, arg);
  }

  @Override
  public void info(String format, Object... argArray) {
    this.logger.info(format, argArray);
  }

  @Override
  public void info(String msg, Throwable t) {
    this.logger.log(null, LOG_INFO, msg, t);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    this.logger.info(format, arg1, arg2);
  }

  @Override
  public boolean isDebugEnabled() {
    return this.logger.isDebugEnabled();
  }

  @Override
  public boolean isErrorEnabled() {
    return this.logger.isErrorEnabled();
  }

  @Override
  public boolean isInfoEnabled() {
    return this.logger.isInfoEnabled();
  }

  @Override
  public boolean isTraceEnabled() {
    return this.logger.isTraceEnabled();
  }

  @Override
  public boolean isWarnEnabled() {
    return this.logger.isWarnEnabled();
  }

  @Override
  public void trace(String msg) {
    this.logger.trace(msg);
  }

  @Override
  public void trace(String format, Object arg) {
    this.logger.trace(format, arg);
  }

  @Override
  public void trace(String format, Object... argArray) {
    this.logger.trace(format, argArray);
  }

  @Override
  public void trace(String msg, Throwable t) {
    this.logger.log(null, LOG_TRACE, msg, t);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    this.logger.trace(format, arg1, arg2);
  }

  @Override
  public void warn(String msg) {
    this.logger.warn(msg);
  }

  @Override
  public void warn(String format, Object arg) {
    this.logger.warn(format, arg);
  }

  @Override
  public void warn(String format, Object... argArray) {
    this.logger.warn(format, argArray);
  }

  @Override
  public void warn(String msg, Throwable t) {
    this.logger.log(null, LOG_WARNING, msg, t);
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    this.logger.warn(format, arg1, arg2);
  }

  @Override
  public void log(Marker marker, String callerFQCN, int level, String msg,
      Object[] argArray, Throwable t) {
    int equinoxLevel;
    switch (level) {
      case LocationAwareLogger.TRACE_INT:
        equinoxLevel = LOG_TRACE;
      case LocationAwareLogger.DEBUG_INT:
        equinoxLevel = LOG_DEBUG;
        break;
      case LocationAwareLogger.INFO_INT:
        equinoxLevel = LOG_INFO;
        break;
      case LocationAwareLogger.WARN_INT:
        equinoxLevel = LOG_WARNING;
        break;
      case LocationAwareLogger.ERROR_INT:
        equinoxLevel = LOG_DEBUG;
        break;
      default:
        throw new IllegalStateException("Level number " + level + " is not recognized.");
    }
    this.logger.log(null, equinoxLevel, msg, t);
  }

}
