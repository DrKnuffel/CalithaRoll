package de.calitha.croller;

import org.bukkit.plugin.java.JavaPlugin;

public final class CalithaRoll extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("CalithaRoll wurde aktiviert.");

        RollCommand rollCommand = new RollCommand(false);
        RollCommand gmRollcommand = new RollCommand()(true);

        this.getCommand("roll").setExecutor(new RollCommand(false));
        this.getCommand("gmroll").setExecutor(new RollCommand(true));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CalithaRoll wurde deaktiviert.");
    }
}
