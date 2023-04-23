package org.slf4j.impl;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

final class SimpleMarkerFactory implements IMarkerFactory {

  private final ConcurrentMap<String, Marker> markerMap;

  SimpleMarkerFactory() {
    this.markerMap = new ConcurrentHashMap<>();
  }

  @Override
  public Marker getMarker(String name) {
    Objects.requireNonNull(name, "marker name");
    return this.markerMap.computeIfAbsent(name, SimpleMarker::new);
  }

  @Override
  public boolean exists(String name) {
    return this.markerMap.containsKey(name);
  }

  @Override
  public boolean detachMarker(String name) {
    if (name == null) {
      return false;
    }
    return markerMap.remove(name) != null;
  }

  @Override
  public Marker getDetachedMarker(String name) {
    return new SimpleMarker(name);
  }

}
