package com.willfp.ecoenchants.enchantments.ecoenchants.special;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
public final class LifeSteal extends EcoEnchant {
    public LifeSteal() {
        super(
                "life_steal", EnchantmentType.SPECIAL
        );
    }

    // START OF LISTENERS


    @Override
    public void onMeleeAttack(LivingEntity attacker, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
        double multiplier = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "health-per-level");
        double amountToHeal = level * multiplier;
        double newHealth = attacker.getHealth() + amountToHeal;
        if (newHealth > attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            newHealth = attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        }
        attacker.setHealth(newHealth);
    }
}
