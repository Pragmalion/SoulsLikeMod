package mitnik.kursach.items;

import mitnik.kursach.KursachMod;
import mitnik.kursach.blocks.ModBlocks;
import mitnik.kursach.items.weapons.AnimalSoundAxe;
import mitnik.kursach.items.weapons.CustomSwordItem;
import mitnik.kursach.items.weapons.ScytheItem;
import mitnik.kursach.items.weapons.StaffItem;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//Создание итемов
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KursachMod.MODID);
                                             //Название итема             Отображаемое имя без .json
    public  static final RegistryObject<Item> BigSwordItem = ITEMS.register("bigsword",() -> new CustomSwordItem(new Item.Properties().stacksTo(1)));
    public  static final RegistryObject<Item> StaffItem = ITEMS.register("staff", () -> new StaffItem(new Item.Properties().stacksTo(1)));
    public  static final RegistryObject<Item> KosaItem = ITEMS.register("kosa", () -> new ScytheItem(new Item.Properties().stacksTo(1)));
    public  static final RegistryObject<Item> ToporItem = ITEMS.register("topor", () -> new AnimalSoundAxe(new Item.Properties().stacksTo(1)));

    public  static final RegistryObject<Item> TabIco = ITEMS.register("tab_ico", () -> new Item(new Item.Properties()));

    //public static final RegistryObject<Item> BLOCK_ONE = ITEMS.register("block_one", () -> new BlockItem(ModBlocks.BLOCK_ONE.get(), new Item.Properties().stacksTo(1)));
}
