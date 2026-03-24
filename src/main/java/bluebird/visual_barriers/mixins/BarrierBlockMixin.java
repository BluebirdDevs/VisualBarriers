package bluebird.visual_barriers.mixins;

import bluebird.visual_barriers.VisualBarriers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BarrierBlock.class)
@Environment(EnvType.CLIENT)
public class BarrierBlockMixin extends AbstractBlockMixin{

    @Inject(at = @At("HEAD"), method = "getRenderShape", cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<RenderShape> cir) {
        cir.setReturnValue(VisualBarriers.barriers_enabled() ? RenderShape.MODEL : RenderShape.INVISIBLE);
    }

    @Override
    public void isSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (VisualBarriers.barriers_enabled()) {
            cir.setReturnValue(stateFrom.isSolidRender() || stateFrom.getBlock() == state.getBlock());
        }
    }

}
