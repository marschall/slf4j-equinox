SLF4J Equinox
=============
This is an implementation [SLF4J](http://www.slf4j.org/) using the [Equinox](http://www.eclipse.org/equinox/) [ExtendedLogService](https://bugs.eclipse.org/bugs/show_bug.cgi?id=260672).

In plain words it allows you to use SLF4J to log using Equinox.

The implementation uses the same techniques as the <code>ch.qos.logback.slf4j</code> bundle.

Caveats
-------
 * the Equinox context object is always <code>null</code>
 * the SLF4J markers are ignored

This shouldn't be installed along side <code>ch.qos.logback.slf4j</code>. A p2 constraint for this is missing.

Implementation Notes
--------------------
We keep a <code>ConcurrentHashMap</code> of strings to logger adapters. A <code>ConcurrentHashMap</code> uses more memory than <code>HashMap</code> but allows for concurrent lookups even though <code>ExtendedLogServiceImpl#getLogger</code> is <code>synchronized</code>. The values are not weak for now <code>Log4jLoggerFactory</code> doesn't use weak values as well.

Altough <code>Eclipse-GenericCapability</code> is depreacated using <code>Provide-Capability</code> instead doesn't pass plugin validation in PDE.

Since a bundle can not have an activator we have to manually get a <code>BundleContext</code> using <code>FrameworkUtil</code>. However at this point the <code>org.slf4j.api</code> bundle is only resolve so we need to manually start it.
