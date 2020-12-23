package user11681.phormat.asm.mixin.access;

import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings({"ConstantConditions", "unused"})
@Mixin(Formatting.class)
public interface FormattingAccess {
    @Accessor("FORMATTING_CODE_PATTERN")
    static Pattern getPattern() {
        throw null;
    }

    @Accessor("FORMATTING_CODE_PATTERN")
    static void setPattern(Pattern pattern) {
        throw null;
    }

    @Accessor("BY_NAME")
    static Map<String, Formatting> getNameMap() {
        throw null;
    }

    @Invoker("sanitize")
    static String sanitize(String name) {
        throw null;
    }
}
