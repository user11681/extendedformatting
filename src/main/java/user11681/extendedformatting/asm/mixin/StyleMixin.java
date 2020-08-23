package user11681.extendedformatting.asm.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.extendedformatting.asm.access.FormattingAccess;
import user11681.extendedformatting.asm.access.StyleAccess;

@Mixin(Style.class)
public abstract class StyleMixin implements StyleAccess {
    @Unique
    private final Style self = (Style) (Object) this;

    @Unique
    private final ReferenceArrayList<FormattingAccess> customFormattings = ReferenceArrayList.wrap(new FormattingAccess[10]);

    @Override
    @Unique
    public ReferenceArrayList<FormattingAccess> getCustomFormattings() {
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
        this.customFormattings.add(FormattingAccess.class.cast(format));
    }

    @Override
    @Unique
    public void addCustomFormattings(final Formatting... formattings) {
        this.customFormattings.addElements(this.customFormattings.size(), FormattingAccess[].class.cast(formattings));
    }

    @Override
    @Unique
    public void addCustomFormattings(final FormattingAccess... formattings) {
        this.customFormattings.addElements(this.customFormattings.size(), formattings);
    }

    @Override
    @Unique
    public void addCustomFormatting(final FormattingAccess format) {
        this.customFormattings.add(format);
    }

    @Override
    @Unique
    public Style cast() {
        return self;
    }

    @Inject(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting"},
              at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting formatting, final CallbackInfoReturnable<Style> info) {
        final StyleAccess style = (StyleAccess) info.getReturnValue();

        style.addCustomFormattings(this.customFormattings.elements());

        if (FormattingAccess.class.cast(formatting).isModded()) {
            style.addCustomFormatting(formatting);
        }
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting[] formattings, final CallbackInfoReturnable<Style> info) {
        final StyleAccess style = (StyleAccess) info.getReturnValue();

        for (final FormattingAccess format : FormattingAccess[].class.cast(formattings)) {
            if (format.isModded()) {
                style.addCustomFormatting(format);
            }
        }
    }
}
