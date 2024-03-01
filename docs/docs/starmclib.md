# StarMCLib Integration
StarCore provides integration and support for things withing StarMCLib. Please see below for what these are.  

## ColorUtils
This plugin provides access to the ColorUtils feature and a [command](command.md) to manage them.  
There are plans to change up ColorUtils, and that will be documented here in the future.  

## ServerActor
The `ServerActor` class in StarMCLib allows cusomizing the Console UUID. StarCore has a config option for this and sets it when StarCore loads.  
StarCore also registers the `ServerActor` instance to the ServicesManager as well. Below is a code snippet.
```java 
RegisteredServiceProvider<ServerActor> rsp = Bukkit.getServicesManager().getRegistration(ServerActor.class);
if (rsp != null) {
    ServerActor serverActor = rsp.getProvider();
}
```