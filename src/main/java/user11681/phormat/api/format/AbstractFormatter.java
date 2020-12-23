package user11681.phormat.api.format;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.text.Style;
import user11681.phormat.asm.access.TextRendererDrawerAccess;

/**
 * A {@linkplain TextFormatter formatter} that stores the arguments from {@linkplain TextFormatter#format TextFormatter#format} in a {@linkplain #context field}.
 */
public abstract class AbstractFormatter implements TextFormatter {
    /**
     * The arguments from {@linkplain TextFormatter#format(TextRendererDrawerAccess, Style, int, int, FontStorage, Glyph, GlyphRenderer, boolean, float, float, float, float, float)}.
     */
    protected final FormattingContext context = new FormattingContext();

    /**
     * Capture the arguments and store them in {@link #context}.
     */
    @Override
    public void format(
        TextRendererDrawerAccess drawer,
        Style style,
        int charIndex,
        int y,
        FontStorage storage,
        Glyph glyph,
        GlyphRenderer glyphRenderer,
        boolean isBold,
        float red,
        float green,
        float blue,
        float alpha,
        float advance) {
        FormattingContext context = this.context;

        context.drawer = drawer;
        context.style = style;
        context.charIndex = charIndex;
        context.y = y;
        context.storage = storage;
        context.glyph = glyph;
        context.glyphRenderer = glyphRenderer;
        context.isBold = isBold;
        context.red = red;
        context.green = green;
        context.blue = blue;
        context.alpha = alpha;
        context.advance = advance;

        this.format();
    }

    /**
     * Format the current glyph with the information in {@link #context}.
     */
    public abstract void format();
}
