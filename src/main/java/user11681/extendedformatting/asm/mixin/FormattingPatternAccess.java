package user11681.extendedformatting.asm.mixin;

import java.util.regex.Pattern;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Formatting.class)
public interface FormattingPatternAccess {
    @Accessor("FORMATTING_CODE_PATTERN")
    static Pattern getPattern() {
        throw new IllegalStateException();
    }

    @Accessor("FORMATTING_CODE_PATTERN")
    static void setPattern(final Pattern pattern) {
        throw new IllegalStateException();
    }
}
