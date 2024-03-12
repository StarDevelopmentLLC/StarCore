# StarCore
A plugin for integrating my other libraries into Spigot/Paper

[![](https://jitpack.io/v/StarDevelopmentLLC/StarCore.svg)](https://jitpack.io/#StarDevelopmentLLC/StarCore)
## To use this Library
You must add JitPack as a repo, below is for Gradle  
```groovy
repositories {
    maven {
        url = 'https://www.jitpack.io'
    }
}
```  
Then to use this library as a dependency  
```goovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarCore:1.0.0-alpha.1'
}
```  
This plugin shades in StarLib, StarMCLib, StarClock and XSeries without any relocations. If you depend on this plugin, you will have access to all of those libraries. Please see below to view the versions that is provided by this plugin.  
StarCore version: `1.0.0-alpha.3`  

| Library | Version |
| ------- | ------- | 
| StarLib | 1.0.0-alpha.2 |
| StarMCLib | 1.0.0-alpha.9 |
| StarClock | 1.0.0-alpha.2 |
| StarSQL-ALL | 1.0.0-alpha.3 |
