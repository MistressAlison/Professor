package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class FourSpiritsAmulet extends AbstractEasyRelic {
    public static final String ID = makeID(FourSpiritsAmulet.class.getSimpleName());

    public FourSpiritsAmulet() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
