package user11681.extendedformatting.asm.access;

import net.minecraft.util.Formatting;
import user11681.extendedformatting.Format;

public interface FormattingAccess {
    boolean isModded();

    Format getFormat();

    void setFormat(Format format);

    Formatting cast();
}
