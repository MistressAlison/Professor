package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class GloryBelt extends AbstractEasyRelic {
    public static final String ID = makeID(GloryBelt.class.getSimpleName());

    public GloryBelt() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
