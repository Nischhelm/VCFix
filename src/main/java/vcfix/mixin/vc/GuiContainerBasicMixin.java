package vcfix.mixin.vc;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import noppes.vc.shared.gui.GuiBasic;
import noppes.vc.shared.gui.GuiContainerBasic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainerBasic.class)
public abstract class GuiContainerBasicMixin extends GuiContainer {
    @Shadow(remap = false) private GuiBasic subgui;

    public GuiContainerBasicMixin(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Inject(
            method = "keyTyped",
            at = @At(value = "TAIL")
    )
    private void vcfix_vcGuiContainerBasic_keyTyped(char typedChar, int keyCode, CallbackInfo ci){
        if (this.subgui == null) this.checkHotbarKeys(keyCode);
    }
}
