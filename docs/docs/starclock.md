# StarClock Integration
StarCore registers and provides an instance of the `ClockManager` to the Bukkit ServicesManager as well as registering the `ClockRunnable` to the Scheduler.  

You can obtain this instance by the following.  
```java 
RegisteredServiceProvider<ClockManager> rsp = Bukkit.getServicesManager().getRegistration(ClockManager.class);
if (rsp != null) {
    ClockManager clockManager = rsp.getProvider();
}
```
Change the variables as needed for your use case, but it is best practice to have a null-check on the provider. Before trying to get the instance.  