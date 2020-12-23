package bruh;

import java.util.Random;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.font.GlyphRenderer;
import user11681.phormat.api.ExtendedFormatting;
import user11681.phormat.api.FormattingRegistry;

public class Test implements ModInitializer {
    public static final ExtendedFormatting test = FormattingRegistry.register("bigbruhmoment", (char) 229, true)
        .formatter((drawer, style, charIndex, character, storage, glyph, glyphRenderer, red, green, blue, alpha, advance) -> {
            float shadow = drawer.shadow() ? glyph.getShadowOffset() : 0;
            float x = drawer.x() + shadow;
            float y = drawer.y() + shadow - 1;

            drawer.invokeAddRectangle(new GlyphRenderer.Rectangle(x, -y, x + advance, 0, 0.01F, red, green, blue, alpha));
        });
    public static final ExtendedFormatting color = FormattingRegistry.register("color", (char) 230, false, 16, 0xAA00AA).colorFunction(color -> {
        Random random = new Random();

        return color + random.nextInt(1 << 24) + random.nextInt(1 << 16) + random.nextInt(1 << 8);
    });
    public static final ExtendedFormatting hybrid = FormattingRegistry.register("hybrid", (char) 231, false, 17, 0xA0FFA0).colorFunction(color.colorFunction()).formatter(test.formatter());

    @Override
    public void onInitialize() {
    }
}
