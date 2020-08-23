package user11681.extendedformatting;

import javax.annotation.Nullable;
import net.minecraft.util.Formatting;

public class Format {
    public final String name;

    public Formatter formatter;

    Formatting formatting;

    public Format(final String name, final char code, final int colorIndex, @Nullable final Integer color) {
        this(name, code);

        ExtendedFormatting.colorAdder.addEnum(name, name, code, colorIndex, color);
    }

    public Format(final String name, final char code, final boolean modifier) {
        this(name, code);

        ExtendedFormatting.modifierAdder.addEnum(name, name, code, modifier);
    }

    public Format(final String name, final char code, final boolean modifier, final int colorIndex, @Nullable final Integer color) {
        this(name, code);

        ExtendedFormatting.primaryAdder.addEnum(name, name, code, modifier, colorIndex, color);
    }

    public Format(final String name, final char code, final int colorIndex, @Nullable final Integer color, final Formatter formatter) {
        this(name, code, formatter);

        ExtendedFormatting.colorAdder.addEnum(name, name, code, colorIndex, color);
    }

    public Format(final String name, final char code, final boolean modifier, final Formatter formatter) {
        this(name, code, formatter);

        ExtendedFormatting.modifierAdder.addEnum(name, name, code, modifier);
    }

    public Format(final String name, final char code, final boolean modifier, final int colorIndex, @Nullable final Integer color, final Formatter formatter) {
        this(name, code, formatter);

        ExtendedFormatting.primaryAdder.addEnum(name, name, code, modifier, colorIndex, color);
    }

    private Format(final String name, final char code, final Formatter formatter) {
        this(name, code);

        this.formatter = formatter;
    }

    private Format(final String name, final char code) {
        if (!ExtendedFormatting.initializing) {
            throw new IllegalStateException("All custom formats should be constructed in the \"extendedformatting\" entrypoint.");
        }

        this.name = name;

        ExtendedFormatting.formats.put(this, code);

    }

    public Formatting formatting() {
        return this.formatting;
    }
}
