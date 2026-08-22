package net.xuwu.confluencelootr.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Persists Lootr's per-container identity and opener state on the original Confluence entity. */
@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void confluenceLootr$loadAdditional(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access) {
            boolean hasLootTableData = tag.contains("LootTable", Tag.TAG_STRING);
            if (hasLootTableData || tag.getBoolean(ConfluenceLootrCompatibility.CONVERTED_MARKER)) {
                access.confluenceLootr$setConverted(true);
            }
            if (hasLootTableData || ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
                access.confluenceLootr$loadAdditional(tag, provider);
            }
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void confluenceLootr$saveAdditional(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            access.confluenceLootr$saveAdditional(tag, provider);
            if (!access.confluenceLootr$isSavingToItem()) {
                tag.putBoolean(ConfluenceLootrCompatibility.CONVERTED_MARKER, true);
            }
        }
    }

    @Inject(method = "saveToItem", at = @At("HEAD"))
    private void confluenceLootr$startSavingToItem(ItemStack stack, HolderLookup.Provider provider, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            access.confluenceLootr$setSavingToItem(true);
        }
    }

    @Inject(method = "saveToItem", at = @At("RETURN"))
    private void confluenceLootr$stopSavingToItem(ItemStack stack, HolderLookup.Provider provider, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            access.confluenceLootr$setSavingToItem(false);
        }
    }

    @Inject(method = "getUpdateTag", at = @At("RETURN"), cancellable = true)
    private void confluenceLootr$getUpdateTag(HolderLookup.Provider provider, CallbackInfoReturnable<CompoundTag> cir) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            CompoundTag tag = cir.getReturnValue();
            access.confluenceLootr$fillUpdateTag(tag, provider);
            tag.putBoolean(ConfluenceLootrCompatibility.CONVERTED_MARKER, true);
            cir.setReturnValue(tag);
        }
    }

    @Inject(method = "getUpdatePacket", at = @At("RETURN"), cancellable = true)
    private void confluenceLootr$getUpdatePacket(CallbackInfoReturnable<Packet<ClientGamePacketListener>> cir) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            cir.setReturnValue(ClientboundBlockEntityDataPacket.create((BlockEntity) (Object) this, BlockEntity::getUpdateTag));
        }
    }
}
