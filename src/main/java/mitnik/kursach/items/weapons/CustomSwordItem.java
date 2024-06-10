package mitnik.kursach.items.weapons;


import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.awt.*;

public class CustomSwordItem extends SwordItem {
    private static final int COOLDOWN_TICKS = 72000; // 3 игровых дня 72000

    public CustomSwordItem(Properties properties) {
        super(Tiers.DIAMOND, 6, -2.4F, new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.hurt(target.damageSources().generic(), 10.0F); // Наносит 10 урона при обычной атаке
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (!player.getCooldowns().isOnCooldown(this)) {
                // Найти ближайшего противника в радиусе 5 блоков
                Vec3 lookVector = player.getLookAngle();
                Vec3 eyePosition = player.getEyePosition(1.0F);
                Vec3 reachVector = eyePosition.add(lookVector.scale(5.0D));
                AABB aabb = new AABB(eyePosition, reachVector);
                LivingEntity target = null;
                double minDistance = Double.MAX_VALUE;
                for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, aabb)) {
                    if (entity != player && player.hasLineOfSight(entity)) {
                        double distance = player.distanceToSqr(entity);
                        if (distance < minDistance) {
                            minDistance = distance;
                            target = entity;
                        }
                    }
                }

                if (target != null) {
                    target.hurt(player.damageSources().generic(), 1000.0F); // Наносит 10000 урона
                    player.getCooldowns().addCooldown(this, COOLDOWN_TICKS); // Устанавливает откат в 72000 тиков
                }
            }
        }

        return InteractionResultHolder.success(itemstack);
    }
}


