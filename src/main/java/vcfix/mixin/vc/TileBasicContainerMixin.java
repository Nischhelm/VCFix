package vcfix.mixin.vc;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import noppes.vc.blocks.tiles.TileBasicContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vcfix.util.VCInvWrapper;

import javax.annotation.Nullable;

// Add IItemHandler Cap to VC Containers
@Mixin(TileBasicContainer.class)
public abstract class TileBasicContainerMixin extends TileEntity {

    @Unique
    private IItemHandler vcfix$itemHandler;

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (vcfix$itemHandler == null)
                vcfix$itemHandler = new VCInvWrapper((TileBasicContainer) (Object) this);
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(vcfix$itemHandler);
        }
        return super.getCapability(capability, facing);
    }
}
