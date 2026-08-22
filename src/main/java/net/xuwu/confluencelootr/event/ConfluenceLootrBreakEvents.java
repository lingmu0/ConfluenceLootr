package net.xuwu.confluencelootr.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.xuwu.confluencelootr.ConfluenceLootr;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import noobanidus.mods.lootr.common.api.LootrAPI;

/** Applies Lootr's warning and sneak-to-break rule to converted Confluence chests. */
@EventBusSubscriber(modid = ConfluenceLootr.MOD_ID)
public final class ConfluenceLootrBreakEvents {
    private ConfluenceLootrBreakEvents() {
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getPos());
        if (!ConfluenceLootrCompatibility.isLootrChest(blockEntity)) {
            return;
        }

        Player player = event.getPlayer();
        if (player.isShiftKeyDown()) {
            return;
        }

        event.setCanceled(true);
        player.displayClientMessage(
                Component.translatable("lootr.message.should_sneak").setStyle(LootrAPI.getChatStyle()), false);
        player.displayClientMessage(
                Component.translatable("lootr.message.should_sneak2").setStyle(LootrAPI.getChatStyle()), false);
    }
}
