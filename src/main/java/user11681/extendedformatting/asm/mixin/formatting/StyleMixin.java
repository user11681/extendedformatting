package user11681.extendedformatting.asm.mixin.formatting;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.extendedformatting.asm.mixin.access.FormattingAccess;

@Mixin(Style.class)
public class StyleMixin implements StyleAccess {
    @Unique
    private final Set<FormattingAccess> formats = new ObjectOpenHashSet<>();

    @Override
    public boolean hasFormat(final Formatting format) {
        return this.formats.contains(format);
    }

    @Override
    public void addFormat(final Formatting format) {
        this.formats.add(FormattingAccess.class.cast(format));
    }

    @Override
    public void addFormat(final FormattingAccess format) {
        this.formats.add(format);
    }

    @Inject(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting"},
              at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting formatting, final CallbackInfoReturnable<Style> info) {
        if (FormattingAccess.class.cast(formatting).isModded()) {
            ((StyleAccess) info.getReturnValue()).addFormat(formatting);
        }
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting[] formattings, final CallbackInfoReturnable<Style> info) {
        final StyleAccess style = (StyleAccess) info.getReturnValue();

        for (final FormattingAccess format : FormattingAccess[].class.cast(formattings)) {
            if (format.isModded()) {
                style.addFormat(format);
            }
        }
    }
}
