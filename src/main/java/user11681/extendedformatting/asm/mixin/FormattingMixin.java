package user11681.extendedformatting.asm.mixin;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import user11681.extendedformatting.Format;
import user11681.extendedformatting.asm.access.CustomFormattingAccess;

@Mixin(Formatting.class)
public abstract class FormattingMixin implements CustomFormattingAccess {
    @Unique
    private final Formatting self = (Formatting) (Object) this;

    @Unique
    private Format format;

    @Unique
    public boolean custom;

    @Override
    @Unique
    public boolean isCustom() {
        return this.custom;
    }

    @Override
    @Unique
    public Format getFormat() {
        return this.format;
    }

    @Override
    @Unique
    public void setFormat(final Format format) {
        this.format = format;
        this.custom = true;
    }

    @Override
    @Unique
    public Formatting cast() {
        return self;
    }
}
