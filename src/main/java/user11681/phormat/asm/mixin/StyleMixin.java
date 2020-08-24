package user11681.phormat.asm.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.lang.reflect.Type;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.phormat.asm.access.ExtendedStyle;
import user11681.phormat.asm.access.PhormatAccess;

@Mixin(Style.class)
public abstract class StyleMixin implements ExtendedStyle {
    @Unique
    private final ReferenceArrayList<PhormatAccess> customFormattings = new ReferenceArrayList<>(10);

    @Override
    @Unique
    public ReferenceArrayList<PhormatAccess> getCustomFormattings() {
        return this.customFormattings;
    }

    @Override
    @Unique
    public boolean hasCustomFormatting(final Formatting formatting) {
        return this.customFormattings.contains(formatting);
    }

    @Override
    @Unique
    public void addCustomFormattings(final Formatting... formattings) {
        for (final Formatting formatting : formattings) {
            this.addCustomFormatting((PhormatAccess) (Object) formatting);
        }
    }

    @Override
    @Unique
    public void addCustomFormattings(final PhormatAccess... formattings) {
        for (final PhormatAccess formatting : formattings) {
            this.addCustomFormatting(formatting);
        }
    }

    @Override
    @Unique
    public void addCustomFormatting(final Formatting formatting) {
        this.addCustomFormatting((PhormatAccess) (Object) formatting);
    }

    @Override
    @Unique
    public void addCustomFormatting(final PhormatAccess format) {
        this.customFormattings.add(format);
    }

    @Override
    @Unique
    public Style cast() {
        return (Style) (Object) this;
    }

    @Inject(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting"},
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting formatting, final CallbackInfoReturnable<Style> info) {
        final StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        style.customFormattings.addAll(customFormattings);

        if (PhormatAccess.class.cast(formatting).isCustom()) {
            style.addCustomFormatting(formatting);
        }
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "RETURN"))
    public void addCustomFormatting(final Formatting[] formattings, final CallbackInfoReturnable<Style> info) {
        final StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        for (final PhormatAccess format : PhormatAccess[].class.cast(formattings)) {
            if (format.isCustom()) {
                style.addCustomFormatting(format);
            }
        }
    }

    @Mixin(Style.Serializer.class)
    public static class SerializerMixin {
        @Inject(method = "deserialize",
                at = @At(value = "NEW",
                         target = "net/minecraft/text/Style"))
        public void deserializeCustomFormatting(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext, final CallbackInfoReturnable<Style> info) {
            final ExtendedStyle style = (ExtendedStyle) info.getReturnValue();
            final JsonObject object = jsonElement.getAsJsonObject();

            if (object.has("phormat")) {
                final JsonArray phormattings = object.getAsJsonArray("phormat");

                for (final JsonElement phormat : phormattings) {
                    style.addCustomFormatting(Formatting.byName(phormat.getAsString()));
                }
            }
        }

        @Inject(method = "serialize",
                at = @At("RETURN"),
                cancellable = true)
        public void serializeCustomFormatting(final Style style, final Type type, final JsonSerializationContext jsonSerializationContext, final CallbackInfoReturnable<JsonElement> info) {
            JsonObject object = (JsonObject) info.getReturnValue();

            if (object == null) {
                object = new JsonObject();

                info.setReturnValue(object);
            } else {
                final JsonArray phormattings = new JsonArray();

                for (final PhormatAccess phormat : ((ExtendedStyle) style).getCustomFormattings()) {
                    if (phormat.isCustom()) {
                        phormattings.add(phormat.cast().getName());
                    }
                }

                object.add("phormat", phormattings);
            }
        }
    }
}
