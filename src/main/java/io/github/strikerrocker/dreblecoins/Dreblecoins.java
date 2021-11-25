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
import net.minecraftforge.fml.common.Mod;
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {

        LivingEntity living = event.getEntityLiving();
        int count, healthMax, countMax, countMin;
        if(event.getSource().getEntity() instanceof PlayerEntity){
            if (living.getMaxHealth() < 50) {
                countMin = 1;
                healthMax = 50;
                countMax = 15;
            } else if (living.getMaxHealth() < 200) {
                healthMax = 200;
                countMin = 10;
                countMax = 30;
            } else {
                healthMax = (int) living.getMaxHealth();
                countMin = 50;
                countMax = 64;
            }
            count = (int) (((living.getMaxHealth() * (countMax - countMin)) / (healthMax)) + countMin);
            LOGGER.info("Max health : " + living.getMaxHealth());
            LOGGER.info("Count : " + count);
            living.spawnAtLocation(new ItemStack(COIN, count));
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
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
