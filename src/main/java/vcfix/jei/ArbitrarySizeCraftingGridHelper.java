package vcfix.jei;

import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.gui.CraftingGridHelper;
import net.minecraft.item.ItemStack;
import vcfix.VCFix;

import javax.annotation.Nonnull;
import java.util.List;

public class ArbitrarySizeCraftingGridHelper extends CraftingGridHelper {
    private final int craftInputSlot1, maxWidth, maxHeight;

    public ArbitrarySizeCraftingGridHelper(int craftInputSlot1, int craftOutputSlot, int maxWidth, int maxHeight) {
        super(craftInputSlot1, craftOutputSlot);
        this.craftInputSlot1 = craftInputSlot1;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    @Override
    public <T> void setInputs(@Nonnull IGuiIngredientGroup<T> ingredientGroup, @Nonnull List<List<T>> inputs) {
        setInputs(ingredientGroup, inputs, maxWidth, maxHeight);
    }

    @Override
    public <T> void setInputs(@Nonnull IGuiIngredientGroup<T> ingredientGroup, @Nonnull List<List<T>> inputs, int width, int height) {
        if(width > maxWidth || height > maxHeight || inputs.size() > maxHeight * maxWidth){
            VCFix.LOGGER.warn("Recipe doesn't fit, aborting transfer");
            return;
        }

        //Move all input items into their correct slots in the grid
        for (int i = 0; i < inputs.size(); i++)
            ingredientGroup.set(craftInputSlot1 + getCraftingIndex(i, width), inputs.get(i));
    }

    private int getCraftingIndex(int indexInInputList, int recipeWidth) {
        int row = indexInInputList / recipeWidth;
        int col = indexInInputList % recipeWidth;

        return row * maxWidth + col;
    }

    @Override
    @Deprecated
    public void setInputStacks(@Nonnull IGuiItemStackGroup guiItemStacks, @Nonnull List<List<ItemStack>> input) {
        VCFix.LOGGER.warn("ICraftingGridHelper.setInputStacks(group, stacks) is deprecated and not implemented in ArbitrarySizeCraftingGridHelper. Use setInputs");
    }

    @Override
    @Deprecated
    public void setInputStacks(@Nonnull IGuiItemStackGroup guiItemStacks, @Nonnull List<List<ItemStack>> input, int width, int height) {
        VCFix.LOGGER.warn("ICraftingGridHelper.setInputStacks(group, stacks, width, height) is deprecated and not implemented in ArbitrarySizeCraftingGridHelper. Use setInputs");
    }
}
