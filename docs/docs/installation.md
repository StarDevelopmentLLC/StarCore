---
hide:
  - navigation
---

# Installation
StarCore runs as a plugin and is only intended to be run as a plugin. StarCore does provide downstream dependencies for the shaded Star Development libraries.  
There should be no need to depend on them directly.  

## Choosing a Build Tool
The two primary build tools for Java are Maven and Gradle. Pick one and stick with it.

## JitPack Repository
Star Development LLC uses JitPack as the repository for development. You must have this repository in whichever build tool you wish to use.
### Maven
```xml 
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

## StarCore Dependency Information
**Replace `{VERSION}` with the version of StarCore you are using!**
### Maven
```xml
<dependency>
    <groupId>com.github.StarDevelopmentLLC</groupId>
    <artifactId>StarCore</artifactId>
    <version>{VERSION}</version>
    <scope>provided</scope>
</dependency>
```
### Gradle
```groovy
dependencies {
    compileOnly 'com.github.StarDevelopmentLLC:StarLib:{VERSION}'
}
```

After you are done installing StarCore, take a look at tabs above for the different aspects of this plugin. 