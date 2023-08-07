package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class SoulOfFortitude extends AbstractEasyRelic {
    public static final String ID = makeID(SoulOfFortitude.class.getSimpleName());

    public SoulOfFortitude() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
