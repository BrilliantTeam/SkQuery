package com.w00tmast3r.skquery.elements.effects;

import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.SkriptParser;

import com.w00tmast3r.skquery.annotations.Description;
import com.w00tmast3r.skquery.annotations.Examples;
import com.w00tmast3r.skquery.annotations.Patterns;
import com.w00tmast3r.skquery.elements.effects.base.OptionsPragma;
import com.w00tmast3r.skquery.skript.PermissionsHandler;

import java.io.File;

@Name("skQueryPerms Option")
@Description("Enables skQuery as a permissions manager. You will then be able to access expressions that represent player permissions and change them with Skript.")
@Examples("script options:;->$ use permissions")
@Patterns("$ use permissions")
public class EffOptionSkriptPermissionsManager extends OptionsPragma {

	@Override
	protected void register(File executingScript, SkriptParser.ParseResult parseResult) {
		PermissionsHandler.enable();
	}

}
