package user11681.phormat.api;

import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import user11681.phormat.asm.access.ExtendedFormatting;
import user11681.phormat.asm.mixin.FormattingAccess;

@SuppressWarnings("JavaReflectionMemberAccess")
public class FormattingRegistry {
    private static final Map<String, Formatting> nameMap = FormattingAccess.getNameMap();

    public static ExtendedFormatting register(String name, char code, int colorIndex, @Nullable Integer color) {
        return register(FormattingAccess.instantiate(name, FormattingAccess.getValues().length, name, code, colorIndex, color), code);
    }

    public static ExtendedFormatting register(String name, char code, boolean modifier) {
        return register(FormattingAccess.instantiate(name, FormattingAccess.getValues().length, name, code, modifier), code);
    }

    public static ExtendedFormatting register(String name, char code, boolean modifier, int colorIndex, @Nullable Integer color) {
        return register(FormattingAccess.instantiate(name, FormattingAccess.getValues().length, name, code, modifier, colorIndex, color), code);
    }

    private static ExtendedFormatting register(Formatting formatting, char code) {
        if (Formatting.byCode(code) != null) {
            throw new IllegalArgumentException(String.format("a Formatting with the code %s already exists.", code));
        }

        if (Formatting.byName(formatting.name()) != null) {
            throw new IllegalArgumentException(String.format("a Formatting with name %s already exists.", formatting.name()));
        }

        try {
            Formatting.class.getDeclaredField("phormat_custom").setBoolean(formatting, true);
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }

        Formatting[] values = FormattingAccess.getValues();
        int valueCount = values.length;
        Formatting[] newValues = new Formatting[valueCount + 1];

        System.arraycopy(values, 0, newValues, 0, valueCount);

        newValues[valueCount] = formatting;

        FormattingAccess.setValues(newValues);

        nameMap.put(FormattingAccess.sanitize(formatting.name()), formatting);

        FormattingAccess.setPattern(Pattern.compile(FormattingAccess.getPattern().toString().replace("]", code + "]")));

        return (ExtendedFormatting) (Object) formatting;
    }
}
