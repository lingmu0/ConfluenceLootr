package net.xuwu.confluencelootr.compat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;

/**
 * The small adapter surface mixed into Confluence's two chest entity families.
 * The default methods intentionally mirror LootrChestBlockEntity while retaining
 * Confluence's original block, renderer, inventory and lid behavior.
 */
public interface ConfluenceLootrAccess extends ILootrBlockEntity {
    SimpleLootrInstance confluenceLootr$getInstance();

    boolean confluenceLootr$isConverted();

    void confluenceLootr$setConverted(boolean converted);

    default BlockEntity confluenceLootr$getBlockEntity() {
        return (BlockEntity) this;
    }

    default ChestBlockEntity confluenceLootr$getChest() {
        return (ChestBlockEntity) this;
    }

    default RandomizableContainerBlockEntity confluenceLootr$getContainerEntity() {
        return (RandomizableContainerBlockEntity) this;
    }

    @Override
    @Deprecated
    default LootrBlockType getInfoBlockType() {
        return LootrBlockType.CHEST;
    }

    @Override
    default ILootrType getInfoNewType() {
        return BuiltInLootrTypes.CHEST;
    }

    @Override
    default java.util.UUID getInfoUUID() {
        return confluenceLootr$getInstance().getInfoUUID();
    }

    @Override
    default String getInfoKey() {
        return confluenceLootr$getInstance().getInfoKey();
    }

    @Override
    default boolean hasBeenOpened() {
        return confluenceLootr$getInstance().hasBeenOpened();
    }

    @Override
    default boolean isPhysicallyOpen() {
        return confluenceLootr$getChest().getOpenNess(1.0F) > 0.0F;
    }

    @Override
    default BlockPos getInfoPos() {
        return confluenceLootr$getBlockEntity().getBlockPos();
    }

    @Override
    default Component getInfoDisplayName() {
        return confluenceLootr$getContainerEntity().getDisplayName();
    }

    @Override
    default ResourceKey<Level> getInfoDimension() {
        return getInfoLevel().dimension();
    }

    @Override
    default int getInfoContainerSize() {
        return confluenceLootr$getInstance().getInfoContainerSize();
    }

    @Override
    default NonNullList<ItemStack> getInfoReferenceInventory() {
        return confluenceLootr$getInstance().getCustomInventory();
    }

    @Override
    default void setInfoReferenceInventory(NonNullList<ItemStack> reference) {
        if (reference != null) {
            confluenceLootr$getInstance().setCustomInventory(reference);
        }
    }

    @Override
    default boolean isInfoReferenceInventory() {
        return isInfoReferenceInventoryInternal(confluenceLootr$getInstance().isCustomInventory());
    }

    @Override
    default ResourceKey<LootTable> getInfoLootTable() {
        return confluenceLootr$getContainerEntity().getLootTable();
    }

    @Override
    default long getInfoLootSeed() {
        return confluenceLootr$getContainerEntity().getLootTableSeed();
    }

    @Override
    default Level getInfoLevel() {
        return confluenceLootr$getBlockEntity().getLevel();
    }

    @Override
    default java.util.Set<java.util.UUID> getClientOpeners() {
        return confluenceLootr$getInstance().getClientOpeners();
    }

    @Override
    default boolean isClientOpened() {
        return confluenceLootr$getInstance().isClientOpened();
    }

    @Override
    default void setClientOpened(boolean opened) {
        confluenceLootr$getInstance().setClientOpened(opened);
    }

    @Override
    default void markChanged() {
        confluenceLootr$getBlockEntity().setChanged();
        markDataChanged();
    }

    @Override
    default void setHasBeenOpened(boolean value) {
        confluenceLootr$getInstance().setHasBeenOpened(value);
    }

    @Override
    default int getPhysicalOpenerCount() {
        return ChestBlockEntity.getOpenCount(confluenceLootr$getBlockEntity().getLevel(), getInfoPos());
    }

    @Override
    default IContainerTrigger getTrigger() {
        return LootrRegistry.getChestTrigger();
    }

    @Override
    default int getRandomOffset() {
        return confluenceLootr$getInstance().getRandomOffset();
    }

    default void confluenceLootr$loadAdditional(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        confluenceLootr$getInstance().loadAdditional(tag, provider);
    }

    default void confluenceLootr$saveAdditional(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        confluenceLootr$getInstance().saveAdditional(tag, provider, getInfoLevel() != null && getInfoLevel().isClientSide());
    }

    default void confluenceLootr$fillUpdateTag(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        confluenceLootr$getInstance().fillUpdateTag(tag, provider, false);
    }

    default void confluenceLootr$setSavingToItem(boolean saving) {
        confluenceLootr$getInstance().setSavingToItem(saving);
    }

    default boolean confluenceLootr$isSavingToItem() {
        return confluenceLootr$getInstance().isSavingToItem();
    }
}
