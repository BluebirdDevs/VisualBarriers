package bluebird.visual_barriers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualBarriers implements ClientModInitializer {
    public static final String MOD_ID = "visualbarriers";
    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of(MOD_ID, MOD_ID));
    private static KeyBinding keybindBarrierVisibility;

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static boolean isEnabled = false;
    @Override
    public void onInitializeClient() {
        keybindBarrierVisibility = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.visualbarriers.toggle",
                GLFW.GLFW_KEY_B,
                CATEGORY
        ));
        BlockRenderLayerMap.putBlock(Blocks.BARRIER, BlockRenderLayer.TRANSLUCENT);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keybindBarrierVisibility.wasPressed()) {
                isEnabled = !isEnabled;
                LOGGER.info("Toggled " +  (isEnabled ? "On" : "Off"));
                if (MinecraftClient.getInstance().worldRenderer != null) MinecraftClient.getInstance().worldRenderer.reload();
            }
        });
    }

    public static boolean barriers_enabled() {
        return isEnabled;
    }
}
