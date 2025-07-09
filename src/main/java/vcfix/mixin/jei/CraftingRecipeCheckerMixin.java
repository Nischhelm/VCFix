package vcfix.mixin.jei;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.crafting.IRecipe;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vcfix.jei.VCJEIPlugin;

//Copied and tweaked from RLMixins
@Mixin(targets = "mezz.jei.plugins.vanilla.crafting.CraftingRecipeChecker$CraftingRecipeValidator")
public abstract class CraftingRecipeCheckerMixin<T extends IRecipe> {

    //This only stops the log warn, but still doesn't allow those recipes on the vanilla crafting table cause they don't fit
    @WrapWithCondition(
            method = "isRecipeValid",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V"),
            remap = false
    )
    public boolean vcfix_jeiCraftingRecipeChecker_isRecipeValid(Logger logger, String errorMsg, Object recipeInfo, @Local(argsOnly = true) T recipe) {
        if(!errorMsg.startsWith("Recipe has too many inputs.")) return true;
        return !VCJEIPlugin.isBigRecipe(recipe);
    }
}