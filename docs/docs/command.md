---
hide:
  - navigation
---

# The Command
There is a single command for StarCore and it only focuses on reloading the config as well as managing custom color codes.  
For server owner defined colors, you must be using plugins that support `ColorUtils` from [StarMCLib](https://www.spigotmc.org/resources/starmclib.113571/)  
I (Firestar311) have a chat plugin that is in progress that supports this, however, it is not yet released (As of 3/1/24). 

The command itself requires the permission `starcore.admin` as a base. 

## Reload Sub command
The reload sub command allows you to reload the configs without having to restart the server. This does not save `config.yml` but does save `colors.yml`  
Permission: `starcore.admin.reload`  
Usage: `/starcore reload`

## Color Sub Command
The color sub command allows management of all colors from StarMCLib's ColorUtils. However, one thing to note, only colors **added** via this command will be saved to the `colors.yml` file if the config option is true.  
The base permission for this command is `starcore.admin.color`  

### `/starcore color listsymbols`
This command shows all valid symbols that are used as a prefix to color codes.  
You are not restricted to just the `&` symbol with this utility. I did this to make it so that I can alias colors.  

### `/starcore color listcolors`
This command lists all of the custom colors registered based on their chat code, hex value and which plugin it came from.  

### `/starcore color add <code> <hex> [permission]`
This command allows you to add a custom color based on a two character `code`, a 3 or 6 character `hex` value (prefixed by a `#`) and an optional `permission`.  
The first part of the `code` must be one of the valid symbols under the listsymbols command.  
The HEX Value must be prefixed with a `#` and have 3 or 6 characters that range from 0-9,A-F (Case insensitive).  
The permission can be anything you want. However it is only respected if a plugin decides to support this functionality. Also messages sent by plugins directly do not support this either. Please check the plugin documentation that is using this utility to see if they support ColorUtils Permissions.  
Command Permission: `starcore.admin.color.add`

### `/starcore color remove <code>`
This command allows you to remove a color based on the `code`. This can remove ANY of the custom colors, even ones not from StarCore. Please be careful with this as it can possibly break other plugin formatting.  
Permission: `starcore.admin.color.remove`