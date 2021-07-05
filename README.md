# Synder
Synder is an Atom/RSS/SearchSuggestion/OPML processing framework.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.escape-technology-llc/Synder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.escape-technology-llc/Synder)

It is Optimized For Android, but works with Java SE/EE.

Synder has a small-footprint, low memory consumption.

The only dependency for parsing is a SAX2 implementation.

# Features
* Additional document types supported: Open Search Suggestions, OPML.
* Bare-metal SAX handlers so it's fast, fast, FAST!
* Flexible namespace processing system. Several popular namespaces are included: Dublin Core, iTunes, RDF Content.
* Actually subjected to HPROF and tweaked in.
* Content generation JAR based on XmlSerializer that outputs RSS 2.0 and Atom for "round-tripping" content.
* Drop-in replacement for ROME via com.sun.syndication.* packages.
* ROME users: No "converters" required; parses directly into Synd* classes.
