package com.w00tmast3r.skquery.elements.expressions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.eclipse.jdt.annotation.Nullable;

import com.w00tmast3r.skquery.annotations.PropertyFrom;
import com.w00tmast3r.skquery.annotations.PropertyTo;
import com.w00tmast3r.skquery.annotations.UsePropertyPatterns;
import com.w00tmast3r.skquery.util.Collect;
import com.w00tmast3r.skquery.util.menus.FormattedSlotManager;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;

@UsePropertyPatterns
@PropertyFrom("inventories")
@PropertyTo("inventory (title|name)[s]")
public class ExprInventoryNameContainer extends PropertyExpression<Inventory, String> {

	private final static boolean older = Skript.methodExists(Inventory.class, "getTitle");
	private Method method;

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (older)
			try {
				method = Inventory.class.getMethod("getTitle");
				method.setAccessible(true);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				Skript.error("There was an error attempting to grab the inventory name. " + Skript.getMinecraftVersion().toString());
				return false;
			}
		setExpr((Expression<Inventory>) exprs[0]);
		return true;
	}

	@Override
	protected String[] get(Event event, Inventory[] source) {
		return Arrays.stream(source).map(inventory -> {
			if (older)
				try {
					return (String) method.invoke(inventory);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					return null;
				}
			InventoryHolder holder = inventory.getHolder();
			if (holder == null || !(holder instanceof Container)) {
				if (event instanceof InventoryClickEvent) {
					InventoryClickEvent inventoryEvent = ((InventoryClickEvent)event);
					if (inventoryEvent.getClickedInventory() == inventory)
						return inventoryEvent.getView().getTitle();
				}
				Skript.error("In 1.13+ you cannot get the title name of an inventory outside of an InventoryClickEvent, Only if this inventory has an InventoryHolder may it be used in this state.", ErrorQuality.SEMANTIC_ERROR);
				return null;
			}
			return ((Container)holder).getCustomName();
		}).toArray(String[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "inventory names of " + getExpr().toString(event, debug);
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
			inventory.getViewers().parallelStream()
					.map(human -> (Player) human)
					.forEach(player -> {
						FormattedSlotManager.exemptNextClose(player);
						player.openInventory(copy);
					});
			copy.setContents(inventory.getContents());
		}
	}

}
