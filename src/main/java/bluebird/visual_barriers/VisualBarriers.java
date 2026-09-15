package bluebird.visual_barriers;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualBarriers implements ClientModInitializer {
    public static final String MOD_ID = "visualbarriers";
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, MOD_ID));
    private static KeyMapping keybindBarrierVisibility;

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static boolean isEnabled = false;
    @Override
    public void onInitializeClient() {
        keybindBarrierVisibility = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.visualbarriers.toggle",
                InputConstants.KEY_B,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keybindBarrierVisibility.consumeClick()) {
                isEnabled = !isEnabled;
                if (client.levelRenderer != null) client.levelExtractor.allChanged();
            }
        });
    }

    public static boolean barriers_enabled() {
        return isEnabled;
    }
}
