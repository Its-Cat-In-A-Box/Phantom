import json
import socket
import sys
import threading
import time

import jpysocket
import pygame

SOUND_LIST = {}
REQUEST_DATA_DIR = ""

pygame.init()
pygame.mixer.init(frequency=44100)
pygame.mixer.set_num_channels(8)
mixerChannel = [None, None, None, None, None, None, None, None]
pauseStatus = [False, False, False, False, False, False, False, False]
host = 'localhost'
port = 1223
s = socket.socket()
s.bind((host, port))
s.listen(128)
s.setblocking(True)
print("Socket Listening")
connection, adress = s.accept()


def main():
    global REQUEST_DATA_DIR, SOUND_LIST, connection

    print("Connected to Java UI client")
    confirmMessage = jpysocket.jpyencode("CLIENTCONNECTED")
    connection.send(confirmMessage)
    msgRecv = connection.recv(1024)
    confirmReceiveMessage = jpysocket.jpydecode(msgRecv)

    if confirmReceiveMessage == "SERVERCONNECTED":
        print("Client confirmed message, initializing")
        requestMessage = jpysocket.jpyencode("REQUEST_DATA_DIR")
        connection.send(requestMessage)
        msgRecv = connection.recv(1024)
        REQUEST_DATA_DIR = jpysocket.jpydecode(msgRecv)
        while True:
            msgRecv = connection.recv(1024)
            msgRecv = jpysocket.jpydecode(msgRecv)
            cmdArgs = msgRecv.split()
            cmdName = cmdArgs[0]
            if cmdName == "TERMINATION":
                sys.exit(0)
            elif cmdName == "LOADSOUND":  # Open the json file and load all the data
                jsonName = cmdArgs[1]
                with open(f"{REQUEST_DATA_DIR}\\{jsonName}\\{jsonName}.json", "r+") as file:
                    data = json.load(file)
                    if (n := data.get('listOfSounds')) is not None:
                        for items in n:
                            print(f"Loaded {items['soundId']} {items['soundFile']}")
                            soundData = pygame.mixer.Sound(f"{REQUEST_DATA_DIR}\\{jsonName}\\{items.get('soundFile')}")
                            SOUND_LIST.update({items['soundId']: soundData})
                msg = jpysocket.jpyencode("LOADED")
                connection.send(msg)
            elif cmdName == "SETVOL":
                setVol(int(cmdArgs[1]), cmdArgs[2])
            elif cmdName == "FADE":
                threading.Thread(target=fadeVolume, args=(int(cmdArgs[1]), int(cmdArgs[2]), int(cmdArgs[3]))).start()
            elif cmdName == "FADEOUT":
                pygame.mixer.Channel(int(cmdArgs[1])).fadeout(int(cmdArgs[2]))
            elif cmdName == "PLAY":
                print("Client requested to play sound " + msgRecv)
                togglePlayPause(cmdArgs[1])
            elif cmdName == "SOUNDADDTOMIXER":
                print("Client requested to add sound to mixer " + msgRecv)
                soundAddToMixer(cmdArgs[1], cmdArgs[2], cmdArgs[3], cmdArgs[4])
            elif cmdName == "STOP":
                print ("Client requested to stop mixer " + msgRecv)
                pygame.mixer.Channel(int(cmdArgs[1])).stop()
            elif cmdName == "FREE":
                print ("Client requested to free channel " + msgRecv)
                pygame.mixer.Channel(int(cmdArgs[1])).stop()
                mixerChannel[int(cmdArgs[1])] = None
                pauseStatus[int(cmdArgs[1])] = False



def setVol(channel, vol):
    print ("Client requested to change volume")
    print (f"{channel} | {vol}")
    if mixerChannel[channel] != None:
        mixerChannel[channel].update({"startingVol": vol})
    pygame.mixer.Channel(int(channel)).set_volume(float(vol) / 100)


def fadeVolume(channel, duration, newVol):
    curVol = round(float(pygame.mixer.Channel(channel).get_volume()) * 100)
    deltaVol = abs(curVol - newVol)
    stepSleep = duration / deltaVol / 1000
    if curVol > newVol:
        for i in range(curVol, newVol, -1):
            pygame.mixer.Channel(channel).set_volume(i/100)
            time.sleep(stepSleep)
    else:
        for i in range(curVol, newVol, 1):
            pygame.mixer.Channel(channel).set_volume(i/100)
            time.sleep(stepSleep)


def togglePlayPause(channel):
    channel = int(channel)
    print(pygame.mixer.Channel(channel).get_sound())
    if pygame.mixer.Channel(channel).get_sound() is None:  # If the sound is not assigned to the channel
        print("Sound is unassigned")
        if (mixerChannel[int(channel)]) is None:
            return
        pauseStatus[channel] = False
        soundId = mixerChannel[channel].get('soundId')

        soundData = SOUND_LIST.get(int(soundId))
        loops = mixerChannel[int(channel)].get("loops")
        startingVol = mixerChannel[int(channel)].get("startingVol")
        fadeIn = mixerChannel[int(channel)].get("fadeIn")
        if fadeIn:
            fadeInMs = mixerChannel[int(channel)].get("fadeInMs")
            pygame.mixer.Channel(channel).set_volume(float(startingVol) / 100)
            pygame.mixer.Channel(channel).play(soundData, int(loops), fade_ms=int(fadeInMs))
        else:
            pygame.mixer.Channel(channel).set_volume(float(startingVol) / 100)
            pygame.mixer.Channel(channel).play(soundData, int(loops))
    else:  # If a sound is assigned, but probably paused
        if pauseStatus[channel] == False:
            print("busy")
            pygame.mixer.Channel(channel).pause()
            pauseStatus[channel] = True
        else:
            print("not busy")
            pygame.mixer.Channel(channel).unpause()
            pauseStatus[channel] = False


def soundAddToMixer(channel, soundId, loops, startingVol):
    mixerChannel[int(channel)] = {"soundId": soundId,
                                  "loops": loops,
                                  "startingVol": startingVol,
                                  "fadeIn": False,
                                  }

if __name__ == '__main__':
    main()
