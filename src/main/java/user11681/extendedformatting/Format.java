package user11681.extendedformatting;

import javax.annotation.Nullable;
import net.minecraft.util.Formatting;
import user11681.extendedformatting.api.Formatter;

public class Format {
    public final String name;

    public Formatter formatter;

    Formatting formatting;

    public Format(final String name, final char code, final int colorIndex, @Nullable final Integer color) {
        this(name);

        ExtendedFormatting.colorAdder.addEnum(name, name, code, colorIndex, color);
    }

    public Format(final String name, final char code, final boolean modifier) {
        this(name);

        ExtendedFormatting.modifierAdder.addEnum(name, name, code, modifier);
    }

    public Format(final String name, final char code, final boolean modifier, final int colorIndex, @Nullable final Integer color) {
        this(name);

        ExtendedFormatting.primaryAdder.addEnum(name, name, code, modifier, colorIndex, color);
    }

    private Format(final String name) {
        if (!ExtendedFormatting.initializing) {
            throw new IllegalStateException("All custom formats should be constructed in the \"extendedformatting\" entrypoint.");
        }

        this.name = name;

        ExtendedFormatting.formats.add(this);
    }

    public Formatting formatting() {
        return this.formatting;
    }
}
