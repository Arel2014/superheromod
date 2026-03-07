package com.superhero.mod;

import com.superhero.mod.items.WebShooterItem;
import com.superhero.mod.util.ArmorHelper;
import com.superhero.mod.util.ChargeHelper;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IronManEvents {

    // 100% charge / 6000 ticks
    private static final float FLIGHT_DRAIN_PER_TICK = 100.0f / 6000.0f;
    private static final Map<UUID, Boolean> wasFlying = new HashMap<>();

    public static void register() {

        // Server tick - handle flight
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerManager().getPlayerList()) {
                handlePlayerTick(player);
            }
        });

        // Left click on entity = web pull (if holding web shooter)
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack stack = player.getMainHandStack();
            if (stack.getItem() instanceof WebShooterItem && entity instanceof LivingEntity living) {
                if (!world.isClient()) {
                    WebShooterItem.handleLeftClick(player, living);
                }
                return ActionResult.FAIL; // Cancel normal attack
            }
            return ActionResult.PASS;
        });

        // Cancel fall damage for Iron Man
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player
                    && source.equals(entity.getWorld().getDamageSources().fall())) {
                if (ArmorHelper.isWearingFullIronMan(player)) {
                    ItemStack chest = ArmorHelper.getIronManChestplate(player);
                    if (ChargeHelper.hasEnough(chest)) {
                        // Land with particles
                        if (entity.getWorld() instanceof ServerWorld sw && amount > 3) {
                            sw.spawnParticles(ParticleTypes.EXPLOSION,
                                    player.getX(), player.getY(), player.getZ(), 4, 1.0, 0, 1.0, 0.1);
                        }
                        return false; // Cancel damage
                    }
                }
            }
            return true;
        });
    }

    private static void handlePlayerTick(ServerPlayerEntity player) {
        boolean hasIronMan = ArmorHelper.isWearingFullIronMan(player);

        if (!hasIronMan) {
            if (player.getAbilities().allowFlying) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
                player.sendAbilitiesUpdate();
            }
            return;
        }

        ItemStack chest = ArmorHelper.getIronManChestplate(player);
        float charge = ChargeHelper.getCharge(chest);
        boolean hasElytra = ArmorHelper.isWearingElytra(player);

        if (charge >= ChargeHelper.MIN_CHARGE) {
            if (!player.getAbilities().allowFlying) {
                player.getAbilities().allowFlying = true;
                player.sendAbilitiesUpdate();
            }

            // Drain charge while flying (not with elytra)
            if (player.getAbilities().flying && !hasElytra) {
                ChargeHelper.drain(chest, FLIGHT_DRAIN_PER_TICK);

                // Thruster particles
                ServerWorld sw = (ServerWorld) player.getWorld();
                sw.spawnParticles(ParticleTypes.FLAME,
                        player.getX(), player.getY() - 0.5, player.getZ(),
                        3, 0.2, 0.0, 0.2, 0.05);
                sw.spawnParticles(ParticleTypes.SMOKE,
                        player.getX(), player.getY() - 0.5, player.getZ(),
                        2, 0.1, 0.0, 0.1, 0.02);
            }
        } else {
            // Charge too low
            if (player.getAbilities().allowFlying) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
                player.sendAbilitiesUpdate();
                player.sendMessage(
                    net.minecraft.text.Text.literal("§c⚡ Suit power critical! Flight disabled."), true);
            }
        }
    }
}
