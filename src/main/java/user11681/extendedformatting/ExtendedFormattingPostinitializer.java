package user11681.extendedformatting;

import com.chocohead.mm.api.ClassTinkerers;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.Formatting;
import user11681.extendedformatting.asm.access.CustomFormattingAccess;
import user11681.extendedformatting.asm.mixin.FormattingPatternAccess;

public class ExtendedFormattingPostinitializer implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        final String pattern = FormattingPatternAccess.getPattern().toString();

        for (final Reference2CharMap.Entry<Format> entry : ExtendedFormattingInitializer.formatToCode.reference2CharEntrySet()) {
            final Format format = entry.getKey();
            final char code = entry.getCharValue();

            format.formatting = ClassTinkerers.getEnum(Formatting.class, format.name);
            ((CustomFormattingAccess) (Object) format.formatting).setFormat(format);

            if (pattern.indexOf(code) >= 0) {
                throw new IllegalArgumentException(String.format("a formatting with the code %s already exists.", code));
            }

            FormattingPatternAccess.setPattern(Pattern.compile(pattern.replace("]", code + "]")));
        }
    }
}
