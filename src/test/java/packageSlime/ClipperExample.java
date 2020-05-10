package packageSlime;

import ffmpeg.Clipper;

public class ClipperExample extends Clipper<String> {

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public ClipperExample start() {
        super.start();
        return this;
    }
}
