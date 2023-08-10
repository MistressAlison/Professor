package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class EnergyPendant extends AbstractEasyRelic {
    public static final String ID = makeID(EnergyPendant.class.getSimpleName());

    public EnergyPendant() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
