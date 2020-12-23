package user11681.phormat.asm.access;

import net.minecraft.util.Formatting;
import user11681.phormat.api.ColorFunction;
import user11681.phormat.api.format.TextFormatter;

public interface ExtendedFormatting {
    boolean custom();

    char code();

    ColorFunction colorFunction();

    ExtendedFormatting colorFunction(ColorFunction colorFunction);

    TextFormatter formatter();

    ExtendedFormatting formatter(TextFormatter formatter);

    Formatting cast();
}
