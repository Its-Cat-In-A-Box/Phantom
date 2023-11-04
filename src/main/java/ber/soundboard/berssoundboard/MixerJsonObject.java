package ber.soundboard.berssoundboard;

public class MixerJsonObject {
    public PlayScene[] scenes;

    public static class PlayScene{
        public PlayScene(String name, int sceneIdx){
            this.name = name;
            this.sceneIdx = sceneIdx;
        }
        public String name;
        public SoundFiles[] listOfSounds;
        public SoundConfiguration[] soundConfiguration;
        public int sceneIdx;

        public class SoundFiles{
            public int songId;
            public String songFile;
        }
        public class SoundConfiguration{
            public int cueId;
            public int songId;
            public String cueName;
            public int startVol;
            public SoundTransition[] transitions;

            public class SoundTransition{
                public int transitionId;
                public String transitionType;
                public int newVolume;
                public int transitionTime;
            }
        }
    }
}
