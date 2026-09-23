package net.xuwu.confluencelootr.compat;

import java.util.Set;
import java.util.UUID;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.api.LootrAPI;
import noobanidus.mods.lootr.api.blockentity.ILootBlockEntity;
import net.xuwu.confluencelootr.mixin.RandomizableContainerBlockEntityAccessor;

/** Shared Lootr adapter for Confluence's base and biome chest block entities. */
public interface ConfluenceLootrAccess extends ILootBlockEntity {
    boolean confluenceLootr$isConverted();

    void confluenceLootr$setConverted(boolean converted);

    UUID confluenceLootr$getId();

    void confluenceLootr$setId(UUID id);

    Set<UUID> getOpeners();

    default BlockEntity confluenceLootr$entity() {
        return (BlockEntity) this;
    }

    @Override
    default ResourceLocation getTable() {
        return ((RandomizableContainerBlockEntityAccessor) this).confluenceLootr$getLootTable();
    }

    @Override
    default long getSeed() {
        return ((RandomizableContainerBlockEntityAccessor) this).confluenceLootr$getLootTableSeed();
    }

    @Override
    default BlockPos getPosition() {
        return confluenceLootr$entity().getBlockPos();
    }

    @Override
    default UUID getTileId() {
        UUID id = confluenceLootr$getId();
        if (id == null) {
            id = UUID.randomUUID();
            confluenceLootr$setId(id);
        }
        return id;
    }

    @Override
    default void updatePacketViaState() {
        BlockEntity entity = confluenceLootr$entity();
        Level level = entity.getLevel();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), 3);
        }
    }

    @Override
    default void unpackLootTable(Player player, Container inventory, ResourceLocation tableId, long seed) {
        BlockEntity entity = confluenceLootr$entity();
        if (!(entity.getLevel() instanceof ServerLevel level) || tableId == null) {
            return;
        }
        LootTable table = level.getServer().getLootData().getLootTable(tableId);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.GENERATE_LOOT.trigger(serverPlayer, tableId);
        }
        LootParams.Builder params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.getBlockPos()))
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withLuck(player.getLuck());
        table.fill(inventory, params.create(LootContextParamSets.CHEST), LootrAPI.getLootSeed(seed));
    }

    default void confluenceLootr$load(CompoundTag tag) {
        if (tag.contains("LootTable", Tag.TAG_STRING)
                || tag.getBoolean(ConfluenceLootrCompatibility.CONVERTED_MARKER)) {
            confluenceLootr$setConverted(true);
        }
        if (tag.hasUUID("ConfluenceLootrId")) {
            confluenceLootr$setId(tag.getUUID("ConfluenceLootrId"));
        }
        getOpeners().clear();
        ListTag openers = tag.getList("ConfluenceLootrOpeners", Tag.TAG_INT_ARRAY);
        for (Tag opener : openers) {
            getOpeners().add(NbtUtils.loadUUID(opener));
        }
    }

    default void confluenceLootr$save(CompoundTag tag) {
        tag.putBoolean(ConfluenceLootrCompatibility.CONVERTED_MARKER, true);
        tag.putUUID("ConfluenceLootrId", getTileId());
        ListTag openers = new ListTag();
        for (UUID opener : getOpeners()) {
            openers.add(NbtUtils.createUUID(opener));
        }
        tag.put("ConfluenceLootrOpeners", openers);
    }
}
