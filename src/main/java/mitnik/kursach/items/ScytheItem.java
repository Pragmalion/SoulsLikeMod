package mitnik.kursach.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.particles.ParticleTypes;

import java.util.Random;

public class ScytheItem extends SwordItem {

    private static final long COOLDOWN_PERIOD = 100L; // 10 секунд (200 тиков)
    private static final Random RANDOM = new Random();

    public ScytheItem(Properties properties) {
        super(Tiers.DIAMOND, 4, -2.4F, new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            if (player.getCooldowns().isOnCooldown(this)) {
                player.displayClientMessage(Component.literal("Коса заряжается!"), true);
                return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
            }

            HitResult hitResult = player.pick(20.0D, 0.0F, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                if (world instanceof ServerLevel) {
                    ServerLevel serverLevel = (ServerLevel) world;
                    BlockPos hitPos = blockHitResult.getBlockPos();
                    int radius = 2;
                    int particleCount = 5000; // увеличил количество частиц в 10 раз

                    // Создаем огненные частицы вокруг точки удара с повышенным уроном
                    for (int i = 0; i < particleCount; i++) {
                        double offsetX = RANDOM.nextGaussian() * radius;
                        double offsetZ = RANDOM.nextGaussian() * radius;
                        double x = hitPos.getX() + 0.5 + offsetX;
                        double y = hitPos.getY() + 0.5;
                        double z = hitPos.getZ() + 0.5 + offsetZ;
                        serverLevel.sendParticles(ParticleTypes.FLAME, x, y, z, 0, 0, 0, 0, 1000); // установили длительность частиц
                    }

                    // Применяем эффект взрыва вокруг точки удара
                    Explosion explosion = new Explosion(serverLevel, null, hitPos.getX(), hitPos.getY(), hitPos.getZ(), radius, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY);
                    explosion.explode();
                    serverLevel.playSound(null, hitPos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
                player.getCooldowns().addCooldown(this, (int) COOLDOWN_PERIOD);
            }
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
