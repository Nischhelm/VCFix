package vcfix.mixin.vc;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Cancellable;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noppes.vc.blocks.BlockCrate;
import noppes.vc.blocks.BlockPedestal;
import noppes.vc.blocks.BlockWeaponRack;
import noppes.vc.blocks.tiles.TileBasicContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BlockCrate.class, BlockWeaponRack.class, BlockPedestal.class})
public abstract class BlockCrateMixin extends Block {
    public BlockCrateMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @ModifyExpressionValue(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;"))
    private TileEntity vcfix_dontCrash(TileEntity tile, World world, BlockPos pos, IBlockState state, @Cancellable CallbackInfo ci) {
        if(!(tile instanceof TileBasicContainer)) {
            ci.cancel();
            super.breakBlock(world, pos, state);
        }
        return tile;
    }
}
