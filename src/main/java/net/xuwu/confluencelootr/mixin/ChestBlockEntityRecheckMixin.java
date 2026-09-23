package net.xuwu.confluencelootr.mixin;

import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Lootr's menu calls startOpen/stopOpen, but vanilla's periodic menu check cannot see it. */
@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityRecheckMixin {
    @Inject(method = "recheckOpen", at = @At("HEAD"), cancellable = true)
    private void confluenceLootr$useMenuOpenCount(CallbackInfo ci) {
        if (ConfluenceLootrCompatibility.isLootrChest((ChestBlockEntity) (Object) this)) {
            ci.cancel();
        }
    }
}
