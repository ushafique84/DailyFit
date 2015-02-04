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
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "Military Press", R.drawable.military_press_exercise));
        addItem(new DummyItem("2", "Flat Bench Press", R.drawable.flat_barbell_bench_press));
        addItem(new DummyItem("3", "Weighted Squats", R.drawable.weighted_squats));
        addItem(new DummyItem("4", "Lat Pulldowns", R.drawable.lat_pulldowns));
        addItem(new DummyItem("5", "Weighted Lunges", R.drawable.weighted_lunges));
        addItem(new DummyItem("6", "Barbell Curls", R.drawable.barbell_curl));
        addItem(new DummyItem("7", "Tricep Pushdowns", R.drawable.triceps_push_down));
        addItem(new DummyItem("8", "Planks", R.drawable.planks));

        //addItem(new DummyItem("2", "Strength Training", R.id.strengthTrainingWorkout));
        //addItem(new DummyItem("3", "Item 3"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String workout_name;
        public int resourceID;

        public DummyItem(String id, String workout_type, int resourceID) {
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
