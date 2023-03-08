package fr.vyxs.kron.registry;

import fr.vyxs.kron.Kron;
import fr.vyxs.kron.entity.MineralInfusingStationBlockEntity;
import fr.vyxs.kron.entity.VinylTurntableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KronBlockEntities {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Kron.MODID);

    public static void register(IEventBus bus) {
        BLOCK_ENTITY.register(bus);
    }

    public static final RegistryObject<BlockEntityType<MineralInfusingStationBlockEntity>> MINERAL_INFUSING_STATION =
            BLOCK_ENTITY.register("mineral_infusing_station",
                    () -> BlockEntityType.Builder.of(MineralInfusingStationBlockEntity::new, KronBlocks.MINERAL_INFUSING_STATION.get()).build(null)
            );

    public static final RegistryObject<BlockEntityType<VinylTurntableBlockEntity>> VINYL_TURNTABLE =
            BLOCK_ENTITY.register("vinyl_turntable",
                    () -> BlockEntityType.Builder.of(VinylTurntableBlockEntity::new, KronBlocks.VINYL_TURNTABLE.get()).build(null)
            );
}
