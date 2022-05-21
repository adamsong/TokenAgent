# TokenAgent

This is a java agent designed to replace the token used by the ATLauncher with one supplied by the user.

Usage: 

1. Change your executable to the jar from the [ATLauncher Download Page](https://atlauncher.com/downloads) 
2. Place the TokenAgent.jar into the same directory.
3. Take script for your platform from the scripts directory, place it in this directory as well. Note, this was developed and tested on windows, I am not sure if the .sh script works, but it should be close, and prs to fix it are welcome.
4. Replace the old token placeholder with the value of [`CURSEFORGE_CORE_API_KEY`](https://github.com/ATLauncher/ATLauncher/blob/master/src/main/java/com/atlauncher/constants/Constants.java#L75). Single quotes are needed to escape any $ in the token
5. Replace the new token placeholder with your token. You can request one [from the curseforge core website](https://console.curseforge.com/?#/signup) or [any other source](https://www.reddit.com/r/feedthebeast/comments/utbt62/i_made_a_basic_curseforge_modpack_installer/), though that isn't officially supported.
6. To allow powershell to run your script via double click, replace the target in the shortcut to the executable with `powershell.exe -command "& 'C:\the\path to\the\script.ps1'"`

This project is is no way affiliated with ATLauncher, I just wanted a way to modify the token that didn't invovled notepad++ opening binary files.
