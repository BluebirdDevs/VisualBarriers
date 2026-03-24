package bluebird.visual_barriers.mixins;

import bluebird.visual_barriers.VisualBarriers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(method = "getMarkerParticleTarget", at = @At("RETURN"), cancellable = true)
    public void removeBarrierParticles(CallbackInfoReturnable<Block> cir) {
        if (cir.getReturnValue() instanceof BarrierBlock && VisualBarriers.barriers_enabled()) {
            cir.setReturnValue(null);
        }
    }
}
