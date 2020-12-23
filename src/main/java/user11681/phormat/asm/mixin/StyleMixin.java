package user11681.phormat.asm.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import user11681.phormat.api.ExtendedFormatting;
import user11681.phormat.asm.access.ExtendedStyle;

@SuppressWarnings("ConstantConditions")
@Mixin(Style.class)
abstract class StyleMixin implements ExtendedStyle {
    @Unique
    private final ReferenceOpenHashSet<Formatting> formattings = new ReferenceOpenHashSet<>();

    @Unique
    private static Formatting switchFormatting;

    @Unique
    private static Boolean previousObfuscated;

    @Override
    @Unique
    public Set<Formatting> getFormattings() {
        return this.formattings;
    }

    @Override
    @Unique
    public boolean hasFormatting(Formatting formatting) {
        return this.formattings.contains(formatting);
    }

    @Override
    @Unique
    public void transferFormatting(Style to) {
        this.transferFormatting(((StyleMixin) (Object) to));
    }

    @Unique
    private void transferFormatting(StyleMixin style) {
        style.addFormattings(this.formattings);
    }

    @Override
    @Unique
    public void addFormatting(Formatting formatting) {
        this.formattings.add(formatting);
    }

    @Override
    @Unique
    public void addFormattings(Formatting... formattings) {
        for (Formatting formatting : formattings) {
            this.addFormatting(formatting);
        }
    }

    @Override
    @Unique
    public void addFormattings(Iterable<Formatting> formattings) {
        for (Formatting formatting : formattings) {
            this.addFormatting(formatting);
        }
    }

    @Override
    @Unique
    public Style cast() {
        return (Style) (Object) this;
    }

