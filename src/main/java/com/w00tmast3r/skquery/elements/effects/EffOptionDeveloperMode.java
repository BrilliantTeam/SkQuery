package com.w00tmast3r.skquery.elements.effects;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.w00tmast3r.skquery.SkQuery;
import com.w00tmast3r.skquery.annotations.Description;
import com.w00tmast3r.skquery.annotations.Examples;
import com.w00tmast3r.skquery.annotations.Patterns;
import com.w00tmast3r.skquery.elements.effects.base.OptionsPragma;
import com.w00tmast3r.skquery.util.CancellableBukkitTask;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.OpenCloseable;

@Name("Developer Mode Option")
@Description("Enable the developer mode pragma to auto reload a script as it changes.  This must be placed under the script-local options.")
@Examples("script options:;->$ developer mode")
@Patterns("$ developer mode")
public class EffOptionDeveloperMode extends OptionsPragma {

	private long lastUpdated;

	@Override
	protected void register(final File executingScript, final SkriptParser.ParseResult parseResult) {
		lastUpdated = executingScript.lastModified();
		CancellableBukkitTask task = new CancellableBukkitTask() {
			@Override
			public void run() {
				if (lastUpdated != executingScript.lastModified()) {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6SkQuery&7] &r(Dev Mode) Starting auto-reload of script '" + executingScript.getName() + "'"));
					try {
						Method unloadScript = ScriptLoader.class.getDeclaredMethod("unloadScript", File.class);
						unloadScript.setAccessible(true);
						unloadScript.invoke(null, executingScript);
						return;
					} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {}
					try {
						Method unloadScript = ScriptLoader.class.getDeclaredMethod("loadScripts", File.class);
						unloadScript.setAccessible(true);
						unloadScript.invoke(null, executingScript);
						return;
					} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {}
					try {
						ScriptLoader.loadScripts(executingScript, OpenCloseable.EMPTY).get();
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6SkQuery&7] &r(Dev Mode) '" + executingScript.getName() + "' has been reloaded."));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
					cancel();
				}
			}
		};
		task.setTaskId(Bukkit.getGlobalRegionScheduler().runAtFixedRate(SkQuery.getInstance(), (ignored) -> task.run(), 1, 100));
	}

}
