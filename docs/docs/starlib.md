# StarLib Integration
This plugin provides StarLib as a shaded library so plugins depending on StarCore have access to StarLib.  
The only direct implementation of a StarLib resource is the `TaskFactory` and the instance is provided by StarMCLib and registered to the ServicesManager. 

```java
RegisteredServiceProvider<TaskFactory> rsp = Bukkit.getServicesManager().getRegistration(TaskFactory.class);
if (rsp != null) {
TaskFactory taskFactory = rsp.getProvider();
}
```

Otherwise the others are just classes you can use that don't need specific registration. 