package net.kaupenjoe.tutorialmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends Item {
    public HammerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // ArmorUtils.isWearingFullArmor(player)
        if (!world.isClientSide ) {
            // Allow the player to fly
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
            player.onUpdateAbilities();

            // Apply custom effects (e.g., particles, sound)
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS, 1.0F, 1.0F);

            // Optionally consume durability or item count
            // if (!player.isCreative()) {
            //     itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            // }

            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }

        return InteractionResultHolder.pass(itemStack);
    }
}
