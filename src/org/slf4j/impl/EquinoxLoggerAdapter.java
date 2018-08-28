package org.slf4j.impl;

import static org.osgi.service.log.LogService.LOG_DEBUG;
import static org.osgi.service.log.LogService.LOG_ERROR;
import static org.osgi.service.log.LogService.LOG_INFO;
import static org.osgi.service.log.LogService.LOG_WARNING;

import org.eclipse.equinox.log.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A wrapper over {@linkorg.eclipse.equinox.log.Logger} in
 * conforming to the {@link org.slf4j.Logger} interface.
 *
 * <p>The TRACE level will be mapped as DEBUG.</p>
 *
 * @author Philippe Marschall
 */
final class EquinoxLoggerAdapter extends MarkerIgnoringBase
implements org.slf4j.Logger, LocationAwareLogger {

  //TODO should the marker be the equinox log context?

  private final Logger logger;

  EquinoxLoggerAdapter(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void debug(String msg) {
    this.logger.log(null, LOG_DEBUG, msg, null);
  }

  @Override
  public void debug(String format, Object arg) {
    if (this.isDebugEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg);
      this.logger.log(null, LOG_DEBUG, ft.getMessage(), null);
    }
  }

  @Override
  public void debug(String format, Object[] argArray) {
    if (this.isDebugEnabled()) {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
      this.logger.log(null, LOG_DEBUG, ft.getMessage(), null);
    }
  }

  @Override
  public void debug(String msg, Throwable t) {
    this.logger.log(null, LOG_DEBUG, msg, t);

  }

  @Override
  public void debug(String format, Object arg1, Object arg2) {
    if (this.isDebugEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      this.logger.log(null, LOG_DEBUG, ft.getMessage(), null);
    }
  }

  @Override
  public void error(String msg) {
    this.logger.log(null, LOG_ERROR, msg, null);
  }

  @Override
  public void error(String format, Object arg) {
    if (this.isErrorEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg);
      this.logger.log(null, LOG_ERROR, ft.getMessage(), null);
    }
  }

  @Override
  public void error(String format, Object[] argArray) {
    if (this.isErrorEnabled()) {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
      this.logger.log(null, LOG_ERROR, ft.getMessage(), null);
    }
  }

  @Override
  public void error(String msg, Throwable t) {
    this.logger.log(null, LOG_ERROR, msg, t);
  }

  @Override
  public void error(String format, Object arg1, Object arg2) {
    if (this.isErrorEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      this.logger.log(null, LOG_DEBUG, ft.getMessage(), null);
    }
  }

  @Override
  public String getName() {
    return this.logger.getName();
  }

  @Override
  public void info(String msg) {
    this.logger.log(null, LOG_INFO, msg, null);
  }

  @Override
  public void info(String format, Object arg) {
    if (this.isInfoEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg);
      this.logger.log(null, LOG_INFO, ft.getMessage(), null);
    }
  }

  @Override
  public void info(String format, Object[] argArray) {
    if (this.isInfoEnabled()) {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
      this.logger.log(null, LOG_INFO, ft.getMessage(), null);
    }
  }

  @Override
  public void info(String msg, Throwable t) {
    this.logger.log(null, LOG_INFO, msg, t);
  }

  @Override
  public void info(String format, Object arg1, Object arg2) {
    if (this.isInfoEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      this.logger.log(null, LOG_INFO, ft.getMessage(), null);
    }
  }

  @Override
  public boolean isDebugEnabled() {
    return this.logger.isLoggable(LOG_DEBUG);
  }

  @Override
  public boolean isErrorEnabled() {
    return this.logger.isLoggable(LOG_ERROR);
  }

  @Override
  public boolean isInfoEnabled() {
    return this.logger.isLoggable(LOG_INFO);
  }

  @Override
  public boolean isTraceEnabled() {
    return this.isDebugEnabled();
  }

  @Override
  public boolean isWarnEnabled() {
    return this.logger.isLoggable(LOG_WARNING);
  }

  // trace mapped to debug
  @Override
  public void trace(String msg) {
    this.debug(msg);
  }

  @Override
  public void trace(String format, Object arg) {
    this.debug(format, arg);
  }

  @Override
  public void trace(String format, Object[] argArray) {
    this.debug(format, argArray);
  }

  @Override
  public void trace(String msg, Throwable t) {
    this.debug(msg, t);
  }

  @Override
  public void trace(String format, Object arg1, Object arg2) {
    this.debug(format, arg1, arg2);
  }

  @Override
  public void warn(String msg) {
    this.logger.log(null, WARN_INT, msg, null);

  }

  @Override
  public void warn(String format, Object arg) {
    if (this.isWarnEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg);
      this.logger.log(null, WARN_INT, ft.getMessage(), null);
    }

  }

  @Override
  public void warn(String format, Object[] argArray) {
    if (this.isWarnEnabled()) {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
      this.logger.log(null, WARN_INT, ft.getMessage(), null);
    }
  }

  @Override
  public void warn(String msg, Throwable t) {
    this.logger.log(null, WARN_INT, msg, t);
  }

  @Override
  public void warn(String format, Object arg1, Object arg2) {
    if (this.isWarnEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      this.logger.log(null, WARN_INT, ft.getMessage(), null);
    }
  }

  @Override
  public void log(Marker marker, String callerFQCN, int level, String msg,
      Object[] argArray, Throwable t) {
    int equinoxLevel;
    switch (level) {
    case LocationAwareLogger.TRACE_INT:
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
      throw new IllegalStateException("Level number " + level
          + " is not recognized.");
    }
    this.logger.log(null, equinoxLevel, msg, t);
  }

}
