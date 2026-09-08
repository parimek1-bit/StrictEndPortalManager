package play.strictmc.ru.strictendportalmanager;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Pattern;

import static play.strictmc.ru.strictendportalmanager.StrictEndPortalManager.inst;

public class Utils {
    private static final Pattern HEX_PATTERN =
            Pattern.compile("&#([A-Fa-f0-9]{6})");


    public static String getMessage(String path, Object... placeholders){
        String pref = inst.getConfig().getString("prefix");
        String msg = pref + inst.getConfig().getString(path);
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        for (int i = 0; i < placeholders.length; i +=2) {
            if (i + 1 < placeholders.length) {
                msg = msg.replace(placeholders[i].toString(), placeholders[i+1].toString());
            }
        }
        return msg;
    }

    public static boolean getBoolean(String path) {
        return inst.getConfig().getBoolean(path);
    }

    public static String getString(String path) {
        return inst.getConfig().getString(path);
    }

    public static List<String> getList(String path) {
        return inst.getConfig().getStringList(path);
    }
}
