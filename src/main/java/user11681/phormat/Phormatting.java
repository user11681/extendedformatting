package user11681.phormat;

import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import user11681.phormat.internal.PhormatInitializer;

public class Phormatting {
    public final String name;

    public Phormatter formatter;
    public ColorFunction colorFunction;
    public Formatting formatting;

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

    private Phormatting(final String name, final char code) {
        if (!PhormatInitializer.initializing) {
            throw new IllegalStateException("All custom formattings should be constructed in classes referenced in the \"phormat:init\" entrypoint.");
        }

        if (PhormatInitializer.names.contains(name)) {
            throw new IllegalArgumentException(String.format("a formatting with name %s already exists.", name));
        }

        this.name = name;

        PhormatInitializer.formatToCode.put(this, code);
    }

    public Phormatting formatter(final Phormatter formatter) {
        this.formatter = formatter;

        return this;
    }

    public Phormatting color(final ColorFunction colorFunction) {
        this.colorFunction = colorFunction;

        return this;
    }

    @Deprecated // for removal; use the field.
    public Phormatter getFormatter() {
        return this.formatter;
    }

    @Deprecated // for removal; use the field.
    public ColorFunction getColorFunction() {
        return this.colorFunction;
    }

    @Deprecated // for removal; use the field.
    public Formatting formatting() {
        return this.formatting;
    }
}
