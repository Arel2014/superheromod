package com.superhero.mod.items;

import com.superhero.mod.util.ArmorHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.item.tooltip.TooltipType;

import java.util.List;

public class WebShooterItem extends Item {

    private static final float PULL_STRENGTH = 2.5f;
    private static final float NO_ARMOR_DAMAGE = 4.0f;
    private static final double WEB_RANGE = 30.0;

    public WebShooterItem(Settings settings) {
        super(settings);
    }

    // RIGHT CLICK = shoot web
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient()) {
            boolean hasSpider = ArmorHelper.isWearingFullSpiderMan(player);
            boolean hasElytra = ArmorHelper.isWearingElytra(player);

            if (!hasSpider && !hasElytra) {
                player.damage(world.getDamageSources().generic(), NO_ARMOR_DAMAGE);
                player.sendMessage(Text.literal("§cThe web shooter strains your bare wrists!"), true);
            }

            // Shoot web - raycast to find target
            Vec3d start = player.getEyePos();
            Vec3d end = start.add(player.getRotationVec(1.0f).multiply(WEB_RANGE));

            // Check for entity hit
            var entityHit = raycastEntity(world, player, start, end);
            if (entityHit != null && entityHit.getEntity() instanceof LivingEntity living) {
                // Web the entity
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 5));
                living.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 80, 128));
                world.playSound(null, living.getX(), living.getY(), living.getZ(),
                        SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.PLAYERS, 1.0f, 0.8f);
            } else {
                // Place cobweb on block hit
                var blockHit = world.raycast(new RaycastContext(start, end,
                        RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));
                if (blockHit.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());
                    if (world.getBlockState(pos).isAir()) {
                        world.setBlockState(pos, Blocks.COBWEB.getDefaultState());
                    }
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.5f);
            player.getItemCooldownManager().set(this, 10);
            stack.damage(1, player, LivingEntity.getSlotForHand(hand));
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    // LEFT CLICK (attack) = pull toward target - handled in IronManEvents via attack callback
    public static void handleLeftClick(PlayerEntity player, LivingEntity target) {
        World world = player.getWorld();
        boolean hasSpider = ArmorHelper.isWearingFullSpiderMan(player);
        boolean hasElytra = ArmorHelper.isWearingElytra(player);

        if (!hasSpider && !hasElytra) {
            player.damage(world.getDamageSources().generic(), NO_ARMOR_DAMAGE);
            player.sendMessage(Text.literal("§cThe web shooter strains your bare wrists!"), true);
            return;
        }

        Vec3d playerPos = player.getEyePos();
        Vec3d targetPos = target.getPos().add(0, target.getHeight() / 2, 0);
        double distance = playerPos.distanceTo(targetPos);

        if (distance <= 30.0) {
            Vec3d dir = targetPos.subtract(playerPos).normalize();
            player.setVelocity(dir.multiply(PULL_STRENGTH).add(0, 0.3, 0));
            player.velocityModified = true;
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.PLAYERS, 1.0f, 0.8f);
            player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 10);
        }
    }

    private net.minecraft.util.hit.EntityHitResult raycastEntity(World world, PlayerEntity player, Vec3d start, Vec3d end) {
        var entities = world.getOtherEntities(player,
                player.getBoundingBox().expand(30),
                e -> e instanceof LivingEntity && e != player);
        for (var entity : entities) {
            var box = entity.getBoundingBox().expand(0.3);
            var hit = box.raycast(start, end);
            if (hit.isPresent()) {
                return new net.minecraft.util.hit.EntityHitResult(entity, hit.get());
            }
        }
        return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("§7Right Click: §fShoot Web / Entangle enemy"));
        tooltip.add(Text.literal("§7Left Click: §fPull toward target"));
        tooltip.add(Text.literal("§cWear Spider-Man armor or Elytra to avoid injury!"));
    }
}
