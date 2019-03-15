package com.w00tmast3r.skquery.util.projectile;

import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.util.CancellableBukkitTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemProjectile {

	private ItemStack projectile;

	public ItemProjectile(ItemStack projectile) {
		this.projectile = projectile;
	}

	public ItemStack getProjectile() {
		return projectile;
	}

	public void setProjectile(ItemStack projectile) {
		this.projectile = projectile;
	}

	public ItemProjectile shoot(LivingEntity shooter, Vector velocity) {
		Item item = shooter.getWorld().dropItem(shooter.getEyeLocation(), projectile);
		item.setVelocity(velocity);
		item.setPickupDelay(Integer.MAX_VALUE);
		CancellableBukkitTask taskBukkit = new CancellableBukkitTask() {
			@Override
			public void run() {
				if (!item.isValid() || (item.getNearbyEntities(0, 0, 0).size() != 0 && !item.getNearbyEntities(0, 0, 0).contains(shooter)) || item.isOnGround()) {
					Bukkit.getPluginManager().callEvent(new ItemProjectileHitEvent(item, shooter));
					item.remove();
					cancel();
				}
			}
		};
		taskBukkit.setTaskId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SkQuery.getInstance(), taskBukkit, 0, 1));
		return this;
	}

}
