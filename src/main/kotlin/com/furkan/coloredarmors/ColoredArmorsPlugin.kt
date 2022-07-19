package com.furkan.coloredarmors

import com.furkan.coloredarmors.command.CommandManager
import com.furkan.coloredarmors.tasks.TaskManager
import com.hakan.core.HCore
import com.hakan.core.utils.yaml.HYaml
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ColoredArmorsPlugin : JavaPlugin() {

    companion object {
        lateinit var yaml: HYaml
        lateinit var INSTANCE: ColoredArmorsPlugin
    }

    override fun onEnable() {
        super.onEnable()

        INSTANCE = this

        //HCore initialization.
        HCore.initialize(this)

        //Yaml initialization.
        yaml = HYaml(File("$dataFolder/config.yml"))
        if (yaml.fileConfiguration.getKeys(true).isEmpty()) {
            this.logger.warning("Config file is empty. Creating default config file.")
            this.saveResource("config.yml", true)
            yaml.reload()
        }

        //Register managers.
        TaskManager.initialize()

        //Register commands.
        HCore.registerCommands(CommandManager())
    }
}