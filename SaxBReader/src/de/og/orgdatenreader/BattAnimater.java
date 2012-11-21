package de.og.orgdatenreader;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.og.battanimation.AnimationList;
import de.og.battanimation.Item;
import de.og.battanimation.LevelList;

public class BattAnimater {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		// Package
		// readAnimation();
		writeAnimation();
	}

	public static void readAnimation() throws JAXBException {
		final JAXBContext jc = JAXBContext.newInstance("de.og.battanimation");
		final Unmarshaller unmarshaller = jc.createUnmarshaller();

		final LevelList levelList = (LevelList) unmarshaller.unmarshal(new File("./xml/stat_sys_battery_charge_circle.xml"));
		final List<Item> levelItems = levelList.getItem();

		for (final Item it : levelItems) {
			System.out.println("LevelItem: " + it.getMaxLevel());
			final AnimationList anim = it.getAnimationList();
			final List<Item> animtems = anim.getItem();
			for (final Item anit : animtems) {
				System.out.println("   Drawable: " + anit.getDrawable());
			}

		}
	}

	public static void writeAnimation() throws JAXBException {

		final LevelList levelList = new LevelList();
		final List<Item> levelItems = levelList.getItem();

		for (int i = 0; i <= 101; i++) {
			// Creating LevelIem
			final Item it = new Item();
			it.setMaxLevel(i);

			// setting animationlist
			final AnimationList anim = new AnimationList();
			anim.setOneshot(false);
			it.setAnimationList(anim);

			// creating drawable Items
			final List<Item> drawableItems = anim.getItem();

			// setting fist animation png
			final Item mainit = new Item();
			mainit.setDuration(2000);
			mainit.setDrawable("@drawable/stat_sys_battery_circle_" + i);
			drawableItems.add(mainit);

			// adding the charge png's
			for (int j = 1; j <= 100; j++) {
				final Item anit = new Item();
				anit.setDuration(80);
				anit.setDrawable("@drawable/stat_sys_battery_circle_charge_anim" + j);
				drawableItems.add(anit);
			}

			// adding the level item
			levelItems.add(it);
		}

		final JAXBContext jc = JAXBContext.newInstance("de.og.battanimation");
		final Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(levelList, new File("./android/stat_sys_battery_charge_circle.xml"));

	}
}
