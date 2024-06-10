//package mitnik.kursach;
//
//import mitnik.kursach.blocks.ModBlocks;
////import mitnik.kursach.items.CustomDamageTypes;
////import mitnik.kursach.items.weapons.ModEventSubscriber;
//import mitnik.kursach.items.ModCreativeTabs;
//import mitnik.kursach.items.ModItems;
//import mitnik.kursach.items.weapons.AnimalSoundAxe;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.CreativeModeTabEvent;
//import net.minecraftforge.event.server.ServerStartingEvent;
//import net.minecraftforge.event.level.BlockEvent;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraftforge.event.entity.EntityJoinLevelEvent;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import java.util.Random;
//
//
//@Mod(KursachMod.MODID)
//public class KursachMod {
//
//    public static final String MODID = "kursachmod";
//
//    public KursachMod() {
//        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        bus.addListener(this::commonSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
//
//        ModItems.ITEMS.register(bus);
//        ModBlocks.BLOCKS.register(bus);
//
//        MinecraftForge.EVENT_BUS.register(this);
//
//        bus.addListener(this::addCreative);
//
//        MinecraftForge.EVENT_BUS.register(RespawnHandler.class);
//        MinecraftForge.EVENT_BUS.register(RollEventHandler.class);
//    }
//
//    private void commonSetup(final FMLCommonSetupEvent event) {}
//
//    private void addCreative(CreativeModeTabEvent.BuildContents event) {
//        if (event.getTab() == ModCreativeTabs.tab) {
//            event.accept(ModItems.BigSwordItem);
//            event.accept(ModItems.StaffItem);
//            event.accept(ModItems.KosaItem);
//            event.accept(ModItems.ToporItem);
//        }
//    }
//
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {}
//
//    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents {
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event) {}
//    }
//
//    private void doClientStuff(final FMLClientSetupEvent event) {}
//
//    @SubscribeEvent
//    public void onBlockBreak(BlockEvent.BreakEvent event) {
//        Player player = event.getPlayer();
//        if (player != null) {
//            ItemStack heldItem = player.getMainHandItem();
//            if (heldItem.getItem() instanceof AnimalSoundAxe) {
//                ((AnimalSoundAxe) heldItem.getItem()).playRandomAnimalSound(player.level, player);
//            }
//        }
//    }
//
//    //Увеличивает здоровье мобов при их появлении в мире
//    @SubscribeEvent
//    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
//        if (!(event.getEntity() instanceof LivingEntity)) {
//            return;
//        }
//
//        LivingEntity entity = (LivingEntity) event.getEntity();
//        Random random = new Random();
//        float multiplier = 1.0f + random.nextInt(3); // Генерируем множитель от 1 до 3
//
//        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() * multiplier);
//        entity.setHealth(entity.getMaxHealth());
//    }
//
//    //Увеличивает урон, наносимый мобами
//    @SubscribeEvent
//    public void onLivingHurt(LivingHurtEvent event) {
//        if (!(event.getSource().getEntity() instanceof LivingEntity)) {
//            return;
//        }
//
//        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
//        Random random = new Random();
//        float multiplier = 1.0f + random.nextInt(3); // Генерируем множитель от 1 до 3
//
//        event.setAmount(event.getAmount() * multiplier);
//    }
//}

package mitnik.kursach;

import mitnik.kursach.blocks.ModBlocks;
import mitnik.kursach.items.ModCreativeTabs;
import mitnik.kursach.items.ModItems;
import mitnik.kursach.items.weapons.AnimalSoundAxe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

@Mod(KursachMod.MODID)
public class KursachMod {

    public static final String MODID = "kursachmod";

    public KursachMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(RespawnHandler.class);
        MinecraftForge.EVENT_BUS.register(RollEventHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeTabs.tab) {
            event.accept(ModItems.BigSwordItem);
            event.accept(ModItems.StaffItem);
            event.accept(ModItems.KosaItem);
            event.accept(ModItems.ToporItem);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }

    private void doClientStuff(final FMLClientSetupEvent event) {}

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() instanceof AnimalSoundAxe) {
                ((AnimalSoundAxe) heldItem.getItem()).playRandomAnimalSound(player.level, player);
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof Player) {
            return;
        }

        LivingEntity entity = (LivingEntity) event.getEntity();
        Random random = new Random();
        float multiplier = 1.0f + random.nextInt(3); // Генерируем множитель от 1 до 3

        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() * multiplier);
        entity.setHealth(entity.getMaxHealth());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity) || event.getSource().getEntity() instanceof Player) {
            return;
        }

        LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
        Random random = new Random();
        float multiplier = 1.0f + random.nextInt(3); // Генерируем множитель от 1 до 3

        event.setAmount(event.getAmount() * multiplier);
    }
}






