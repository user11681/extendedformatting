package user11681.extendedformatting;

import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.text.Style;
import user11681.extendedformatting.asm.mixin.TextRendererDrawerAccess;

public class Test {
    public static final Format format = new Format("OVERLINE", 'O', true);

    public static void init() {}

    static {
        format.formatter = (final Style style, final int i, final int j, final float red, final float green, final float blue, final float advance, final TextRendererDrawerAccess drawer) ->
            drawer.addRectangle(new GlyphRenderer.Rectangle(drawer.x(), drawer.y() - 2, drawer.x() + advance, drawer.y() - 1, 0.01F, red, green, blue, drawer.alpha()));
    }
}
