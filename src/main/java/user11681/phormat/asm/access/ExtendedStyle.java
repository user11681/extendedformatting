package user11681.phormat.asm.access;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.util.Collection;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface ExtendedStyle {
    boolean hasPhormatting(PhormatAccess formatting);

    boolean hasPhormatting(Formatting formatting);

    ReferenceOpenHashSet<PhormatAccess> getPhormattings();

    void transferPhormats(Style to);

    void addPhormatting(Formatting formatting);

    void addFormattings(Collection<Formatting> formattings);

    void addPhormattings(Collection<PhormatAccess> formattings);

    void addPhormattings(Formatting... formattings);

    void addPhormattings(PhormatAccess... formattings);

    void addPhormatting(PhormatAccess formatting);

    Style cast();
}
