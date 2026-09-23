package net.xuwu.confluencelootr.mixin;

import net.minecraft.nbt.CompoundTag;
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

/** Persists the Lootr identity and each player's opened state on the original chest entity. */
@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {
    @Inject(method = "load", at = @At("TAIL"))
    private void confluenceLootr$load(CompoundTag tag, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access) {
            access.confluenceLootr$load(tag);
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void confluenceLootr$save(CompoundTag tag, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            access.confluenceLootr$save(tag);
        }
    }

    @Inject(method = "saveToItem", at = @At("RETURN"))
    private void confluenceLootr$clearItemData(ItemStack item, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess) {
            CompoundTag data = item.getTagElement("BlockEntityTag");
            if (data != null) {
                data.remove("LootTable");
                data.remove("LootTableSeed");
                data.remove(ConfluenceLootrCompatibility.CONVERTED_MARKER);
                data.remove("ConfluenceLootrId");
                data.remove("ConfluenceLootrOpeners");
            }
        }
    }

    @Inject(method = "getUpdateTag", at = @At("RETURN"), cancellable = true)
    private void confluenceLootr$getUpdateTag(CallbackInfoReturnable<CompoundTag> cir) {
        if ((Object) this instanceof ConfluenceLootrAccess access
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            CompoundTag tag = cir.getReturnValue();
            access.confluenceLootr$save(tag);
            cir.setReturnValue(tag);
        }
    }

    @Inject(method = "getUpdatePacket", at = @At("RETURN"), cancellable = true)
    private void confluenceLootr$getUpdatePacket(CallbackInfoReturnable<Packet<ClientGamePacketListener>> cir) {
        if ((Object) this instanceof ConfluenceLootrAccess
                && ConfluenceLootrCompatibility.isLootrChest((BlockEntity) (Object) this)) {
            cir.setReturnValue(ClientboundBlockEntityDataPacket.create((BlockEntity) (Object) this,
                    BlockEntity::getUpdateTag));
        }
    }
}
