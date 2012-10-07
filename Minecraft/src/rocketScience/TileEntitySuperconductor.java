package rocketScience;

import ic2.api.*;
import ic2.api.Direction;
import java.util.*;
import net.minecraft.src.*;

//Referenced classes of package ic2.common:
//         TileEntityBlock, INetworkTileEntityEventListener, EnergyNet

public class TileEntitySuperconductor extends TileEntity implements IEnergyConductor
{

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return true;
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		return true;
	}

	@Override
	public double getConductionLoss() {
		return 0;
	}

	@Override
	public int getInsulationEnergyAbsorption() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getInsulationBreakdownEnergy() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public int getConductorBreakdownEnergy() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void removeInsulation() {
		
	}

	@Override
	public void removeConductor() {
		//TODO
		
	}


}
