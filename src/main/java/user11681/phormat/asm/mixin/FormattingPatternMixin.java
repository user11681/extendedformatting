package user11681.phormat.asm.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import it.unimi.dsi.fastutil.objects.Reference2CharMap;
import java.util.regex.Pattern;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import user11681.phormat.internal.PhormatInitializer;
import user11681.phormat.Phormatting;
import user11681.phormat.asm.access.PhormatAccess;

@Mixin(Formatting.class)
abstract class FormattingPatternMixin {
    @Final
    @Shadow
    @Mutable
    private static Pattern FORMATTING_CODE_PATTERN;

    static {
        final String pattern = FORMATTING_CODE_PATTERN.toString();

        for (final Reference2CharMap.Entry<Phormatting> entry : PhormatInitializer.formatToCode.reference2CharEntrySet()) {
            final Phormatting format = entry.getKey();
            final char code = entry.getCharValue();

            format.formatting = ClassTinkerers.getEnum(Formatting.class, format.name);
            ((PhormatAccess) (Object) format.formatting).setFormat(format);

            if (pattern.indexOf(code) >= 0) {
                throw new IllegalArgumentException(String.format("a formatting with the code %s already exists.", code));
            }

            FORMATTING_CODE_PATTERN = Pattern.compile(pattern.replace("]", code + "]"));
        }

        PhormatInitializer.formatToCode = null;
    }
}
