package vcfix.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class CarpentryBenchShapedRecipeWrapper extends CarpentryBenchRecipeWrapper<IShapedRecipe> implements IShapedCraftingRecipeWrapper {
    private final int height, width;

    public CarpentryBenchShapedRecipeWrapper(IJeiHelpers jeiHelpers, IShapedRecipe recipe) {
        super(jeiHelpers, recipe);
        this.height = recipe.getRecipeHeight();
        this.width = recipe.getRecipeWidth();
    }

    @Override public int getHeight(){
        return this.height;
    }
    @Override public int getWidth(){
        return this.width;
    }
}