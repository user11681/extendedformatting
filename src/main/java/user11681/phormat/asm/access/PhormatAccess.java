package user11681.phormat.asm.access;

import net.minecraft.util.Formatting;
import user11681.phormat.Phormatting;

public interface PhormatAccess {
    boolean isCustom();

    Phormatting getPhormatting();

    void setFormat(Phormatting format);

    Formatting cast();
}
