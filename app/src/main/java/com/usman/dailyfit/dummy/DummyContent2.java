package com.usman.dailyfit.dummy;

import com.usman.dailyfit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
*/
 public class DummyContent2 {

    /**
     * An array of sample (dummy) items.
    */
    public static List<DummyItem2> ITEMS = new ArrayList<DummyItem2>();

    /**
     * A map of sample (dummy) items, by ID.
    */
    public static Map<String, DummyItem2> ITEM_MAP = new HashMap<String, DummyItem2>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem2("1", "Lunges", R.drawable.Body_Weight_Lunge));
        addItem(new DummyItem2("2", "Squats", R.drawable.bodyweight_squats));
        addItem(new DummyItem2("3", "Chin-ups", R.drawable.chinups));
        addItem(new DummyItem2("4", "Sit-ups", R.drawable.Core_Full_Sit_Ups));
        addItem(new DummyItem2("5", "Dips", R.drawable.dips));
        addItem(new DummyItem2("6", "Inverted Row", R.drawable.inverted_row));
        addItem(new DummyItem2("7", "Pull-ups", R.drawable.pullups));
        addItem(new DummyItem2("8", "Push-ups", R.drawable.pushups));
    }

    private static void addItem(DummyItem2 item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
    */
    public static class DummyItem2 {
        public String id;
        public String workout_name;
        public int resourceID;

        public DummyItem2(String id, String workout_type, int resourceID) {
            this.id = id;
            this.workout_name = workout_type;
            this.resourceID = resourceID;
        }

        @Override
        public String toString() {
            return workout_name;
        }
    }
}
