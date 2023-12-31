package de.calitha.croller;

import org.bukkit.plugin.java.JavaPlugin;

public final class CalithaRoll extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("CalithaRoll wurde aktiviert.");

        this.getCommand("roll").setExecutor(new de.calitha.croll.RollCommand(false));
        this.getCommand("gmroll").setExecutor(new de.calitha.croll.RollCommand(true));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("CalithaRoll wurde deaktiviert.");
    }
}
