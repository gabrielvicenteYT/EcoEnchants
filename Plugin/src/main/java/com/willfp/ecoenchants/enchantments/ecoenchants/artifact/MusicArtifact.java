package com.willfp.ecoenchants.enchantments.ecoenchants.artifact;

import com.willfp.ecoenchants.enchantments.itemtypes.Artifact;
import org.bukkit.Particle;
public final class MusicArtifact extends Artifact {
    public MusicArtifact() {
        super(
                "music_artifact"
        );
    }

    @Override
    protected Particle getParticle() {
        return Particle.NOTE;
    }
}