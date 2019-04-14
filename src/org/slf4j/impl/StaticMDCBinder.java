package org.slf4j.impl;

import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * This implementation is bound to {@link BasicMDCAdapter}.
 *
 * @author Philippe Marschall
 */
public final class StaticMDCBinder {

  /**
   * The unique instance of this class.
   */
  public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

  private StaticMDCBinder() {
    super();
  }

  /**
   * Currently this method always returns an instance of
   * {@link BasicMDCAdapter}.
   */
  public MDCAdapter getMDCA() {
    // note that this method is invoked only from within the static initializer of
    // the org.slf4j.MDC class.
    return new BasicMDCAdapter();
  }

  /**
   * Retrieve the adapter class name.
   *
   * @return rhe adapter class name
   */
  public String getMDCAdapterClassStr() {
    return BasicMDCAdapter.class.getName();
  }

}
