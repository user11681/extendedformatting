package user11681.extendedformatting.asm.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.extendedformatting.asm.access.ExtendedStyle;
import user11681.extendedformatting.asm.access.CustomFormattingAccess;

@Mixin(Style.class)
public abstract class StyleMixin implements ExtendedStyle {
    @Unique
    private final ReferenceArrayList<CustomFormattingAccess> customFormattings = new ReferenceArrayList<>(10);

    @Override
    @Unique
    public ReferenceArrayList<CustomFormattingAccess> getCustomFormattings() {
        return this.customFormattings;
    }

    @Override
    @Unique
    public boolean hasCustomFormatting(final Formatting format) {
        return this.customFormattings.contains(format);
    }

    @Override
    @Unique
    public void addCustomFormatting(final Formatting format) {
        this.customFormattings.add(CustomFormattingAccess.class.cast(format));
    }

    @Override
    @Unique
    public void addCustomFormattings(final Formatting... formattings) {
        this.customFormattings.addElements(this.customFormattings.size(), CustomFormattingAccess[].class.cast(formattings));
    }

    @Override
    @Unique
    public void addCustomFormattings(final CustomFormattingAccess... formattings) {
        this.customFormattings.addElements(this.customFormattings.size(), formattings);
    }

    @Override
    @Unique
    public void addCustomFormatting(final CustomFormattingAccess format) {
        this.customFormattings.add(format);
    }

    @Override
    @Unique
    public Style cast() {
        return (Style) (Object) this;
    }

    @Inject(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting"},
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting formatting, final CallbackInfoReturnable<Style> info) {
        final StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        style.customFormattings.addAll(customFormattings);

        if (CustomFormattingAccess.class.cast(formatting).isCustom()) {
            style.addCustomFormatting(formatting);
        }
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting[] formattings, final CallbackInfoReturnable<Style> info) {
        final StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        for (final CustomFormattingAccess format : CustomFormattingAccess[].class.cast(formattings)) {
            if (format.isCustom()) {
                style.customFormattings.add(format);
            }
        }
    }
}
