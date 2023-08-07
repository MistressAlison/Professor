package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class BaronsCrown extends AbstractEasyRelic {
    public static final String ID = makeID(BaronsCrown.class.getSimpleName());

    public BaronsCrown() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
