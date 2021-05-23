package com.w00tmast3r.skquery.elements.expressions;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.w00tmast3r.skquery.annotations.Patterns;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.parser.ParserInstance;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;


@Patterns("[the] (destroyed|exploded|boom boomed) blocks")
public class ExprExplodedBlocks extends SimpleExpression<Block> {

    @Override
    protected Block[] get(Event event) {
        List<Block> blockList = ((EntityExplodeEvent) event).blockList();
        return blockList.toArray(new Block[blockList.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Block> getReturnType() {
        return Block.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "boom boom blocks";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (!ParserInstance.get().isCurrentEvent(EntityExplodeEvent.class)) {
            Skript.error("Boom Boomed Blocks can only be used in an explode event.");
            return false;
        }
        return true;
    }
}
