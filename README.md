SLF4J Equinox [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/com.github.marschall.slf4j-equinox/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.marschall/com.github.marschall.slf4j-equinox) [![Javadocs](https://www.javadoc.io/badge/com.github.marschall/com.github.marschall.slf4j-equinox.svg)](https://www.javadoc.io/doc/com.github.marschall/com.github.marschall.slf4j-equinox)
=============
This is an implementation of [SLF4J](https://www.slf4j.org) using the [Equinox](https://www.eclipse.org/equinox/) [ExtendedLogService](https://bugs.eclipse.org/bugs/show_bug.cgi?id=260672).

In plain words it makes all code that uses SLF4J and log to Equinox <code>.metadata/.log</code> log file. It does this by redirecting all the log messages to the Equinox ExtendedLogService. This is mostly interesting for code that runs inside Eclipse RCP applications. This does _not_ make Equinox use SLF4J. 

The implementation uses the same techniques as the <code>ch.qos.logback.slf4j</code> bundle.

Caveats
-------

 * the Equinox context object is always `null`
 * the SLF4J markers are ignored

This shouldn't be installed along side `ch.qos.logback.slf4j` or `org.slf4j.impl.log4j12` There's a p2 negation requirement taking care of this.

There are two SLF4J bundles the Maven artifact uses `slf4j.api` the [Orbit](https://www.eclipse.org/orbit/) uses `org.slf4j.api`.

Implementation Notes
--------------------

We keep a <code>ConcurrentHashMap</code> of strings to logger adapters. A <code>ConcurrentHashMap</code> uses more memory than <code>HashMap</code> but allows for concurrent lookups even though <code>ExtendedLogServiceImpl#getLogger</code> is <code>synchronized</code>. The values are not weak for now as <code>Log4jLoggerFactory</code> doesn't use weak values as well.

Although <code>Eclipse-GenericCapability</code> is depreacated using <code>Provide-Capability</code> instead doesn't pass plugin validation in PDE.

Since a bundle can not have an activator we have to manually get a <code>BundleContext</code> using <code>FrameworkUtil</code>. However at this point the <code>org.slf4j.api</code> bundle is only resolve so we need to manually start it.

Building
--------
If you want to build this project then you need [Maven 3](https://maven.apache.org/) and add the following section to your <code>settings.xml</code>

```xml
<profile>
  <id>photon</id>
  <activation>
    <activeByDefault>false</activeByDefault>
  </activation>
  <repositories>
    <repository>
      <id>photon</id>
      <layout>p2</layout>
      <url>http://download.eclipse.org/releases/photon</url>
    </repository>
  </repositories>
</profile>
```

Publishing
----------
To publish the artifact to a p2 site (or build a new one) you can use the [Features And Bundles Publisher Application](http://wiki.eclipse.org/Equinox/p2/Publisher#Features_And_Bundles_Publisher_Application)

```sh
java -jar <targetProductFolder>/plugins/org.eclipse.equinox.launcher_*.jar \
-application org.eclipse.equinox.p2.publisher.FeaturesAndBundlesPublisher \
-metadataRepository file:/<some location>/repository \
-artifactRepository file:/<some location>/repository \
-source /<location with a plugins and features directory> \
-configs gtk.linux.x86 \
-compress \
-publishArtifacts
```

Installing
----------
You can install form the site you built above using the [p2 director application](http://help.eclipse.org/indigo/index.jsp?topic=/org.eclipse.platform.doc.isv/guide/p2_director.html)

```sh
java -jar <targetProductFolder>/plugins/org.eclipse.equinox.launcher_*.jar \
-application org.eclipse.equinox.p2.director \
-repository file:/<some location>/repository \
-installIU com.github.marschall.slf4j-equinox \
-uninstallIU org.slf4j.log4j,ch.qos.logback.slf4j,org.eclipse.jetty.slf4jlogback.feature.group,org.eclipse.jetty.sdk.feature.group \
-tag slf4j-equinox \
-destination <targetProductFolder>
```

