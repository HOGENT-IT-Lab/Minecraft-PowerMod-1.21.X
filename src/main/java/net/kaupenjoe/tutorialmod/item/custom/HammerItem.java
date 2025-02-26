package net.kaupenjoe.tutorialmod.item.custom;

import net.kaupenjoe.tutorialmod.item.ModItems;
import net.kaupenjoe.tutorialmod.particle.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HammerItem extends Item {
    public HammerItem(Properties pProperties) {
        super(pProperties);
    }


    // Right hand
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // ArmorUtils.isWearingFullArmor(player)
        if (!world.isClientSide && isWearingFullArmor(player)) {

            // Toggle flight
            if (player.getAbilities().mayfly) {
                spawnParticles((ServerLevel) world, player, ParticleTypes.SMOKE);
                disableFlight(player, world);
                player.sendSystemMessage(Component.literal("Flight Disabled!").withStyle(ChatFormatting.RED));
            } else {
                spawnParticles((ServerLevel) world, player, ParticleTypes.CLOUD);
                enableFlight(player, world);
                player.sendSystemMessage(Component.literal("Flight Enabled!").withStyle(ChatFormatting.GREEN));
            }

            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }

        return InteractionResultHolder.pass(itemStack);
    }

    private void enableFlight(Player player, Level world) {
        

        player.getAbilities().mayfly = true;
        player.getAbilities().flying = true;
        player.onUpdateAbilities();
        
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private void disableFlight(Player player, Level world) {
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
    }

    private boolean isWearingFullArmor(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ALEXANDRITE_HELMET.get()) &&
            player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ALEXANDRITE_CHESTPLATE.get()) &&
            player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ALEXANDRITE_LEGGINGS.get()) &&
            player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ALEXANDRITE_BOOTS.get());
    }


    private void spawnParticles(ServerLevel world, Player player, ParticleOptions particleType) {
        // Number of particles to spawn
        int particleCount = 20;  // Adjust as needed for more or fewer particles
    
        // Radius of the circle
        double radius = 1.0;  // You can increase this value for a larger circle
    
        // Spawn particles in a circular pattern around the player's feet
        for (int i = 0; i < particleCount; i++) {
            // Calculate angle for each particle in the circle (divide 360 by particleCount)
            double angle = (i * 360.0 / particleCount) * (Math.PI / 180.0);  // Convert to radians
    
            // Calculate the x and z position of the particle in a circle around the player
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
    
            // Spawn the particle slightly above the player's feet
            world.sendParticles(particleType,
                player.getX() + xOffset, player.getY(), player.getZ() + zOffset, 
                3, 0, 0, 0, 0);
        }
    }
    
}
