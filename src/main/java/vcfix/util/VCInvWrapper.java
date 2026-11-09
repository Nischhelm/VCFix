package vcfix.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;
import noppes.vc.blocks.tiles.TileBasicContainer;
import noppes.vc.blocks.tiles.TilePedestal;
import noppes.vc.blocks.tiles.TileWeaponRack;

import javax.annotation.Nonnull;

/**
 * IItemHandler view of a VC TileBasicContainer.
 * Pedestals and weapon racks render their contents, so changes made through this handler
 * (pipes, hoppers via capability, etc.) will post a block update to resync the client.
 */
public class VCInvWrapper extends InvWrapper {
    private final TileBasicContainer tile;
    private final boolean syncsToClient;

    public VCInvWrapper(TileBasicContainer tile) {
        super(tile);
        this.tile = tile;
        this.syncsToClient = tile instanceof TilePedestal || tile instanceof TileWeaponRack;
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        ItemStack remainder = super.insertItem(slot, stack, simulate);
        if (!simulate && remainder.getCount() != stack.getCount()) sync();
        return remainder;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack extracted = super.extractItem(slot, amount, simulate);
        if (!simulate && !extracted.isEmpty()) sync();
        return extracted;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        super.setStackInSlot(slot, stack);
        sync();
    }

    private void sync() {
        if (!syncsToClient) return;
        World world = tile.getWorld();
        if (world == null || world.isRemote) return;
        IBlockState state = world.getBlockState(tile.getPos());
        world.notifyBlockUpdate(tile.getPos(), state, state, 3);
    }
}
