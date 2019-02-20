package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;

import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;

@UsePropertyPatterns
@PropertyFrom("entities")
@PropertyTo("(primer|fuse lighting piece of shit)")
public class ExprTntSource extends SimplePropertyExpression<Entity, Entity> {

    @Override
    protected String getPropertyName() {
        return "tnt source";
    }

    @Override
    public Entity convert(Entity entity) {
        return entity instanceof TNTPrimed ? ((TNTPrimed) entity).getSource() : null;
    }

    @Override
    public Class<? extends Entity> getReturnType() {
        return Entity.class;
    }
}
