//package mitnik.kursach;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.contents.TranslatableContents;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.CampfireBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.client.event.ClientChatReceivedEvent;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent;
//import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
//
//@Mod.EventBusSubscriber(modid = KursachMod.MODID, bus = Bus.FORGE)
//public class RespawnHandler {
//
//    private static BlockPos respawnPos = null;
//    private static BlockPos lastCheckedPos = null;
//    private static boolean lastCheckedLitState = false;
//
//    @SubscribeEvent
//    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
//        Level world = event.getLevel();
//        BlockPos pos = event.getPos();
//        Player player = event.getEntity();
//
//        if (!world.isClientSide) { // Убедимся, что код выполняется только на серверной стороне
//            BlockState state = world.getBlockState(pos);
//            Block block = state.getBlock();
//
//            if (block instanceof CampfireBlock) {
//                boolean isLit = state.getValue(CampfireBlock.LIT);
//                if (isLit) {
//                    if (player instanceof ServerPlayer) {
//                        ServerPlayer serverPlayer = (ServerPlayer) player;
//                        if (respawnPos == null || !respawnPos.equals(pos)) {
//                            respawnPos = pos;
//                            serverPlayer.setRespawnPosition(serverPlayer.getLevel().dimension(), pos, 0.0F, true, false);
//                            serverPlayer.sendSystemMessage(Component.literal("ОООО, ты открыл костер, грац! Точка возрождения установлена!"));
//                        }
//                    }
//                } else {
//                    if (player instanceof ServerPlayer) {
//                        ServerPlayer serverPlayer = (ServerPlayer) player;
//                        if (!pos.equals(lastCheckedPos) || (pos.equals(lastCheckedPos) && lastCheckedLitState)) {
//                            serverPlayer.sendSystemMessage(Component.literal("Этот костер потушен, какая тебе точка возрождения, а?"));
//                            lastCheckedPos = pos;
//                            lastCheckedLitState = isLit;
//                        }
//                    }
//                }
//                event.setCanceled(true); // Отмена дальнейшей обработки события
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
//        Player player = event.getEntity();
//        Level world = player.getLevel();
//
//        if (!world.isClientSide && player instanceof ServerPlayer) {
//            ServerPlayer serverPlayer = (ServerPlayer) player;
//            if (respawnPos != null) {
//                BlockState state = world.getBlockState(respawnPos);
//                Block block = state.getBlock();
//                if (block instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)) {
//                    serverPlayer.teleportTo((ServerLevel) world, respawnPos.getX() + 0.5, respawnPos.getY(), respawnPos.getZ() + 0.5, player.getYRot(), player.getXRot());
//                } else {
//                    serverPlayer.sendSystemMessage(Component.literal("Точка возрождения не задана! Удачи :)"));
//                }
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
//        event.setResult(Player.BedSleepingProblem.OTHER_PROBLEM); // Прерываем установку точки возрождения на кровати
//    }
//
//    @SubscribeEvent
//    public static void onPlayerSetSpawn(PlayerEvent.PlayerChangedDimensionEvent event) {
//        Player player = event.getEntity();
//        if (player instanceof ServerPlayer) {
//            ServerPlayer serverPlayer = (ServerPlayer) player;
//            BlockPos bedPos = serverPlayer.getRespawnPosition();
//            if (bedPos != null) {
//                BlockState state = serverPlayer.getLevel().getBlockState(bedPos);
//                if (state.getBlock() == Blocks.WHITE_BED) {
//                    serverPlayer.setRespawnPosition(null, bedPos, 0.0F, false, false);
//                }
//            }
//        }
//    }
//
//    @Mod.EventBusSubscriber(modid = KursachMod.MODID, value = Dist.CLIENT)
//    public static class ChatHandler {
//        @SubscribeEvent
//        public static void onClientChatReceived(ClientChatReceivedEvent event) {
//            Component message = event.getMessage();
//            if (message instanceof TranslatableContents) {
//                TranslatableContents translatable = (TranslatableContents) message;
//                if ("death.noRespawnPoint".equals(translatable.getKey())) {
//                    event.setCanceled(true);
//                }
//            }
//        }
//    }
//}
