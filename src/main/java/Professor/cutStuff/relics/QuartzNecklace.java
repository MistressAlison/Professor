package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class QuartzNecklace extends AbstractEasyRelic {
    public static final String ID = makeID(QuartzNecklace.class.getSimpleName());

    public QuartzNecklace() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
