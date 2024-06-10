package mitnik.kursach.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.Random;

public class AnimalSoundAxe extends AxeItem {

    public AnimalSoundAxe(Properties properties) {
        super(Tiers.DIAMOND, 2, -2.4F, new Item.Properties());
    }

    public void playRandomAnimalSound(Level level, LivingEntity entity) {
        SoundEvent[] animalSounds = {
                SoundEvents.COW_AMBIENT,
                SoundEvents.SHEEP_AMBIENT,
                SoundEvents.CHICKEN_AMBIENT,
                SoundEvents.PIG_AMBIENT,
                SoundEvents.WOLF_AMBIENT
        };

        Random random = new Random();
        SoundEvent soundEvent = animalSounds[random.nextInt(animalSounds.length)];
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, SoundSource.PLAYERS, 1.0F, 2.0F); // 2.0F для максимальной громкости
    }
}
