package user11681.phormat;

import com.chocohead.mm.api.ClassTinkerers;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.util.Formatting;
import user11681.phormat.asm.access.PhormatAccess;
import user11681.phormat.asm.mixin.FormattingPatternAccess;

public class PhormatPostinitializer implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        final String pattern = FormattingPatternAccess.getPattern().toString();

        for (final Reference2CharMap.Entry<Phormatting> entry : PhormatInitializer.formatToCode.reference2CharEntrySet()) {
            final Phormatting format = entry.getKey();
            final char code = entry.getCharValue();

            format.formatting = ClassTinkerers.getEnum(Formatting.class, format.name);
            ((PhormatAccess) (Object) format.formatting).setFormat(format);

            if (pattern.indexOf(code) >= 0) {
                throw new IllegalArgumentException(String.format("a formatting with the code %s already exists.", code));
            }

            FormattingPatternAccess.setPattern(Pattern.compile(pattern.replace("]", code + "]")));
        }

        PhormatInitializer.formatToCode = null;
    }
}
