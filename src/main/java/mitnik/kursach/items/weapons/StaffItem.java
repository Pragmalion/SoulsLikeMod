package mitnik.kursach.items.weapons;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class StaffItem extends Item {

    private static final long COOLDOWN_PERIOD = 200L; // 10 секунд (200 тиков)
    private static final Random RANDOM = new Random();

    public StaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            if (player.getCooldowns().isOnCooldown(this)) {
                player.displayClientMessage(Component.literal("Посох заряжается!"), true);
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
            }

            HitResult hitResult = player.pick(20.0D, 0.0F, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                if (world instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) world;
                    int lightningCount = 3 + RANDOM.nextInt(7); // Случайное количество молний от 3 до 9

                    for (int i = 0; i < lightningCount; i++) {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, serverLevel);
                        lightningBolt.moveTo(blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ(), 0.0F, 0.0F);
                        serverLevel.addFreshEntity(lightningBolt);
                    }
                }
                player.getCooldowns().addCooldown(this, (int) COOLDOWN_PERIOD);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
