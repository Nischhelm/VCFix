package vcfix.mixin.vc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.StateMap;
import noppes.vc.VCBlocks;
import noppes.vc.blocks.BlockCarpentryBench;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VCBlocks.class)
public abstract class VCBlocksMixin {
    @WrapOperation(
            method = "registerModels",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/statemap/StateMap$Builder;ignore([Lnet/minecraft/block/properties/IProperty;)Lnet/minecraft/client/renderer/block/statemap/StateMap$Builder;")
    )
    public StateMap.Builder vcfix_unignoreBlockStateProperties(StateMap.Builder instance, IProperty<?>[] ignores, Operation<StateMap.Builder> original) {
        //all length 1 only ignore damage, remove that ignore
        if(ignores.length <= 1) return instance;

        //remove damage from 2 length ones (except lightable lamps)
        if(ignores[0].getName().equals("damage") && !ignores[1].getName().equals("lit"))
            return original.call(instance, new IProperty[]{ignores[1]});

        //remove type from carpentry bench
        if(ignores[1].equals(BlockCarpentryBench.TYPE))
            return original.call(instance, new IProperty[]{ignores[0]});

        //default behavior
        return original.call(instance, ignores);
    }
}
