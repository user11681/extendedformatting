package user11681.phormat.api;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import user11681.phormat.asm.access.ExtendedFormatting;
import user11681.phormat.asm.mixin.access.FormattingAccess;
import user11681.phormat.asm.mixin.access.TextColorAccess;

@SuppressWarnings("JavaReflectionMemberAccess")
public class FormattingRegistry {
    private static final Map<String, Formatting> nameMap = FormattingAccess.getNameMap();
    private static final Reference2ObjectOpenHashMap<Formatting, TextColor> colorMap = new Reference2ObjectOpenHashMap<>(TextColorAccess.getFormattingColors());

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
        if (Character.toString(code).toLowerCase(Locale.ROOT).charAt(0) != code) {
            throw new IllegalArgumentException(String.format("%s; uppercase codes are not allowed.", code));
        }

        if (Formatting.byCode(code) != null) {
            throw new IllegalArgumentException(String.format("a Formatting with the code %s already exists.", code));
        }

        if (Formatting.byName(formatting.getName()) != null) {
            throw new IllegalArgumentException(String.format("a Formatting with name %s already exists.", formatting.getName()));
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

        if (formatting.isColor()) {
            colorMap.put(formatting, TextColorAccess.instantiate(formatting.getColorValue(), formatting.getName()));
        }

        return (ExtendedFormatting) (Object) formatting;
    }

    static {
        TextColorAccess.setFormattingColors(colorMap);
    }
}
