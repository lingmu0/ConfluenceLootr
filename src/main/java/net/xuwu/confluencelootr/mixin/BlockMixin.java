package net.xuwu.confluencelootr.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import noobanidus.mods.lootr.common.api.LootrAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Keeps Lootr's per-player contents when a Confluence chest is broken. */
@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "playerDestroy", at = @At("TAIL"))
    private void confluenceLootr$playerDestroy(Level level, Player player, BlockPos pos, BlockState state,
                                                BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
        if (ConfluenceLootrCompatibility.isLootrChest(blockEntity)) {
            LootrAPI.playerDestroyed(level, player, pos, blockEntity);
        }
    }
}
