package mitnik.kursach.items;

import mitnik.kursach.KursachMod;
import mitnik.kursach.blocks.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//Создание итемов
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KursachMod.MODID);
                                             //Название итема           Отображаемое название
    public  static final RegistryObject<Item> KatanaItem = ITEMS.register("katana", NewItemCreate::new);

    public  static final RegistryObject<Item> TabIco = ITEMS.register("tab_ico", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLOCK_ONE = ITEMS.register("block_one", () -> new BlockItem(ModBlocks.BLOCK_ONE.get(), new Item.Properties()));
}
