package org.slf4j.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.equinox.log.ExtendedLogService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
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
		
		// reimplement BundleActivator#start() 
		final Bundle bundle = FrameworkUtil.getBundle(EquinoxLoggerFactory.class);
		if (bundle.getState() == Bundle.RESOLVED) {
			try {
				bundle.start();
			} catch (BundleException e) {
				throw new RuntimeException("could not start bundle", e);
			}
		}
		final BundleContext context = bundle.getBundleContext();
		final ServiceTracker<?, ExtendedLogService> serviceTracker =
				new ServiceTracker<ExtendedLogService, ExtendedLogService>(context, ExtendedLogService.class, null);
		
		serviceTracker.open();
		// reimplement BundleActivator#stop()
		context.addBundleListener(new BundleListener() {
			
			public void bundleChanged(BundleEvent event) {
				if (event.getBundle().getBundleId() == bundle.getBundleId()
						&& event.getType() == BundleEvent.STOPPING) {
					serviceTracker.close();
				}
				
			}
		});
		
		
		this.logService = serviceTracker.getService();
		this.loggerMap = new ConcurrentHashMap<String, Logger>();
	}

	public Logger getLogger(String name) {
		Logger slf4jLogger = this.loggerMap.get(name);
		if (slf4jLogger == null) {
			Logger newLoggerAdapter = new EquinoxLoggerAdapter(logService.getLogger(name));
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
