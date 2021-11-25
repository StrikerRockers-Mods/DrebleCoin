package io.github.strikerrocker.dreblecoins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dreblecoins")
public class Dreblecoins {
    private static final Logger LOGGER = LogManager.getLogger();
    public static String MODID = "dreblecoins";
    public static Item COIN = new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(new ResourceLocation(MODID, "coin"));

    public Dreblecoins() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,Config.COMMON_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {

        LivingEntity living = event.getEntityLiving();
        int count, healthMax, countMax, countMin;
        if(event.getSource().getEntity() instanceof PlayerEntity){
            if (living.getMaxHealth() < 50) {
                countMin = Config.COMMON.below50MinCount.get();
                healthMax = 50;
                countMax = Config.COMMON.below50MaxCount.get();
            } else if (living.getMaxHealth() < 200) {
                healthMax = 200;
                countMin = Config.COMMON.below200MinCount.get();
                countMax = Config.COMMON.below200MaxCount.get();
            } else {
                healthMax = (int) living.getMaxHealth();
                countMin = Config.COMMON.above200MinCount.get();
                countMax = Config.COMMON.above200MaxCount.get();
            }
            count = (int) (((living.getMaxHealth() * (countMax - countMin)) / (healthMax)) + countMin);
            living.spawnAtLocation(new ItemStack(COIN, count));
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().register(COIN);
            LOGGER.info("Registering coin item");
        }
    }
}
