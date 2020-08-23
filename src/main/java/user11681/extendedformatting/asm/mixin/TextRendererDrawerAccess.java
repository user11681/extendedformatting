package user11681.extendedformatting.asm.mixin;

import java.util.List;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(targets = "net.minecraft.client.font.TextRenderer$Drawer")
public interface TextRendererDrawerAccess {
    @Accessor("light")
    int light();

    @Accessor("brightnessMultiplier")
    float brightnessMultiplier();

    @Accessor("red")
    float red();

    @Accessor("green")
    float green();

    @Accessor("blue")
    float blue();

    @Accessor("alpha")
    float alpha();

    @Accessor("x")
    float x();

    @Accessor("y")
    float y();

    @Accessor("shadow")
    boolean hasShadow();

    @Accessor("seeThrough")
    boolean isTranslucent();

    @Accessor("matrix")
    Matrix4f matrix();

    @Accessor("rectangles")
    List<GlyphRenderer.Rectangle> rectangles();

    @Accessor("vertexConsumers")
    VertexConsumerProvider vertexConsumers();

    @Invoker("drawLayer")
    float drawLayer(int underlineColor, float x);

    @Invoker("addRectangle")
    void addRectangle(GlyphRenderer.Rectangle rectangle);
}
