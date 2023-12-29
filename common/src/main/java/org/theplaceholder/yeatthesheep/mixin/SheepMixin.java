package org.theplaceholder.yeatthesheep.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements Shearable {
    @Shadow public abstract void setSheared(boolean bl);

    @Shadow @Final private static Map<DyeColor, ItemLike> ITEM_BY_DYE;

    @Shadow public abstract DyeColor getColor();

    protected SheepMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (!this.level().isClientSide && this.readyForShearing()){
            this.yeatthesheep$yeet();
        }
        return super.hurt(damageSource, f);
    }

    @Unique
    public void yeatthesheep$yeet() {
        this.setSheared(true);
        int i = 1 + this.random.nextInt(2);

        for(int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.spawnAtLocation(ITEM_BY_DYE.get(this.getColor()), 1);
            if (itemEntity != null) {
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (this.random.nextFloat() * 0.05F), ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
            }
        }
    }
}
