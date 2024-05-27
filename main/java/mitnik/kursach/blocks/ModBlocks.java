package mitnik.kursach.blocks;

import mitnik.kursach.KursachMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KursachMod.MODID);

    public static final RegistryObject<Block> BLOCK_ONE = BLOCKS.register("block_one", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));

}
