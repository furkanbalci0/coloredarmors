package com.furkan.coloredarmors.command

import com.hakan.core.command.executors.base.BaseCommand
import com.hakan.core.command.executors.sub.SubCommand
import com.hakan.core.utils.yaml.HYaml
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import com.furkan.coloredarmors.ColoredArmorsPlugin
import com.furkan.coloredarmors.data.DataManager
import com.hakan.core.command.HCommandAdapter

@BaseCommand(name = "discoarmor", usage = "/discoarmor", aliases = ["coloredarmors", "ca", "da", "carmor", "darmor"])
class CommandManager : HCommandAdapter {

    @SubCommand
    fun main(sender: CommandSender, args: Array<String>) {

        if (sender is ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED.toString() + "This command can only be executed by players.")
            return
        }

        //Get yaml.
        val yaml: HYaml = ColoredArmorsPlugin.yaml

        if (!sender.hasPermission("coloredarmors.use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', yaml.getString("messages.no-permission")))
            return
        }

        val player: Player = sender as Player
        if (player.uniqueId in DataManager.players) {
            DataManager.players.remove(player.uniqueId)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', yaml.getString("messages.disable-armor")))
            player.playSound(player.location, Sound.NOTE_PLING, 1f, 1f)
        } else {
            DataManager.players.add(player.uniqueId)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', yaml.getString("messages.enable-armor")))
            player.playSound(player.location, Sound.NOTE_PLING, 1f, 1f)
        }
    }
}