package com.chongyu.aliveandwell.hometpaback.util;

import com.chongyu.aliveandwell.hometpaback.HomeComponent;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IStoreHome {
    List<HomeComponent> getHomes();

    int getMaxHomes();

    boolean addHome(HomeComponent home);

    boolean removeHome(String name);

    void addBack(Vec3d pos, RegistryKey<World> dimension);

    Pair<Vec3d, RegistryKey<World>> getBack();

}
