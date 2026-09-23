package net.xuwu.confluencelootr.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xuwu.confluencelootr.ConfluenceLootr;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import noobanidus.mods.lootr.util.ChestUtil;
import org.confluence.mod.common.block.common.BaseChestBlock;
import org.confluence.mod.common.block.common.BiomeChestBlock;

@Mod.EventBusSubscriber(modid = ConfluenceLootr.MOD_ID)
public final class ConfluenceLootrEvents {
    private ConfluenceLootrEvents() {
    }

    @SubscribeEvent
    public static void onUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        BlockEntity entity = event.getLevel().getBlockEntity(event.getPos());
        if (!ConfluenceLootrCompatibility.isLootrChest(entity)) {
            return;
        }
        BlockState state = event.getLevel().getBlockState(event.getPos());
        if ((state.hasProperty(BaseChestBlock.UNLOCKED) && !state.getValue(BaseChestBlock.UNLOCKED))
                || (state.hasProperty(BiomeChestBlock.UNLOCKED) && !state.getValue(BiomeChestBlock.UNLOCKED))) {
            return;
        }

        Player player = event.getEntity();
        if (!event.getLevel().isClientSide()) {
            if (player.isShiftKeyDown()) {
                ChestUtil.handleLootSneak(state.getBlock(), event.getLevel(), event.getPos(), player);
            } else if (!ChestBlock.isChestBlockedAt(event.getLevel(), event.getPos())) {
                ChestUtil.handleLootChest(state.getBlock(), event.getLevel(), event.getPos(), player);
            }
        }
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()
                || !ConfluenceLootrCompatibility.isLootrChest(event.getLevel().getBlockEntity(event.getPos()))) {
            return;
        }
        Player player = event.getPlayer();
        if (player.isShiftKeyDown()) {
            return;
        }
        event.setCanceled(true);
        Style style = Style.EMPTY.withColor(ChatFormatting.AQUA);
        player.displayClientMessage(Component.translatable("lootr.message.should_sneak").setStyle(style), false);
        player.displayClientMessage(Component.translatable("lootr.message.should_sneak2",
                Component.translatable("lootr.message.should_sneak3").withStyle(ChatFormatting.BOLD))
                .setStyle(style), false);
    }
}
