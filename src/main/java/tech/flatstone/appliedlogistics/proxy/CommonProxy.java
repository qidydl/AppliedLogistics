/*
 *
 * LIMITED USE SOFTWARE LICENSE AGREEMENT
 * This Limited Use Software License Agreement (the "Agreement") is a legal agreement between you, the end-user, and the FlatstoneTech Team ("FlatstoneTech"). By downloading or purchasing the software material, which includes source code (the "Source Code"), artwork data, music and software tools (collectively, the "Software"), you are agreeing to be bound by the terms of this Agreement. If you do not agree to the terms of this Agreement, promptly destroy the Software you may have downloaded or copied.
 * FlatstoneTech SOFTWARE LICENSE
 * 1. Grant of License. FlatstoneTech grants to you the right to use the Software. You have no ownership or proprietary rights in or to the Software, or the Trademark. For purposes of this section, "use" means loading the Software into RAM, as well as installation on a hard disk or other storage device. The Software, together with any archive copy thereof, shall be destroyed when no longer used in accordance with this Agreement, or when the right to use the Software is terminated. You agree that the Software will not be shipped, transferred or exported into any country in violation of the U.S. Export Administration Act (or any other law governing such matters) and that you will not utilize, in any other manner, the Software in violation of any applicable law.
 * 2. Permitted Uses. For educational purposes only, you, the end-user, may use portions of the Source Code, such as particular routines, to develop your own software, but may not duplicate the Source Code, except as noted in paragraph 4. The limited right referenced in the preceding sentence is hereinafter referred to as "Educational Use." By so exercising the Educational Use right you shall not obtain any ownership, copyright, proprietary or other interest in or to the Source Code, or any portion of the Source Code. You may dispose of your own software in your sole discretion. With the exception of the Educational Use right, you may not otherwise use the Software, or an portion of the Software, which includes the Source Code, for commercial gain.
 * 3. Prohibited Uses: Under no circumstances shall you, the end-user, be permitted, allowed or authorized to commercially exploit the Software. Neither you nor anyone at your direction shall do any of the following acts with regard to the Software, or any portion thereof:
 * Rent;
 * Sell;
 * Lease;
 * Offer on a pay-per-play basis;
 * Distribute for money or any other consideration; or
 * In any other manner and through any medium whatsoever commercially exploit or use for any commercial purpose.
 * Notwithstanding the foregoing prohibitions, you may commercially exploit the software you develop by exercising the Educational Use right, referenced in paragraph 2. hereinabove.
 * 4. Copyright. The Software and all copyrights related thereto (including all characters and other images generated by the Software or depicted in the Software) are owned by FlatstoneTech and is protected by United States copyright laws and international treaty provisions. FlatstoneTech shall retain exclusive ownership and copyright in and to the Software and all portions of the Software and you shall have no ownership or other proprietary interest in such materials. You must treat the Software like any other copyrighted material. You may not otherwise reproduce, copy or disclose to others, in whole or in any part, the Software. You may not copy the written materials accompanying the Software. You agree to use your best efforts to see that any user of the Software licensed hereunder complies with this Agreement.
 * 5. NO WARRANTIES. FLATSTONETECH DISCLAIMS ALL WARRANTIES, BOTH EXPRESS IMPLIED, INCLUDING BUT NOT LIMITED TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. THIS LIMITED WARRANTY GIVES YOU SPECIFIC LEGAL RIGHTS. YOU MAY HAVE OTHER RIGHTS WHICH VARY FROM JURISDICTION TO JURISDICTION. FlatstoneTech DOES NOT WARRANT THAT THE OPERATION OF THE SOFTWARE WILL BE UNINTERRUPTED, ERROR FREE OR MEET YOUR SPECIFIC REQUIREMENTS. THE WARRANTY SET FORTH ABOVE IS IN LIEU OF ALL OTHER EXPRESS WARRANTIES WHETHER ORAL OR WRITTEN. THE AGENTS, EMPLOYEES, DISTRIBUTORS, AND DEALERS OF FlatstoneTech ARE NOT AUTHORIZED TO MAKE MODIFICATIONS TO THIS WARRANTY, OR ADDITIONAL WARRANTIES ON BEHALF OF FlatstoneTech.
 * Exclusive Remedies. The Software is being offered to you free of any charge. You agree that you have no remedy against FlatstoneTech, its affiliates, contractors, suppliers, and agents for loss or damage caused by any defect or failure in the Software regardless of the form of action, whether in contract, tort, includinegligence, strict liability or otherwise, with regard to the Software. Copyright and other proprietary matters will be governed by United States laws and international treaties. IN ANY CASE, FlatstoneTech SHALL NOT BE LIABLE FOR LOSS OF DATA, LOSS OF PROFITS, LOST SAVINGS, SPECIAL, INCIDENTAL, CONSEQUENTIAL, INDIRECT OR OTHER SIMILAR DAMAGES ARISING FROM BREACH OF WARRANTY, BREACH OF CONTRACT, NEGLIGENCE, OR OTHER LEGAL THEORY EVEN IF FLATSTONETECH OR ITS AGENT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES, OR FOR ANY CLAIM BY ANY OTHER PARTY. Some jurisdictions do not allow the exclusion or limitation of incidental or consequential damages, so the above limitation or exclusion may not apply to you.
 */

