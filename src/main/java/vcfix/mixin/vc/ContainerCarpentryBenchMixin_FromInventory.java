package vcfix.mixin.vc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ItemStack;
import noppes.vc.containers.ContainerCarpentryBench;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ContainerCarpentryBench.class)
public abstract class ContainerCarpentryBenchMixin_FromInventory {
    @WrapOperation(
            method = "transferStackInSlot",
            at = @At(value = "INVOKE", target = "Lnoppes/vc/containers/ContainerCarpentryBench;mergeItemStack(Lnet/minecraft/item/ItemStack;IIZ)Z", ordinal = 1)
    )
    private boolean vcfix_vcContainerCarpentryBench_transferStackInSlot_fromInvToCrafting(ContainerCarpentryBench instance, ItemStack stack, int startIndex, int endIndex, boolean reverseDirection, Operation<Boolean> original){
        //Shift clicking on a stack in the inventory will move it into the crafting grid (instead of into the hotbar)
        return original.call(instance, stack, 1, 17, reverseDirection);
    }
}
