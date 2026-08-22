package net.xuwu.confluencelootr.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import org.confluence.mod.common.block.common.BaseChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Routes only Confluence chest interactions through Lootr and leaves all other chests untouched. */
@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin {
    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void confluenceLootr$useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
                                                BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!ConfluenceLootrCompatibility.isLootrChest(blockEntity)) {
            return;
        }

        // Locked Confluence chests must still require their original key item.
        if (state.hasProperty(BaseChestBlock.UNLOCKED) && !state.getValue(BaseChestBlock.UNLOCKED)) {
            cir.setReturnValue(InteractionResult.CONSUME);
            return;
        }

        if (level.isClientSide() || player.isSpectator() || !(player instanceof ServerPlayer serverPlayer)) {
            cir.setReturnValue(InteractionResult.CONSUME);
            return;
        }

        if (serverPlayer.isShiftKeyDown()) {
            LootrAPI.handleProviderSneak(ILootrInfoProvider.of(pos, level), serverPlayer);
        } else if (!ChestBlock.isChestBlockedAt(level, pos)) {
            LootrAPI.handleProviderOpen(ILootrInfoProvider.of(pos, level), serverPlayer);
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }

    @Inject(method = "getTicker", at = @At("RETURN"), cancellable = true)
    private <T extends BlockEntity> void confluenceLootr$addTicker(Level level, BlockState state,
                                                                    BlockEntityType<T> blockEntityType,
                                                                    CallbackInfoReturnable<BlockEntityTicker<T>> cir) {
        if (!ConfluenceLootrCompatibility.isSupportedBlockEntityType(blockEntityType)) {
            return;
        }

        BlockEntityTicker<T> originalTicker = cir.getReturnValue();
        cir.setReturnValue((tickLevel, pos, tickState, blockEntity) -> {
            if (originalTicker != null) {
                originalTicker.tick(tickLevel, pos, tickState, blockEntity);
            }
            if (ConfluenceLootrCompatibility.isLootrChest(blockEntity)) {
                ILootrBlockEntity.ticker(tickLevel, pos, tickState, blockEntity);
            }
        });
    }
}
