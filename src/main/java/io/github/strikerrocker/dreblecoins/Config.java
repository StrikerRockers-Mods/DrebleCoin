package io.github.strikerrocker.dreblecoins;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class CommonConfig {

        public final ForgeConfigSpec.IntValue below50MinCount;
        public final ForgeConfigSpec.IntValue below50MaxCount;
        public final ForgeConfigSpec.IntValue below200MinCount;
        public final ForgeConfigSpec.IntValue below200MaxCount;
        public final ForgeConfigSpec.IntValue above200MinCount;
        public final ForgeConfigSpec.IntValue above200MaxCount;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Settings for Dreble coin drop").push("drops");
            below50MinCount = builder
                    .comment("What is the minimum count for health less than 50")
                    .defineInRange("below50MinCount", 1, 0, 64);
            below50MaxCount = builder
                    .comment("What is the maximum count for health less than 50")
                    .defineInRange("below50MaxCount", 15, 0, 64);
            below200MinCount = builder
                    .comment("What is the minimum count for health less than 50")
                    .defineInRange("below200MinCount", 10, 0, 64);
            below200MaxCount = builder
                    .comment("What is the maximum count for health less than 50")
                    .defineInRange("below200MaxCount", 30, 0, 64);
            above200MinCount = builder
                    .comment("What is the minimum count for health less than 50")
                    .defineInRange("above200MinCount", 50, 0, 64);
            above200MaxCount = builder
                    .comment("What is the maximum count for health less than 50")
                    .defineInRange("above200MaxCount", 64, 0, 64);
            builder.pop();
        }
    }
}
