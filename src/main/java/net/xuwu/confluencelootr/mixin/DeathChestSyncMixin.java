package net.xuwu.confluencelootr.mixin;

import net.minecraft.nbt.CompoundTag;
import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import org.confluence.mod.common.block.functional.DeathChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Death chests have custom sync methods that do not call BlockEntity's implementations. */
@Mixin(value = DeathChestBlock.BEntity.class, remap = false)
public abstract class DeathChestSyncMixin {
    @Inject(method = {"getUpdateTag", "m_5995_"}, at = @At("RETURN"),
            cancellable = true, remap = false)
    private void confluenceLootr$appendSync(CallbackInfoReturnable<CompoundTag> cir) {
        ConfluenceLootrAccess access = (ConfluenceLootrAccess) this;
        if (access.confluenceLootr$isConverted()) {
            CompoundTag tag = cir.getReturnValue();
            access.confluenceLootr$save(tag);
            cir.setReturnValue(tag);
        }
    }

    @Inject(method = "handleUpdateTag", at = @At("TAIL"), remap = false)
    private void confluenceLootr$readSync(CompoundTag tag, CallbackInfo ci) {
        ((ConfluenceLootrAccess) this).confluenceLootr$load(tag);
    }
}
