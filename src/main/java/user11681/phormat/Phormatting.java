package user11681.phormat;

import javax.annotation.Nullable;
import net.minecraft.util.Formatting;

public class Phormatting {
    public final String name;

    public Phormatter formatter;

    Formatting formatting;

    public Phormatting(final String name, final char code, final int colorIndex, @Nullable final Integer color) {
        this(name, code);

        PhormatInitializer.colorAdder.addEnum(name, name, code, colorIndex, color);
    }

    public Phormatting(final String name, final char code, final boolean modifier) {
        this(name, code);

        PhormatInitializer.modifierAdder.addEnum(name, name, code, modifier);
    }

    public Phormatting(final String name, final char code, final boolean modifier, final int colorIndex, @Nullable final Integer color) {
        this(name, code);

        PhormatInitializer.primaryAdder.addEnum(name, name, code, modifier, colorIndex, color);
    }

    public Phormatting(final String name, final char code, final int colorIndex, @Nullable final Integer color, final Phormatter formatter) {
        this(name, code, formatter);

        PhormatInitializer.colorAdder.addEnum(name, name, code, colorIndex, color);
    }

    public Phormatting(final String name, final char code, final boolean modifier, final Phormatter formatter) {
        this(name, code, formatter);

        PhormatInitializer.modifierAdder.addEnum(name, name, code, modifier);
    }

    public Phormatting(final String name, final char code, final boolean modifier, final int colorIndex, @Nullable final Integer color, final Phormatter formatter) {
        this(name, code, formatter);

        PhormatInitializer.primaryAdder.addEnum(name, name, code, modifier, colorIndex, color);
    }

    private Phormatting(final String name, final char code, final Phormatter formatter) {
        this(name, code);

        this.formatter = formatter;
    }

    private Phormatting(final String name, final char code) {
        if (!PhormatInitializer.initializing) {
            throw new IllegalStateException("All custom formats should be constructed in the \"extendedformatting\" entrypoint.");
        }

        if (PhormatInitializer.names.contains(name)) {
            throw new IllegalArgumentException(String.format("a formatting with name %s already exists.", name));
        }

        this.name = name;

        PhormatInitializer.formatToCode.put(this, code);

    }

    public Formatting formatting() {
        return this.formatting;
    }
}
