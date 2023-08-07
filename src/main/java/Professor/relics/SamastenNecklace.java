package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class SamastenNecklace extends AbstractEasyRelic {
    public static final String ID = makeID(SamastenNecklace.class.getSimpleName());

    public SamastenNecklace() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
