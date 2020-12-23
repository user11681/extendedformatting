package user11681.phormat.asm.mixin;

import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.phormat.api.ColorFunction;
import user11681.phormat.api.ExtendedFormatting;

@Mixin(TextColor.class)
abstract class TextColorMixin {
    @Shadow
    @Final
    private int rgb;

    @SuppressWarnings("FieldMayBeFinal") // mutated via ASM
    private int phormat_previousColor = this.rgb;

    private boolean phormat_hasColorFunction;

    @SuppressWarnings({"unused", "RedundantSuppression"})
    private ColorFunction phormat_colorFunction;

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "fromFormatting", at = @At("RETURN"), cancellable = true)
    private static void setColorFunction(Formatting formatting, CallbackInfoReturnable<TextColor> info) {
        if (formatting instanceof ExtendedFormatting) {
            TextColorMixin color = (TextColorMixin) (Object) info.getReturnValue();
            color.phormat_hasColorFunction = (color.phormat_colorFunction = ((ExtendedFormatting) formatting).colorFunction()) != null;
        }
    }

    @Inject(method = "getHexCode", at = @At("HEAD"), cancellable = true)
    public void matchHexCode(CallbackInfoReturnable<String> info) {
        if (this.phormat_hasColorFunction) {
            info.setReturnValue(Integer.toHexString(this.phormat_previousColor));
        }
    }
}
