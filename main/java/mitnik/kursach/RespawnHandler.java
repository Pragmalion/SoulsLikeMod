package mitnik.kursach;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = KursachMod.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class RespawnHandler {

    private static BlockPos respawnPos = null;
    private static BlockPos lastCheckedPos = null;
    private static boolean lastCheckedLitState = false;

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getPlacedBlock();

        if (!world.isClientSide) {
            Block block = state.getBlock();
            if (block instanceof CampfireBlock) {
                world.setBlock(pos, state.setValue(CampfireBlock.LIT, Boolean.FALSE), 3);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        Level world = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        if (!world.isClientSide) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof CampfireBlock) {
                boolean isLit = state.getValue(CampfireBlock.LIT);
                if (!isLit) {
                    world.setBlock(pos, state.setValue(CampfireBlock.LIT, Boolean.TRUE), 3);
                } else {
                    if (player instanceof ServerPlayer) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        if (respawnPos == null || !respawnPos.equals(pos)) {
                            respawnPos = pos;
                            serverPlayer.setRespawnPosition(serverPlayer.getLevel().dimension(), pos, 0.0F, true, false);
                            serverPlayer.sendSystemMessage(Component.literal("ОООО, ты открыл костер, грац! Точка возрождения установлена!"));
                        }
                    }
                }
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getPlayer();

        if (!world.isClientSide) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof CampfireBlock) {
                if (pos.equals(respawnPos)) {
                    respawnPos = null;
                    if (player instanceof ServerPlayer) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        serverPlayer.sendSystemMessage(Component.literal("Ты сломал костер, молодец! Точки сохранения больше нет"));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        Level world = (Level) event.getLevel();
        BlockPos pos = event.getPos();

        if (!world.isClientSide) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (block instanceof CampfireBlock) {
                boolean isLit = state.getValue(CampfireBlock.LIT);
                if (!isLit && pos.equals(respawnPos)) {
                    respawnPos = null;
                    for (Player player : world.players()) {
                        if (player instanceof ServerPlayer) {
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.sendSystemMessage(Component.literal("Ты сломал костер, молодец! Точки сохранения больше нет"));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        Level world = player.getLevel();

        if (!world.isClientSide && player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (respawnPos != null) {
                BlockState state = world.getBlockState(respawnPos);
                Block block = state.getBlock();
                if (block instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
                    serverPlayer.teleportTo((ServerLevel) world, respawnPos.getX() + 0.5, respawnPos.getY(), respawnPos.getZ() + 0.5, player.getYRot(), player.getXRot());
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("Точка возрождения не задана! Удачи :)"));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM);
    }

    @SubscribeEvent
    public static void onPlayerSetSpawn(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            BlockPos bedPos = serverPlayer.getRespawnPosition();
            if (bedPos != null) {
                BlockState state = serverPlayer.getLevel().getBlockState(bedPos);
                if (state.getBlock() == Blocks.WHITE_BED) {
                    serverPlayer.setRespawnPosition(null, bedPos, 0.0F, false, false);
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = KursachMod.MODID, value = Dist.CLIENT)
    public static class ChatHandler {
        private static final String RESPAWN_MESSAGE = "You have no home bed or charged respawn anchor, or it was obstructed";
        private static final String RESPAWN_MESSAGE2 = "У вас нет кровати или заряженного якоря возрождения, либо доступ к ним затруднён";

        @SubscribeEvent
        public static void onClientChatReceived(ClientChatReceivedEvent event) {
            Component message = event.getMessage();
            if (message.getString().contains(RESPAWN_MESSAGE) || message.getString().contains(RESPAWN_MESSAGE2)) {
                event.setCanceled(true);
            }
        }
    }
}
