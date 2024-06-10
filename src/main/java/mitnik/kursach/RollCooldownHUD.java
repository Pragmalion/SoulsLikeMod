package mitnik.kursach;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "kursachmod", value = Dist.CLIENT)
public class RollCooldownHUD {

    private static final ResourceLocation ROLL_READY = new ResourceLocation("kursachmod", "textures/gui/roll_ready.png");
    private static final ResourceLocation ROLL_COOLDOWN = new ResourceLocation("kursachmod", "textures/gui/roll_cooldown.png");

    private final Minecraft minecraft;

    public RollCooldownHUD(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        boolean isRollOnCooldown = RollEventHandler.isRollOnCooldown();
        boolean isHungerEmpty = mc.player.getFoodData().getFoodLevel() <= 0;

        // Указываем текстуру в зависимости от состояния кувырка
        ResourceLocation texture = (isRollOnCooldown || isHungerEmpty) ? ROLL_COOLDOWN : ROLL_READY;
        RenderSystem.setShaderTexture(0, texture);

        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();

        // Настраиваем размер и позицию
        int x = (width / 2) - 6; // По горизонтали
        int y = height - 50; // По вертикали
        int iconWidth = 14; // Ширина иконки
        int iconHeight = 13; // Высота иконки

        PoseStack poseStack = event.getPoseStack();

        RenderSystem.enableBlend();
        Gui.blit(poseStack, x, y, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
        RenderSystem.disableBlend();
    }
}




