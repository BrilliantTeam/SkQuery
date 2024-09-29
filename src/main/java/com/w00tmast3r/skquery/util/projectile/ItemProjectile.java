package com.w00tmast3r.skquery.util.projectile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.util.CancellableBukkitTask;

import ch.njol.skript.aliases.ItemType;

public class ItemProjectile {

	private final ItemType projectile;

	public ItemProjectile(ItemType projectile) {
		this.projectile = projectile;
	}

	public ItemType getProjectile() {
		return projectile;
	}

	public ItemProjectile shoot(LivingEntity shooter, Vector velocity) {
		Item item = shooter.getWorld().dropItem(shooter.getEyeLocation(), projectile.getRandom());
		item.setVelocity(velocity);
		item.setPickupDelay(Integer.MAX_VALUE);
		CancellableBukkitTask task = new CancellableBukkitTask() {
			@Override
			public void run() {
				if (!item.isValid() || (!item.getNearbyEntities(0, 0, 0).isEmpty() && !item.getNearbyEntities(0, 0, 0).contains(shooter)) || item.isOnGround()) {
					Bukkit.getPluginManager().callEvent(new ItemProjectileHitEvent(item, shooter));
					item.remove();
					cancel();
				}
			}
		};

		task.setTaskId(item.getScheduler().runAtFixedRate(SkQuery.getInstance(), (ignored) -> task.run(), null, 1L, 1L));
		return this;
	}

}
