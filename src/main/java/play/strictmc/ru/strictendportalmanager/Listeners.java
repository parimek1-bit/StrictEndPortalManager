package play.strictmc.ru.strictendportalmanager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import static play.strictmc.ru.strictendportalmanager.Utils.*;

public class Listeners implements Listener {

    @EventHandler
    public void onInteractPortal(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission(getString("end-block.bypass.permission"))) {
            return;
        }
        if (getBoolean("end-block.enable")) {
            if (e.getClickedBlock() != null) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.END_PORTAL_FRAME) {
                    ItemStack i = e.getItem();
                    if (i != null && i.getType() == Material.ENDER_EYE) {
                        e.setCancelled(true);
                        if (getBoolean("end-block.message.enable")) {
                            e.getPlayer().sendMessage(getMessage("end-block." +
                                    "message.message"));
                        }
                    }

                }
            }
        }
    }

    @EventHandler
    public void onBanan(BlockDispenseEvent e) {
        if (getBoolean("end-block.enable")) {
            ItemStack d = e.getItem();
            if (d.getType() == Material.ENDER_EYE) {
                Block t = e.getBlock();
                if (t != null && t.getType() == Material.END_PORTAL_FRAME) {
                    e.setCancelled(true);
                }

            }
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (getBoolean("end-block.enable")) {
            Block b = e.getBlock();
            if (b.getType() == Material.END_PORTAL_FRAME) {
                EndPortalFrame fD = (EndPortalFrame) b.getBlockData();
                if (fD.hasEye()) {
                    EndPortalFrame nD = (EndPortalFrame) Material.END_PORTAL_FRAME
                            .createBlockData();
                    nD.setFacing(fD.getFacing());
                    b.setBlockData(nD, false);
                }

            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission(getString("end-block.bypass.permission"))) {
            return;
        }
        if (getBoolean("end-block.enable")) {
            if (e.getTo() != null) {
                World fW = p.getWorld();
                World tW = e.getTo().getWorld();
                if (tW.getEnvironment() == World.Environment.THE_END &&
                        fW.getEnvironment() != World.Environment.THE_END) {
                    e.setCancelled(true);
                    if (getBoolean("end-block.message.enable")) {
                        p.getPlayer().sendMessage(getMessage("end-block." +
                                "message.message"));
                    }
                }

            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission(getString("end-block.bypass.permission"))) {
            return;
        }
        if (getBoolean("end-block.enable")) {
            if (p.getWorld().getEnvironment() == World.Environment.THE_END) {
                if (!getBoolean("end-block.player-join-on-world-the-end." +
                        "command-by-console")) {
                    Bukkit.dispatchCommand(p,
                            getString("end-block.player-join-on-world-the-end." +
                                    "command"));
                } else {
                    String cmd = "end-block.player-join-on-world-the-end.command";
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            getString("end-block.player-join-on-world-the-end." +
                                    "command")
                                    .replace("{player}", p.getName()));
                }
                if (getBoolean("end-block.message.enable")) {
                    p.getPlayer().sendMessage(getMessage("end-block." +
                            "message.message"));
                }
            }
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (getBoolean("end-portal-frame-replace.enable")) {
            if (getBoolean("end-portal-frame-replace.permissions" +
                    ".enable")) {
                if (p.hasPermission(getString("end-portal-frame-replace.permissions" +
                        ".permission"))) {
                    portalPlace(p, b);
                } else if (getBoolean("end-portal-frame-replace.permissions" +
                        ".message.enable")) {
                    p.sendMessage(getMessage("end-portal-frame-replace.permissions" +
                            ".message.message"));
                }
            } else {
                portalPlace(p, b);
            }
        }
    }

    public static void portalPlace(Player p, Block b) {
        if (b.getType().equals(Material.END_PORTAL_FRAME)) {
            if (getBoolean("end-portal-frame-replace.message.enable")) {
                Location loc = b.getLocation();
                p.sendMessage(getMessage("end-portal-frame-replace.message" +
                        ".message", "{x}", loc.getBlockX(),
                        "{y}", loc.getBlockY(), "{z}", loc.getBlockZ()));
            }
            b.setType(Material.END_PORTAL);
        }
    }


}
