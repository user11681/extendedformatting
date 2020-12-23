package user11681.phormat;

import net.fabricmc.api.ModInitializer;
import user11681.phormat.api.FormattingRegistry;

public class Test implements ModInitializer {
    @Override
    public void onInitialize() {
        for (int i = 122; i != 200; i++) {
            System.out.println(FormattingRegistry.register(String.valueOf(i), (char) i, true));
        }
    }
}
