package net.xuwu.confluencelootr.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import noobanidus.mods.lootr.util.ChestUtil;
import org.confluence.mod.common.block.common.BaseChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** The key path in BaseChestBlock calls ChestBlock.use during the unlocking click. */
@Mixin(ChestBlock.class)
public abstract class ChestBlockUseFallbackMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void confluenceLootr$openedWithKey(BlockState state, Level level, BlockPos pos, Player player,
                                                InteractionHand hand, BlockHitResult hit,
                                                CallbackInfoReturnable<InteractionResult> cir) {
        BlockState current = level.getBlockState(pos);
        if (!ConfluenceLootrCompatibility.isLootrChest(level.getBlockEntity(pos))
                || !current.hasProperty(BaseChestBlock.UNLOCKED)
                || !current.getValue(BaseChestBlock.UNLOCKED)) {
            return;
        }
        if (!level.isClientSide()) {
            if (player.isShiftKeyDown()) {
                ChestUtil.handleLootSneak(current.getBlock(), level, pos, player);
            } else if (!ChestBlock.isChestBlockedAt(level, pos)) {
                ChestUtil.handleLootChest(current.getBlock(), level, pos, player);
            }
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
