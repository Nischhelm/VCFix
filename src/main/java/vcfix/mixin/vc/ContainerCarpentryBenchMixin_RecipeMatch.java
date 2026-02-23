package vcfix.mixin.vc;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import noppes.vc.containers.ContainerCarpentryBench;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ContainerCarpentryBench.class)
public abstract class ContainerCarpentryBenchMixin_RecipeMatch {
    @Shadow(remap = false) public InventoryCrafting craftMatrix;
    @Shadow(remap = false) private World world;

    @ModifyExpressionValue(
            method = "onCraftMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnoppes/vc/VCRecipes;match(Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/world/World;)Lnet/minecraft/item/crafting/IRecipe;", remap = false)
    )
    private IRecipe vcfix_vcContainerCarpentryBench_onCraftMatrixChanged(IRecipe recipe){
        if(recipe == null) return null;
        if(!recipe.matches(this.craftMatrix, this.world)) return null; //only allow recipes that match using their own matches method
        return recipe;
    }
}
