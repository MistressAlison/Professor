package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class InnocentRiches extends AbstractEasyRelic {
    public static final String ID = makeID(InnocentRiches.class.getSimpleName());

    public InnocentRiches() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
