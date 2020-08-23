package user11681.extendedformatting.api;

import net.minecraft.text.Style;
import user11681.extendedformatting.asm.mixin.TextRendererDrawerAccess;

/**
 * a functional interface for custom text formatting
 */
@FunctionalInterface
public interface Formatter {
    void format(Style style, int i, int j, float red, float green, float blue, float advance, TextRendererDrawerAccess drawer);
}
