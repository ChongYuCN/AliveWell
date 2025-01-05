package com.chongyu.aliveandwell.xpgui.gui.screen;

import com.chongyu.aliveandwell.xpgui.common.PlayerStatsManagerAccess;
import com.chongyu.aliveandwell.xpgui.common.XPStates;
import com.chongyu.aliveandwell.xpgui.network.PlayerStatsClientPacket;
import com.chongyu.aliveandwell.xpgui.network.PlayerStatsServerPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class XPScreen extends Screen {
    private int xpCost1 = 1;
    private int xpCost2 = 10;
    private int xpCost3 = 50;
    private int xpCost4 = 100;
    private int xpCost5 = 500;
    private int xpCost6 = 1000;
    int scaledHeight ;


    private ButtonWidget buttonsPlus10;
    private ButtonWidget buttonsPlus100;
    private ButtonWidget buttonsPlus500;
    private ButtonWidget buttonsPlus1000;
    private ButtonWidget buttonsPlus5000;
    private ButtonWidget buttonsPlus10000;
    private ButtonWidget buttonsPlus;

    private ButtonWidget buttonsOut10;
    private ButtonWidget buttonsOut100;
    private ButtonWidget buttonsOut500;
    private ButtonWidget buttonsOut1000;
    private ButtonWidget buttonsOut5000;
    private ButtonWidget buttonsOut10000;
    private ButtonWidget buttonsOut;
    private int xpBox;
    private int playerEx;
    private final XPStates xpStates;
    public XPScreen(MinecraftClient client, Text title) {
        super(title);
        this.xpStates = ((PlayerStatsManagerAccess) client.player).getPlayerStatsManager();
        scaledHeight = client.getWindow().getScaledHeight();
    }

    @Override
    protected void init() {

        this.buttonsPlus10 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("+"+xpCost1), (button) -> {
            if(xpStates.canPlus(xpCost1)){
                this.buttonsPlus10.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpCost1);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, xpCost1);
            }else {
                this.buttonsPlus10.active  =false;
            }
        }).dimensions(this.width/2 - 25, this.scaledHeight / 2 -65, 50, 10).build());

        this.buttonsOut10 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("-"+xpCost1), (button) -> {
            if(xpStates.canPlus(-xpCost1)){
                this.buttonsOut10.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, -xpCost1);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, -xpCost1);
            }else {
                this.buttonsOut10.active  =false;
            }
        }).dimensions(this.width/2 +40, this.scaledHeight / 2 -65, 50, 10).build());

        //=================================
        this.buttonsPlus100 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder( Text.translatable("+"+xpCost2), (button) -> {
            if(xpStates.canPlus(xpCost2)){
                this.buttonsPlus100.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpCost2);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, xpCost2);
            }else {
                this.buttonsPlus100.active  =false;
            }
        }).dimensions(this.width/2 - 25, this.scaledHeight / 2 -50, 50, 10).build());

        this.buttonsOut100 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("-"+xpCost2), (button) -> {
            if(xpStates.canPlus(-xpCost2)){
                this.buttonsOut100.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, -xpCost2);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, -xpCost2);
            }else {
                this.buttonsOut100.active  =false;
            }
        }).dimensions(this.width/2 +40, this.scaledHeight / 2 -50, 50, 10).build());

        //=================================
        this.buttonsPlus500 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("+"+xpCost3), (button) -> {
            if(xpStates.canPlus(xpCost3)){
                this.buttonsPlus500.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpCost3);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, xpCost3);
            }else {
                this.buttonsPlus500.active  =false;
            }
        }).dimensions(this.width/2 - 25, this.scaledHeight / 2 -35, 50, 10).build());

        this.buttonsOut500 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("-"+xpCost3), (button) -> {
            if(xpStates.canPlus(-xpCost3)){
                this.buttonsOut500.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, -xpCost3);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, -xpCost3);
            }else {
                this.buttonsOut500.active  =false;
            }
        }).dimensions(this.width/2 +40, this.scaledHeight / 2 -35, 50, 10).build());

        //=================================
        this.buttonsPlus1000 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("+"+xpCost4), (button) -> {
            if(xpStates.canPlus(xpCost4)){
                this.buttonsPlus1000.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpCost4);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, xpCost4);
            }else {
                this.buttonsPlus1000.active  =false;
            }
        }).dimensions(this.width/2 - 25, this.scaledHeight / 2 -20, 50, 10).build());
        
        this.buttonsOut1000 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("-"+xpCost4), (button) -> {
            if(xpStates.canPlus(-xpCost4)){
                this.buttonsOut1000.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, -xpCost4);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, -xpCost4);
            }else {
                this.buttonsOut1000.active  =false;
            }
        }).dimensions(this.width/2 +40, this.scaledHeight / 2 -20,50, 10).build());

        //====================================
        this.buttonsPlus5000 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("+"+xpCost5), (button) -> {
            if(xpStates.canPlus(xpCost5)){
                this.buttonsPlus5000.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpCost5);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, xpCost5);
            }else {
                this.buttonsPlus5000.active  =false;
            }
        }).dimensions(this.width/2 - 25, this.scaledHeight / 2 -5, 50, 10).build());

        this.buttonsOut5000 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("-"+xpCost5), (button) -> {
            if(xpStates.canPlus(-xpCost5)){
                this.buttonsOut5000.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, -xpCost5);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, -xpCost5);
            }else {
                this.buttonsOut5000.active  =false;
            }
        }).dimensions(this.width/2 +40, this.scaledHeight / 2 -5, 50, 10).build());

        //============================
        this.buttonsPlus10000 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("+"+xpCost6), (button) -> {
            if(xpStates.canPlus(xpCost6)){
                this.buttonsPlus10000.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpCost6);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, xpCost6);
            }else {
                this.buttonsPlus10000.active  =false;
            }
        }).dimensions(this.width/2 - 25, this.scaledHeight / 2 +10, 50, 10).build());

        this.buttonsOut10000 =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(Text.translatable("-"+xpCost6), (button) -> {
            if(xpStates.canPlus(-xpCost6)){
                this.buttonsOut10000.active  =true;
                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, -xpCost6);
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates, -xpCost6);
            }else {
                this.buttonsOut10000.active  =false;
            }
        }).dimensions(this.width/2 +40, this.scaledHeight / 2 +10, 50, 10).build());

        //============================
