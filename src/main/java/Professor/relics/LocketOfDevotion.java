package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class LocketOfDevotion extends AbstractEasyRelic {
    public static final String ID = makeID(LocketOfDevotion.class.getSimpleName());

    public LocketOfDevotion() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
