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
        return new TranslatableText("§" + Test.color.code() + "§" + Test.test.code() + "test123");
    }
}
