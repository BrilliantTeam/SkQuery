package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.w00tmast3r.skquery.api.Description;
import com.w00tmast3r.skquery.api.Examples;
import com.w00tmast3r.skquery.api.Name;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skquery.util.Collect;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Client Sign")
@Description("Cause a sign to have custom lines to certain viewers")
@Examples("command /hidesign:;->trigger:;->->make all players see lines of targeted block as \"\", \"\", \"\", \"\"")
@Patterns("make %players% see lines of %blocks% as %string%, %string%, %string%( and|,) %string%")
public class EffClientSign extends Effect {

	private Expression<String> line1, line2, line3, line4;
	private Expression<Player> players;
	private Expression<Block> blocks;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		players = (Expression<Player>) expressions[0];
		blocks = (Expression<Block>) expressions[1];
		line1 = (Expression<String>) expressions[2];
		line2 = (Expression<String>) expressions[3];
		line3 = (Expression<String>) expressions[4];
		line4 = (Expression<String>) expressions[5];
		return true;
	}
	
	@Override
	protected void execute(Event event) {
		String l1 = line1.getSingle(event);
		String l2 = line2.getSingle(event);
		String l3 = line3.getSingle(event);
		String l4 = line4.getSingle(event);
		for (Block block : blocks.getArray(event)) {
			Material material = block.getType();
			if (material == Material.SIGN_POST || material == Material.WALL_SIGN || material == Material.SIGN) {
				for (Player player : players.getArray(event)){
					player.sendSignChange(block.getLocation(), Collect.asArray(l1, l2, l3, l4));
				}
			}
		}
	}

	@Override
	public String toString(Event event, boolean debug) {
		return "client sign for " + players.toString(event, debug);
	}

}
