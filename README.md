# java-8-matchers

[![Build Status](https://travis-ci.org/unruly/java-8-matchers.svg?branch=master)](https://travis-ci.org/unruly/java-8-matchers)
[![Release Version](https://img.shields.io/maven-central/v/co.unruly/java-8-matchers.svg)](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22co.unruly%22%20AND%20a%3A%22java-8-matchers%22)
[![Javadocs](https://www.javadoc.io/badge/co.unruly/java-8-matchers.svg?color=yellow)](https://www.javadoc.io/doc/co.unruly/java-8-matchers)

**Hamcrest Matchers for Java 8 features.**

The library contains matchers for the following types introduced with Java 8:

* `java.util.Optional` (and primitive variants)
* `java.util.Stream` (and primitive variants)
* `java.time.Temporal` (`Instant`, `LocalDateTime`, various other calendar systems)
* `java.time.TemporalAmount` (`Duration` and `Period`)

In addition, there is an API to construct matchers by using lambdas to extract data from arbitrary types and apply matchers to the result. See examples of this below.



## Installation

Available from the Central Repository. In Maven style:

```xml
<dependency>
  <groupId>co.unruly</groupId>
  <artifactId>java-8-matchers</artifactId>
  <version>1.6</version>
</dependency>
```

A test-jar is also available to gain access to a helper for testing the error messages of failing
Matchers.

```xml
<type>test-jar</type>
```



# Examples

Below are some examples of the java-8-matchers API.


## Optionals

```java
// Contents of Optional
assertThat(Optional.of("Hi!"), OptionalMatchers.contains("Hi!"));

// Contents of Optional with Matchers
assertThat(Optional.of(4), OptionalMatchers.contains(Matchers.greaterThan(3)));

// Assert empty
assertThat(Optional.empty(), OptionalMatchers.empty());
```



## Streams

```java
// Stream is empty
assertThat(Stream.empty(), StreamMatchers.empty());

// Stream contains elements
assertThat(Stream.of("a", "b", "c"), StreamMatchers.contains("a", "b", "c"));

// Stream has only elements matching specified Matcher
assertThat(Stream.of("bar","baz"), StreamMatchers.allMatch(containsString("a")));

// Stream contains at least one element matching specific Matcher
assertThat(Stream.of("foo", "bar", "baz", "waldo"), StreamMatchers.anyMatch(containsString("ald")));

// Stream has declared first elements (iterates over whole stream)
assertThat(Stream.of("a","b","c","d","e"), StreamMatchers.startsWith("a", "b", "c"));

// Stream has declared first elements (only pull first 10 values)
assertThat(Stream.iterate(0,i -> i + 1), StreamMatchers.startsWith(Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 10));

// Stream matches all objects within limit
assertThat(Stream.generate(() -> 10), StreamMatchers.startsWithAll(Matchers.equalTo(10), 100));

// Stream matches at least one object within limit
assertThat(Stream.iterate(0, i -> i + 1), StreamMatchers.startsWithAny(Matchers.equalTo(10), 100));
```



## Time

```java
// Time is before another
assertThat(Instant.parse("2014-01-01T00:00:00.00Z"), TimeMatchers.before(Instant.parse("2015-01-01T00:00:00.00Z")));

// Time is after another
assertThat(LocalDate.parse("2015-01-01"), TimeMatchers.after(LocalDate.parse("2014-01-01")));

// Time is between two limits
assertThat(Instant.parse("2015-01-01T00:00:00.00Z"), TimeMatchers.between(
  Instant.parse("2014-01-01T00:00:00.00Z"),
  Instant.parse("2016-01-01T00:00:00.00Z")
));

// Duration is longer than another
assertThat(Duration.ofMinutes(4), TimeMatchers.longerThan(Duration.ofSeconds(4)));

// Duration is shorter than another
assertThat(Duration.ofSeconds(4), TimeMatchers.shorterThan(Duration.ofMinutes(4)));

// Period matches element-wise
assertThat(Period.of(1, 2, 3), TimeMatchers.matches(equalTo(1), equalTo(2), equalTo(3)));
```



## Method references and lambdas

[`Java8Matchers`](https://oss.sonatype.org/service/local/repositories/releases/archive/co/unruly/java-8-matchers/1.6/java-8-matchers-1.6-javadoc.jar/!/co/unruly/matchers/Java8Matchers.html) enables asserting the state of objects of arbitrary types by using method references or lambdas to resolve values which can be matched by other matchers.

Say we have the following domain:

```java
class EmailAddress {
  static EmailAddress verified(String address) {
    return new EmailAddress(address, true);
  }

  static EmailAddress unverified(String address) {
    return new EmailAddress(address, false);
  }

  String address;
  boolean verified;

  private EmailAddress(String address, boolean verified) {
    this.address = address;
    this.verified = verified;
  }

  String getAddress() {
    return address;
  }

  boolean isVerified() {
    return verified;
  }

  // equals and hashCode
}

class ContactInfo {
  String name;
  EmailAddress email;
  Instant expiration;

  String getName() {
    return name;
  }

  Instant getExpiration() {
    return expiration;
  }

  EmailAddress getEmailAddress() {
    return email;
  }
}
```

Following is some examples on how we can assert the state of the domain objects above:

```java
import static co.unruly.matchers.Java8Matchers.where;
import static org.hamcrest.Matchers.is; //regular matcher from the Hamcrest library
...
ContactInfo contactInfo = // resolve ContactInfo from somewhere

// check the name is as expected
assertThat(contactInfo, where(ContactInfo::getName, is("John Doe")));
```

What is the point of performing the assert in this manner instead of just invoking `contactInfo.getName()` directly? It enables the construction of more context information in test failure messages. The Hamcrest Matcher created by the `Java8Matchers.where(..)`-method is able to reflect on the method reference, and, in the event of a failing test, is able to tell both that the object of type `ContactInfo` was not as expected, and it was specifically the method `getName` which yielded the unexpected value.

This is especially helpful when asserting `boolean` values:

```java
import static co.unruly.matchers.Java8Matchers.where;
import static co.unruly.matchers.Java8Matchers.whereNot;
...
assertThat(EmailAddress.verified("john.doe@example.com"), where(EmailAddress::isVerified));

assertThat(EmailAddress.unverified("john.doe@example.com"), whereNot(EmailAddress::isVerified));
```

Using the `where(<predicate>)` or `whereNot(<predicate>)`, instead of the non-informative "expected true, but was false" test failure messages, you will be told that there was an EmailAddress which was expected to be verified, but was not.

As shown above, matchers created using the `where(..)` or `whereNot(..)` will be able to read the method names of _method references_, i.e. on the form `a::method`. However, if resolving values using _lambdas_, i.e. `a -> a.method()`, it is not possible to resolve the actual method which has been invoked, and the failure messages will instead contain the return type from the lambda. If the return type itself is a custom domain type, it will still often be sufficiently specific:

```java
ContactInfo contactInfo = // resolve ContactInfo from somewhere
assertThat(contactInfo, where(c -> c.getEmailAddress(), is(EmailAddress.verified("john.doe@example.com"))));
```
Here, even though using a lambda instead of a method reference, the test failure message will contain that the `EmailAddress` of a `ContactInfo`-object was not as expected.
