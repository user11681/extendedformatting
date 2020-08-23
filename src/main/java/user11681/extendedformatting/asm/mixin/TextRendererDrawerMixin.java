package user11681.extendedformatting.asm.mixin;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.Style;
import net.minecraft.util.math.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import user11681.extendedformatting.Formatter;
import user11681.extendedformatting.asm.access.CustomFormattingAccess;
import user11681.extendedformatting.asm.access.ExtendedStyle;
import user11681.extendedformatting.asm.access.TextRendererDrawerAccess;

@Environment(EnvType.CLIENT)
@SuppressWarnings("public-target")
@Mixin(targets = "net.minecraft.client.font.TextRenderer$Drawer")
abstract class TextRendererDrawerMixin implements TextRendererDrawerAccess {
    @Override
    @Accessor("light")
    public abstract int light();

    @Override
    @Accessor("brightnessMultiplier")
    public abstract float brightnessMultiplier();

    @Override
    @Accessor("red")
    public abstract float red();

    @Override
    @Accessor("green")
    public abstract float green();

    @Override
    @Accessor("blue")
    public abstract float blue();

    @Override
    @Accessor("alpha")
    public abstract float alpha();

    @Override
    @Accessor("x")
    public abstract float x();

    @Override
    @Accessor("y")
    public abstract float y();

    @Override
    @Accessor("shadow")
    public abstract boolean hasShadow();

    @Override
    @Accessor("seeThrough")
    public abstract boolean isTranslucent();

    @Override
    @Accessor("matrix")
    public abstract Matrix4f matrix();

    @Override
    @Accessor("rectangles")
    public abstract List<GlyphRenderer.Rectangle> rectangles();

    @Override
    @Accessor("vertexConsumers")
    public abstract VertexConsumerProvider vertexConsumers();

    @Override
    @Invoker("drawLayer")
    public abstract float invokeDrawLayer(int underlineColor, float x);

    @Override
    @Invoker("addRectangle")
    public abstract void invokeAddRectangle(GlyphRenderer.Rectangle rectangle);

    @Inject(method = "accept", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void formatCustom(final int i,
                             final Style style,
                             final int j,
                             final CallbackInfoReturnable<Boolean> info,
                             final FontStorage storage,
                             final Glyph glyph,
                             final GlyphRenderer glyphRenderer,
                             final boolean isBold,
                             final float red,
                             final float green,
                             final float blue,
                             final float alpha,
                             final float advance) {
        Formatter formatter;

        for (final CustomFormattingAccess formatting : ((ExtendedStyle) style).getCustomFormattings()) {
            if (formatting.isCustom()) {
                formatter = formatting.getFormat().formatter;

                if (formatter != null) {
                    formatter.format(this, style, i, j, storage, glyph, glyphRenderer, isBold, red, green, blue, alpha, advance);
                }
            }
        }
    }
}
