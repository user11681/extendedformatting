package user11681.extendedformatting.asm.access;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

public interface StyleAccess {
    boolean hasCustomFormatting(Formatting formatting);

    void addCustomFormatting(Formatting formatting);

    void addCustomFormattings(Formatting... formattings);

    void addCustomFormattings(FormattingAccess... formattings);

    void addCustomFormatting(FormattingAccess formatting);

    ReferenceArrayList<FormattingAccess> getCustomFormattings();

    Style cast();
}
