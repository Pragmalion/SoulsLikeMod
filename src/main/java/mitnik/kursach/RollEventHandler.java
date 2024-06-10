package mitnik.kursach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "kursachmod", value = Dist.CLIENT)
public class RollEventHandler {

    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean isRolling = false;
    private static int rollTicks = 0;
    private static Vec3 rollDirection;
    private static long lastRollTime = 0;
    private static final long ROLL_COOLDOWN = 5000; // 5 seconds cooldown

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (!isRolling && System.currentTimeMillis() - lastRollTime >= ROLL_COOLDOWN) {
            LocalPlayer player = mc.player;
            if (player != null && canRoll(player)) {
                if (mc.options.keyUp.isDown() && event.getKey() == KeyBindings.ROLL_KEY.getKey().getValue()) {
                    startRoll("keyUp");
                } else if (mc.options.keyDown.isDown() && event.getKey() == KeyBindings.ROLL_KEY.getKey().getValue()) {
                    startRoll("keyDown");
                } else if (mc.options.keyLeft.isDown() && event.getKey() == KeyBindings.ROLL_KEY.getKey().getValue()) {
                    startRoll("keyLeft");
                } else if (mc.options.keyRight.isDown() && event.getKey() == KeyBindings.ROLL_KEY.getKey().getValue()) {
                    startRoll("keyRight");
                }
            }
        }
    }

    public static boolean isRollOnCooldown() {
        return System.currentTimeMillis() - lastRollTime < ROLL_COOLDOWN;
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (isRolling && event.player == mc.player) {
            if (rollTicks < 2) { // Adjust duration of the roll here
                mc.player.setDeltaMovement(rollDirection);
                rollTicks++;
            } else {
                isRolling = false;
                rollTicks = 0;
            }
        }
    }

    private static void startRoll(String direction) {
        LocalPlayer player = mc.player;

        if (player == null || (player.getFoodData().getFoodLevel() <= 0 && !player.isCreative())) {
            return;
        }

        // Reduce the player's hunger level by 1 if not in creative mode
        if (!player.isCreative()) {
            player.getFoodData().eat(-2, 0);
        }

        switch (direction) {
            case "keyUp":
                rollDirection = player.getLookAngle().scale(1);
                break;
            case "keyDown":
                rollDirection = player.getLookAngle().scale(-1);
                break;
            case "keyLeft":
                rollDirection = new Vec3(player.getLookAngle().z, 0, -player.getLookAngle().x).scale(1);
                break;
            case "keyRight":
                rollDirection = new Vec3(-player.getLookAngle().z, 0, player.getLookAngle().x).scale(1);
                break;
        }

        isRolling = true;
        lastRollTime = System.currentTimeMillis();

        // Add a large number of smoke particles
        Vec3 pos = player.position();
        for (int i = 0; i < 1500; i++) { // Increase the number to generate more particles
            double offsetX = (Math.random() - 0.5) * 2.0;
            double offsetY = Math.random() * 2.0;
            double offsetZ = (Math.random() - 0.5) * 2.0;
            player.level.addParticle(ParticleTypes.SMOKE, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, 0, 0, 0);
        }

        Vec3 pos2 = player.position();
        for (int i = 0; i < 100; i++) { // Increase the number to generate more particles
            double offsetX = (Math.random() - 0.5) * 2.0;
            double offsetY = Math.random() * 2.0;
            double offsetZ = (Math.random() - 0.5) * 2.0;
            player.level.addParticle(ParticleTypes.FLASH, pos2.x + offsetX, pos2.y + offsetY, pos2.z + offsetZ, 0, 0, 0);
        }
    }

    private static boolean canRoll(LocalPlayer player) {
        return player.isCreative() || player.getFoodData().getFoodLevel() > 0;
    }
}
