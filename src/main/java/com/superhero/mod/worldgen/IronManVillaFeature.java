package com.superhero.mod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class IronManVillaFeature extends Feature<IronManVillaFeature.Config> {

    public IronManVillaFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<Config> context) {
        BlockPos origin = context.getOrigin();
        if (!context.getWorld().getBlockState(origin).isAir()) return false;
        if (origin.getY() < 60 || origin.getY() > 120) return false;

        IronManVillaGenerator.generate(context.getWorld(), origin,
                new Random(context.getRandom().nextLong()));
        return true;
    }

    public record Config() implements FeatureConfig {
        public static final Codec<Config> CODEC = Codec.unit(Config::new);
    }
}
