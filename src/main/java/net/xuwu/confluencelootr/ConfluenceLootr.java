package net.xuwu.confluencelootr;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * Makes Confluence's chest block entities usable by Lootr without changing their blocks or renderers.
 */
@Mod(ConfluenceLootr.MOD_ID)
public final class ConfluenceLootr {
    public static final String MOD_ID = "confluence_lootr";

    public ConfluenceLootr(IEventBus modEventBus, ModContainer modContainer) {
        // All compatibility is installed through the Mixin and Lootr service-provider entries.
    }
}
