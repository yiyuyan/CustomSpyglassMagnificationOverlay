package cn.ksmcbrigade.csms.mixin;

import cn.ksmcbrigade.csms.CustomSpyglassMagnificationScreen;
import cn.ksmcbrigade.csms.config.Mode;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow protected int screenWidth;

    @Inject(method = "renderSpyglassOverlay",at = @At("HEAD"),cancellable = true)
    public void spyglassOverlay(GuiGraphics p_282069_, float p_283442_, CallbackInfo ci){
        if(CustomSpyglassMagnificationScreen.config.mode.equals(Mode.NONE)) ci.cancel();
    }

    @Inject(method = "renderSpyglassOverlay",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIFFIIII)V",shift = At.Shift.AFTER), cancellable = true,locals = LocalCapture.CAPTURE_FAILSOFT)
    public void spyglassOverlayBackground(GuiGraphics p_282069_, float p_283442_, CallbackInfo ci, float f, float f1, int i, int j, int k, int l, int i1, int j1){
        if(CustomSpyglassMagnificationScreen.config.mode.equals(Mode.NO_BACKGROUND)) ci.cancel();
        if(CustomSpyglassMagnificationScreen.config.mode.equals(Mode.CUSTOM)){
            p_282069_.blit(new ResourceLocation(CustomSpyglassMagnificationScreen.MOD_ID,"gui/left.png"),0,l, 0.0F,0.0F,k,j1,k,j1);
            p_282069_.blit(new ResourceLocation(CustomSpyglassMagnificationScreen.MOD_ID,"gui/right.png"),i1,l,0,0,this.screenWidth-i1,j, this.screenWidth-i1,j);

            ci.cancel();
        }
    }
}
