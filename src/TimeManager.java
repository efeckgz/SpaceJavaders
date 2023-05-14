public class TimeManager {
    // THIS IS A WORK IN PROGRESS !!!
    // A class to manage time. DeltaTime is used to make sure that the speed at which
    // game objects travel is constant - regardless of the frame rate. This is used to
    // convert the in game speed measurements from units per frame to units per second.
    private static long lastFrameTime = System.nanoTime();

    public static float getDeltaTime() {
        long currentFrameTime = System.nanoTime();
        float deltaTime = (currentFrameTime - lastFrameTime) / 1_000_000_000.0f; // Convert nanoseconds to seconds
        lastFrameTime = currentFrameTime;

        return deltaTime;
    }
}
