package play.strictmc.ru.strictendportalmanager;

import org.bukkit.plugin.java.JavaPlugin;

public final class StrictEndPortalManager extends JavaPlugin {

    public static StrictEndPortalManager inst;
    public static StrictEndPortalManager getInstance(){
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Listeners(), this);

    }

    @Override
    public void onDisable() {
        //Тут пуста
    }
}
