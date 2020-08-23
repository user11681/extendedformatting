package user11681.extendedformatting;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.text.Style;
import user11681.extendedformatting.asm.access.TextRendererDrawerAccess;

public abstract class Formatter {
    public abstract void format(TextRendererDrawerAccess drawer,
                                Style style,
                                int i,
                                int j,
                                FontStorage storage,
                                Glyph glyph,
                                GlyphRenderer glyphRenderer,
                                boolean isBold,
                                float red,
                                float green,
                                float blue,
                                float alpha,
                                float advance);
}
