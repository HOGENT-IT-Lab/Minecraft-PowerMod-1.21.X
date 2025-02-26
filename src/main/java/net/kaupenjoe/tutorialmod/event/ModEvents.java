package net.kaupenjoe.tutorialmod.event;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // check if player is wearing full alexandrite armor to enable flight
        if (!player.level().isClientSide) {
            boolean isWearingArmor = player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ALEXANDRITE_HELMET.get()) &&
                                     player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ALEXANDRITE_CHESTPLATE.get()) &&
                                     player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ALEXANDRITE_LEGGINGS.get()) &&
                                     player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ALEXANDRITE_BOOTS.get());

            if (!isWearingArmor && player.getAbilities().mayfly) {
                player.getAbilities().flying = false;
                player.getAbilities().mayfly = false;
                player.onUpdateAbilities();
                player.sendSystemMessage(Component.literal("Flight disabled! Armor set incomplete.").withStyle(ChatFormatting.YELLOW));
            }
        }
    }

    
}
