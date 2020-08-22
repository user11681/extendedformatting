package user11681.extendedformatting.asm.mixin.formatting;

import net.minecraft.util.Formatting;
import user11681.extendedformatting.asm.mixin.access.FormattingAccess;

public interface StyleAccess {
    boolean hasFormat(Formatting format);

    void addFormat(Formatting format);

    void addFormat(FormattingAccess format);
}
