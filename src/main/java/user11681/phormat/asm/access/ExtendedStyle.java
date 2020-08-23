package user11681.phormat.asm.access;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface ExtendedStyle {
    boolean hasCustomFormatting(Formatting formatting);

    void addCustomFormatting(Formatting formatting);

    void addCustomFormattings(Formatting... formattings);

    void addCustomFormattings(PhormatAccess... formattings);

    void addCustomFormatting(PhormatAccess formatting);

    ReferenceArrayList<PhormatAccess> getCustomFormattings();

    Style cast();
}
