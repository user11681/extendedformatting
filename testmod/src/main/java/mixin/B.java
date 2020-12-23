package mixin;

import bruh.Test;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({ClientPlayerEntity.class, ServerPlayerEntity.class})
abstract class B {
    @ModifyVariable(method = "sendMessage(Lnet/minecraft/text/Text;Z)V", at = @At("LOAD"), ordinal = 0)
    public Text test(Text text) {
        return new TranslatableText(String.format("§%s123§r§%s456§r§%s789", Test.test.code(), Test.color.code(), Test.hybrid.code()));
    }
}
