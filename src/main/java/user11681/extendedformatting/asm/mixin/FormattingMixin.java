package user11681.extendedformatting.asm.mixin;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import user11681.extendedformatting.Format;
import user11681.extendedformatting.asm.access.FormattingAccess;

@Mixin(Formatting.class)
public abstract class FormattingMixin implements FormattingAccess {
    @Unique
    private final Formatting self = (Formatting) (Object) this;

    @Unique
    private Format format;

    @Unique
    public boolean modded;

    @Override
    @Unique
    public boolean isModded() {
        return this.modded;
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
    }

    @Override
    @Unique
    public Formatting cast() {
        return self;
    }
}
