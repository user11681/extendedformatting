package user11681.phormat.asm.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.phormat.ColorFunction;
import user11681.phormat.asm.access.PhormatAccess;

@Mixin(TextColor.class)
abstract class TextColorMixin {
    @Shadow
    @Final
    private int rgb;

    @Unique
    private int previousColor = this.rgb;

    @Unique
    private boolean hasColorFunction;

    @Unique
    private ColorFunction colorFunction;

    @Inject(method = "fromFormatting",
            at = @At("RETURN"),
            cancellable = true)
    private static void setColorFunction(final Formatting formatting, final CallbackInfoReturnable<TextColor> info) {
        final TextColorMixin color = (TextColorMixin) (Object) info.getReturnValue();

        if (color != null) {
            final PhormatAccess formattingAccess = (PhormatAccess) (Object) formatting;

            if (formattingAccess.isCustom()) {
                final ColorFunction colorFunction = formattingAccess.getPhormatting().getColorFunction();

                if (colorFunction != null) {
                    color.colorFunction = colorFunction;
                    color.hasColorFunction = true;
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "getRgb",
            at = @At("HEAD"),
            cancellable = true)
    public void applyColorFunction(final CallbackInfoReturnable<Integer> info) {
        if (this.hasColorFunction) {
            info.setReturnValue(this.previousColor = this.colorFunction.apply(this.rgb));
        }
    }

    @Inject(method = "getHexCode",
            at = @At("HEAD"),
            cancellable = true)
    public void matchHexCode(final CallbackInfoReturnable<String> info) {
        if (this.hasColorFunction) {
            info.setReturnValue(Integer.toHexString(this.previousColor));
        }
    }
}
