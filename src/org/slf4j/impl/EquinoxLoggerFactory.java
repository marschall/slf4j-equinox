package org.slf4j.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.equinox.log.ExtendedLogService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
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
    // this is a bit hairy
    // since we're a bundle we can't have an activator we have to work around this

    Bundle bundle = FrameworkUtil.getBundle(EquinoxLoggerFactory.class);
    // start the bundle so that we have a bundle context
    // maybe the bundle is not started because it has no Bundle-ActivationPolicy: lazy
    if (bundle.getState() == Bundle.RESOLVED) {
      try {
        bundle.start();
      } catch (BundleException e) {
        throw new RuntimeException("could not start bundle", e);
      }
    }
    // reimplement BundleActivator#start()
    BundleContext context = bundle.getBundleContext();
    ServiceTracker<?, ExtendedLogService> serviceTracker =
        new ServiceTracker<>(context, ExtendedLogService.class, null);

    serviceTracker.open();
    // reimplement BundleActivator#stop()
    context.addBundleListener((BundleEvent event) -> {
      if (event.getBundle().getBundleId() == bundle.getBundleId()
          && event.getType() == BundleEvent.STOPPING) {
        serviceTracker.close();
      }
    });


    this.logService = serviceTracker.getService();
    this.loggerMap = new ConcurrentHashMap<>();
  }

  @Override
  public Logger getLogger(String name) {
    Logger slf4jLogger = this.loggerMap.get(name);
    if (slf4jLogger == null) {
      org.eclipse.equinox.log.Logger equinoxLogger = this.logService.getLogger(name);
      Logger newLoggerAdapter = new EquinoxLoggerAdapter(name, equinoxLogger);
      Logger previousLogger = this.loggerMap.putIfAbsent(name, newLoggerAdapter);
      if (previousLogger == null) {
        slf4jLogger = newLoggerAdapter;
      } else {
        slf4jLogger = previousLogger;
      }

    }
    return slf4jLogger;
  }

}
