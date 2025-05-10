package vcfix.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import noppes.vc.VCBlocks;
import noppes.vc.client.gui.GuiCarpentryBench;
import noppes.vc.containers.ContainerCarpentryBench;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@JEIPlugin
@SuppressWarnings({"unused","deprecation"})
public class VCJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //Adding the category
        registry.addRecipeCategories(new CarpentryBenchRecipeCategory(guiHelper));

        //Adding the original recipes
        registry.addRecipes(getBigRecipes(jeiHelpers), CarpentryBenchRecipeCategory.UID);

        //Adding a "Get Recipes" click area on top of the arrow inside the GUI that shows both vanilla and VC recipes
        registry.addRecipeClickArea(GuiCarpentryBench.class, 97, 38, 28, 23, CarpentryBenchRecipeCategory.UID, VanillaRecipeCategoryUid.CRAFTING);

        //Adding button to move the items to carpentry bench to craft the currently viewed CB recipe
        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
        recipeTransferRegistry.addRecipeTransferHandler(ContainerCarpentryBench.class, CarpentryBenchRecipeCategory.UID,
                1, //startIndex crafting
                16,  //count of crafting slots
                17,  //startIndex player
                36   //count of player slots
        );
        //Special TransferInfo for Vanilla Recipes only using the top left 3x3 bc JEI assumes a 3x3 maximum
        recipeTransferRegistry.addRecipeTransferHandler(new IRecipeTransferInfo<ContainerCarpentryBench>() {
            @Override @Nonnull public Class<ContainerCarpentryBench> getContainerClass() { return ContainerCarpentryBench.class; }
            @Override @Nonnull public String getRecipeCategoryUid() { return VanillaRecipeCategoryUid.CRAFTING; }
            @Override public boolean canHandle(@Nonnull ContainerCarpentryBench container) { return true; }
            @Override @Nonnull public List<Slot> getInventorySlots(@Nonnull ContainerCarpentryBench container) { return IntStream.range(17, 53).mapToObj(container::getSlot).collect(Collectors.toList()); }
            @Override @Nonnull public List<Slot> getRecipeSlots(@Nonnull ContainerCarpentryBench container) {
                return Arrays.stream(new int[]{
                        1,2,3, //4,
                        5,6,7, //8,
                        9,10,11 //,12,
                        //13, 14, 15, 16
                }).mapToObj(container::getSlot).collect(Collectors.toList());
            }
        });

        //See CarpentryBench Recipes when right-clicking the Bench block in JEI
        registry.addRecipeCatalyst(new ItemStack(VCBlocks.carpentry_bench), CarpentryBenchRecipeCategory.UID);
    }

    public static List<CarpentryBenchRecipeWrapper<? extends IRecipe>> getBigRecipes(IJeiHelpers jeiHelpers){
        List<CarpentryBenchRecipeWrapper<? extends IRecipe>> recipes = new ArrayList<>();

        for(IRecipe recipe : ForgeRegistries.RECIPES)
            if(isBigRecipe(recipe)) {
                if(recipe instanceof IShapedRecipe)
                    recipes.add(new CarpentryBenchShapedRecipeWrapper(jeiHelpers, (IShapedRecipe) recipe));
                else
                    recipes.add(new CarpentryBenchRecipeWrapper<>(jeiHelpers, recipe));
            }

        return recipes;
    }

    public static boolean isBigRecipe(IRecipe recipe){
        int recipeHeight = recipe instanceof IShapedRecipe ? ((IShapedRecipe) recipe).getRecipeHeight() : -1;
        int recipeWidth = recipe instanceof IShapedRecipe ? ((IShapedRecipe) recipe).getRecipeWidth() : -1;
        return recipeHeight == 4 || recipeWidth == 4 || recipe.getIngredients().size() > 9 && recipe.getIngredients().size() <= 16;
    }
}