package com.superhero.mod.blocks;

import com.superhero.mod.armor.IronManArmorItem;
import com.superhero.mod.registry.ModBlockEntities;
import com.superhero.mod.util.ChargeHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmorChargingStationBlockEntity extends BlockEntity {

    // 0.25 MC day = 6000 ticks -> 100% charge / 6000 ticks
    private static final float CHARGE_PER_TICK = 100.0f / 6000.0f;

    private ItemStack storedArmor = ItemStack.EMPTY;

    public ArmorChargingStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHARGING_STATION, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state,
                             ArmorChargingStationBlockEntity be) {
        if (world.isClient()) return;
        if (!be.storedArmor.isEmpty() && be.storedArmor.getItem() instanceof IronManArmorItem) {
            if (!ChargeHelper.isFull(be.storedArmor)) {
                ChargeHelper.add(be.storedArmor, CHARGE_PER_TICK);
                be.markDirty();
            }
        }
    }

    public void interact(PlayerEntity player) {
        if (storedArmor.isEmpty()) {
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            if (!chest.isEmpty() && chest.getItem() instanceof IronManArmorItem) {
                storedArmor = chest.copy();
                player.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
                player.sendMessage(Text.literal("§b⚡ Chestplate placed for charging..."), true);
                markDirty();
            } else {
                player.sendMessage(Text.literal("§cPlace Iron Man chestplate in hand first, then right-click!"), true);
            }
        } else {
            float charge = ChargeHelper.getCharge(storedArmor);
            player.sendMessage(Text.literal("§b⚡ Charge: §f" + String.format("%.1f", charge) + "%"), true);
            if (ChargeHelper.isFull(storedArmor)) {
                if (player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    player.equipStack(EquipmentSlot.CHEST, storedArmor.copy());
                    storedArmor = ItemStack.EMPTY;
                    player.sendMessage(Text.literal("§a⚡ Fully charged! Armor returned."), true);
                    markDirty();
                }
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!storedArmor.isEmpty()) {
            nbt.put("stored_armor", storedArmor.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("stored_armor")) {
            storedArmor = ItemStack.fromNbt(nbt.getCompound("stored_armor"));
        }
    }
}
