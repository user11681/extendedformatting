package user11681.phormat.asm.mixin;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
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
    public char getCode() {
        return this.code;
    }

    @Override
    public ColorFunction getColorFunction() {
        return this.colorFunction;
    }

    @Override
    public void setColorFunction(ColorFunction colorFunction) {
        this.colorFunction = colorFunction;
    }

    @Override
    public TextFormatter getFormatter() {
        return this.formatter;
    }

    @Override
    public void setFormatter(TextFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    @Unique
    public boolean isCustom() {
        return this.phormat_custom;
    }

    @Override
    @Unique
    public Formatting cast() {
        return (Formatting) (Object) this;
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "<clinit>",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/stream/Stream;collect(Ljava/util/stream/Collector;)Ljava/lang/Object;"))
    private static Object fixDuplicates(Stream<Formatting> stream, Collector<Formatting, Object, Map<String, Formatting>> collector) {
        Object[] values = stream.toArray();
        Map<String, Object> byName = new Object2ReferenceArrayMap<>(values.length);

        for (Object formatting : values) {
            byName.put(((Formatting) formatting).getName(), formatting);
        }

        return byName;
    }

    @Redirect(method = "sanitize",
              at = @At(value = "INVOKE",
                       target = "java/lang/String.replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
    private static String replaceNone(String string, String regex, String replacement) {
        return string;
    }

    @Inject(method = "getColorValue", at = @At("HEAD"), cancellable = true)
    public void applyColorFunction(CallbackInfoReturnable<Integer> info) {
        if (this.phormat_custom) {
            ColorFunction function = this.colorFunction;

            if (function != null) {
                info.setReturnValue(function.apply(info.getReturnValueI()));
            }
        }
    }
}
