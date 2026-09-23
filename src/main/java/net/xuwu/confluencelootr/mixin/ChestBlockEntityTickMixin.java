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

/** Visible enchantment sparkles are shown only to players who have not opened this loot chest. */
@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityTickMixin {
    @Inject(method = "lidAnimateTick", at = @At("TAIL"))
    private static void confluenceLootr$unopenedParticles(Level level, BlockPos pos, BlockState state,
                                                             ChestBlockEntity chest, CallbackInfo ci) {
        if (!level.isClientSide() || !ConfluenceLootrCompatibility.isLootrChest(chest)
                || !(chest instanceof ConfluenceLootrAccess access)
                || Minecraft.getInstance().player == null
                || access.getOpeners().contains(Minecraft.getInstance().player.getUUID())
                || level.random.nextInt(4) != 0) {
            return;
        }

        // Spawn a small cluster above the lid so the chest does not occlude the particles.
        for (int i = 0; i < 3; i++) {
            double x = pos.getX() + 0.1 + level.random.nextDouble() * 0.8;
            double y = pos.getY() + 0.95 + level.random.nextDouble() * 0.45;
            double z = pos.getZ() + 0.1 + level.random.nextDouble() * 0.8;
            double dx = (level.random.nextDouble() - 0.5) * 0.02;
            double dy = 0.025 + level.random.nextDouble() * 0.025;
            double dz = (level.random.nextDouble() - 0.5) * 0.02;
            level.addParticle(ParticleTypes.ENCHANT, x, y, z, dx, dy, dz);
        }
    }
}
