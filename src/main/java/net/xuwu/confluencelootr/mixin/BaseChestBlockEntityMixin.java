package net.xuwu.confluencelootr.mixin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.xuwu.confluencelootr.compat.ConfluenceLootrAccess;
import org.confluence.mod.common.block.common.BaseChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BaseChestBlock.BEntity.class)
public abstract class BaseChestBlockEntityMixin implements ConfluenceLootrAccess {
    @Unique private UUID confluenceLootr$id;
    @Unique private final Set<UUID> confluenceLootr$openers = new HashSet<>();
    @Unique private boolean confluenceLootr$converted;

    @Override public UUID confluenceLootr$getId() { return confluenceLootr$id; }
    @Override public void confluenceLootr$setId(UUID id) { confluenceLootr$id = id; }
    @Override public Set<UUID> getOpeners() { return confluenceLootr$openers; }
    @Override public boolean confluenceLootr$isConverted() { return confluenceLootr$converted; }
    @Override public void confluenceLootr$setConverted(boolean value) { confluenceLootr$converted = value; }
    @Override public void setOpened(boolean opened) { }
}
