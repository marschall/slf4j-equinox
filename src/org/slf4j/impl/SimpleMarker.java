package org.slf4j.impl;

import java.io.ObjectStreamException;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

final class SimpleMarker implements Marker {
  
  private final String name;
  private final ReadWriteLock lock;

  SimpleMarker(String name) {
    Objects.requireNonNull(name, "marker name");
    this.name = name;
    this.lock = new ReentrantReadWriteLock();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void add(Marker reference) {
    Lock writeLock = this.lock.writeLock();
    writeLock.lock();
    try {
      
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public boolean remove(Marker reference) {
    Lock writeLock = this.lock.writeLock();
    writeLock.lock();
    try {
      
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public boolean hasChildren() {
    Lock readLock = this.lock.readLock();
    readLock.lock();
    try {
      
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public boolean hasReferences() {
    Lock readLock = this.lock.readLock();
    readLock.lock();
    try {
      
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public Iterator<Marker> iterator() {
    Lock readLock = this.lock.readLock();
    readLock.lock();
    try {
      
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public boolean contains(Marker other) {
    Lock readLock = this.lock.readLock();
    readLock.lock();
    try {
      
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public boolean contains(String name) {
    Lock readLock = this.lock.readLock();
    readLock.lock();
    try {
      
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null)
      return false;
    if (!(obj instanceof Marker)) {
      return false;
    }
    Marker other = (Marker) obj;
    return name.equals(other.getName());
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  private Object readResolve() throws ObjectStreamException {
    return MarkerFactory.getMarker(this.name);
  }

}
