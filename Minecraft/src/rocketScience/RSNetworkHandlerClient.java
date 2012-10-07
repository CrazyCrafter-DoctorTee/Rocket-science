package rocketScience;

import ic2.api.INetworkItemEventListener;
import ic2.api.INetworkTileEntityEventListener;
import ic2.api.INetworkUpdateListener;
import ic2.common.DataEncoder;
import ic2.common.IHandHeldInventory;
import ic2.common.IHasGui;
import ic2.platform.Platform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.IPacketHandler;

public class RSNetworkHandlerClient implements IPacketHandler {

	@Override
	public void onPacketData(NetworkManager network, String channel, byte[] data) {
		 ByteArrayInputStream isRaw = new ByteArrayInputStream(data, 1, data.length - 1);
	      try {
	         DataInputStream inputForUpdateTE;
	         int currentItemPosition;
	         int heightForUpdate;
	         int y;
	         int x;
	         int z;
	         int worldType;
	         TileEntity tileEntityToUpdate;
	         switch(data[0]) {
	         case 0:
	            GZIPInputStream GZIPIn = new GZIPInputStream(isRaw, data.length - 1);
	            DataInputStream inputStream = new DataInputStream(GZIPIn);
	            currentItemPosition = inputStream.readInt();
	            World worldClient = ModLoader.getMinecraftInstance().theWorld;
	            if(worldClient.worldProvider.worldType != currentItemPosition) {
	               return;
	            }

	            while(true) {
	               try {
	                  x = inputStream.readInt();
	               } catch (EOFException var21) {
	                  inputStream.close();
	                  return;
	               }

	               y = inputStream.readInt();
	               z = inputStream.readInt();
	               tileEntityToUpdate = worldClient.getBlockTileEntity(x, y, z);
	               short shortStream = inputStream.readShort();
	               char[] characterArrayForFieldName = new char[shortStream];

	               for(int shortStreamItiterator = 0; shortStreamItiterator < shortStream; ++shortStreamItiterator) {
	                  characterArrayForFieldName[shortStreamItiterator] = inputStream.readChar();
	               }

	               String fieldNameForTileEntity = new String(characterArrayForFieldName);
	               Field field = null;

	               try {
	                  if(tileEntityToUpdate != null) {
	                     Class tileEntityClass = tileEntityToUpdate.getClass();
	                     do {
	                        try {
	                           field = tileEntityClass.getDeclaredField(fieldNameForTileEntity);
	                        } catch (NoSuchFieldException var19) {
	                           tileEntityClass = tileEntityClass.getSuperclass();
	                        }
	                     } while(field == null && tileEntityClass != null);

	                     if(field == null) {
	                        System.out.println("[RS] can\'t find field " + fieldNameForTileEntity + " in tile entity " + tileEntityToUpdate + " at " + x + "/" + y + "/" + z);
	                     } else {
	                        field.setAccessible(true);
	                     }
	                  }

	                  Object objectFromDecoder = DataEncoder.decode(inputStream);
	                  if(field != null && tileEntityToUpdate != null) {
	                     field.set(tileEntityToUpdate, objectFromDecoder);
	                  }
	               } catch (Exception ex) {
	                  throw new RuntimeException(ex);
	               }

	               if(tileEntityToUpdate instanceof INetworkUpdateListener) {
	                  ((INetworkUpdateListener)tileEntityToUpdate).onNetworkUpdate(fieldNameForTileEntity);
	               }
	            }
	         case 1:
	            inputForUpdateTE = new DataInputStream(isRaw);
	            worldType = inputForUpdateTE.readInt();
	            currentItemPosition = inputForUpdateTE.readInt();
	            heightForUpdate = inputForUpdateTE.readInt();
	            x = inputForUpdateTE.readInt();
	            y = inputForUpdateTE.readInt();
	            World theWorldForTEUpdate = ModLoader.getMinecraftInstance().theWorld;
	            if(theWorldForTEUpdate.worldProvider.worldType != worldType) {
	               return;
	            }

	            tileEntityToUpdate = theWorldForTEUpdate.getBlockTileEntity(currentItemPosition, heightForUpdate, x);
	            if(tileEntityToUpdate instanceof INetworkTileEntityEventListener) {
	               ((INetworkTileEntityEventListener)tileEntityToUpdate).onNetworkEvent(y);
	            }
	            break;
	         case 2:
	            inputForUpdateTE = new DataInputStream(isRaw);
	            byte byteForCharacter = inputForUpdateTE.readByte();
	            char[] charArrayForTEUpdate = new char[byteForCharacter];

	            for(heightForUpdate = 0; heightForUpdate < byteForCharacter; ++heightForUpdate) {
	               charArrayForTEUpdate[heightForUpdate] = inputForUpdateTE.readChar();
	            }

	            String stringFromTEUpdateCharArray = new String(charArrayForTEUpdate);
	            x = inputForUpdateTE.readInt();
	            y = inputForUpdateTE.readInt();
	            z = inputForUpdateTE.readInt();
	            worldClient = ModLoader.getMinecraftInstance().theWorld;
	            Iterator iteratorForPlayers = worldClient.playerEntities.iterator();

	            EntityPlayer entityPlayer1;
	            do {
	               if(!iteratorForPlayers.hasNext()) {
	                  return;
	               }

	               Object obj = iteratorForPlayers.next();
	               entityPlayer1 = (EntityPlayer)obj;
	            } while(!entityPlayer1.username.equals(stringFromTEUpdateCharArray));

	            Item item = Item.itemsList[x];
	            if(item instanceof INetworkItemEventListener) {
	               ((INetworkItemEventListener)item).onNetworkEvent(y, entityPlayer1, z);
	            }
	            break;
	         case 3:
	            inputForUpdateTE = new DataInputStream(isRaw);
	            worldType = inputForUpdateTE.readInt();
	            currentItemPosition = inputForUpdateTE.readInt();
	            heightForUpdate = inputForUpdateTE.readInt();
	            x = inputForUpdateTE.readInt();
	            World var30 = ModLoader.getMinecraftInstance().theWorld;
	            if(var30.worldProvider.worldType != worldType) {
	               return;
	            }

	            var30.markBlockNeedsUpdate(currentItemPosition, heightForUpdate, x);
	            break;
	         case 4:
	            inputForUpdateTE = new DataInputStream(isRaw);
	            EntityPlayer entityPlayer = Platform.getPlayerInstance();
	            switch(inputForUpdateTE.readByte()) {
	            case 0:
	               currentItemPosition = inputForUpdateTE.readInt();
	               heightForUpdate = inputForUpdateTE.readInt();
	               x = inputForUpdateTE.readInt();
	               y = inputForUpdateTE.readInt();
	               z = inputForUpdateTE.readInt();
	               worldClient = ModLoader.getMinecraftInstance().theWorld;
	               if(worldClient.worldProvider.worldType != currentItemPosition) {
	                  return;
	               }

	               TileEntity te = worldClient.getBlockTileEntity(heightForUpdate, x, y);
	               if(te instanceof IHasGui) {
	                  Platform.launchGui(entityPlayer, (IHasGui)te);
	               }

	               entityPlayer.craftingInventory.windowId = z;
	               break;
	            case 1:
	               currentItemPosition = inputForUpdateTE.readInt();
	               heightForUpdate = inputForUpdateTE.readInt();
	               if(currentItemPosition != entityPlayer.inventory.currentItem) {
	                  return;
	               }

	               ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
	               if(currentItem != null && currentItem.getItem() instanceof IHandHeldInventory) {
	                  Platform.launchGui(entityPlayer, ((IHandHeldInventory)currentItem.getItem()).getInventory(entityPlayer, currentItem));
	               }

	               entityPlayer.craftingInventory.windowId = heightForUpdate;
	            }
	         }
	      } catch (IOException ex) {
	         ex.printStackTrace();
	      }

	   }
	}

