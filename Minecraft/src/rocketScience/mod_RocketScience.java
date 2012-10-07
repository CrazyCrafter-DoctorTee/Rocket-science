package rocketScience;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class mod_RocketScience extends BaseMod implements cpw.mods.fml.common.Mod {
	
	public static int missileModelID;
	public static int warheadModelID;
	public static int machineModelID;
	public static boolean needsInit = true;
	public static int height=1;
	public static Block booster;
	public static Block warhead;
	public static Block superconductor;
	public static Block RSmachine;
	public static Block RSgenerator;
	public static ItemRS parachute;
	public static ItemRS passengerModule;
	public static ItemRS boosterModule;
	public static ItemRS tntModule;
	public static ItemRS incendiaryModule;
	public static ItemRS nuclearModule;
	public static ItemRS rangefinderItem;
	public static ItemRS superconductorUncompressed;
	public static ItemRS cellDeuterium;
	public static ItemRS copperCoil;
	public static ItemRS copperCoils;
	public static ItemRS superCoil;
	public static ItemRS superCoils;
	public static ItemRS ohmicHeater;
	public static ItemRS neutralHeater;
	public static ItemRS rfHeater;
	public static ItemRS ionDrive;
	public static ItemRS passengerDepleted;
	public static ItemRS lithium;
	public static ItemRS lithiumCell;
	public static ItemRS lithium6Cell;
	public static ItemRS tritiumCell;
	public static ItemRS thermoModule;
	public static ItemVacuum vacuum;
	public static ItemArmorRS parachuteArmor;
	public static ItemHandPump handPump;
	public static ItemAutominerFinder finder;
	public static File optionsFile;
	public static final String saveFileVersion = "1.1.0";
	public static int boosterID = 4096;
	public static int warheadID = 4095;
	public static int superconductorID = 4094;
	public static int machineID = 4093;
	public static int generatorID = 4092;
	public static int parachuteID = 20000;
	public static int passengerModuleID = 20001;
	public static int boosterModuleID = 20002;
	public static int tntModuleID = 20003;
	public static int incendiaryModuleID = 20004;
	public static int nuclearModuleID = 20005;
	public static int rangefinderItemID = 20006;
	public static int parachuteArmorID = 20007;
	public static int superconductorUncompressedID=20008;
	public static int deuteriumID=20009;
	public static int copperCoilID=20010;
	public static int copperCoilsID=20011;
	public static int superCoilID=20012;
	public static int superCoilsID=20013;
	public static int ohmicHeaterID=20014;
	public static int neutralHeaterID=20015;
	public static int rfHeaterID=20016;
	public static int vacuumID=20017;
	public static int handPumpID=20020;
	public static int ionDriveID=20021;
	public static int passengerDepletedID=20022;
	public static int finderID=20023;
	public static int lithiumID=20024;
	public static int lithiumCellID=20025;
	public static int lithium6CellID=20026;
	public static int tritiumCellID=20027;
	public static int thermoModuleID=20028;
	public static int guiIsotopeID=40;
	public static int guiFusionID=41;
	public static int guiAutoMinerID=42;
	public static int guiDefenseID=43;
	public static int guiOffenseID=44;
	public static int guiLaserID=45;
	
	boolean parachuteDeployed=false;
	
	public static mod_RocketScience instance;
	
	public static int cableRenderId;
	
	public mod_RocketScience()
	{
		instance = this;
		MinecraftForge.registerEntity(MissileBoosterEntity.class,this,143,160,40,true);
		MinecraftForge.registerEntity(MissileWarheadEntity.class,this,144,160,40,true);
		MinecraftForge.registerEntity(MissilePassengerBoosterEntity.class,this,145,160,40,true);
		MinecraftForge.registerEntity(MissilePassengerWarheadEntity.class,this,146,160,40,true);
		MinecraftForge.registerEntity(ParachuteEntity.class,this,147,160,40,true);
		MinecraftForge.registerEntity(RangefinderEntity.class,this,148,160,40,true);
		MinecraftForge.registerEntity(MissileMinerBoosterEntity.class,this,149,160,40,true);
	}
	
	public static void initializeMod()
	{	
	      optionsFile = new File(Minecraft.getMinecraftDir(), "/config/RocketScience.cfg");
	      func_22024_func_21238_readOptions();
	      booster = new MissileBoosterBlock(boosterID, 0);
	      warhead = new MissileWarheadBlock(warheadID);
	      RSmachine = new BlockRSMachine(machineID);
	      RSgenerator = new BlockRSGenerator(generatorID);
	      superconductor = new BlockSuperconductor(superconductorID);
	      parachute=(ItemRS)new ItemRS(parachuteID,0);
	      passengerModule=(ItemRS)new ItemRS(passengerModuleID,1);
	      boosterModule=(ItemRS)new ItemRS(boosterModuleID,2);
	      tntModule=(ItemRS)new ItemRS(tntModuleID,3);
	      incendiaryModule=(ItemRS)new ItemRS(incendiaryModuleID,4);
	      nuclearModule=(ItemRS)new ItemRS(nuclearModuleID,5);
	      rangefinderItem=(ItemRS)new RangefinderItem(rangefinderItemID,6);
	      parachuteArmor = (ItemArmorRS)new ItemArmorRS(parachuteArmorID, 7, EnumArmorMaterial.CLOTH, GameRegistry.addArmor("parachute"), 1, 1000);
	      superconductorUncompressed=(ItemRS)new ItemRS(superconductorUncompressedID,8);
	      cellDeuterium = (ItemRS)(new ItemRS(deuteriumID, 9));
	      copperCoil=(ItemRS)new ItemRS(copperCoilID,10);
	      copperCoils=(ItemRS)new ItemRS(copperCoilsID,11);
	      superCoil=(ItemRS)new ItemRS(superCoilID,12);
	      superCoils=(ItemRS)new ItemRS(superCoilsID,13);
	      ohmicHeater=(ItemRS)new ItemRS(ohmicHeaterID,14);
	      neutralHeater=(ItemRS)new ItemRS(neutralHeaterID,15);
	      rfHeater=(ItemRS)new ItemRS(rfHeaterID,16);
	      vacuum=(ItemVacuum)new ItemVacuum(vacuumID,17);
	      handPump=(ItemHandPump)new ItemHandPump(handPumpID,20);
	      ionDrive=(ItemRS)new ItemRS(ionDriveID,21);
	      passengerDepleted=(ItemPassengerDepleted)new ItemPassengerDepleted(passengerDepletedID,22);
	      finder=(ItemAutominerFinder)new ItemAutominerFinder(finderID,23);
	      lithium=(ItemRS)new ItemRS(lithiumID,24);
	      lithiumCell=(ItemRS)new ItemRS(lithiumCellID,25);
	      lithium6Cell=(ItemRS)new ItemRS(lithium6CellID,26);
	      tritiumCell=(ItemRS)new ItemRS(tritiumCellID,27);
	      thermoModule=(ItemRS)new ItemRS(thermoModuleID,5);
	      
	      GameRegistry.registerBlock(booster, MissileItem.class);
	      GameRegistry.registerBlock(warhead, null);
	      GameRegistry.registerBlock(superconductor);
	      GameRegistry.registerBlock(RSmachine, ItemMachineRS.class);
	      GameRegistry.registerBlock(RSgenerator);
	      GameRegistry.registerTileEntity(MissileTileEntity.class, "Missile");
	      GameRegistry.registerTileEntity(TileEntitySuperconductor.class, "Superconductor");
	      GameRegistry.registerTileEntity(TileEntityIsotope.class, "Isotopic Separator");
	      GameRegistry.registerTileEntity(TileEntityFusion.class, "Fusion Reactor");
	      GameRegistry.registerTileEntity(TileEntityAutoMiner.class, "Autominer");
	      GameRegistry.registerTileEntity(TileEntityDefense.class, "Missile Defense");
	      GameRegistry.registerTileEntity(TileEntityOffense.class, "Missile Targeting");
	      GameRegistry.registerTileEntity(TileEntityLaser.class,"Defense Laser");
	      GameRegistry.registerTileEntity(TileEntityRadar.class,"Radar");
	      TileEntityIsotope.initRecipes();
	      
	      LanguageRegistry.addName(boosterModule, "Booster Module");
	      LanguageRegistry.addName(passengerModule, "Passenger Module");
	      LanguageRegistry.addName(parachute, "Parachute");
	      LanguageRegistry.addName(booster, "Missile");
	      LanguageRegistry.addName(warhead, "If you have this block, it's a glitch.");
	      LanguageRegistry.addName(tntModule, "TNT Warhead");
	      LanguageRegistry.addName(incendiaryModule, "Incendiary Warhead");
	      LanguageRegistry.addName(nuclearModule, "Nuclear Warhead");
	      LanguageRegistry.addName(rangefinderItem, "Laser Rangefinder");
	      LanguageRegistry.addName(parachuteArmor, "Parachute Pack");
	      LanguageRegistry.addName(superconductor, "Superconductor");
	      LanguageRegistry.addName(superconductorUncompressed, "Graphene-Gold Lattice");
	      LanguageRegistry.addName(cellDeuterium, "Deuterium Cell");
	      LanguageRegistry.addName(copperCoil, "Copper Loop");
	      LanguageRegistry.addName(copperCoils, "Copper Coils");
	      LanguageRegistry.addName(superCoil, "Superconductor Loop");
	      LanguageRegistry.addName(superCoils, "Superconducting Coils");
	      LanguageRegistry.addName(ohmicHeater, "Ohmic Heating System");
	      LanguageRegistry.addName(neutralHeater, "Neutral-Beam Heating System");
	      LanguageRegistry.addName(rfHeater, "RF Cyclotron Heating System");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 14), "Missile");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 15), "Reusable Passenger Rocket (half charge)");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 0), "Reusable Passenger Rocket");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 4), "Incendiary Missile");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 8), "Nuclear Missile");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 12), "Passenger Rocket");
	      LanguageRegistry.addName(new ItemStack(booster, 1, 13), "Thermonuclear Missile");
	      LanguageRegistry.addName(new ItemStack(RSmachine,1,0), "Isotopic Separator");
	      LanguageRegistry.addName(new ItemStack(RSmachine,1,1), "Mobile Auto-Miner");
	      LanguageRegistry.addName(new ItemStack(RSmachine,1,2), "Missile Defense System");
	      LanguageRegistry.addName(new ItemStack(RSmachine,1,3), "Missile Targeting System");
	      LanguageRegistry.addName(new ItemStack(RSmachine,1,4), "Missile Defense Laser");
	      LanguageRegistry.addName(new ItemStack(RSmachine,1,5), "Radar");
	      LanguageRegistry.addName(new ItemStack(RSgenerator,1,0), "Fusion Reactor");
	      LanguageRegistry.addName(vacuum, "Wet/Dry Vac");
	      LanguageRegistry.addName(handPump,"Hand Pump");
	      LanguageRegistry.addName(ionDrive, "Ion Drive");
	      LanguageRegistry.addName(passengerDepleted, "Discharged Passenger Rocket");
	      LanguageRegistry.addName(finder, "Autominer Location Device");
	      LanguageRegistry.addName(lithium,"Lithium");
	      LanguageRegistry.addName(lithiumCell,"Lithium Cell");
	      LanguageRegistry.addName(lithium6Cell,"Lithium-6 Cell");
	      LanguageRegistry.addName(tritiumCell,"Tritium Cell");
	      LanguageRegistry.addName(thermoModule, "Thermonuclear Warhead");
	      
	      GameRegistry.addRecipe(new ItemStack(boosterModule, 2), new Object[] { "#X#", "#X#", "#X#", Character.valueOf('#'), Ic2Items.refinedIronIngot, Character.valueOf('X'), Ic2Items.coalfuelCell});
	      GameRegistry.addRecipe(new ItemStack(boosterModule, 2), new Object[] { "#X#", "#X#", "#X#", Character.valueOf('#'), Ic2Items.refinedIronIngot, Character.valueOf('X'), Ic2Items.biofuelCell});
	      GameRegistry.addRecipe(new ItemStack(parachute,1), new Object[] {"XXX", "O O", " O ", Character.valueOf('X'), Item.leather,Character.valueOf('O'), Item.silk});
	      GameRegistry.addRecipe(new ItemStack(parachuteArmor,1), new Object[] {"L L", "LPL", "LLL", Character.valueOf('L'), Item.leather,Character.valueOf('P'), parachute});
	      GameRegistry.addRecipe(new ItemStack(passengerModule, 1), new Object[] {"P", "M", Character.valueOf('P'), parachute, Character.valueOf('M'), Item.minecartEmpty});
	      GameRegistry.addRecipe(new ItemStack(tntModule,1), new Object[] {" I ", "ITI", Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('T'),Block.tnt});
	      GameRegistry.addRecipe(new ItemStack(incendiaryModule,1), new Object[] {" I ", "ITI", Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('T'),Ic2Items.lavaCell});
	      GameRegistry.addRecipe(new ItemStack(nuclearModule,1), new Object[] {" I ", "ITI", Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('T'),Ic2Items.nuke});
	      GameRegistry.addRecipe(new ItemStack(booster, 1, 12), new Object[] {"P", "M", Character.valueOf('P'), passengerModule, Character.valueOf('M'), boosterModule});
	      GameRegistry.addRecipe(new ItemStack(booster, 1, 8), new Object[] {"P", "M", Character.valueOf('P'), nuclearModule, Character.valueOf('M'), boosterModule});
	      GameRegistry.addRecipe(new ItemStack(booster, 1, 4), new Object[] {"P", "M", Character.valueOf('P'), incendiaryModule, Character.valueOf('M'), boosterModule});
	      GameRegistry.addRecipe(new ItemStack(booster, 1, 14), new Object[] {"P", "M", Character.valueOf('P'), tntModule, Character.valueOf('M'), boosterModule});
	      GameRegistry.addRecipe(new ItemStack(superconductorUncompressed), new Object[] {" C ", " D ", " G ", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('D'), Item.redstone, Character.valueOf('G'), Item.ingotGold});
	      GameRegistry.addRecipe(new ItemStack(RSmachine,1,0), new Object[] {"ICI", "RER", "IAI", Character.valueOf('E'), Ic2Items.extractor, Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('R'), Item.redstone, Character.valueOf('A'),Ic2Items.advancedMachine, Character.valueOf('C'),Ic2Items.advancedCircuit});
	      GameRegistry.addRecipe(new ItemStack(RSgenerator, 1, 0), new Object[] {"CCC","CAC","CCC", Character.valueOf('C'), Ic2Items.reactorChamber, Character.valueOf('A'), Ic2Items.advancedMachine});
	      GameRegistry.addRecipe(new ItemStack(copperCoil,1),new Object[] {"CCC", "C C", "CCC", Character.valueOf('C'), Ic2Items.copperIngot});
	      GameRegistry.addRecipe(new ItemStack(copperCoils,1),new Object[] {"CCC", "C C", "CCC", Character.valueOf('C'), copperCoil});
	      GameRegistry.addRecipe(new ItemStack(superCoil,1),new Object[] {"CCC", "C C", "CCC", Character.valueOf('C'), superconductor});
	      GameRegistry.addRecipe(new ItemStack(superCoils,1),new Object[] {"CCC", "C C", "CCC", Character.valueOf('C'), superCoil});
	      GameRegistry.addRecipe(new ItemStack(ohmicHeater,1),new Object[] {"WWW", "C W", "WWW", Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('C'), Ic2Items.electronicCircuit});
	      GameRegistry.addRecipe(new ItemStack(rfHeater,1),new Object[] {"F", "A", "M", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('F'), Ic2Items.frequencyTransmitter});
	      GameRegistry.addRecipe(new ItemStack(neutralHeater,1),new Object[] {"AMW"," I ", Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('A'), Ic2Items.advancedCircuit,Character.valueOf('W'), Ic2Items.insulatedCopperCableItem});
	      GameRegistry.addRecipe(new ItemStack(rangefinderItem,1),new Object[] {"III","ICD","III", Character.valueOf('I'), Ic2Items.refinedIronIngot, Character.valueOf('C'), Ic2Items.electronicCircuit, Character.valueOf('D'), Item.diamond});
	      GameRegistry.addRecipe(new ItemStack(vacuum), new Object[] {"R","P","B", Character.valueOf('R'), Ic2Items.rubber, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('P'), Ic2Items.pump});
	      GameRegistry.addRecipe(new ItemStack(handPump), new Object[] {"RB"," C", Character.valueOf('C'), Ic2Items.cell, Character.valueOf('B'), Ic2Items.bronzeIngot, Character.valueOf('R'), Ic2Items.rubber});
	      GameRegistry.addRecipe(new ItemStack(ionDrive), new Object[] {"ACA","ARA","AGA",Character.valueOf('A'),Ic2Items.advancedAlloy,Character.valueOf('C'),new ItemStack(Ic2Items.energyCrystal.getItem(),1,26),Character.valueOf('R'),Ic2Items.advancedCircuit,Character.valueOf('G'),Block.glowStone});
	      GameRegistry.addRecipe(new ItemStack(ionDrive), new Object[] {"ACA","ARA","AGA",Character.valueOf('A'),Ic2Items.advancedAlloy,Character.valueOf('C'),new ItemStack(Ic2Items.energyCrystal.getItem(),1,27),Character.valueOf('R'),Ic2Items.advancedCircuit,Character.valueOf('G'),Block.glowStone});
	      GameRegistry.addRecipe(new ItemStack(passengerDepleted,1,10001), new Object[] {"P","I",Character.valueOf('P'),passengerModule,Character.valueOf('I'),ionDrive});
	      GameRegistry.addRecipe(new ItemStack(RSmachine,1,1), new Object[] {"CTC","AMA","I I",Character.valueOf('C'),Ic2Items.advancedCircuit,Character.valueOf('T'),Block.chest,Character.valueOf('A'),Ic2Items.advancedMachine,Character.valueOf('M'),Ic2Items.miner,Character.valueOf('I'),ionDrive});
	      GameRegistry.addRecipe(new ItemStack(thermoModule), new Object[] {" T ","DND"," T ",Character.valueOf('T'), tritiumCell, Character.valueOf('D'), cellDeuterium, Character.valueOf('N'), nuclearModule});
	      GameRegistry.addRecipe(new ItemStack(thermoModule), new Object[] {" D ","TNT"," D ",Character.valueOf('T'), tritiumCell, Character.valueOf('D'), cellDeuterium, Character.valueOf('N'), nuclearModule});
	      GameRegistry.addRecipe(new ItemStack(booster,1,13), new Object[] {"P","M",Character.valueOf('P'),thermoModule,Character.valueOf('M'),boosterModule});
	      GameRegistry.addRecipe(new ItemStack(RSmachine,1,2), new Object[] {"GAG","RMR","GCG", Character.valueOf('G'),Block.thinGlass, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('R'), Item.redstone, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit});
	      GameRegistry.addRecipe(new ItemStack(RSmachine,1,3), new Object[] {"GCG","RMR","GAG", Character.valueOf('G'),Block.thinGlass, Character.valueOf('A'), Ic2Items.advancedCircuit, Character.valueOf('R'), Item.redstone, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.electronicCircuit});
	      GameRegistry.addRecipe(new ItemStack(RSmachine,1,4), new Object[] {" E ","GCG","AMA", Character.valueOf('E'),new ItemStack(Ic2Items.energyCrystal.getItem(),1,26),Character.valueOf('G'),Item.lightStoneDust, Character.valueOf('C'), Ic2Items.advancedCircuit, Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('M'), Ic2Items.advancedMachine});
	      GameRegistry.addRecipe(new ItemStack(RSmachine,1,5), new Object[] {"IFI"," I "," M ", Character.valueOf('I'),Ic2Items.refinedIronIngot,Character.valueOf('F'),Ic2Items.frequencyTransmitter,Character.valueOf('M'), Ic2Items.machine});
	      GameRegistry.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.copperIngot, 64), new Object[] {copperCoils});
	      GameRegistry.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.copperIngot, 8), new Object[] {copperCoil});
	      GameRegistry.addShapelessRecipe(new ItemStack(superconductor,64), new Object[] {superCoils});
	      GameRegistry.addShapelessRecipe(new ItemStack(superconductor,8), new Object[] {superCoil});
	      GameRegistry.addShapelessRecipe(new ItemStack(finder,1), new Object[] {Ic2Items.frequencyTransmitter, new ItemStack(Item.dyePowder,1,1)});
	      GameRegistry.addShapelessRecipe(new ItemStack(lithiumCell,1),new Object[] {Ic2Items.cell, lithium});
	      
	      GameRegistry.registerEntityID(MissileBoosterEntity.class, "Missile", GameRegistry.getUniqueEntityId());
	      GameRegistry.registerEntityID(MissileWarheadEntity.class, "Warhead", GameRegistry.getUniqueEntityId());
	      GameRegistry.registerEntityID(MissilePassengerBoosterEntity.class, "Passenger Missile", GameRegistry.getUniqueEntityId());
	      GameRegistry.registerEntityID(MissilePassengerWarheadEntity.class, "Passenger Warhead", GameRegistry.getUniqueEntityId());
	      GameRegistry.registerEntityID(RangefinderEntity.class, "Rangefinder", GameRegistry.getUniqueEntityId());
	      GameRegistry.registerEntityID(MissileMinerBoosterEntity.class, "Miner", GameRegistry.getUniqueEntityId());
	      GameRegistry.registerEntityID(EntityDefenseLaser.class,"Defense Laser", GameRegistry.getUniqueEntityId());
	      
	      Ic2Recipes.addCompressorRecipe(new ItemStack(superconductorUncompressed), new ItemStack(superconductor));
	      Ic2Recipes.addExtractorRecipe(new ItemStack(Item.clay), new ItemStack(lithium));
	      
	      GameRegistry.setInGameHook(instance,true,false);

	}
	
	public String Version()
	  {
	    return "1.1.0";
	  }
	
	public static void func_22022_g21240_saveOptions()
	  {
	    try
	    {
	      optionsFile.createNewFile();
	      PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
	      printwriter.println("saveFileVersion:1.1.0");
	      printwriter.println("boosterBlockID:" + boosterID);
	      printwriter.println("warheadBlockID:" + warheadID);
	      printwriter.println("machineID:" + machineID);
	      printwriter.println("generatorID:"+generatorID);
	      printwriter.println("boosterItemID:" + boosterModuleID);
	      printwriter.println("passengerItemID:" + passengerModuleID);
	      printwriter.println("tntWarheadItemID:" + tntModuleID);
	      printwriter.println("incendiaryWarheadItemID:" + incendiaryModuleID);
	      printwriter.println("nuclearWarheadItemID:" + nuclearModuleID);
	      printwriter.println("parachuteItemID:" + parachuteID);
	      printwriter.println("rangefinderItemID:"+rangefinderItemID);
	      printwriter.println("parachuteArmorID:"+parachuteArmorID);
	      printwriter.println("superconductorID:"+superconductorID);
	      printwriter.println("superconductorUncompressedID:"+superconductorUncompressedID);
	      printwriter.println("cellDeuteriumID:"+deuteriumID);
	      printwriter.println("copperCoilID:"+copperCoilID);
	      printwriter.println("copperCoilsID:"+copperCoilsID);
	      printwriter.println("superCoilID:"+superCoilID);
	      printwriter.println("superCoilsID:"+superCoilsID);
	      printwriter.println("ohmicHeaterID:"+ohmicHeaterID);
	      printwriter.println("rfHeaterID:"+rfHeaterID);
	      printwriter.println("neutralHeaterID:"+neutralHeaterID);
	      printwriter.println("vacuumID:"+vacuumID);
	      printwriter.println("handPumpID:"+handPumpID);
	      printwriter.println("ionDriveID:"+ionDriveID);
	      printwriter.println("passengerDepletedID:"+passengerDepletedID);
	      printwriter.println("finderID:"+finderID);
	      printwriter.println("lithiumID:"+lithiumID);
	      printwriter.println("lithiumCellID:"+lithiumCellID);
	      printwriter.println("lithium6CellID:"+lithium6CellID);
	      printwriter.println("tritiumCellID:"+tritiumCellID);
	      printwriter.println("thermoModuleID:"+thermoModuleID);
	      printwriter.close();
	    }
	    catch (Exception exception)
	    {
	      System.out.println("Failed to save options");
	      exception.printStackTrace();
	    }
	  }

	  public static void func_22024_func_21238_readOptions()
	  {
	    try
	    {
	      if (!optionsFile.exists())
	      {
	        func_22022_g21240_saveOptions();
	      }
	      BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
	      //String s = "";
	      /*if (!func_22023_func_21239_checkVersion(bufferedreader.readLine()))
	      {
	        func_22022_g21240_saveOptions();
	      }*/
	      String s1;
	      int num;
	      while ((s1 = bufferedreader.readLine()) != null)
	      {
	        String[] as = s1.split(":");
	        if (as[0].equals("boosterBlockID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	boosterID = num;
	        }
	        else if (as[0].equals("warheadBlockID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	warheadID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("generatorID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	generatorID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("machineID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	machineID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("parachuteItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	parachuteID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("boosterItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	boosterModuleID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("passengerItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	passengerModuleID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("tntWarheadItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	tntModuleID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("incendiaryWarheadItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	incendiaryModuleID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("nuclearWarheadItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	nuclearModuleID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("rangefinderItemID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	rangefinderItemID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("parachuteArmorID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	parachuteArmorID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("superconductorID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	superconductorID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("superconductorUncompressedID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	superconductorUncompressedID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("cellDeuteriumID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	deuteriumID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("copperCoilID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	copperCoilID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("copperCoilsID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	copperCoilsID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("superCoilID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	superCoilID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("superCoilsID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	superCoilsID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("ohmicHeaterID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	ohmicHeaterID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("rfHeaterID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	rfHeaterID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("neutralHeaterID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	neutralHeaterID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("vacuumID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	vacuumID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("handPumpID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	handPumpID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("ionDriveID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	ionDriveID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("passengerDepletedID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	passengerDepletedID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("finderID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	finderID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("lithiumID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	lithiumID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("lithiumCellID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	lithiumCellID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("lithium6CellID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	lithium6CellID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("tritiumCellID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	tritiumCellID = Integer.parseInt(as[1]);
	        }
	        else if (as[0].equals("thermoModuleID"))
	        {
	        	num=Integer.parseInt(as[1]);
	        	thermoModuleID = Integer.parseInt(as[1]);
	        }
	      }
	      bufferedreader.close();
	    }
	    catch (IOException exception)
	    {
	      System.out.println("Rocket Science: Failed to load options");
	      exception.printStackTrace();
	    }
	  }

	  public static boolean func_22023_func_21239_checkVersion(String s)
	  {
	    String[] as = s.split(":");
	    return (as[0].equals("saveFileVersion")) && (as[1].equals("1.2.5"));
	  }
	  
	  public void addRenderer(Map map)
	  {
			 map.put(MissileBoosterEntity.class, new MissileRender(1));
			 map.put(MissileWarheadEntity.class, new MissileWarheadRender(1));
			 map.put(MissilePassengerBoosterEntity.class, new MissilePassengerRender(1));
			 map.put(MissilePassengerWarheadEntity.class, new MissilePassengerWarheadRender(1));
			 map.put(ParachuteEntity.class, new ParachuteRender(1));
			 map.put(MissileMinerBoosterEntity.class, new MissileMinerBoosterRender(1));
			 map.put(EntityDefenseLaser.class, new RenderDefenseLaser());
	  }
	  
	  private boolean RenderMissileInWorld(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block) {
		   block.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, .9375F);
		   renderer.renderStandardBlock(block, x, y, z);
		   block.setBlockBounds(0.4375F, 0.75F, 0.0625F, 0.5625F, 0F, 0F);
		   renderer.renderStandardBlock(block, x, y, z);
		   block.setBlockBounds(0.4375F, 0.75F, 1.0F, 0.5625F, 0F, 0.9375F);
		   renderer.renderStandardBlock(block, x, y, z);
		   block.setBlockBounds(1.0F, 0.75F, 0.4375F, 0.9375F, 0F, 0.5625F);
		   renderer.renderStandardBlock(block, x, y, z);
		   block.setBlockBounds(.0625F, 0.75F, 0.4375F, 0F, 0F, 0.5625F);
		   renderer.renderStandardBlock(block, x, y, z);
		   
		   block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		   return true;
		  }
	  
	  private boolean RenderWarheadInWorld(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block) {
		   if((world.getBlockMetadata(x,y,z)!=12)&&(world.getBlockMetadata(x,y,z)!=0)&&(world.getBlockMetadata(x,y,z)!=15))
		   {
			   block.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.7F, .9F);
			   renderer.renderStandardBlock(block, x, y, z);
			   block.setBlockBounds(0.2F, 0.7F, 0.2F, 0.8F, 0.9F, 0.8F);
			   renderer.renderStandardBlock(block, x, y, z);
			   block.setBlockBounds(0.3F, 0.9F, 0.3F, 0.7F, 1F, 0.7F);
			   renderer.renderStandardBlock(block, x, y, z);
		   }
		   else
		   {
			   block.setBlockBounds(0.0F, 0.0F, 0F, 1F, 0.1F, 1F);
			   renderer.renderStandardBlock(block, x, y, z);
			   block.setBlockBounds(0F, 0.1F, 0F, 1F, 1F, .1F);
			   renderer.renderStandardBlock(block, x, y, z);
			   block.setBlockBounds(0F, 0.1F, 0.9F, 1F, 1F, 1F);
			   renderer.renderStandardBlock(block, x, y, z);
			   block.setBlockBounds(0F, 0.1F, 0.1F, 0.1F, 1F, 0.9F);
			   renderer.renderStandardBlock(block, x, y, z);
			   block.setBlockBounds(0.9F, 0.1F, 0.1F, 1F, 1F, .9F);
			   renderer.renderStandardBlock(block, x, y, z);
		   }
		   block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		   return true;
		  }
	  
	  private boolean RenderMachineInWorld(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block) {
		  if(world.getBlockMetadata(x,y,z)<4)
		   {
			  block.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
			  renderer.renderStandardBlock(block,x,y,z);
		   }
		  else if(world.getBlockMetadata(x, y, z)==4)
		  {
			  block.setBlockBounds(0,0,0,1,0.5f,1);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0.25f,0.5f,0.25f,0.75f,0.625f,0.75f);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0.625f,0.625f,0.625f,0.375f,1,0.375f);
			  renderer.renderStandardBlock(block, x, y, z);
		  }
		  else if(world.getBlockMetadata(x, y, z)==5)
		  {
			  block.setBlockBounds(0,0,0,1,0.125f,1);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0.625f,.125f,0.625f,0.375f,0.375f,0.375f);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0,0.375f,0,1,0.5f,1);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0,0.5f,0.875f,1,0.875f,1);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0,0.5f,0,1,0.875f,0.125f);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0,0.5f,0.875f,1,0.875f,1);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0,0.5f,0.125f,0.125f,0.875f,0.875f);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0.875f,0.5f,0.125f,1,0.875f,0.875f);
			  renderer.renderStandardBlock(block, x, y, z);
			  block.setBlockBounds(0.375f,0.5f,0.375f,0.625f,1,0.625f);
			  renderer.renderStandardBlock(block, x, y, z);
		  }
		  return true;
	  }
	  
	  private void RenderMachineInInv(RenderBlocks renderer, Block block, int metadata)
  	  {
		  Tessellator tesselator = Tessellator.instance;
		  block.setBlockBounds(0,0,0,1,1,1);
		  GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	      tesselator.startDrawingQuads();
	      tesselator.setNormal(0.0F, -1.0F, 0.0F);
	      renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0,metadata));
	      tesselator.draw();
	      tesselator.startDrawingQuads();
	      tesselator.setNormal(0.0F, 1.0F, 0.0F);
	      renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1,metadata));
	      tesselator.draw();
	      tesselator.startDrawingQuads();
	      tesselator.setNormal(0.0F, 0.0F, -1.0F);
	      renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2,metadata));
	      tesselator.draw();
	      tesselator.startDrawingQuads();
	      tesselator.setNormal(0.0F, 0.0F, 1.0F);
	      renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3,metadata));
	      tesselator.draw();
	      tesselator.startDrawingQuads();
	      tesselator.setNormal(-1.0F, 0.0F, 0.0F);
	      renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4,metadata));
	      tesselator.draw();
	      tesselator.startDrawingQuads();
	      tesselator.setNormal(1.0F, 0.0F, 0.0F);
	      renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5,metadata));
	      tesselator.draw();
	      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
  	  }

	  	  private void RenderMissileInInv(RenderBlocks renderer, Block block, int metadata)
	  	  {
	  		Tessellator tesselator = Tessellator.instance;
	  		int sideTex=0;
	  		int topTex=0;
		    for (int i = 0; i < 6; i++) {
		    	//Get bounds for each rectangle
			  if (i == 0) block.setBlockBounds(0.4F, 0.95F, 0.4F, 0.6F, 1.0F, 0.6F);
		      else if (i == 1) block.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.95F, 0.7F);
		      else if (i == 2) block.setBlockBounds(0.4F, 0F, 0.1F, 0.6F, 0.4F, 0.3F);
		      else if (i == 3) block.setBlockBounds(0.4F, 0F, 0.7F, 0.6F, 0.4F, 0.9F);
		      else if (i == 4) block.setBlockBounds(0.1F, 0F, 0.4F, 0.3F, 0.4F, 0.6F);
		      else if (i == 5) block.setBlockBounds(0.7F, 0F, 0.4F, 0.9F, 0.4F, 0.6F);
		      	//Get textures
		      if(i==0&&metadata!=0&&metadata!=15)
		      {
		    	  topTex=15;
		    	  sideTex=block.getBlockTextureFromSideAndMetadata(1,metadata);
		      }
		      else if(i==0)
		      {
		    	  topTex=18;
		    	  sideTex=block.getBlockTextureFromSideAndMetadata(1,metadata);
		      }
		      else if (i==1&&metadata!=0&&metadata!=15)
		      {
		    	  sideTex=block.getBlockTextureFromSideAndMetadata(1, 1);
		    	  topTex=block.getBlockTextureFromSideAndMetadata(1, 1);
		      }
		      else if (i==1)
		      {
		    	  sideTex=block.getBlockTextureFromSideAndMetadata(1, metadata);
		    	  topTex=block.getBlockTextureFromSideAndMetadata(1, metadata);
		      }
		      else if(metadata!=0&&metadata!=15)
		      {
		    	  sideTex=topTex=15;
		      }
		      else
		      {
		    	  sideTex=topTex=18;
		      }
		      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		      tesselator.startDrawingQuads();
		      tesselator.setNormal(0.0F, -1.0F, 0.0F);
		      renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, sideTex);
		      tesselator.draw();
		      tesselator.startDrawingQuads();
		      tesselator.setNormal(0.0F, 1.0F, 0.0F);
		      renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, topTex);
		      tesselator.draw();
		      tesselator.startDrawingQuads();
		      tesselator.setNormal(0.0F, 0.0F, -1.0F);
		      renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, sideTex);
		      tesselator.draw();
		      tesselator.startDrawingQuads();
		      tesselator.setNormal(0.0F, 0.0F, 1.0F);
		      renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, sideTex);
		      tesselator.draw();
		      tesselator.startDrawingQuads();
		      tesselator.setNormal(-1.0F, 0.0F, 0.0F);
		      renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, sideTex);
		      tesselator.draw();
		      tesselator.startDrawingQuads();
		      tesselator.setNormal(1.0F, 0.0F, 0.0F);
		      renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, sideTex);
		      tesselator.draw();
		      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		    }
		    block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	  	  }
	  
	  	  @Override
		  public void renderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID)
		  {
		    if (modelID == missileModelID)
		      RenderMissileInInv(renderer, block, metadata);
		    else if(modelID==warheadModelID)
		    	RenderMissileInInv(renderer,block,metadata);
		    else if(modelID==machineModelID)
		    	RenderMachineInInv(renderer,block,metadata);
		  }

		  @Override
		  public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
		  {
			  GameRegistry.getLogger().fine("Rocket Science: Rendering missile blocks");
		    if (l == missileModelID){
		    	GameRegistry.getLogger().fine("Rocket Science: Rendering missile blocks");
		      return RenderMissileInWorld(renderblocks, iblockaccess, i, j, k, block);
		    }
		    else if (l == warheadModelID){
		    	GameRegistry.getLogger().fine("Rocket Science: Rendering warhead blocks");
			      return RenderWarheadInWorld(renderblocks, iblockaccess, i, j, k, block);
		    }else if (l == machineModelID){
		    	GameRegistry.getLogger().fine("Rocket Science: Rendering machiene blocks");
		    	return RenderMachineInWorld(renderblocks, iblockaccess, i, j, k, block);
		    }
		    	return false;
		  }
	
	public GuiScreen HandleGUI(int i)
    {
        net.minecraft.src.EntityPlayerSP entityplayersp = ModLoader.getMinecraftInstance().thePlayer;
        if(entityplayersp == null)
        {
            return null;
        } else
        {
            return getGuiForId(entityplayersp, i, null);
        }
    }

    public static GuiScreen getGuiForId(EntityPlayer entityplayer, int i, TileEntity tileentity)
    {
    	if(i==mod_RocketScience.guiIsotopeID)
    		return new GUISeparator(entityplayer.inventory, tileentity == null ? new TileEntityIsotope() : (TileEntityIsotope)tileentity);
    	else if(i==mod_RocketScience.guiFusionID)
    		return new GUIFusion(entityplayer.inventory, tileentity == null ? new TileEntityFusion() : (TileEntityFusion)tileentity);
    	else if(i==mod_RocketScience.guiAutoMinerID)
    		return new GUIAutoMiner(entityplayer.inventory, tileentity == null ? new TileEntityAutoMiner() : (TileEntityAutoMiner)tileentity);
    	else if(i==mod_RocketScience.guiDefenseID)
    		return new GUIDefense(entityplayer.inventory, tileentity== null ? new TileEntityDefense() : (TileEntityDefense)tileentity);
    	else if(i==mod_RocketScience.guiOffenseID)
    		return new GUIOffense(entityplayer.inventory, tileentity==null ? new TileEntityOffense() : (TileEntityOffense)tileentity);
    	return null;
    }
	
	static 
    {
        MinecraftForgeClient.preloadTexture("/rocketScience/blocks.png");
        MinecraftForgeClient.preloadTexture("/rocketScience/items.png");
        MinecraftForgeClient.preloadTexture("/rocketScience/MissileModel.png");
    }

	public void init() {
		if(needsInit){
		  missileModelID = ModLoader.getUniqueBlockModelID(mod_RocketScience.instance, true);
		  warheadModelID = ModLoader.getUniqueBlockModelID(mod_RocketScience.instance, true);
		  machineModelID = ModLoader.getUniqueBlockModelID(mod_RocketScience.instance, true);
		  mod_RocketScience.initializeMod();
		  needsInit=false;
		}
	}
	
    public boolean serverSideRequired()
    {
        return true;
    }
    
    public boolean clientSideRequired()
    {
        return true;
    }

	@Override
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modid() {
		return "Because Rockets are Cool!";
	}

	@Override
	public String name() {
		return "Rocket Science";
	}

	@Override
	public String version() {
		return "2.0!";
	}

	@Override
	public String dependencies() {
		return null;
	}

	@Override
	public boolean useMetadata() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void load() {
		
	}
    

}
