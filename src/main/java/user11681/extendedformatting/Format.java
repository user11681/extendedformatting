package user11681.extendedformatting;

import net.minecraft.util.Formatting;

public class Format {
    public Formatting formatting;
    public Formatter formatter;

    private static final

    public Format(final String name, final char code, final int colorIndex, final int color) {
        this(name, code, false, colorIndex, color);
    }

    public Format(final String name, final char code, final boolean modifier) {
        this(name, code, modifier, -1, 0xFFFFFF);
    }

    public Format(final String name, final char code, final boolean modifier, final int colorIndex, final int color) {
        this.name = name;
        this.code = code;
        this.modifier = modifier;
        this.colorIndex = colorIndex;
        this.color = color;
        this.string = "§" + code;
    }
}
