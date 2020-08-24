package user11681.phormat.asm.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.lang.reflect.Type;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
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

    @Inject(method = "withColor(Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/Style;",
            at = @At("RETURN"))
    public void withColor(final @Nullable TextColor color, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Inject(method = "withBold",
            at = @At("RETURN"))
    public void withBold(final @Nullable Boolean bold, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Inject(method = "withItalic",
            at = @At("RETURN"))
    public void withItalic(final @Nullable Boolean italic, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "method_30938",
            at = @At("RETURN"))
    public void method_30938(final @Nullable Boolean boolean_, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Inject(method = "withClickEvent",
            at = @At("RETURN"))
    public void withClickEvent(final @Nullable ClickEvent clickEvent, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Inject(method = "withHoverEvent",
            at = @At("RETURN"))
    public void withHoverEvent(final @Nullable HoverEvent hoverEvent, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Inject(method = "withInsertion",
            at = @At("RETURN"))
    public void withInsertion(final @Nullable String insertion, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "withFont",
            at = @At("RETURN"))
    public void withFont(final @Nullable Identifier font, final CallbackInfoReturnable<Style> info) {
        transferPhormats(info.getReturnValue());
    }

    private static void transferPhormats(final Style style) {
        ((StyleMixin) (Object) style).addCustomFormattings(((StyleMixin) (Object) style).customFormattings.toArray(new PhormatAccess[0]));
    }

    @Inject(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting"},
            at = @At(value = "TAIL"))
    public void addCustomFormatting(final Formatting formatting, final CallbackInfoReturnable<Style> info) {
        final StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        style.customFormattings.addAll(customFormattings);

        if (PhormatAccess.class.cast(formatting).isCustom()) {
            style.addCustomFormatting(formatting);
        }
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "TAIL"))
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
