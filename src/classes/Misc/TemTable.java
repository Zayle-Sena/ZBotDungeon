package classes.Misc;

import java.awt.Color;
import java.util.Random;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

/**
 *
 * @author PC
 */
public class TemTable {
    EmbedBuilder builder = new EmbedBuilder();
    Random RNG = new Random();
    
    public TemTable(){
    }
    
    public MessageEmbed getOne() {
        builder = new EmbedBuilder();
        float colour[] = {0,0,0};

        switch (RNG.nextInt(10)) {
            case 0:
                builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Acid_Splash%22");
                builder.addField("Acid Splash", "You hurl a bubble of acid."
                        + "\nChoose up to two creatures within range that are within 5 feet of each other."
                        + "\nA target takes 1d6 Acid damage on a failed Dex save.", true);
                builder.addField("At Higher Levels", "The damage increases by 1d6 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(0, 150, 75, colour);
                break;
            case 1:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Chill_Touch");
                builder.addField("Chill Touch", "You create a ghostly, skeletal hand in the space of a creature within range."
						+ "\nMake a ranged spell attack including spell attack bonus, against the creature to assail it with the chill of the grave."
						+ "\nOn a hit, the target takes 1d8 necrotic damage, and it can't regain hit points until the start of your next turn.Until then, the hand clings to the target."
						+ "\n\nIf you hit an undead target, it also has disadvantage on attack rolls against you until the end of your next turn.", true);
                builder.addField("At Higher Levels", "This spell's damage increases by 1d8 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(233, 220, 201, colour);
                break;
            case 2:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Fire_Bolt");
                builder.addField("Fire bolt", "You hurl a mote of fire at a creature or object within range."
						+ "\nMake a ranged spell Attack against the target. On a hit, the target takes 1d10 fire damage."
						+ "\nA flammable object hit by this spell ignites if it isn't being worn or carried.", true);
                builder.addField("At Higher Levels", "This spell's damage increases by 1d10 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(255, 61, 0, colour);
                break;
            case 3:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Light");
                builder.addField("Light", "You touch one object that is no larger than 10 feet in any dimension."
						+ "\nUntil the spell ends, the object sheds bright light in a 20-foot radius and dim light for an additional 20 feet. The light can be colored as you like."
						+ "\nCompletely covering the object with something opaque blocks the light. The spell ends if you cast it again or dismiss it as an action."
						+ "\n\nIf you target an object held or worn by a hostile creature, that creature must succeed on a Dexterity saving throw to avoid the spell.", true);
                colour = Color.RGBtoHSB(251, 243, 23, colour);
                break;
            case 4:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Poison_Spray");
                builder.addField("Poison Spray", "You extend your hand toward a creature you can see within range and project a puff of noxious gas from your palm."
						+ "\nThe creature must succeed on a Constitution saving throw or take 1d12 poison damage.", true);
                builder.addField("At Higher Levels", "This spell's damage increases by 1d12 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(0, 150, 75, colour);
                break;
            case 5:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Ray_of_Frost");
                builder.addField("Ray of Frost", "A frigid beam of blue-white light streaks toward a creature within range. Make a ranged spell Attack against the target."
						+ "\n On a hit, it takes 1d8 cold damage, and its speed is reduced by 10 feet until the start of your next turn.", true);
                builder.addField("At Higher Levels", "This spell's damage increases by 1d8 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(66, 255, 247, colour);
                break;
            case 6:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Shocking_Grasp");
                builder.addField("Shocking Grasp", "Lightning springs from your hand to deliver a shock to a creature you try to touch."
						+ "\nMake a melee spell Attack against the target. You have advantage on the Attack roll if the target is wearing armor made of metal."
						+ "\nOn a hit, the target takes 1d8 lightning damage, and it can't take reactions until the start of its next turn.", true);
                builder.addField("At Higher Levels", "This spell's damage increases by 1d8 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(236, 252, 255, colour);
                break;
            case 7:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Sacred_Flame");
                builder.addField("Sacred Flame", "Flame-like radiance descends on a creature that you can see within range."
						+ "\nThe target must succeed on a Dexterity saving throw or take 1d8 radiant damage. The target gains no benefit from cover for this saving throw.", true);
                builder.addField("At Higher Levels", "This spell’s damage increases by 1d8 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(255, 61, 0, colour);
                break;
            case 8:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Thorn_Whip");
                builder.addField("Thorn Whip", "You create a long, vine-like whip covered in thorns that lashes out at your command toward a creature in range."
						+ "\nMake a melee spell attack against the target. If the attack hits, the creature takes 1d6 piercing damage, and if the creature is Large or smaller, you pull the creature up to 10 feet closer to you.", true);
                builder.addField("At Higher Levels", "This spell’s damage increases by 1d6 at levels 5, 11, and 17.", true);
                colour = Color.RGBtoHSB(81, 135, 93, colour);
                break;
            case 9:
		return reroll();

        }

        builder.setColor(Color.getHSBColor(colour[0], colour[1], colour[2]));
        return builder.build();
    }
	
public MessageEmbed getTwo() {
        builder = new EmbedBuilder();
        float colour[] = {0,0,0};

        switch (RNG.nextInt(10)) {
            case 0:
                builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Burning_Hands");
                builder.addField("Burning Hands", "As you hold your hands with thumbs touching and fingers spread, a thin sheet of flames shoots forth from your outstretched fingertips."
						+ "\nEach creature in a 15-foot cone must make a Dexterity saving throw. A creature takes 3d6 fire damage on a failed save, or half as much damage on a successful one."
						+ "\nThe fire ignites any flammable objects in the area that aren’t being worn or carried.", true);
                colour = Color.RGBtoHSB(255, 61, 0, colour);
                break;
            case 1:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Chromatic_Orb");
                builder.addField("Chromatic Orb", "You hurl a 4-inch-diameter sphere of energy at a creature that you can see within range."
						+ "\nYou choose acid, cold, fire, lightning, poison, or thunder for the type of orb you create, and then make a ranged spell attack against the target."
						+ "\nIf the attack hits, the creature takes 3d8 damage of the type you chose.", true);
                colour = Color.RGBtoHSB(RNG.nextInt(256), RNG.nextInt(256), RNG.nextInt(256), colour);
                break;
            case 2:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Color_Spray");
                builder.addField("Colour Spray", "A dazzling array of flashing, colored light springs from your hand."
						+ "\nRoll 6d10; the total is how many hit points of creatures this spell can affect."
						+ "\nCreatures in a 15-foot cone originating from you are affected in ascending order of their current hit points(ignoring unconscious creatures and creatures that can't see)."
						+ "\n\nStarting with the creature that has the lowest current hit points, each creature affected by this spell is blinded until the spell ends."
						+ "\nSubtract each creature's hit points from the total before moving on to the creature with the next lowest hit points."
						+ "\nA creature's hit points must be equal to or less than the remaining total for that creature to be affected.", true);
                colour = Color.RGBtoHSB(RNG.nextInt(256), RNG.nextInt(256), RNG.nextInt(256), colour);
                break;
            case 3:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Faerie_Fire");
                builder.addField("Faerie Fire", "Each object in a 20-foot cube within range is outlined by blue, green, or violet light (your choice)."
						+ "\nAny creature in the area when the spell is cast is also outlined in light if it fails a Dexterity saving throw."
						+ "\nFor the duration, objects and affected creatures shed dim light in a 10-foot radius."
						+ "\nAny attack roll against an affected creature or object has advantage if the attacker can see it, and the affected creature or object can't benefit from being invisible.", true);
                colour = Color.RGBtoHSB(223, 94, 255, colour);
                break;
            case 4:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/False_Life");
                builder.addField("False Life", "Bolstering your self with a necromantic facsimile of life, you gain 1d4+4 temporary hitpoints for the duration.", true);
                colour = Color.RGBtoHSB(49, 187, 82, colour);
                break;
            case 5:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Fog_Cloud");
                builder.addField("Fog Cloud", "You create a 20-foot-radius of fog centered on a point within range."
						+ "\nThe sphere spreads around corners, and its area is heavily obscured."
						+ "\nIt lasts for the duration or until a wind of moderate or greater speed (at least 10 miles per hour) disperses it.", true);
                colour = Color.RGBtoHSB(236, 236, 236, colour);
                break;
            case 6:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Jump");
                builder.addField("Jump", "You touch a creature, the creature’s jump distance is tripled until the spell ends.", true);
                colour = Color.RGBtoHSB(255, 255, 255, colour);
                break;
            case 7:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Magic_Missile");
                builder.addField("Magic Missle", "You create three glowing darts of magical force."
						+ "\nEach dart hits a creature of your choice that you can see within range."
						+ "\nThe darts all strike simultaneously and you can direct them to hit one creature or several.", true);
                colour = Color.RGBtoHSB(255, 255, 255, colour);
                break;
            case 8:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Thunderwave");
                builder.addField("Thunderwave", "A wave of thunderous force sweeps out from you."
						+ "\nEach creature in a 15-foot cube originating from you must make a Constitution saving throw."
						+ "\nOn a failed save, a creature takes 2d8 thunder damage and is pushed 10 feet away from you."
						+ "\nOn a successful save, the creature takes half as much damage and isn't pushed."
						+ "\n\nIn addition, unsecured objects that are completely within the area of effect are automatically pushed 10 feet away from you by the spell's effect, and the spell emits a thunderous boom audible out to 300 feet.", true);
                colour = Color.RGBtoHSB(200, 200, 0, colour);
                break;
            case 9:
		return reroll();

        }

        builder.setColor(Color.getHSBColor(colour[0], colour[1], colour[2]));
        return builder.build();
    }
	
public MessageEmbed getThree() {
        builder = new EmbedBuilder();
        float colour[] = {0,0,0};

        switch (RNG.nextInt(10)) {
            case 0:
                builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Blur");
                builder.addField("Blur", "Your body becomes blurred, shifting and wavering to all who can see you."
						+ "\nFor the duration, any creature has disadvantage on attack rolls against you."
						+ "\nAn attacker is immune to this effect if it doesn’t rely on sight, as with blindsight, or can see through illusions, as with truesight.", true);
                colour = Color.RGBtoHSB(200, 200, 200, colour);
                break;
            case 1:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Darkness");
                builder.addField("Darkness", "Magical darkness spreads from a point you choose within range to fill a 15-foot-radius sphere for the duration."
						+ "\nThe darkness spreads around corners. A creature with darkvision can’t see through this darkness, and nonmagical light can’t illuminate it."
						+ "\n\nIf the point you choose is on an object you are holding or one that isn’t being worn or carried, the darkness emanates from the object and moves with it."
						+ "\nCompletely covering the source of the darkness with an opaque object, such as a bowl or a helm, blocks the darkness."
						+ "\n\nIf any of this spell’s area overlaps with an area of light created by a spell of 2nd level or lower, the spell that created the light is dispelled.", true);
                colour = Color.RGBtoHSB(0, 0, 0, colour);
                break;
            case 2:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Enlarge/Reduce");
                builder.addField("Enlarge/Reduce", "You cause a creature or an object you can see within range to grow larger or smaller for the duration."
						+ "\n\nThis one is effectively two spells and does a lot, so make life easy and just click the link", true);
                colour = Color.RGBtoHSB(100, 100, 100, colour);
                break;
            case 3:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Gust_of_Wind");
                builder.addField("Gust of Wind", "A line of strong wind 60 feet long and 10 feet wide blasts from you in a direction you choose for the spell’s duration."
						+ "\nEach creature that starts its turn in the line must succeed on a Strength saving throw or be pushed 15 feet away from you in a direction following the line."
						+ "\nAny creature in the line must spend 2 feet of movement for every 1 foot it moves when moving closer to you."
						+ "\n\nThe gust disperses gas or vapor, and it extinguishes candles, torches, and similar unprotected flames in the area."
						+ "\n It causes protected flames, such as those of lanterns, to dance wildly and has a 50 percent chance to extinguish them."
						+ "\nAs a bonus action on each of your turns before the spell ends, you can change the direction in which the line blasts from you.", true);
                colour = Color.RGBtoHSB(200, 200, 200, colour);
                break;
            case 4:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Invisibility");
                builder.addField("Invisibility", "A creature you touch becomes invisible until the spell ends."
						+ "\nAnything the target is wearing or carrying is invisible as long as it is on the target's person."
						+ "\nThe spell ends for a target that attacks or casts a spell.", true);
                colour = Color.RGBtoHSB(255, 255, 255, colour);
                break;
            case 5:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Levitate");
                builder.addField("Levitate", "One creature or object of your choice that you can see within range rises vertically, up to 20 feet, and remains suspended there for the duration."
						+ "\nThe spell can levitate a target that weighs up to 500 pounds. An unwilling creature that succeeds on a Constitution saving throw is unaffected."
						+ "\n\nThe target can move only by pushing or pulling against a fixed object or surface within reach (such as a wall or a ceiling), which allows it to move as if it were climbing."
						+ "\nYou can change the target’s altitude by up to 20 feet in either direction on your turn."
						+ "\nIf you are the target, you can move up or down as part of your move. Otherwise, you can use your action to move the target, which must remain within the spell’s range."
						+ "\n\nWhen the spell ends, the target floats gently to the ground if it is still aloft.", true);
                colour = Color.RGBtoHSB(255, 255, 255, colour);
                break;
            case 6:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Melf%27s_Acid_Arrow");
                builder.addField("Melf's Acid Arrow", "A shimmering green arrow streaks toward a target within range and burst in a spray of acid. Make a ranged spell attack against the target."
						+ "\nOn a hit, the target takes 4d4 acid damage immediately and 2d4 acid damage at the end of its next turn."
						+ "\nOn a miss, the arrow splashes the target for half as much of the initial damage and no damage at the end of its next turn.", true);
                colour = Color.RGBtoHSB(50, 255, 50, colour);
                break;
            case 7:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Scorching_Ray");
                builder.addField("Scorching Ray", "You create three rays of fire and hurl them at targets within range. You can hurl them at one target or several."
						+ "\nMake a ranged spell attack for each ray. On a hit, the target takes 2d6 fire damage.", true);
                colour = Color.RGBtoHSB(150, 50, 0, colour);
                break;
            case 8:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Shatter");
                builder.addField("Shatter", "A sudden loud ringing noise, painfully intense, erupts from a point of your choice within range."
						+ "\nEach creature in a 10-foot-radius sphere centered on that point must make a Constitution saving throw."
						+ "\nA creature takes 3d8 thunder damage on a failed save, or half as much damage on a successful one."
						+ "\nA creature made of inorganic material such as stone or metal has disadvantage on this saving throw."
						+ "\n\nA nonmagical object that isn’t being worn or carried also takes the damage if it’s in the spell's area.", true);
                colour = Color.RGBtoHSB(150, 200, 50, colour);
                break;
            case 9:
		return reroll();
				
        }

        builder.setColor(Color.getHSBColor(colour[0], colour[1], colour[2]));
        return builder.build();
    }
	
	public MessageEmbed getFour() {
        builder = new EmbedBuilder();
        float colour[] = {0,0,0};

        switch (RNG.nextInt(10)) {
            case 0:
                builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Blink");
                builder.addField("Blink", "Roll a d20 at the end of each of your turns for the duration of the spell."
						+ "\nOn a roll of 11 or higher, you vanish from your current plane of existence and appear in the Ethereal Plane (the spell fails and the casting is wasted if you were already on that plane)."
						+ "\nAt the start of your next turn, and when the spell ends if you are on the Ethereal Plane, you return to an unoccupied space of your choice that you can see within 10 feet of the space you vanished from."
						+ "\nIf no unoccupied space is available within that range, you appear in the nearest unoccupied space (chosen at random if more than one space is equally near). You can dismiss this spell as an action."
						+ "\n\nWhile on the Ethereal Plane, you can see and hear the plane you originated from, which is cast in shades of gray, and you can’t see anything there more than 60 feet away."
						+ "\nYou can only affect and be affected by other creatures on the Ethereal Plane. Creatures that aren’t there can’t perceive you or interact with you, unless they have the ability to do so.", true);
                colour = Color.RGBtoHSB(0, 0, 0, colour);
                break;
            case 1:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Fear");
                builder.addField("Fear", "You project a phantasmal image of a creature’s worst fears."
						+ "\nEach creature in a 30-foot cone must succeed on a Wisdom saving throw or drop whatever it is holding and become frightened for the duration."
						+ "\nWhile frightened by this spell, a creature must take the Dash action and move away from you by the safest available route on each of its turns, unless there is nowhere to move."
						+ "\nIf the creature ends its turn in a location where it doesn’t have line of sight to you, the creature can make a Wisdom saving throw. On a successful save, the spell ends for that creature.", true);
                colour = Color.RGBtoHSB(0, 0, 0, colour);
                break;
            case 2:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Feign_Death");
                builder.addField("Feign Death", "You touch a willing creature and put it into a cataleptic state that is indistinguishable from death."
						+ "\nFor the spell’s duration, or until you use an action to touch the target and dismiss the spell, the target appears dead to all outward inspection and to spells used to determine the target’s status."
						+ "\nThe target is blinded and incapacitated, and its speed drops to 0. The target has resistance to all damage except psychic damage."
						+ "\nIf the target is diseased or poisoned when you cast the spell, or becomes diseased or poisoned while under the spell’s effect, the disease and poison have no effect until the spell ends.", true);
                colour = Color.RGBtoHSB(0, 0, 0, colour);
                break;
            case 3:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Fireball");
                builder.addField("Fireball", "A bright streak flashes from your pointing finger to a point you choose within range and then blossoms with a low roar into an explosion of flame."
						+ "\nEach creature in a 20-foot-radius sphere centered on that point must make a dexterity saving throw."
						+ "\nA target takes 8d6 fire damage on a failed save, or half as much damage on a successful one."
						+ "\nThe fire spreads around corners. It ignites flammable objects in the area that aren't being worn or carried.", true);
                colour = Color.RGBtoHSB(200, 50, 0, colour);
                break;
            case 4:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Fly");
                builder.addField("Fly", "You touch a willing creature. The target gains a flying speed of 60 feet for the duration."
						+ "\nWhen the spell ends the target falls if it is still aloft, unless it can stop the fall.", true);
                colour = Color.RGBtoHSB(0, 0, 0, colour);
                break;
            case 5:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Gaseous_Form");
                builder.addField("Gaseous Form", "You transform a willing creature you touch, along with everything it’s wearing and carrying, into a misty cloud for the duration."
						+ "\nThe spell ends if the creature drops to 0 hit points. An incorporeal creature isn’t affected."
						+ "\n\nWhile in this form, the target’s only method of movement is a flying speed of 10 feet. The target can enter and occupy the space of another creature."
						+ "\nThe target has resistance to nonmagical damage, and it has advantage on Strength, Dexterity, and Constitution saving throws."
						+ "\nThe target can pass through small holes, narrow openings, and even mere cracks, though it treats liquids as though they were solid surfaces."
						+ "\nThe target can't fall and remains hovering in the air even when stunned or otherwise incapacitated."
						+ "\n\nWhile in the form of a misty cloud, the target can’t talk or manipulate objects, and any objects it was carrying or holding can’t be dropped, used, or otherwise interacted with. The target can’t attack or cast spells.", true);
                colour = Color.RGBtoHSB(0, 0, 0, colour);
                break;
            case 6:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Lightning_Bolt");
                builder.addField("Lightning Bolt", "A stroke of lightning forming a line 100 feet long and 5 feet wide blasts out from you in a direction you choose."
						+ "\nEach creature in the line must make a Dexterity saving throw. A creature takes 8d6 lightning damage on a failed save, or half as much damage on a successful one."
						+ "\nThe lightning ignites flammable objects in the area that aren’t being worn or carried.", true);
                colour = Color.RGBtoHSB(150, 150, 0, colour);
                break;
            case 7:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Sleet_Storm");
                builder.addField("Sleet Storm", "Until the spell ends, freezing rain and sleet fall in a 20-foot-tall cylinder with a 40-foot radius centered on a point you choose within range."
				+ "\nThe area is heavily obscured, and exposed flames in the area are doused."
				+ "\n\nThe ground in the area is covered with slick ice, making it difficult terrain."
				+ "\nWhen a creature enters the spell’s area for the first time on a turn or starts its turn there, it must make a Dexterity saving throw. On a failed save, it falls prone."
				+ "\n\nIf a creature is concentrating in the spell’s area, the creature must make a successful Constitution saving throw against your spell save DC or lose concentration.", true);
                colour = Color.RGBtoHSB(0, 0, 150, colour);
                break;
            case 8:
				builder.setTitle("Link to Wiki", "http://engl393-dnd5th.wikia.com/wiki/Stinking_Cloud");
                builder.addField("Stinking Cloud", "You create a 20-foot-radius sphere of yellow, nauseating gas centered on a point within range."
						+ "\nThe cloud spreads around corners, and its area is heavily obscured. The cloud lingers in the air for the duration."
						+ "\n\nEach creature that is completely within the cloud at the start of its turn must make a Constitution saving throw against poison."
						+ "\nOn a failed save, the creature spends its action that turn retching and reeling. Creatures that don't need to breathe or are immune to poison automatically succeed on this saving throw."
						+ "\nA moderate wind (at least 10 miles per hour) disperses the cloud after 4 rounds. A strong wind (at least 20 miles per hour) disperses it after 1 round.", true);
                colour = Color.RGBtoHSB(50, 255, 50, colour);
                break;
            case 9:
		return reroll();
				
        }

        builder.setColor(Color.getHSBColor(colour[0], colour[1], colour[2]));
        return builder.build();
    }
        private MessageEmbed reroll(){
            float colour[] = {0,0,0};
            builder.setTitle("Reroll");
            builder.addField("Reroll", "Roll twice more, ignoring this result."
                    + "\nChoose one.", true);
            colour = Color.RGBtoHSB(0, 0, 50, colour);
            
            return builder.build();
        }
    
}
