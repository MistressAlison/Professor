package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class GnadeRing extends AbstractEasyRelic {
    public static final String ID = makeID(GnadeRing.class.getSimpleName());

    public GnadeRing() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
