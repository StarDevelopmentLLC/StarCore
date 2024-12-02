package com.stardevllc.starcore.utils.cmdflags;

import java.util.*;

public class CmdFlags {
    protected Set<Flag> flags = new LinkedHashSet<>(); //This is just a list of valid flags for the command
    
    protected Map<Flag, Object> flagValues = new HashMap<>(); //This is the values of the flags after parsing
    
    public CmdFlags(Set<Flag> flags) {
        this.flags.addAll(flags);
    }
    
    public CmdFlags(Flag... flags) {
        if (flags != null) {
            this.flags.addAll(List.of(flags));
        }
    }
    
    public String[] parse(String[] args) {
        LinkedList<String> argsList = new LinkedList<>(List.of(args));
        ListIterator<String> iterator = argsList.listIterator();
        
        while (iterator.hasNext()) {
            String arg = iterator.next();

            for (Flag flag : this.flags) {
                if (arg.startsWith("-" + flag.id())) {
                    if (flag.type() == FlagType.PRESENCE) {
                        flagValues.put(flag, flag.valueIfPresent());
                        iterator.remove();
                        break;
                    } else if (flag.type() == FlagType.COMPLEX) {
                        iterator.remove();
                        if (iterator.hasNext()) {
                            StringBuilder value = new StringBuilder(iterator.next());
                            iterator.remove();
                            if (!value.toString().startsWith("\"")) {
                                flagValues.put(flag, value.toString());
                            } else {
                                while (iterator.hasNext()) {
                                    String next = iterator.next();
                                    if (next.endsWith("\"")) {
                                        value.append(" ").append(next);
                                        iterator.remove();
                                        break;
                                    } else {
                                        value.append(" ").append(next);
                                        iterator.remove();
                                    }
                                }
                                flagValues.put(flag, value.toString().replace("\"", ""));
                            }
                        } else {
                            flagValues.put(flag, flag.defaultValue());
                        }
                        break;
                    }
                }
            }
        }

        for (Flag flag : this.flags) {
            if (!this.flagValues.containsKey(flag)) {
                if (flag.type() == FlagType.PRESENCE) {
                    this.flagValues.put(flag, flag.valueIfNotPresent());
                } else if (flag.type() == FlagType.COMPLEX) {
                    this.flagValues.put(flag, flag.defaultValue());
                }
            }
        }
        
        return argsList.toArray(new String[0]);
    }

    public Map<Flag, Object> getFlagValues() {
        return flagValues;
    }
}