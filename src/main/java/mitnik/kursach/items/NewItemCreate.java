package mitnik.kursach.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Random;

public class NewItemCreate extends Item {
    public NewItemCreate() {super(new Properties());}

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide ){
            for (int i = 0; i < player.getInventory().items.size(); i++){
                if (player.getInventory().items.get(i).isEmpty()) player.getInventory().items.set(i, new ItemStack(Items.DIRT, 3));
                if (player.getInventory().items.get(i).is(Items.DIRT)) player.getInventory().items.set(i, new Random().nextBoolean()
                        ? new ItemStack(Items.APPLE) : new ItemStack(Items.BEEF));
            }
        }
        return super.use(world, player, hand);
    }
}
