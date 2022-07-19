package com.furkan.coloredarmors.tasks

import com.hakan.core.HCore
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import com.furkan.coloredarmors.ColoredArmorsPlugin
import com.furkan.coloredarmors.data.DataManager.Companion.players
import java.util.*
import kotlin.random.Random

class TaskManager {

    companion object {

        private val updateTime: Int = ColoredArmorsPlugin.yaml.getInt("update-time")

        init {
            //Check update time.
            if (updateTime <= 0) {
                throw IllegalArgumentException("Update time must be greater than 0.")
            }
        }

        private val task = HCore.syncScheduler().every(updateTime.toLong())

        fun initialize() {

            task.run { ->

                for (uuid: UUID in players) {

                    val player: Player? = Bukkit.getPlayer(uuid)

                    //If player is leaves the server, remove him from players list.
                    if (player == null) {
                        players.remove(uuid)
                        continue
                    }

                    for (itemStack: ItemStack in player.inventory.armorContents) {
                        val material: Material = itemStack.type
                        if (material == Material.AIR) continue
                        if (itemStack.itemMeta !is LeatherArmorMeta) continue

                        val meta: LeatherArmorMeta = itemStack.itemMeta as LeatherArmorMeta

                        //Set color.
                        meta.color = Color.fromRGB(
                            Random.nextInt(0, 255),
                            Random.nextInt(0, 255),
                            Random.nextInt(0, 255)
                        )
                        itemStack.itemMeta = meta

                        //Set item.
                        when (material) {
                            Material.LEATHER_BOOTS -> player.inventory.boots = itemStack
                            Material.LEATHER_LEGGINGS -> player.inventory.leggings = itemStack
                            Material.LEATHER_CHESTPLATE -> player.inventory.chestplate = itemStack
                            Material.LEATHER_HELMET -> player.inventory.helmet = itemStack
                            else -> {}
                        }

                        player.updateInventory()
                    }
                }
            }
        }
    }
}