package tech.flatstone.appliedlogistics.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;
import tech.flatstone.appliedlogistics.AppliedLogistics;
import tech.flatstone.appliedlogistics.api.features.EnumOreType;
import tech.flatstone.appliedlogistics.api.registries.HammerRegistry;
import tech.flatstone.appliedlogistics.api.registries.PlanRegistry;
import tech.flatstone.appliedlogistics.api.registries.PulverizerRegistry;
import tech.flatstone.appliedlogistics.client.gui.GuiHandler;
import tech.flatstone.appliedlogistics.common.blocks.Blocks;
import tech.flatstone.appliedlogistics.common.items.Items;
import tech.flatstone.appliedlogistics.common.plans.PlanMachinePulverizer;
import tech.flatstone.appliedlogistics.common.plans.PlanPlanBuilder;
import tech.flatstone.appliedlogistics.common.plans.PlanPlanLibrary;
import tech.flatstone.appliedlogistics.common.util.EnumOres;
import tech.flatstone.appliedlogistics.common.util.IProvideEvent;
import tech.flatstone.appliedlogistics.common.util.IProvideRecipe;
import tech.flatstone.appliedlogistics.common.util.IProvideSmelting;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerBlocks() {
        Blocks.registerBlocks();
    }

    @Override
    public void registerItems() {
        Items.registerItems();
    }

    @Override
    public void registerOreDict() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            String oreName = EnumOres.byMeta(i).getName();

            // Register Ore
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.ORE))
                OreDictionary.registerOre("ore" + oreName, new ItemStack(Blocks.BLOCK_ORE.getBlock(), 1, i));

            // Register Ore Block
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.BLOCK))
                OreDictionary.registerOre("block" + oreName, new ItemStack(Blocks.BLOCK_ORE_BLOCK.getBlock(), 1, i));

            // Register Ingot
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT))
                OreDictionary.registerOre("ingot" + oreName, new ItemStack(Items.ITEM_ORE_INGOT.item, 1, i));

            // Register Dusts
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.DUST))
                OreDictionary.registerOre("dust" + oreName, new ItemStack(Items.ITEM_ORE_DUST.item, 1, i));

            // Register Nuggets
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.NUGGET))
                OreDictionary.registerOre("nugget" + oreName, new ItemStack(Items.ITEM_ORE_NUGGET.item, 1, i));

            // Register Gears
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.GEAR))
                OreDictionary.registerOre("gear" + oreName, new ItemStack(Items.ITEM_MATERIAL_GEAR.item, 1, i));
        }
    }

    @Override
    public void registerCrusherRecipes() {
        // Ores -> Dust
        for (int i = 0; i < EnumOres.values().length; i++) {
            String oreName = EnumOres.byMeta(i).getName();

            // Register Ores
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.DUST) && (EnumOres.byMeta(i).isTypeSet(EnumOreType.ORE) || EnumOres.byMeta(i).isTypeSet(EnumOreType.VANILLA))) {
                HammerRegistry.registerOreDictOre(oreName);
                PulverizerRegistry.registerOreDictOre(oreName);
            }

            // Register Ingots
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT) && (EnumOres.byMeta(i).isTypeSet(EnumOreType.ORE) || EnumOres.byMeta(i).isTypeSet(EnumOreType.VANILLA)))
                PulverizerRegistry.registerOreDictIngot(oreName);
        }

        // Add other misc vanilla things

        // Coal
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.coal_ore), new ItemStack(Items.ITEM_ORE_NUGGET.item, 1, EnumOres.DIAMOND.getMeta()), 0.005f, 0.01f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.coal_ore), new ItemStack(net.minecraft.init.Items.coal, 2), 1.0f, 0.5f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.coal_ore), new ItemStack(net.minecraft.init.Items.coal, 1), 0.5f, 0.5f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.coal_ore), new ItemStack(net.minecraft.init.Items.coal, 1), 0.5f, 0.5f);
        PulverizerRegistry.register(new ItemStack(net.minecraft.init.Blocks.coal_ore), new ItemStack(Items.ITEM_ORE_NUGGET.item, 1, EnumOres.DIAMOND.getMeta()), 0.05f, true);
        PulverizerRegistry.register(new ItemStack(net.minecraft.init.Blocks.coal_ore), new ItemStack(net.minecraft.init.Items.coal, 2), 1.5f, true);

        // Redstone
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 6), 1.0f, 0.5f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 4), 0.1f, 0.1f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 2), 0.0f, 0.5f);
        PulverizerRegistry.register(new ItemStack(net.minecraft.init.Blocks.redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 4), 4.0f, true);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.lit_redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 6), 1.0f, 0.5f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.lit_redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 4), 0.1f, 0.1f);
        HammerRegistry.register(new ItemStack(net.minecraft.init.Blocks.lit_redstone_ore), new ItemStack(net.minecraft.init.Items.redstone, 2), 0.0f, 0.5f);
    }

    @Override
    public void registerFurnaceRecipes() {
        for (int i = 0; i < Items.values().length; i++) {
            Item item = Items.values()[i].getItem();
            if (item instanceof IProvideSmelting) {
                ((IProvideSmelting) item).RegisterSmelting();
            }
        }

        for (int i = 0; i < Blocks.values().length; i++) {
            Block block = Blocks.values()[i].getBlock();
            if (block instanceof IProvideSmelting) {
                ((IProvideSmelting) block).RegisterSmelting();
            }
        }
    }

    @Override
    public void registerEvents() {
        //todo: Re-do this, make better... fore intead of fori

        for (int i = 0; i < Items.values().length; i++) {
            Item item = Items.values()[i].getItem();
            if (item instanceof IProvideEvent) {
                MinecraftForge.EVENT_BUS.register(item);
            }
        }

        for (int i = 0; i < Blocks.values().length; i++) {
            Block block = Blocks.values()[i].getBlock();
            if (block instanceof IProvideEvent) {
                MinecraftForge.EVENT_BUS.register(block);
            }
        }

    }

    @Override
    public void registerRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideRecipe)
                ((IProvideRecipe) item.getItem()).RegisterRecipes();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideRecipe)
                ((IProvideRecipe) block.getBlock()).RegisterRecipes();
        }
    }

    @Override
    public void registerBlueprints() {
        PlanRegistry.registerPlan(new PlanMachinePulverizer());
        PlanRegistry.registerPlan(new PlanPlanBuilder());
        PlanRegistry.registerPlan(new PlanPlanLibrary());
    }

    @Override
    public void registerGUIs() {
        NetworkRegistry.INSTANCE.registerGuiHandler(AppliedLogistics.instance, new GuiHandler());
    }

    @Override
    public void registerRenderers() {

    }
}
