package com.stardevllc.minecraft.command.argument;

import com.stardevllc.minecraft.command.CommandContext;

import java.util.function.*;

public class ArgFilter<T> {
    protected final BiPredicate<T, CommandContext> predicate;
    protected final BiConsumer<T, CommandContext> success, fail;
    
    public ArgFilter(BiPredicate<T, CommandContext> predicate, BiConsumer<T, CommandContext> success, BiConsumer<T, CommandContext> fail) {
        this.predicate = predicate;
        this.success = success;
        this.fail = fail;
    }
    
    public void test(T t, CommandContext context) {
        if (t == null) {
            return;
        }
        
        if (predicate.test(t, context)) {
            if (success != null) {
                success.accept(t, context);
            }
        } else {
            if (fail != null) {
                fail.accept(t, context);
            }
        }
    }
}