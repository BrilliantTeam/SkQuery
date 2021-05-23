package com.w00tmast3r.skquery.elements.effects.base;

import com.w00tmast3r.skquery.elements.events.lang.ScriptOptionsEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.util.Kleenean;

public abstract class OptionsPragma extends Pragma {

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(ScriptOptionsEvent.class)) {
            Skript.error("Pragma cannot be declared outside of script options.");
            return false;
        }
        super.init(expressions, i, kleenean, parseResult);
        return true;
    }
}
