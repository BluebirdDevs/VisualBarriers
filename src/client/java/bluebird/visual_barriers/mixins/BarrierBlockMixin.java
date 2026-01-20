package bluebird.visual_barriers.mixins;

import bluebird.visual_barriers.VisualBarriers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BarrierBlock.class)
@Environment(EnvType.CLIENT)
public class BarrierBlockMixin extends AbstractBlockMixin{

    @Inject(at = @At("HEAD"), method = "getRenderType", cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        cir.setReturnValue(VisualBarriers.barriers_enabled()?BlockRenderType.MODEL:BlockRenderType.INVISIBLE);
    }

    @Override
    public void isSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (VisualBarriers.barriers_enabled()) {
            cir.setReturnValue(stateFrom.isOpaqueFullCube() || stateFrom.getBlock() == state.getBlock());
        }
    }

}
