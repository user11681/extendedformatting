package user11681.extendedformatting.asm.mixin;

import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.extendedformatting.api.Formatter;
import user11681.extendedformatting.asm.access.FormattingAccess;
import user11681.extendedformatting.asm.access.StyleAccess;

@Mixin(targets = "net.minecraft.client.font.TextRenderer$Drawer")
public abstract class TextRendererDrawerMixin implements TextRendererDrawerAccess {
    @Unique
    private float advance;

    @Unique
    private float red;

    @Unique
    private float green;

    @Unique
    private float blue;

    @ModifyVariable(method = "accept",
                    ordinal = 4,
                    name = "r",
                    at = @At(value = "INVOKE_ASSIGN",
                             target = "Lnet/minecraft/client/font/Glyph;getAdvance(Z)F"))
    public float captureAdvance(final float advance) {
        return this.advance = advance;
    }

    @ModifyVariable(method = "accept", at = @At(value = "STORE"), index = 8)
    public float captureRed(final float red) {
        return this.red = red;
    }

    @ModifyVariable(method = "accept", at = @At(value = "STORE"), index = 9)
    public float captureGreen(final float green) {
        return this.green = green;
    }

    @ModifyVariable(method = "accept", at = @At(value = "STORE"), index = 10)
    public float captureBlue(final float blue) {
        return this.blue = blue;
    }

    @Inject(method = "accept", at = @At("RETURN"))
    public void formatCustom(final int i, final Style style, final int j, final CallbackInfoReturnable<Boolean> info) {
        Formatter formatter;

        for (final FormattingAccess formatting : ((StyleAccess) style).getCustomFormattings().elements()) {
            if (formatting.getFormat() != null) {
                formatter = formatting.getFormat().formatter;

                if (formatter != null) {
                    formatter.format(style, i, j, red, green, blue, advance, this);
                }
            }
        }
    }
}
