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
    implementation 'com.github.StarDevelopmentLLC:StarCore:1.0.0-alpha.15'
}
```  
This plugin shades in StarLib, StarMCLib, StarClock and XSeries without any relocations. If you depend on this plugin, you will have access to all of those libraries. Please see below to view the versions that is provided by this plugin.  
StarCore version: `1.0.0-alpha.15`  

| Library | Version        |
| ------- |----------------| 
| StarLib | 1.0.0-alpha.4  |
| StarMCLib | 1.0.0-alpha.12 |
| StarClock | 1.0.0-alpha.4  |
| StarSQL | 1.0.0-alpha.5  |
