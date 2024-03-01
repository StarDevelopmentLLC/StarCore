# StarSQL Integration
StarCore provides access to all 4 of the supported SQL Types that StarSQL implements.  

| Name       | Version |
|------------|---------| 
| MySQL      | 8.1.0   | 
| H2         | 2.2.220 | 
| PostgreSQL | 42.6.0  |
| SQLite     | 43.0.0  |

StarCore provides a default instance for `DatabaseRegistry` to the ServicesManager.  
```java
RegisteredServiceProvider<DatabaseRegistry<?>> rsp = Bukkit.getServicesManager().getRegistration(DatabaseRegistry.class);
if (rsp != null) {
    DatabaseRegistry<?> databaseRegistry = rsp.getProvider();
    SQLDatabaseRegistry sqlDatabaseRegistry = (SQLDatabaseRegistry) databaseRegistry;
}
```

Please note: This was intended to be used with multiple types of databases outside of SQL, and I may still do that, however the instance is a `SQLDatabaseRegistry`.