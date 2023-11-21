package ber.soundboard.berssoundboard;

public class MixerJsonObject {
    public PlayScene[] scenes;
    public SoundFiles[] listOfSounds;
    public MixerJsonObject(){
        scenes = new PlayScene[0];
        listOfSounds = new SoundFiles[0];
    }

    public static class SoundFiles{

        public int soundId;
        public String soundFile;

        public SoundFiles(int soundId, String soundFile) {
            this.soundId = soundId;
            this.soundFile = soundFile;
        }
    }

    public static class PlayScene{
        public PlayScene(String name, int sceneIdx){
            this.name = name;
            this.sceneIdx = sceneIdx;
        }
        public String name;

        public SoundConfiguration[] soundConfiguration;
        public int sceneIdx;


        public static class SoundConfiguration{
            public SoundConfiguration(int cueId, int soundId, String cueName, int startVol, SoundTransition[] transitions) {
                this.cueId = cueId;
                this.soundId = soundId;
                this.cueName = cueName;
                this.startVol = startVol;
                this.transitions = transitions;
            }

            public int cueId;
            public int soundId;
            public String cueName;
            public int startVol;
            public int loop;
            public SoundTransition[] transitions;


            public static class SoundTransition{
                public SoundTransition(int transitionId, String transitionType, int newVolume, int transitionTime) {
                    this.transitionId = transitionId;
                    this.transitionType = transitionType;
                    this.newVolume = newVolume;
                    this.transitionTime = transitionTime;
                }

                public int transitionId;
                public String transitionType;
                public int newVolume;
                public int transitionTime;
            }
        }
    }
}
