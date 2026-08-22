package net.xuwu.confluencelootr.mixin;

import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import org.confluence.mod.common.block.common.BaseChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BaseChestBlock.BEntity.class)
public abstract class BaseChestBlockEntityMixin implements ConfluenceLootrAccess {
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
