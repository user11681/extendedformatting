package user11681.phormat.asm.mixin;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.phormat.api.ColorFunction;
import user11681.phormat.api.format.TextFormatter;
import user11681.phormat.asm.access.ExtendedFormatting;
import user11681.phormat.asm.mixin.access.FormattingAccess;

@SuppressWarnings("ConstantConditions")
@Mixin(Formatting.class)
abstract class FormattingMixin implements FormattingAccess, ExtendedFormatting {
    @Shadow
    @Final
    private char code;

    public boolean phormat_custom;

    @Unique
    private ColorFunction colorFunction;

    @Unique
    private TextFormatter formatter;

    @Override
    public char code() {
        return this.code;
    }

    @Override
    public ColorFunction colorFunction() {
        return this.colorFunction;
    }

    @Override
    public ExtendedFormatting colorFunction(ColorFunction colorFunction) {
        this.colorFunction = colorFunction;

        return this;
    }

    @Override
    public TextFormatter formatter() {
        return this.formatter;
    }

    @Override
    public ExtendedFormatting formatter(TextFormatter formatter) {
        this.formatter = formatter;

        return this;
    }

    @Override
    public boolean custom() {
        return this.phormat_custom;
    }

    @Override
    public Formatting cast() {
        return (Formatting) (Object) this;
    }

    @Redirect(method = "sanitize",
              at = @At(value = "INVOKE",
                       target = "java/lang/String.replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String replaceNone(String string, String regex, String replacement) {
        return string;
    }

    @Inject(method = "getColorValue", at = @At("HEAD"), cancellable = true)
    public void applyColorFunction(CallbackInfoReturnable<Integer> info) {
        if (this.phormat_custom && this.colorFunction != null) {
            info.setReturnValue(this.colorFunction.apply(info.getReturnValueI()));
        }
    }
}
