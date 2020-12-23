package user11681.phormat.asm.access;

import java.util.Set;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface ExtendedStyle {
    Set<Formatting> getFormattings();

    boolean hasFormatting(Formatting formatting);

    void addFormatting(Formatting formatting);

    void addFormattings(Formatting... formattings);

    void addFormattings(Iterable<Formatting> formattings);

    void transferFormatting(Style to);

    Style cast();
}
