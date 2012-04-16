SLF4J Equinox
=============
This is an implementation [SLF4J](http://www.slf4j.org/) using the [Equinox](http://www.eclipse.org/equinox/) [ExtendedLogService](https://bugs.eclipse.org/bugs/show_bug.cgi?id=260672).

In plain words it allows you to use SLF4J and log to Equinox. It does _not_ make Equinox use Equinox. This is mostly interesting for code that runs inside Eclipse RCP applications.

The implementation uses the same techniques as the <code>ch.qos.logback.slf4j</code> bundle.

Caveats
-------
 * the Equinox context object is always <code>null</code>
 * the SLF4J markers are ignored

This shouldn't be installed along side <code>ch.qos.logback.slf4j</code>. There's a p2 negation requirement taking care of this.

Implementation Notes
--------------------
We keep a <code>ConcurrentHashMap</code> of strings to logger adapters. A <code>ConcurrentHashMap</code> uses more memory than <code>HashMap</code> but allows for concurrent lookups even though <code>ExtendedLogServiceImpl#getLogger</code> is <code>synchronized</code>. The values are not weak for now as <code>Log4jLoggerFactory</code> doesn't use weak values as well.

Altough <code>Eclipse-GenericCapability</code> is depreacated using <code>Provide-Capability</code> instead doesn't pass plugin validation in PDE.

Since a bundle can not have an activator we have to manually get a <code>BundleContext</code> using <code>FrameworkUtil</code>. However at this point the <code>org.slf4j.api</code> bundle is only resolve so we need to manually start it.

Building
--------
If you want to build this project then you need [Maven 3](http://maven.apache.org/) and add the following section to your <code>settings.xml</code>
```xml
  <repositories>
    <repository>
      <id>indigo</id>
      <layout>p2</layout>
      <url>http://download.eclipse.org/releases/indigo</url>
    </repository>
  </repositories>
```
