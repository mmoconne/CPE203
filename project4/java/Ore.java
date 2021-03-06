import java.util.List;

import processing.core.PImage;

final class Ore extends ActivitiedEntity {

    private static final int BLOB_ANIMATION_MAX = 150;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final String BLOB_KEY = "blob";


    public Ore(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, actionPeriod, images);
    }

    public void executeActivity(EventScheduler scheduler, Activity activity) {
        WorldModel world = activity.world;
        ImageStore imageStore = activity.imageStore;

        Point pos = getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(getId() + BLOB_ID_SUFFIX,
                pos, getActionPeriod() / BLOB_PERIOD_SCALE, BLOB_ANIMATION_MIN + Functions.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),  imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActivity(scheduler, world, imageStore);
    }

    public <R> R accept(EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
