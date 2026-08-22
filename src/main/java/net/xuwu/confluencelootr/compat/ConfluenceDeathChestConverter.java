package net.xuwu.confluencelootr.compat;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;

public final class ConfluenceDeathChestConverter implements ILootrBlockEntityConverter<BlockEntity> {
    @Override
    public ILootrBlockEntity apply(BlockEntity blockEntity) {
        return blockEntity instanceof ILootrBlockEntity lootrBlockEntity
                && ConfluenceLootrCompatibility.isLootrChest(blockEntity)
                ? lootrBlockEntity : null;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ConfluenceLootrCompatibility.blockEntityType("death_chest_entity");
    }
}
