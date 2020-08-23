package user11681.phormat.asm.mixin;

import it.unimi.dsi.fastutil.objects.Object2ReferenceArrayMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import user11681.phormat.Phormatting;
import user11681.phormat.asm.access.PhormatAccess;

@Mixin(Formatting.class)
public abstract class FormattingMixin implements PhormatAccess {
    @Unique
    private final Formatting self = (Formatting) (Object) this;

    @Unique
    private Phormatting format;

    @Unique
    public boolean custom;

    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "<clinit>",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/stream/Stream;collect(Ljava/util/stream/Collector;)Ljava/lang/Object;"))
    private static Object fixDuplicates(final Stream<Formatting> stream, Collector<Formatting, Object, Map<String, Formatting>> collector) {
        final Formatting[] formattings = stream.toArray(Formatting[]::new);
        final Map<String, Formatting> byName = new Object2ReferenceArrayMap<>(formattings.length);

        for (final Formatting formatting : formattings) {
            byName.put(formatting.getName(), formatting);
        }

        return byName;
    }

    @Override
    @Unique
    public boolean isCustom() {
        return this.custom;
    }

    @Override
    @Unique
    public Phormatting getFormat() {
        return this.format;
    }

    @Override
    @Unique
    public void setFormat(final Phormatting format) {
        this.format = format;
        this.custom = true;
    }

    @Override
    @Unique
    public Formatting cast() {
        return self;
    }
}
