package user11681.extendedformatting.asm.mixin.formatting;

import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import user11681.extendedformatting.asm.mixin.access.FormattingAccess;

@Mixin(Formatting.class)
public class FormattingMixin implements FormattingAccess {
    @Unique
    public boolean modded;

    @Override
    public boolean isModded() {
        return this.modded;
    }
}
