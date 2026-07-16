package com.demonicrous.furusato.ui.client.gui.component;

import com.demonicrous.furusato.ui.FurusatoUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/** Minecraft's modern 3-pixel nine-slice button adapted to the 1.12.2 GUI API. */
public class FurusatoButton extends GuiButton {
    private static final ResourceLocation NORMAL = texture("button");
    private static final ResourceLocation HIGHLIGHTED = texture("button_highlighted");
    private static final ResourceLocation DISABLED = texture("button_disabled");
    private boolean focused;

    public FurusatoButton(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
        ResourceLocation texture = !enabled ? DISABLED : hovered || focused ? HIGHLIGHTED : NORMAL;
        minecraft.getTextureManager().bindTexture(texture);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        drawThreeSlice(x, y, width, height);
        int color = enabled ? 0xFFFFFFFF : 0xFFA0A0A0;
        drawCenteredString(minecraft.fontRenderer, displayString,
                x + width / 2, y + (height - 8) / 2, color);
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(FurusatoUI.MOD_ID, "textures/gui/widget/" + name + ".png");
    }

    private static void drawThreeSlice(int x, int y, int width, int height) {
        drawSlice(x, y, 3, height, 0, 3);
        drawSlice(x + 3, y, width - 6, height, 3, 194);
        drawSlice(x + width - 3, y, 3, height, 197, 3);
    }

    private static void drawSlice(int x, int y, int width, int height, int sourceX, int sourceWidth) {
        double u0 = sourceX / 200.0D;
        double u1 = (sourceX + sourceWidth) / 200.0D;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + height, 0).tex(u0, 1).endVertex();
        buffer.pos(x + width, y + height, 0).tex(u1, 1).endVertex();
        buffer.pos(x + width, y, 0).tex(u1, 0).endVertex();
        buffer.pos(x, y, 0).tex(u0, 0).endVertex();
        tessellator.draw();
    }
}
