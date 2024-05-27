package mitnik.kursach;

import mitnik.kursach.blocks.ModBlocks;
import mitnik.kursach.items.ModCreativeTabs;
import mitnik.kursach.items.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//В аннотацию Mod присывается название
@Mod(KursachMod.MODID)
public class KursachMod {

    public static final String MODID = "kursachmod";

    // Конструктор
    public KursachMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        bus.addListener(this::commonSetup);

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        bus.addListener(this::addCreative);

        // Регаем класс для возрождения
        MinecraftForge.EVENT_BUS.register(RespawnHandler.class);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
//         if (event.getTab() == CreativeModeTabs.COMBAT)
//            event.accept(ModItems.TestItem);
        if (event.getTab() == ModCreativeTabs.tab)
            event.accept(ModItems.KatanaItem);
            event.accept(ModBlocks.BLOCK_ONE);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }

}
