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
package com.tealcube.minecraft.spigot.worldguard.adapters.v7_0_x

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform
import com.sk89q.worldguard.protection.flags.StateFlag
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry
import com.sk89q.worldguard.protection.regions.RegionContainer
import com.tealcube.minecraft.spigot.worldguard.adapters.IWorldGuardAdapter
import org.bukkit.Location

object WorldGuardAdapter70X : IWorldGuardAdapter {
    // we had to change this due to WG 7.0.4 not always having a platform for some reason
    @Suppress("detekt.TooGenericExceptionCaught")
    private val worldGuardPlatform: WorldGuardPlatform?
        get() = try {
            WorldGuard.getInstance().platform
        } catch (npe: NullPointerException) {
            null
        }
    private val regionContainer: RegionContainer?
        get() = worldGuardPlatform?.regionContainer
    private val flagRegistry: FlagRegistry by lazy {
        WorldGuard.getInstance().flagRegistry
    }

    override fun isFlagAllowAtLocation(location: Location, flagName: String): Boolean =
        getFlagFromRegistry(flagName)?.let { isFlagAllowAtLocation(location, it) } ?: true

    override fun isFlagDenyAtLocation(location: Location, flagName: String): Boolean =
        getFlagFromRegistry(flagName)?.let { isFlagDenyAtLocation(location, it) } ?: false

    override fun registerFlag(flagName: String) {
        flagRegistry.register(StateFlag(flagName, true))
    }

    private fun getRegionQuery() = regionContainer?.createQuery()

    private fun isFlagAllowAtLocation(location: Location, flag: StateFlag) =
        getRegionQuery()?.testState(BukkitAdapter.adapt(location), null, flag)

    private fun isFlagDenyAtLocation(location: Location, flag: StateFlag) =
        getRegionQuery()?.let { !it.testState(BukkitAdapter.adapt(location), null, flag) }

    private fun getFlagFromRegistry(flagName: String): StateFlag? = flagRegistry.get(flagName) as? StateFlag
}