    @Inject(method = "withColor(Lnet/minecraft/text/TextColor;)Lnet/minecraft/text/Style;",
            at = @At("RETURN"))
    public void withColor(TextColor color, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Inject(method = "withBold",
            at = @At("RETURN"))
    public void withBold(Boolean bold, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Inject(method = "withItalic",
            at = @At("RETURN"))
    public void withItalic(Boolean italic, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "withUnderline",
            at = @At("RETURN"))
    public void withUnderline(Boolean underline, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Inject(method = "withClickEvent",
            at = @At("RETURN"))
    public void withClickEvent(ClickEvent clickEvent, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Inject(method = "withHoverEvent",
            at = @At("RETURN"))
    public void withHoverEvent(HoverEvent hoverEvent, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Inject(method = "withInsertion",
            at = @At("RETURN"))
    public void withInsertion(String insertion, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "withFont",
            at = @At("RETURN"))
    public void withFont(Identifier font, CallbackInfoReturnable<Style> info) {
        this.transferFormatting(info.getReturnValue());
    }

    @Inject(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting"},
            at = @At(value = "TAIL"))
    public void addFormatting(Formatting formatting, CallbackInfoReturnable<Style> info) {
        StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        this.transferFormatting(style);

        if (formatting instanceof ExtendedFormatting) {
            style.addFormatting(formatting);
        }
    }

    @SuppressWarnings({"unused", "RedundantSuppression"}) // invoked with ASM
    private static int phormat_hackOrdinal(Formatting formatting) {
        return (switchFormatting = formatting) instanceof ExtendedFormatting && !formatting.isColor() ? Formatting.OBFUSCATED.ordinal() : formatting.ordinal();
    }

    @ModifyVariable(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting", "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;"},
                    at = @At(value = "CONSTANT",
                             args = "intValue=1",
                             ordinal = 0),
                    ordinal = 4)
    public Boolean saveObfuscated(Boolean previous) {
        return previousObfuscated = previous;
    }

    @ModifyVariable(method = {"withFormatting(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;", "withExclusiveFormatting", "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;"},
                    at = @At(value = "STORE",
                             ordinal = 1),
                    ordinal = 4)
    public Boolean fixObfuscated(Boolean previous) {
        return switchFormatting == Formatting.OBFUSCATED || previousObfuscated == Boolean.TRUE;
    }

    @Inject(method = "withFormatting([Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/Style;",
            at = @At(value = "TAIL"))
    public void addFormatting(Formatting[] formattings, CallbackInfoReturnable<Style> info) {
        StyleMixin style = (StyleMixin) (Object) info.getReturnValue();

        for (Formatting formatting : formattings) {
            if (formatting instanceof ExtendedFormatting) {
                style.addFormatting(formatting);
            }
        }
    }

    @Inject(method = "withParent", at = @At("TAIL"))
    public void withParent(Style parent, CallbackInfoReturnable<Style> info) {
        if (parent != Style.EMPTY) {
            StyleMixin child = (StyleMixin) (Object) info.getReturnValue();

            for (Formatting formatting : ((StyleMixin) (Object) parent).formattings) {
                if (!child.hasFormatting(formatting)) {
                    child.addFormatting(formatting);
                }
            }
        }
    }

    @Inject(method = "toString",
            at = @At("RETURN"),
            cancellable = true)
    public void appendFormatting(CallbackInfoReturnable<String> info) {
        info.setReturnValue(info.getReturnValue().replace("}", ", phormat:formatting=" + Arrays.toString(this.formattings.toArray(new Formatting[0])) + '}'));
    }

    @Inject(method = "equals",
            at = @At(value = "RETURN",
                     ordinal = 1),
            cancellable = true)
    public void compareCustomFormatting(Object obj, CallbackInfoReturnable<Boolean> info) {
        ReferenceOpenHashSet<Formatting> otherFormattings = ((StyleMixin) obj).formattings;
        ReferenceOpenHashSet<Formatting> formattings = this.formattings;

        if (otherFormattings.size() != formattings.size()) {
            info.setReturnValue(false);
        } else for (Formatting formatting : otherFormattings) {
            if (!formattings.contains(formatting)) {
                info.setReturnValue(false);

                return;
            }
        }
    }

    @ModifyArg(method = "hashCode",
               at = @At(value = "INVOKE",
                        target = "Ljava/util/Objects;hash([Ljava/lang/Object;)I"))
    public Object[] hashCustomFormatting(Object[] previous) {
        Object[] formattings = this.formattings.toArray();
        int previousLength = previous.length;
        int formattingCount = formattings.length;
        Object[] all = new Object[previousLength + formattingCount];

        System.arraycopy(previous, 0, all, 0, previousLength);
        System.arraycopy(formattings, 0, all, previousLength, formattingCount);

        return all;
    }

    @Mixin(Style.Serializer.class)
    abstract static class SerializerMixin {
        @Inject(method = "deserialize",
                at = @At(value = "NEW",
                         target = "net/minecraft/text/Style"))
        public void deserializeCustomFormatting(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<Style> info) {
            ExtendedStyle style = (ExtendedStyle) info.getReturnValue();
            JsonObject object = jsonElement.getAsJsonObject();

            if (object.has("phormat")) {
                for (JsonElement phormat : object.getAsJsonArray("phormat")) {
                    style.addFormatting(Formatting.byName(phormat.getAsString()));
                }
            }
        }

        @Inject(method = "serialize",
                at = @At("RETURN"),
                cancellable = true)
        public void serializeCustomFormatting(Style style, Type type, JsonSerializationContext jsonSerializationContext, CallbackInfoReturnable<JsonElement> info) {
            JsonObject object = (JsonObject) info.getReturnValue();

            if (object == null) {
                info.setReturnValue(object = new JsonObject());
            }

            JsonArray formatting = new JsonArray();

            for (Formatting phormat : ((ExtendedStyle) style).getFormattings()) {
                if (phormat instanceof ExtendedFormatting) {
                    formatting.add(phormat.getName());
                }
            }

            object.add("phormat", formatting);
        }
    }
}
