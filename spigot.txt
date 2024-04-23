[COLOR=#ff0000][B]This plugin is mostly intended for plugin developers, there is little in terms of features and even those features require supporting plugins to use them[/B][/COLOR]

[B]Dependencies[/B]
Java 17
[URL='https://www.spigotmc.org/resources/nbt-api.7939/']NBT API 2.12.3[/URL]

[B]Provides the following[/B]
[URL='https://www.spigotmc.org/resources/starlib.106562/']StarLib 1.0.0-alpha.11[/URL]
[URL='https://www.spigotmc.org/resources/starclock.110553/']StarClock 1.0.0-alpha.10[/URL]
[URL='https://www.spigotmc.org/resources/starsql.110548/']StarSQL 1.0.0-alpha.9[/URL]
[URL='https://www.spigotmc.org/threads/%E2%9A%A1boostedyaml-feature-rich-library-write-once-run-everywhere-updater-comments-and-more-%E2%9A%A1.545585/']BoostedYAML 1.3[/URL]
[URL='https://www.spigotmc.org/threads/xseries-xmaterial-xparticle-xsound-xpotion-titles-actionbar-etc.378136/']XSeries 9.7.0[/URL]

[B]Basic Information[/B]
StarCore is a library that shades in the 3 other libraries made by me and also has BoostedYAML and XSeries that it uses for several of the utilities within the plugin.

[B]Installation - Server Owners[/B]
Download the plugin and place it in your plugins folder and [B]restart[/B] the server to apply everything. You can configure the behavior of the plugin in the config.yml file. This file is documented using comments and generated when it loads.

[B]Installation - Plugin Developers[/B]
I strongly encourage the use of a build tool like Gradle or Maven. You can find the details needed to fill in what you need in your build tool.
[B]Repository[/B]: [URL='https://www.jitpack.io/']https://www.jitpack.io[/URL]
[B]Group[/B]: com.github.StarDevelopmentLLC
[B]Artifact[/B]: StarCore
[B]Version[/B]: 1.0.0-alpha.26
This should be compileOnly for Gradle and provided scope for Maven, its a plugin.

[B]Command[/B]
Base permission: starcore.admin
The plugin provides a basic command to manage things

/starcore color
- Permission: starcore.admin.color
/starcore color list symbols
- Permission: starcore.admin.color.list
/starcore color list codes
- Permission: starcore.admin.color.list
/starcore color add <code> <hex> [permission]
[I]Only supported on 1.16 and above[/I]
- Permission: starcore.admin.color.add
/starcore color remove <code> 
- Permission: starcore.admin.color.remove
/starcore reload
- Permission: starcore.admin.reload
/starcore gui list
- Permission: starcore.admin.gui.lsit

[B]Single Class Utilities[/B]
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/utils/StarThread.java']StarThread [/URL]- This is a wrapper for BukkitRunnable that has timing support that can go down to the nano-second precision. It keeps track of the last 100 runs and has averages, mins and maxes for the timing as well.
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/utils/Cuboid.java']Cuboid [/URL]- This class represents a cubiod structure. It is meant to be a very simple class that is interacted with in code. It does detect which of the two points are actually the minimum and maximum points and primarily operates on integers and doubles for most of the methods and doesn't touch the World or Location objects very much.
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/utils/NMSVersion.java']NMSVersion [/URL]- This is a simple utility that auto-detects the current NMS Version and stores it as an Enum value. I got tired of manually doing this check all the time.
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/utils/Position.java']Position [/URL]- This class is a simple thing for storing x,y,z,yaw,pitch that is not tied directly to a World. I use this in a minigame project that I am working on, and it is useful as most of the time you don't need a full Location object. It also provides methods to convert to a location and convert from a location as well.
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/utils/ServerProperties.java']ServerProperties [/URL]- This class allows you to [B]read[/B] from the server.properties file without having to other weird things. Just a static class that I can update and maintain for multiple versions
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/utils/Config.java']Config [/URL]- A wrapper for the BoostedYAML utility. This mainly just adds a method to create a default value with comments in a single line.
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/actor/Actor.java']Actor [/URL]- This utility is a class that wraps a Player, ConsoleSender, and a Plugin into a single utility to allow easier support for this kind of needs. This is used in a WIP plugin that will be released soon. Use Actor.of(Object) to get a generic instance. This class will cache instances.
[URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/color/ColorUtils.java']ColorUtils [/URL]- This class allows support for different methods of coloring text in the Minecraft Context. On 1.16+ it allows you to use HEX Colors. You can also define your own colors (Either via command or in code) that can be used as long as the sending plugin supports using this class. You can also control these with permissions.
- Spigot colors have the permission: starcore.color.spigot.<name>
   - Note: This will probably change, this utility was imported from a separate utility that I merged into this plugin
- Plugins that add their own colors can specify their own permissions
- Colors added via command can also specify the permissions (See the command for details)
- HEX Colors have an overall permission: starmclib.color.hex
   - This only controls the overall ability to use hex colors, if you want individual control over hex codes, use a CustomColor (Command or plugin)
- On 1.16+, there is a material color set based on the colors that are Bedrock Only on the Minecraft Wiki.
There are two methods of support for this, plugins can choose to use permission checks, or to not to use permission checks. Please double check plugin documentation.

[B]Utility Implementation[/B]
StarCore implements the TaskFactory utility from StarLib and registers it to the ServicesManager and the Scheduler. This can be obtained using
[code=Java]TaskFactory taskFactory = Bukkit.getServicesManager().getRegistration(TaskFactory.class).getProvider();[/code]

[B][URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/item/ItemBuilder.java']ItemBuilder[/URL]Utility[/B]
This utility allows you to create items using a Builder style pattern. This utility has sub classes for the different item meta to allow type-safe building of items.
ItemBuilder uses XMaterial to provide cross-version support
The ItemBuilder utility also allows you to save and load items from YAML Files. The multiple sub-classes allow for easier implementation of this functionality.
You can create and ItemBuilder from an ItemStack instance.
The ItemBuilder class also supports custom NBT using NBT API. The NBT methods do not allow you to change vanilla NBT tags though. This is intentionally done.

[B][URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/objecttester/ObjectCommand.java']ObjectTester [/URL]Utility[/B]
This utility is entirely a Developer Utiltiy. It basically allows you to see and modify fields within an object using commands. I am planning on reworking how this works, and I definitely need a better name for this utiltiy. You are welcome to suggest names and ideas for this. I also want to add a GUI for this to more visually see things, but I am not entirely sure on that one quite yet. You have to register the command and set the executor to the ObjectTester instance. It uses reflection to get the information, so it should be fine to use on pretty much any class.

[B][URL='https://github.com/StarDevelopmentLLC/StarCore/tree/main/src/main/java/com/stardevllc/starcore/gui']GUI Framework[/URL][/B]
StarCore provides a framework for creating GUIs. You can use the interface [URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/gui/handler/InventoryHandler.java']InventoryHandler [/URL]to do things and it will work with the system, you just have to manually register the things to the [URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/gui/GuiManager.java']GuiManager [/URL](See the methods in GuiManager for how this is done).
Or you can use [URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/gui/gui/InventoryGUI.java']InventoryGUI [/URL]which has some built-in features for doing this already. You interact with it by using [URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/gui/element/Element.java']Elements [/URL]or any sub-class of Element, which includes a [URL='https://github.com/StarDevelopmentLLC/StarCore/blob/main/src/main/java/com/stardevllc/starcore/gui/element/button/Button.java']Button [/URL]class.
The GuiManager is obtained through the ServicesManager, or you can create your own instance and register the listener (GuiManager is a Listener)

[I]This plugin is provided free of charge and under the MIT Open Source License. I will never sell this library anywhere. If you see it being sold, it is not by me and please report any of these cases to me directly. This library is only published as a GitHub Release and on SpigotMC, other sources are to be considered use at your own risk.[/I]