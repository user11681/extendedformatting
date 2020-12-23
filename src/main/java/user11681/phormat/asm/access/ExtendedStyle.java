package user11681.phormat.asm.access;

import java.util.Set;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface ExtendedStyle {
    boolean hasFormatting(ExtendedFormatting formatting);

    boolean hasFormatting(Formatting formatting);

    Set<ExtendedFormatting> getFormattings();

    void transferFormatting(Style to);

    void addFormatting(Formatting formatting);

    void addFormattings(Iterable<Formatting> formattings);

    void addPhormattings(Iterable<ExtendedFormatting> formattings);

    void addPhormattings(Formatting... formattings);

    void addPhormattings(ExtendedFormatting... formattings);

    void addFormatting(ExtendedFormatting formatting);

    Style cast();
}
