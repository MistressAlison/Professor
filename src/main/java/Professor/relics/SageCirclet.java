package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class SageCirclet extends AbstractEasyRelic {
    public static final String ID = makeID(SageCirclet.class.getSimpleName());

    public SageCirclet() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
