package vcfix.mixin.vc;

import net.minecraft.item.ItemStack;
import noppes.vc.VCRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.LinkedHashMap;
import java.util.List;

@Mixin(VCRecipes.RecipeContainer.class)
public interface VCRecipesRecipeContainerAccessor {
    @Accessor(value = "items", remap = false)
    LinkedHashMap<ItemStack, List<ItemStack>> getRecipes();
}
