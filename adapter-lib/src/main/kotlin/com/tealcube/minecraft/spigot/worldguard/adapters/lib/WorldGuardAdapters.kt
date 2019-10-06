/*
 * This file is part of WorldGuard Adapters, licensed under the MIT License.
 *
 * Copyright (C) 2019 Richard Harrah
 *
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.tealcube.minecraft.spigot.worldguard.adapters.lib

import com.tealcube.minecraft.spigot.worldguard.adapters.WorldGuardAdapter
import com.tealcube.minecraft.spigot.worldguard.adapters.v6_2_x.WorldGuardAdapter62X
import com.tealcube.minecraft.spigot.worldguard.adapters.v7_0_x.WorldGuardAdapter70X
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.Logger

object WorldGuardAdapters {
    private val logger: Logger = Logger.getLogger(WorldGuardAdapters::class.java.canonicalName)
    @JvmStatic
    val instance: WorldGuardAdapter by lazy {
        val worldGuardPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard") ?: return@lazy NoOpWorldGuardAdapter
        return@lazy try {
            val versionOfWorldGuard = worldGuardPlugin.description.version
            with(versionOfWorldGuard) {
                when {
                    startsWith("6.2") -> WorldGuardAdapter62X()
                    startsWith("7.0") -> WorldGuardAdapter70X()
                    else -> {
                        logger.warning("Using an unsupported WorldGuard version! Defaulting to 7.0.x adapter!")
                        WorldGuardAdapter70X()
                    }
                }
            }
        } catch (ex: Exception) {
            logger.log(Level.WARNING, "Unable to find correct WorldGuardAdapter, defaulting to no-op!", ex)
            NoOpWorldGuardAdapter
        }
    }
}