//        this.buttonsPlus =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(this.width/2 - 25, this.height / 2 +25, 50, 10, Text.translatable("全部存入"), (button) -> {
//            if(xpStates.canPlusTotal()){
//                this.buttonsPlus.active  =true;
//                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates, xpStates.playerEntity.totalExperience);
//                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates,xpStates.playerEntity.totalExperience);
//            }else {
//                this.buttonsPlus.active  =false;
//            }
//        }));
//        this.buttonsOut =  (ButtonWidget)this.addDrawableChild(ButtonWidget.builder(this.width/2 +40, this.height / 2 +25, 50, 10, Text.translatable("全部取出"), (button) -> {
//            if(xpStates.canPlus(-xpStates.xp)){
//                this.buttonsOut.active  =true;
//                PlayerStatsServerPacket.writeS2CXPPacket2(this.xpStates,-xpStates.xp);
//                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.xpStates,-xpStates.xp);
//            }else {
//                this.buttonsOut.active  =false;
//            }
//        }));
    }

    @Override
    public void tick() {
        super.tick();
        xpBox = xpStates.getXp();
        playerEx = xpStates.getPlayerEntity().totalExperience;
        if(xpStates.canPlus(xpCost1)){
            this.buttonsPlus10.active  =true;
        }else {
            this.buttonsPlus10.active  =false;
        }
        if(xpStates.canPlus(-xpCost1)){
            this.buttonsOut10.active  =true;
        }else {
            this.buttonsOut10.active  =false;
        }
        //===========================
        if(xpStates.canPlus(xpCost2)){
            this.buttonsPlus100.active  =true;
        }else {
            this.buttonsPlus100.active  =false;
        }
        if(xpStates.canPlus(-xpCost2)){
            this.buttonsOut100.active  =true;
        }else {
            this.buttonsOut100.active  =false;
        }
        //===========================
        if(xpStates.canPlus(xpCost3)){
            this.buttonsPlus500.active  =true;
        }else {
            this.buttonsPlus500.active  =false;
        }
        if(xpStates.canPlus(-xpCost3)){
            this.buttonsOut500.active  =true;
        }else {
            this.buttonsOut500.active  =false;
        }

        //===========================
        if(xpStates.canPlus(xpCost4)){
            this.buttonsPlus1000.active  =true;
        }else {
            this.buttonsPlus1000.active  =false;
        }
        if(xpStates.canPlus(-xpCost4)){
            this.buttonsOut1000.active  =true;
        }else {
            this.buttonsOut1000.active  =false;
        }

        //===========================
        if(xpStates.canPlus(xpCost5)){
            this.buttonsPlus5000.active  =true;
        }else {
            this.buttonsPlus5000.active  =false;
        }
        if(xpStates.canPlus(-xpCost5)){
            this.buttonsOut5000.active  =true;
        }else {
            this.buttonsOut5000.active  =false;
        }

        //===========================
        if(xpStates.canPlus(xpCost6)){
            this.buttonsPlus10000.active  =true;
        }else {
            this.buttonsPlus10000.active  =false;
        }
        if(xpStates.canPlus(-xpCost6)){
            this.buttonsOut10000.active  =true;
        }else {
            this.buttonsOut10000.active  =false;
        }

        //===========================
//        if(xpStates.getPlayerEntity().totalExperience > 0 && xpStates.xp < xpStates.GetMaxXp()){
//            this.buttonsPlus.active  =true;
//        }else {
//            this.buttonsPlus.active  =false;
//        }
//        if(xpStates.getXp() > 0){
//            this.buttonsOut.active  =true;
//        }else {
//            this.buttonsOut.active  =false;
//        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext matrices, int mouseX, int mouseY, float partialTicks) {
        super.render(matrices, mouseX, mouseY, partialTicks);
        assert this.client != null;
        if (this.client.player != null) {
            int scaledWidth = this.client.getWindow().getScaledWidth();
            int scaledHeight = this.client.getWindow().getScaledHeight();
            InventoryScreen.drawEntity(matrices,(int)(scaledWidth / 2 - 75), (int)(scaledHeight / 2 - 10),  30, -mouseX, -mouseY, (LivingEntity)this.client.player);

            matrices.drawCenteredTextWithShadow(this.textRenderer,  xpStates.getPlayerEntity().getName().copy().formatted(Formatting.YELLOW).append(Text.translatable("aliveandwell.xpgui.info1")), this.width / 2 + 27, this.scaledHeight / 2 -108, 16777215);
            matrices.drawTextWithShadow(this.textRenderer,  Text.translatable("aliveandwell.xpgui.info2").append(Text.of(xpBox+"/"+xpStates.GetMaxXp())).formatted(Formatting.GOLD), this.width/2 -25, this.scaledHeight / 2 -95, 16777215);
            matrices.drawTextWithShadow(this.textRenderer,  Text.translatable("aliveandwell.xpgui.info3").formatted(Formatting.GREEN), this.width/2 -25, this.scaledHeight / 2 -80, 16777215);
            matrices.drawTextWithShadow(this.textRenderer,  Text.translatable("aliveandwell.xpgui.info4").append(Text.of(String.valueOf(playerEx))).formatted(Formatting.LIGHT_PURPLE), this.width/2 -25, this.scaledHeight / 2 +25, 16777215);
        }

//        drawTextWithShadow(matrices, this.textRenderer,  Text.translatable("玩家经验："+ playerEx).formatted(Formatting.LIGHT_PURPLE), this.width/2 -25, this.height / 2 +40, 16777215);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        assert this.client != null;
        if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
