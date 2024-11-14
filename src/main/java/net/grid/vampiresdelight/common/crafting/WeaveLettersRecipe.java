package net.grid.vampiresdelight.common.crafting;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.component.VampireBookContents;
import de.teamlapen.vampirism.util.VampireBookManager;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class WeaveLettersRecipe extends CustomRecipe {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "weave_letters");

    public WeaveLettersRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        int weatheredLetterCount = 0;

        for (int i = 0; i < craftingInput.size(); ++i) {
            ItemStack itemStack = craftingInput.getItem(i);
            if (!itemStack.isEmpty()) {
                if (itemStack.is(VDItems.WEATHERED_LETTER)) {
                    weatheredLetterCount++;
                } else {
                    return false;
                }
            }
        }

        return weatheredLetterCount == 3;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        ItemStack itemStack = new ItemStack(ModItems.VAMPIRE_BOOK.get());
        VampireBookManager.BookContext bookContext = VampireBookManager.getInstance().getRandomBook(RandomSource.create());
        VampireBookContents.addFromBook(itemStack, bookContext);

        return itemStack;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i * i1 >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VDRecipes.WEAVE_LETTERS.get();
    }
}
