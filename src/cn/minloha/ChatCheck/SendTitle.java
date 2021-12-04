package cn.minloha.ChatCheck;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class SendTitle {
    static String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    public static Class<?> getNmsClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + version + "." + name);
    }
    private static void sendPacket(Player player, Object packet) throws Exception {
        Object handle = player.getClass().getMethod("getHandle").invoke(player);
        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
        playerConnection.getClass().getMethod("sendPacket", getNmsClass("Packet")).invoke(playerConnection, packet);
    }
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) throws Exception {
        Object enumTitle = getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object)null);
        Object titleChat = getNmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
        Object enumSubtitle = getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object)null);
        Object subtitleChat = getNmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + subtitle + "\"}");
        Constructor<?> titleConstructor = getNmsClass("PacketPlayOutTitle").getConstructor(getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNmsClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
        Object titlePacket = titleConstructor.newInstance(enumTitle, titleChat, fadeIn, stay, fadeOut);
        Object subtitlePacket = titleConstructor.newInstance(enumSubtitle, subtitleChat, fadeIn, stay, fadeOut);
        sendPacket(player, titlePacket);
        sendPacket(player, subtitlePacket);
    }
}
