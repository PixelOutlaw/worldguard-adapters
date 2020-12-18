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
package com.tealcube.minecraft.spigot.worldguard.adapters

import org.bukkit.Location

/**
 * An adapter for checking flag statuses at a location.
 */
interface IWorldGuardAdapter {
    /**
     * Returns true if flag is allow at location.
     *
     * @param location Location to check
     * @param flagName Name of flag
     *
     * @return if flag is allow at location
     */
    fun isFlagAllowAtLocation(location: Location, flagName: String): Boolean

    /**
     * Returns true if flag is deny at location.
     *
     * @param location Location to check
     * @param flagName Name of flag
     *
     * @return if flag is deny at location
     */
    fun isFlagDenyAtLocation(location: Location, flagName: String): Boolean

    /**
     * Registers a flag with WorldGuard.
     *
     * @param flagName Name of the flag
     */
    fun registerFlag(flagName: String)
}
