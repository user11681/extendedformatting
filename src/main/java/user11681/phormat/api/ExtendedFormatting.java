package user11681.phormat.api;

import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class ExtendedFormatting extends Formatting {
    protected ColorFunction colorFunction;
    protected TextFormatter formatter;

    protected ExtendedFormatting(String name, char code, int colorIndex, @Nullable Integer colorValue) {
        super(name, field_1072.length, name, code, colorIndex, colorValue);
    }

    protected ExtendedFormatting(String name, char code, boolean modifier) {
        super(name, field_1072.length, name, code, modifier);
    }

    protected ExtendedFormatting(String name, char code, boolean modifier, int colorIndex, @Nullable Integer colorValue) {
        super(name, field_1072.length, name, code, modifier, colorIndex, colorValue);
    }

    @Nullable
    @Override
    public Integer getColorValue() {
        Integer color = super.getColorValue();

        if (this.colorFunction != null && color != null) {
            return this.colorFunction.apply(color);
        }

        return color;
    }

    public char code() {
        return super.code;
    }

    public ColorFunction colorFunction() {
        return this.colorFunction;
    }

    public ExtendedFormatting colorFunction(ColorFunction colorFunction) {
        this.colorFunction = colorFunction;

        return this;
    }

    public TextFormatter formatter() {
        return this.formatter;
    }

    public ExtendedFormatting formatter(TextFormatter formatter) {
        this.formatter = formatter;

        return this;
    }
}
