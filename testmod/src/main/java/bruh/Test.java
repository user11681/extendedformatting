package bruh;

import java.util.Random;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.font.GlyphRenderer;
import user11681.phormat.api.FormattingRegistry;
import user11681.phormat.api.format.SimpleFormatter;
import user11681.phormat.asm.access.ExtendedFormatting;
import user11681.phormat.asm.access.TextRendererDrawerAccess;

public class Test implements ModInitializer {
    public static final ExtendedFormatting test = FormattingRegistry.register("bigbruhmoment", (char) 229, true)
        .formatter(new SimpleFormatter(context -> {
            TextRendererDrawerAccess drawer = context.drawer;
            float shadow = drawer.hasShadow() ? context.glyph.getShadowOffset() : 0;
            float x = drawer.x() + shadow;
            float y = drawer.y() + shadow - 1;

            drawer.invokeAddRectangle(new GlyphRenderer.Rectangle(x, y, x + context.advance, y - 1, 0.01F, context.red, context.green, context.blue, context.alpha));
        }));
    public static final ExtendedFormatting color = FormattingRegistry.register("color", (char) 230, false, 16, 0xAA00AA).colorFunction(color -> {
        Random random = new Random();

        return color + random.nextInt(1 << 24) + random.nextInt(1 << 16) + random.nextInt(1 << 8);
    });

    @Override
    public void onInitialize() {
    }
}
