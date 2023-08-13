package com.github.marschall.slf4j.equinox;

import org.eclipse.equinox.log.ExtendedLogService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Bundle activator whos sole purpuse is to give access to {@link BundleContext}.
 */
public final class EquinoxSLF4JBundleActivator implements BundleActivator {

  private static volatile EquinoxSLF4JBundleActivator defaultInstance;

  private volatile ServiceTracker<?, ExtendedLogService> serviceTracker;

  /**
   * Default constructor to be called by OSGi.
   */
  public EquinoxSLF4JBundleActivator() {
    defaultInstance = this;
  }

  @Override
  public void start(BundleContext context) {
    this.serviceTracker = new ServiceTracker<>(context, ExtendedLogService.class, null);
    this.serviceTracker.open();
  }

  @Override
  public void stop(BundleContext context) {
    this.serviceTracker.close();
  }

  ExtendedLogService getExtendedLogService() {
    return this.serviceTracker.getService();
  }

  static EquinoxSLF4JBundleActivator getDefaultInstance() {
    return defaultInstance;
  }

}
