package vcfix.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.startup.ForgeModIdHelper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import noppes.vc.VCBlocks;

import javax.annotation.Nonnull;
import java.util.List;

public class CarpentryBenchRecipeCategory implements IRecipeCategory<IRecipeWrapper> {
    public static final String UID = "vc.carpentrybench";

    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;
    private final ICraftingGridHelper craftingGridHelper;

    private final IDrawable icon;
    private final IDrawable background;

    public CarpentryBenchRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(new ResourceLocation("variedcommodities:textures/gui/carpentry.png"), 16, 13, 138, 72);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(VCBlocks.carpentry_bench));
        this.craftingGridHelper = new ArbitrarySizeCraftingGridHelper(craftInputSlot1, craftOutputSlot, 4, 4);
    }

    @Override
    @Nonnull
    public String getUid() {
        return UID;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return VCBlocks.carpentry_bench.getLocalizedName();
    }

    @Override
    @Nonnull
    public String getModName() {
        return "Varied Commodities";
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        int slot = craftOutputSlot;
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(slot, false, 116, 27);

        slot = craftInputSlot1;
        for (int y = 0; y < 4; ++y)
            for (int x = 0; x < 4; ++x)
                guiItemStacks.init(slot++, true, x * 18, y * 18);

        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        if (recipeWrapper instanceof IShapedCraftingRecipeWrapper) {
            IShapedCraftingRecipeWrapper wrapper = (IShapedCraftingRecipeWrapper) recipeWrapper;
            this.craftingGridHelper.setInputs(guiItemStacks, inputs, wrapper.getWidth(), wrapper.getHeight());
        } else {
            this.craftingGridHelper.setInputs(guiItemStacks, inputs);
            recipeLayout.setShapeless();
        }

        guiItemStacks.set(0, outputs.get(0));

        if (recipeWrapper instanceof ICraftingRecipeWrapper) {
            ICraftingRecipeWrapper craftingRecipeWrapper = (ICraftingRecipeWrapper) recipeWrapper;
            ResourceLocation registryName = craftingRecipeWrapper.getRegistryName();
            if (registryName != null)
                guiItemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
                    if (slotIndex == craftOutputSlot) {
                        String recipeModId = registryName.getNamespace();

                        boolean modIdDifferent = false;
                        ResourceLocation itemRegistryName = ingredient.getItem().getRegistryName();
                        if (itemRegistryName != null) {
                            String itemModId = itemRegistryName.getNamespace();
                            modIdDifferent = !recipeModId.equals(itemModId);
                        }

                        if (modIdDifferent) {
                            String modName = ForgeModIdHelper.getInstance().getFormattedModNameForModId(recipeModId);
                            if (modName != null) {
                                tooltip.add(TextFormatting.GRAY + Translator.translateToLocalFormatted("jei.tooltip.recipe.by", modName));
                            }
                        }

                        boolean showAdvanced = Minecraft.getMinecraft().gameSettings.advancedItemTooltips || GuiScreen.isShiftKeyDown();
                        if (showAdvanced) {
                            tooltip.add(TextFormatting.DARK_GRAY + Translator.translateToLocalFormatted("jei.tooltip.recipe.id", registryName.toString()));
                        }
                    }
                });
        }
    }
}