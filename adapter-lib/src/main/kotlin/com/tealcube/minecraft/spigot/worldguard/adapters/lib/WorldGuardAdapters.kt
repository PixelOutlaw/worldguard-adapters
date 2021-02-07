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

import com.tealcube.minecraft.spigot.worldguard.adapters.IWorldGuardAdapter
import org.bukkit.Bukkit
import org.bukkit.Location
import saschpe.log4k.Log

object WorldGuardAdapters : IWorldGuardAdapter {
    private val internalAdapter: IWorldGuardAdapter by lazy {
        val worldGuardPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard") ?: return@lazy NoOpWorldGuardAdapter
        try {
            val versionOfWorldGuard = worldGuardPlugin.description.version
            with(versionOfWorldGuard) {
                when {
                    startsWith("6.2") -> com.tealcube.minecraft.spigot.worldguard.adapters.v62x.WorldGuardAdapter
                    startsWith("7.0") -> com.tealcube.minecraft.spigot.worldguard.adapters.v70x.WorldGuardAdapter
                    else -> {
                        Log.warn("Using an unsupported WorldGuard version! Defaulting to no-op!")
                        NoOpWorldGuardAdapter
                    }
                }
            }
        } catch (expected: Exception) {
            Log.warn("Unable to find correct WorldGuardAdapter, defaulting to no-op!", expected)
            NoOpWorldGuardAdapter
        }
    }

    override fun isFlagAllowAtLocation(location: Location, flagName: String): Boolean =
        internalAdapter.isFlagAllowAtLocation(location, flagName)

    override fun isFlagDenyAtLocation(location: Location, flagName: String): Boolean =
        internalAdapter.isFlagDenyAtLocation(location, flagName)

    override fun registerFlag(flagName: String) = internalAdapter.registerFlag(flagName)
}
