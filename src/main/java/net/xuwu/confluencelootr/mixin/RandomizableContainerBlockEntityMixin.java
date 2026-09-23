package net.xuwu.confluencelootr.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** A loot table marks a world-generated chest; its table must remain available to every player. */
@Mixin(RandomizableContainerBlockEntity.class)
public abstract class RandomizableContainerBlockEntityMixin {
    @Inject(method = "setLootTable(Lnet/minecraft/resources/ResourceLocation;J)V", at = @At("TAIL"))
    private void confluenceLootr$setLootTable(ResourceLocation table, long seed, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access && table != null) {
            access.confluenceLootr$setConverted(true);
        }
    }

    @Inject(method = "unpackLootTable", at = @At("HEAD"), cancellable = true)
    private void confluenceLootr$keepLootTable(Player player, CallbackInfo ci) {
        if ((Object) this instanceof ConfluenceLootrAccess access && access.confluenceLootr$isConverted()) {
            ci.cancel();
        }
    }
}
