package user11681.phormat.asm.access;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Collection;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface ExtendedStyle {
    boolean hasFormatting(ExtendedFormatting formatting);

    boolean hasFormatting(Formatting formatting);

    ReferenceOpenHashSet<ExtendedFormatting> getFormattings();

    void transferFormatting(Style to);

    void addFormatting(Formatting formatting);

    void addFormattings(Collection<Formatting> formattings);

    void addPhormattings(Collection<ExtendedFormatting> formattings);

    void addPhormattings(Formatting... formattings);

    void addPhormattings(ExtendedFormatting... formattings);

    void addFormatting(ExtendedFormatting formatting);

    Style cast();
}
