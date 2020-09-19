package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.annotations.Patterns;
import com.w00tmast3r.skquery.util.Collect;

import org.bukkit.event.Event;

import java.util.UUID;


@Patterns("[new] (guid|uuid)")
public class ExprNewUUID extends SimpleExpression<String> {

    @Override
    protected String[] get(Event event) {
        return Collect.asArray(UUID.randomUUID().toString());
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "uuid";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
