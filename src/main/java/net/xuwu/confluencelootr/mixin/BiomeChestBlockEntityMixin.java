package net.xuwu.confluencelootr.mixin;

import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import org.confluence.mod.common.block.common.BiomeChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BiomeChestBlock.BEntity.class)
public abstract class BiomeChestBlockEntityMixin implements ConfluenceLootrAccess {
    @Unique
    private final SimpleLootrInstance confluenceLootr$instance = new SimpleLootrInstance(this::getVisualOpeners, 27);

    @Unique
    private boolean confluenceLootr$converted;

    @Override
    public SimpleLootrInstance confluenceLootr$getInstance() {
        return confluenceLootr$instance;
    }

    @Override
    public boolean confluenceLootr$isConverted() {
        return confluenceLootr$converted;
    }

    @Override
    public void confluenceLootr$setConverted(boolean converted) {
        confluenceLootr$converted = converted;
    }
}
