package user11681.extendedformatting.asm.mixin.formatting;

import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.font.TextRenderer$Drawer")
public class TextRendererDrawerMixin {
    @Inject(method = "accept", at = @At("RETURN"))
    public void drawOverline(final int i, final Style style, final int j, final CallbackInfoReturnable<Boolean> info) {

    }
}
