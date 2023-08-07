package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class MemoriaBracelet extends AbstractEasyRelic {
    public static final String ID = makeID(MemoriaBracelet.class.getSimpleName());

    public MemoriaBracelet() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
