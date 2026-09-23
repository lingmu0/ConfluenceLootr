package net.xuwu.confluencelootr.compat;

import net.minecraft.world.level.block.entity.BlockEntity;

public final class ConfluenceLootrCompatibility {
    public static final String CONVERTED_MARKER = "ConfluenceLootrConverted";

    private ConfluenceLootrCompatibility() {
    }

    public static boolean isLootrChest(BlockEntity blockEntity) {
        if (!(blockEntity instanceof ConfluenceLootrAccess access)) {
            return false;
        }
        if (access.getTable() != null) {
            access.confluenceLootr$setConverted(true);
        }
        return access.confluenceLootr$isConverted();
    }
}
