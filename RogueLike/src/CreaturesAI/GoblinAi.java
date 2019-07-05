package CreaturesAI;

import Elements.Creature;

public class GoblinAi extends CreatureAi {

	private Creature player;

	public GoblinAi(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}

	public void onUpdate(){ //TODO comrpobar aqui que le esta pasando al goblin que se queda tieso
		if (canUseBetterEquipment())
			useBetterEquipment();
		else if (canRangedWeaponAttack(player))
			creature.rangedWeaponAttack(player);
		else if (canThrowAt(player))
			creature.throwItem(getWeaponToThrow(), player.getX(), player.getY(), player.getZ());
		else if (creature.canSee(player.getX(), player.getY(), player.getZ()))
			hunt(player);
		else if (canPickup())
			creature.pickup();
		else
			wander();
	}
}
