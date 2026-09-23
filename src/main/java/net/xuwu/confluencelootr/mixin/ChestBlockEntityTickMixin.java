package net.xuwu.confluencelootr.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import net.xuwu.confluencelootr.compat.ConfluenceLootrCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** A subtle sparkle is visible only to players who have not opened this loot chest. */
@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityTickMixin {
    @Inject(method = "lidAnimateTick", at = @At("TAIL"))
    private static void confluenceLootr$unopenedParticles(Level level, BlockPos pos, BlockState state,
                                                             ChestBlockEntity chest, CallbackInfo ci) {
        if (!level.isClientSide() || !ConfluenceLootrCompatibility.isLootrChest(chest)
                || !(chest instanceof ConfluenceLootrAccess access)
                || Minecraft.getInstance().player == null
                || access.getOpeners().contains(Minecraft.getInstance().player.getUUID())
                || level.random.nextInt(8) != 0) {
            return;
        }
        double x = pos.getX() + 0.15 + level.random.nextDouble() * 0.7;
        double y = pos.getY() + 0.65 + level.random.nextDouble() * 0.45;
        double z = pos.getZ() + 0.15 + level.random.nextDouble() * 0.7;
        level.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0.02, 0);
    }
}
