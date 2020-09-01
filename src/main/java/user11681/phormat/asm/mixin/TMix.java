package user11681.phormat.asm.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class TMix {
    @Inject(method = "tick", at = @At("RETURN"))
    private void test(final CallbackInfo ci) {
        ((PlayerEntity) (Object) this).sendMessage(new TranslatableText("§n§"+ (char) 4096 + "§ute§rxt"), true);
    }
}
