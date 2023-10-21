package dev.xernas.loupgarou;

import dev.xernas.loupgarou.commands.LgCommand;
import dev.xernas.loupgarou.events.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LoupGarou extends JavaPlugin {

    private static LoupGarou instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
        Objects.requireNonNull(this.getCommand("lg")).setExecutor(new LgCommand());
        Objects.requireNonNull(this.getCommand("lg")).setTabCompleter(new LgCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LoupGarou getInstance() {
        return instance;
    }
}
