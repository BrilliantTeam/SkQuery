package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import com.w00tmast3r.skquery.api.PropertyFrom;
import com.w00tmast3r.skquery.api.PropertyTo;
import com.w00tmast3r.skquery.api.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import com.w00tmast3r.skquery.util.menus.FormattedSlotManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@UsePropertyPatterns
@PropertyFrom("inventory")
@PropertyTo("inventory (title|name)")
public class ExprInventoryName extends SimplePropertyExpression<Inventory, String> {

	@Override
	protected String getPropertyName() {
		return "inventory name";
	}

	@SuppressWarnings("deprecation")
	@Override
	public String convert(Inventory inventory) {
		return inventory.getTitle();
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return Collect.asArray(String.class);
		return null;
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		String title = delta[0] == null ? "" : (String) delta[0];
		for (Inventory inventory : getExpr().getArray(event)) {
			if (inventory.getType() != InventoryType.CHEST)
				continue;
			Inventory copy = Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), title);
			inventory.getViewers().stream()
					.filter(human -> human instanceof Player)
					.map(human -> (Player) human)
					.forEach(player -> {
						FormattedSlotManager.exemptNextClose(player);
						player.openInventory(copy);
					});
			copy.setContents(inventory.getContents());
		}
	}
}
