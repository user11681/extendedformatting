package user11681.phormat.asm.access;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Collection;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface ExtendedStyle {
    boolean hasPhormatting(ExtendedFormatting formatting);

    boolean hasPhormatting(Formatting formatting);

    ReferenceOpenHashSet<ExtendedFormatting> getPhormattings();

    void transferPhormats(Style to);

    void addPhormatting(Formatting formatting);

    void addFormattings(Collection<Formatting> formattings);

    void addPhormattings(Collection<ExtendedFormatting> formattings);

    void addPhormattings(Formatting... formattings);

    void addPhormattings(ExtendedFormatting... formattings);

    void addPhormatting(ExtendedFormatting formatting);

    Style cast();
}
