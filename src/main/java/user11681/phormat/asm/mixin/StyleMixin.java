package user11681.phormat.asm.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.phormat.asm.access.ExtendedStyle;
import user11681.phormat.asm.access.PhormatAccess;

@Mixin(Style.class)
public abstract class StyleMixin implements ExtendedStyle {
    @Unique
    private final ReferenceArrayList<PhormatAccess> customFormattings = new ReferenceArrayList<>(10);

    @Override
    @Unique
    public ReferenceArrayList<PhormatAccess> getCustomFormattings() {
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
        this.customFormattings.add(PhormatAccess.class.cast(format));
    }

    @Override
    @Unique
    public void addCustomFormattings(final Formatting... formattings) {
        this.customFormattings.addElements(this.customFormattings.size(), PhormatAccess[].class.cast(formattings));
    }

    @Override
    @Unique
    public void addCustomFormattings(final PhormatAccess... formattings) {
        this.customFormattings.addElements(this.customFormattings.size(), formattings);
    }

    @Override
    @Unique
    public void addCustomFormatting(final PhormatAccess format) {
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

        if (PhormatAccess.class.cast(formatting).isCustom()) {
            style.addCustomFormatting(formatting);
        }
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting[] formattings, final CallbackInfoReturnable<Style> info) {
        final StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        for (final PhormatAccess format : PhormatAccess[].class.cast(formattings)) {
            if (format.isCustom()) {
                style.customFormattings.add(format);
            }
        }
    }
}
