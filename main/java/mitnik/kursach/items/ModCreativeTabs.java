package mitnik.kursach.items;


import mitnik.kursach.KursachMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KursachMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {

    public static CreativeModeTab tab;

    @SubscribeEvent
    public static void regTab(CreativeModeTabEvent.Register e){
        tab = e.registerCreativeModeTab(new ResourceLocation(KursachMod.MODID, "kursach_tab"),
                builder -> builder.icon( () -> new ItemStack(ModItems.TabIco.get() ))
                        .title(Component.translatable("creativemodtab.tab")));
    }
}
