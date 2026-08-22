package net.xuwu.confluencelootr.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import noobanidus.mods.lootr.common.data.LootrInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Lets the vanilla chest opener counter recognize Lootr's per-player menu. */
@Mixin(targets = "net.minecraft.world.level.block.entity.ChestBlockEntity$1")
public abstract class ChestBlockEntityOpenersMixin {
    @Shadow(remap = false)
    @Final
    private ChestBlockEntity this$0;

    @Inject(method = "isOwnContainer", at = @At("HEAD"), cancellable = true)
    private void confluenceLootr$recognizeLootrMenu(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (!ConfluenceLootrCompatibility.isLootrChest(this$0)
                || !(player.containerMenu instanceof ChestMenu menu)
                || !(menu.getContainer() instanceof LootrInventory inventory)) {
            return;
        }

        if (inventory.getInfo().getInfoPos().equals(this$0.getBlockPos())
                && inventory.getInfo().getInfoDimension().equals(this$0.getLevel().dimension())) {
            cir.setReturnValue(true);
        }
    }
}
