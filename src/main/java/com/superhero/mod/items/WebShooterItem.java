package com.superhero.mod.items;

import com.superhero.mod.util.ArmorHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class WebShooterItem extends Item {

    private static final float PULL_STRENGTH = 2.5f;
    private static final float NO_ARMOR_DAMAGE = 4.0f;
    private static final double WEB_RANGE = 30.0;

    public WebShooterItem(Settings settings) {
        super(settings);
    }

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

            Vec3d start = player.getEyePos();
            Vec3d end = start.add(player.getRotationVec(1.0f).multiply(WEB_RANGE));

            var entities = world.getOtherEntities(player,
                    player.getBoundingBox().expand(WEB_RANGE),
                    e -> e instanceof LivingEntity);

            LivingEntity hitEntity = null;
            double closest = Double.MAX_VALUE;
            for (var e : entities) {
                var box = e.getBoundingBox().expand(0.3);
                var hit = box.raycast(start, end);
                if (hit.isPresent()) {
                    double dist = start.distanceTo(hit.get());
                    if (dist < closest) {
                        closest = dist;
                        hitEntity = (LivingEntity) e;
                    }
                }
            }

            if (hitEntity != null) {
                hitEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 5));
                hitEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 80, 128));
                world.playSound(null, hitEntity.getX(), hitEntity.getY(), hitEntity.getZ(),
                        SoundEvents.BLOCK_SLIME_BLOCK_FALL, SoundCategory.PLAYERS, 1.0f, 0.8f);
            } else {
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

        if (distance <= WEB_RANGE) {
            Vec3d dir = targetPos.subtract(playerPos).normalize();
            player.setVelocity(dir.multiply(PULL_STRENGTH).add(0, 0.3, 0));
            player.velocityModified = true;
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.PLAYERS, 1.0f, 0.8f);
            player.getItemCooldownManager().set(player.getMainHandStack().getItem(), 10);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("§7Right Click: §fShoot Web"));
        tooltip.add(Text.literal("§7Left Click: §fPull toward target"));
        tooltip.add(Text.literal("§cWear Spider-Man armor or Elytra to avoid injury!"));
    }
}
