package com.superhero.mod.items;

import com.superhero.mod.util.ArmorHelper;
import com.superhero.mod.util.ChargeHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class IronManRepulsorItem extends Item {

    public IronManRepulsorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient()) {
            if (!ArmorHelper.isWearingFullIronMan(player)) {
                player.sendMessage(Text.literal("§cRequires full Iron Man Armor!"), true);
                return TypedActionResult.fail(stack);
            }

            ItemStack chest = ArmorHelper.getIronManChestplate(player);
            if (!ChargeHelper.hasEnough(chest)) {
                player.sendMessage(Text.literal("§c⚡ Charge too low!"), true);
                return TypedActionResult.fail(stack);
            }

            ChargeHelper.drain(chest, 0.5f);

            Vec3d start = player.getEyePos();
            Vec3d dir = player.getRotationVec(1.0f);
            Vec3d end = start.add(dir.multiply(40));

            var entities = world.getOtherEntities(player,
                    player.getBoundingBox().expand(40), e -> e instanceof LivingEntity);

            LivingEntity target = null;
            double closest = Double.MAX_VALUE;
            for (var e : entities) {
                var box = e.getBoundingBox().expand(0.5);
                var hit = box.raycast(start, end);
                if (hit.isPresent()) {
                    double dist = start.distanceTo(hit.get());
                    if (dist < closest) {
                        closest = dist;
                        target = (LivingEntity) e;
                    }
                }
            }

            if (target != null) {
                target.damage(world.getDamageSources().magic(), 8.0f);
                Vec3d push = dir.multiply(2.0);
                target.setVelocity(push.x, 0.5, push.z);
                target.velocityModified = true;
            }

            if (world instanceof ServerWorld sw) {
                for (int i = 1; i <= 15; i++) {
                    Vec3d p = start.add(dir.multiply(i * 2));
                    sw.spawnParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 2, 0.1, 0.1, 0.1, 0);
                }
                if (target != null) {
                    sw.spawnParticles(ParticleTypes.EXPLOSION,
                            target.getX(), target.getY() + 1, target.getZ(), 5, 0.3, 0.3, 0.3, 0);
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 0.4f, 2.0f);

            player.getItemCooldownManager().set(this, 8);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("§7Right Click: §fFire Repulsor Blast"));
        tooltip.add(Text.literal("§cRequires Full Iron Man Armor + Charge"));
    }
}
