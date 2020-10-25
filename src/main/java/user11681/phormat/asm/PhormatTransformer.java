package user11681.phormat.asm;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import user11681.fabricasmtools.plugin.TransformerPlugin;

public class PhormatTransformer extends TransformerPlugin {
    @Override
    public void onLoad(final String mixinPackage) {
        super.onLoad(mixinPackage);

        this.registerPreMixinTransformer(klass(2583), PhormatTransformer::transformStyle);
        this.registerPreMixinTransformer(klass(5251), PhormatTransformer::transformTextColor);
    }

    private static void transformStyle(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo info) {
        final ObjectOpenHashSet<String> methodNames = new ObjectOpenHashSet<>();

        methodNames.add(method(27705));
        methodNames.add(method(27706));
        methodNames.add(method(27707));

        for (final MethodNode method : targetClass.methods) {
            final String name = method.name;

            if (methodNames.contains(name)) {
                AbstractInsnNode instruction = method.instructions.getFirst();

                while (instruction != null) {
                    MethodInsnNode methodInstruction;

                    if (instruction.getOpcode() == Opcodes.INVOKEVIRTUAL && (methodInstruction = (MethodInsnNode) instruction).name.equals("ordinal")) {
                        method.instructions.set(instruction, new MethodInsnNode(Opcodes.INVOKESTATIC, targetClassName.replace('.', '/'), "phormat_hackOrdinal", "(L" + methodInstruction.owner + ";)I"));

                        break;
                    }

                    instruction = instruction.getNext();
                }
            }
        }
    }

    private static void transformTextColor(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo info) {
        final String getRgb = method(27716);

        for (final MethodNode method : targetClass.methods) {
            if (getRgb.equals(method.name)) {
                final String internalName = targetClassName.replace('.', '/');
                final String rgb = method(24364);

                final InsnList insertion = new InsnList();
                final LabelNode endIf = new LabelNode();

                insertion.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
                insertion.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this this
                insertion.add(new FieldInsnNode(Opcodes.GETFIELD, internalName, "phormat_hasColorFunction", "Z")); // this boolean
                insertion.add(new JumpInsnNode(Opcodes.IFEQ, endIf)); // this
                insertion.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this this
                insertion.add(new FieldInsnNode(Opcodes.GETFIELD, internalName, "phormat_colorFunction", "Luser11681/phormat/ColorFunction;")); // this ColorFunction
                insertion.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this ColorFunction this
                insertion.add(new FieldInsnNode(Opcodes.GETFIELD, internalName, rgb, "I")); // this ColorFunction int
                insertion.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "user11681/phormat/ColorFunction", "apply", "(I)I", true)); // this int
                insertion.add(new InsnNode(Opcodes.DUP_X1)); // int this int
                insertion.add(new FieldInsnNode(Opcodes.PUTFIELD, internalName, "phormat_previousColor", "I")); // int
                insertion.add(new InsnNode(Opcodes.IRETURN));
                insertion.add(endIf);

                method.instructions.insertBefore(method.instructions.getFirst(), insertion);

                break;
            }
        }
    }
}
