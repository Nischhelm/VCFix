package vcfix.mixin.vc;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import noppes.vc.containers.ContainerCarpentryBench;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ContainerCarpentryBench.class)
public abstract class ContainerCarpentryBenchMixin_StopDrop {
    @WrapWithCondition(
            method = "onContainerClosed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;")
    )
    private boolean vcfix_vcContainerCarpentryBench_containerClosed(EntityPlayer player, ItemStack stack, boolean unused) {
        if (!player.isEntityAlive() || player instanceof EntityPlayerMP && ((EntityPlayerMP) player).hasDisconnected())
            return true; //original behavior of dropping the items
        else {
            player.inventory.placeItemBackInInventory(player.world, stack);
            return false;
        }
    }
}
