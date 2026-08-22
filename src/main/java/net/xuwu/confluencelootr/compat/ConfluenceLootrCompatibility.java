package net.xuwu.confluencelootr.compat;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/** Registry lookups are deliberately lazy so Lootr's ServiceLoader runs after mod registries exist. */
public final class ConfluenceLootrCompatibility {
    private static final String CONFLUENCE = "confluence";
    public static final String CONVERTED_MARKER = "ConfluenceLootrConverted";

    private ConfluenceLootrCompatibility() {
    }

    public static BlockEntityType<?> blockEntityType(String path) {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(CONFLUENCE, path));
    }

    public static boolean isSupportedBlockEntityType(BlockEntityType<?> type) {
        return type != null
                && (type == blockEntityType("base_chest_entity")
                || type == blockEntityType("biome_chest_entity")
                || type == blockEntityType("death_chest_entity"));
    }

    public static boolean isLootrChest(BlockEntity blockEntity) {
        if (!(blockEntity instanceof ConfluenceLootrAccess access)) {
            return false;
        }
        if (access.hasLootTable()) {
            access.confluenceLootr$setConverted(true);
            return true;
        }
        return access.confluenceLootr$isConverted();
    }
}
