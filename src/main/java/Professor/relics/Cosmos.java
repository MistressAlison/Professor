package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class Cosmos extends AbstractEasyRelic {
    public static final String ID = makeID(Cosmos.class.getSimpleName());

    public Cosmos() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
